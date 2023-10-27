package com.guess.secure1.demo;


import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guess.secure1.user.Teacher;
import com.guess.secure1.user.TeacherRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping({"/teachers","/hom*"})
@CrossOrigin(origins="*")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/list")
    public List<Teacher> getAllTeachers() {
        return (List<Teacher>) teacherRepository.findAll();
    }

    @PostMapping("/add")
    public Teacher createTeacher(@Valid @RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PutMapping("/{teacherId}")
    public Teacher updateTeacher(@PathVariable Long teacherId, @Valid @RequestBody Teacher teacherRequest) {
        return teacherRepository.findById(teacherId).map(teacher -> {
            teacher.setName(teacherRequest.getName());
            teacher.setEmail(teacherRequest.getEmail());
            teacher.setAddress(teacherRequest.getAddress());
            return teacherRepository.save(teacher);
        }).orElseThrow(() -> new IllegalArgumentException("TeacherId " + teacherId + " not found"));
    }


    @DeleteMapping("/{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long teacherId) {
        return teacherRepository.findById(teacherId).map(teacher -> {
            teacherRepository.delete(teacher);
            System.out.println(teacherId);
            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new IllegalArgumentException("TeacherId " + teacherId + " not found"));

    }

    @GetMapping("/{teacherId}")
    public Teacher getTeacher(@PathVariable Long teacherId) {

        Optional<Teacher> p = teacherRepository.findById(teacherId);

        return p.get();

    }
}


