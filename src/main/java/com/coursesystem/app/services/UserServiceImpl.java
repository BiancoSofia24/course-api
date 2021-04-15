package com.coursesystem.app.services;

import java.util.List;
import java.util.Optional;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.User;
import com.coursesystem.app.payload.forms.UserForm;
import com.coursesystem.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepo;

    public Optional<User> findByUsername(String username) {
        return this.userRepo.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return this.userRepo.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return this.existsByEmail(email);
    }

    public boolean existById(Long id) {
        return this.userRepo.existsById(id);
    }

    public User save(User user) {
        this.userRepo.save(user);
        return user;
    }

    @Override
    public User findById(Long id) throws nonExistentIdException {
        Optional<User> optionalUser = userRepo.findById(id);
        if (Optional.empty().equals(optionalUser)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }
        User user = optionalUser.get();
        return user;
    }

    public void delete(User user) {
        this.userRepo.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User chargeFormData(UserForm userForm, User user) {
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        return user;
    }
}
