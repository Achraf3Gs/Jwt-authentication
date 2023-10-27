package com.guess.secure1.user;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.guess.secure1.user.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

}

