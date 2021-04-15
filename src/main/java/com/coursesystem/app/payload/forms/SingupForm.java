package com.coursesystem.app.payload.forms;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SingupForm {
    
    @NotNull
	@Size(min = 3, max = 20)
	private String username;

	@NotNull
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> role;
	// es un set porque el usuario no puede tener dos veces el mismo rol

	@NotNull
	@Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
}
