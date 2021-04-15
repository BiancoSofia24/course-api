package com.coursesystem.app.services;

import java.util.List;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.User;
import com.coursesystem.app.payload.forms.UserForm;

public interface UserService {

    public User findById(Long id) throws nonExistentIdException;

    public List<User> findAll();

    public User chargeFormData(UserForm userForm, User user);
}

