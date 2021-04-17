package com.coursesystem.app.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.coursesystem.app.enums.EUserRole;
import com.coursesystem.app.models.Role;
import com.coursesystem.app.models.User;
import com.coursesystem.app.payload.forms.LoginForm;
import com.coursesystem.app.payload.forms.SingupForm;
import com.coursesystem.app.payload.response.JwtResponse;
import com.coursesystem.app.payload.response.MessageResponse;
import com.coursesystem.app.repository.RoleRepository;
import com.coursesystem.app.repository.UserRepository;
import com.coursesystem.app.security.jwt.JwtUtils;
import com.coursesystem.app.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// @CrossOrigin must have an IP to be called by the frontend
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetailsImpl.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

        return ResponseEntity.ok(
            new JwtResponse(jwt, 
				userDetailsImpl.getId(), 
				userDetailsImpl.getUsername(), 
				userDetailsImpl.getEmail(), 
				roles)
        );
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SingupForm singupForm) {
		if (userRepo.existsByUsername(singupForm.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("[ERROR] Username already taken"));
		}

		if (userRepo.existsByEmail(singupForm.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("[ERROR] Email in user"));
		}

		User user = new User(singupForm.getUsername(), singupForm.getEmail(), encoder.encode(singupForm.getPassword()));

		Set<String> setRole = singupForm.getRole();
		Set<Role> roles = new HashSet<>();

		if (setRole == null) {
			Role userRole = roleRepository.findByUserRole(EUserRole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("[ERROR] Role not found"));
			roles.add(userRole);
		} else {
			setRole.forEach(role -> {
				switch (role.toLowerCase()) {
				case "admin":
					Role admin = roleRepository.findByUserRole(EUserRole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("[ERROR] Role not found"));
					roles.add(admin);

					break;
				case "agent":
					Role agent = roleRepository.findByUserRole(EUserRole.ROLE_AGENT)
							.orElseThrow(() -> new RuntimeException("[ERROR] Role not found"));
					roles.add(agent);

					break;
				default:
					Role student = roleRepository.findByUserRole(EUserRole.ROLE_STUDENT)
							.orElseThrow(() -> new RuntimeException("[ERROR] Role not found"));
					roles.add(student);
				}
			});
		}
        
		user.setRole(roles);
		userRepo.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}