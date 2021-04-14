package com.coursesystem.app.services;

import java.util.List;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.forms.UserForm;
import com.coursesystem.app.models.User;

public interface UserService {

    public User findById(Long id) throws nonExistentIdException;

    public List<User> findAll();

    public User chargeFormData(UserForm userForm, User user);
}

