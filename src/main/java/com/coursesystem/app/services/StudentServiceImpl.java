package com.coursesystem.app.services;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Student;
import com.coursesystem.app.payload.forms.StudentForm;

import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Override
    public Student findById(Long id) throws nonExistentIdException {
        return null;
    }

    @Override
    public Student chargeFormData(StudentForm studentForm, Student student) throws nonExistentIdException {
        return null;
    }
    
}
