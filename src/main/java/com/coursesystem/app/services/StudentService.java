package com.coursesystem.app.services;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Student;
import com.coursesystem.app.payload.forms.SocioEconomicForm;
import com.coursesystem.app.payload.forms.StudentForm;

public interface StudentService {
    
    public Student findById(Long id) throws nonExistentIdException;
    
    public Student chargeFormData(StudentForm studentForm) throws nonExistentIdException;

    public Student chargeSEFormData(SocioEconomicForm seForm, Long id) throws nonExistentIdException;
    
    public Student changeScholarshipStatus(Long id, String status) throws nonExistentIdException;

    public Student update(StudentForm studentForm, Long studentId) throws nonExistentIdException;
}
