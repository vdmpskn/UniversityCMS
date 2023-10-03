package ua.foxminded.pskn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.foxminded.pskn.domain.school.School;
import ua.foxminded.pskn.domain.school.SchoolService;

@RestController
@RequestMapping("/")
public class SchoolController {


    private final SchoolService schoolService;

    @Autowired
    public SchoolController(final SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<?> kek(@RequestBody School school) {
        return ResponseEntity.ok(schoolService.save(school));
    }
}
