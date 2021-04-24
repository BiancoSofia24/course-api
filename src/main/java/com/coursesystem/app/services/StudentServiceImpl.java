package com.coursesystem.app.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.coursesystem.app.enums.EStatus;
import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Role;
import com.coursesystem.app.models.Student;
import com.coursesystem.app.models.User;
import com.coursesystem.app.payload.forms.SocioEconomicForm;
import com.coursesystem.app.payload.forms.StudentForm;
import com.coursesystem.app.repository.RoleRepository;
import com.coursesystem.app.repository.StudentRepository;
import com.coursesystem.app.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private RoleRepository roleRepo;

    public Iterable<Student> findAll() {
        return this.studentRepo.findAll();
    }

    public Student save(Student student) {
        this.studentRepo.save(student);
        return student;
    }

    public void delete(Student student) {
        this.studentRepo.delete(student);
    }

    @Override
    public Student findById(Long id) throws nonExistentIdException {
        Optional<Student> optionalStudent = studentRepo.findById(id);

        if (Optional.empty().equals(optionalStudent)) {
            throw new nonExistentIdException("The given id doesn't exist");
        }

        Student student = optionalStudent.get();
        return student;
    }

    @Override
    public Student chargeFormData(StudentForm studentForm) throws nonExistentIdException {
        Optional<User> optionalUser = userRepo.findById(studentForm.getUserId());

        if (Optional.empty().equals(optionalUser)) {
            throw new nonExistentIdException("The given id doesn't exist");
        }

        Student student = new Student();
        User user = optionalUser.get();
        student.setUser(user);
        student.setName(studentForm.getName());
        student.setLastname(studentForm.getLastname());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String birthdayString = studentForm.getBirthday();
        Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
        log.error(birthdayString);

        try {
            Date birthday = dateFormat.parse(birthdayString);
            log.error("date", birthday);
            student.setBirthday(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        student.setGender(studentForm.getGender());
        student.setLocation(studentForm.getLocation());

        Optional<Role> role = roleRepo.findById(3L);
        role.get().getUserRole();
        Set<Role> set = new HashSet<Role>();
        set.add(role.get());
        user.setRole(set);

        return student;
    }

    // Method is not setting properly the socioeconomic info of a student
    @Override
    public Student chargeSEFormData(SocioEconomicForm seForm, Long id) throws nonExistentIdException {
        Optional<Student> optionalStudent = studentRepo.findById(id);

        if (Optional.empty().equals(optionalStudent)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Student student = optionalStudent.get();
        try {
            if (seForm.getStudying() == 1) {
                student.setStudying(true);

            } else if (seForm.getStudying() == 0) {
                student.setStudying(false);
            }

            if (seForm.getWorking() == 1) {
                student.setWorking(true);
                student.setIncome(seForm.getIncome());

            } else if (seForm.getWorking() == 0) {
                student.setWorking(false);
                student.setIncome(0F);
            }

            if (seForm.getFamilyInCharge() == 1) {
                student.setFamilyInCharge(true);

            } else if (seForm.getFamilyInCharge() == 0) {
                student.setFamilyInCharge(false);
            }

            student.setDependents(seForm.getDependents());

            if (student.getScholarshipStatus() == null) {
                student.setScholarshipStatus(EStatus.AWAITING_APPROVAL);
            } else {
                student.setScholarshipStatus(student.getScholarshipStatus());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return student;
    }

    @Override
    public Student changeScholarshipStatus(Long id, String status) throws nonExistentIdException {
        Optional<Student> optionalStudent = studentRepo.findById(id);

        if (Optional.empty().equals(optionalStudent)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Student student = optionalStudent.get();

        switch (status.toUpperCase()) {
        case "APPROVED":
            student.setScholarshipStatus(EStatus.APPROVED);
            break;
        case "REJECTED":
            student.setScholarshipStatus(EStatus.REJECTED);
            break;
        case "CANCELLED":
            student.setScholarshipStatus(EStatus.CANCELLED);
            break;
        default:
            break;
        }

        return student;
    }

    @Override
    public Student update(StudentForm studentForm, Long id) throws nonExistentIdException {
        Optional<Student> optionalStudent = studentRepo.findById(id);

        if (Optional.empty().equals(optionalStudent)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Student student = optionalStudent.get();
        student.setName(studentForm.getName());
        student.setLastname(studentForm.getLastname());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String birthdayString = studentForm.getBirthday();

        try {
            Date birthday = dateFormat.parse(birthdayString);
            student.setBirthday(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        student.setGender(studentForm.getGender());
        student.setLocation(studentForm.getLocation());

        return student;
    }

}
