package application.controllers;

import application.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController{

    private QuestionRepository questionRepo;

    @Autowired
    public QuestionController(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }
}