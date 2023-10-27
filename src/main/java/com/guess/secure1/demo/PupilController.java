package com.guess.secure1.demo;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guess.secure1.user.Pupil;
import com.guess.secure1.user.Teacher;
import com.guess.secure1.user.PupilRepository;
import com.guess.secure1.user.TeacherRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping({""})
public class PupilController {

    private final PupilRepository pupilRepository;


    @Autowired
    public PupilController(PupilRepository pupilRepository) {
        this.pupilRepository = pupilRepository;
    }



    @GetMapping("/students/list")
    public List<Pupil> getAllPupils() {
        return (List<Pupil>) pupilRepository.findAll();
    }

    @PostMapping("/students/add-student")
    Pupil createPupil( @Valid @RequestBody Pupil pupil) {

        return pupilRepository.save(pupil);

    }

    @PatchMapping("/students/update/{pupilId}")
    public Pupil updateArticle(
            @PathVariable (value = "pupilId") Long pupilId,
            @Valid @RequestBody Pupil pupilRequest) {


        return pupilRepository.findById(pupilId).map(pupil -> {
            pupil.setFirstname(pupilRequest.getFirstname());
            pupil.setLastname(pupilRequest.getLastname());
            pupil.setPhone(pupilRequest.getPhone());
            pupil.setEmail(pupilRequest.getEmail());
            pupil.setAge(pupilRequest.getAge());
            return pupilRepository.save(pupil);
        }).orElseThrow(() -> new IllegalArgumentException("PupilId " + pupilId + "not found"));
    }

    @GetMapping("/students/{pupilId}")
    public Pupil getPupil(@PathVariable Long pupilId) {

        Optional<Pupil> p = pupilRepository.findById(pupilId);
        return p.get();
    }

    @DeleteMapping("/students/delete/{pupilId}")
    public Pupil deletePupil(@PathVariable (value = "pupilId") Long pupilId) {
        return pupilRepository.findById(pupilId).map(pupil -> {
            pupilRepository.delete(pupil);
            return pupil;
        }).orElseThrow(() -> new IllegalArgumentException("Pupil not found with id " + pupilId));
    }
}

