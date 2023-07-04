package priyopujonggo.restful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import priyopujonggo.restful.entity.Question;
import priyopujonggo.restful.service.QuestionService;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping(path = "allQuestions")
    public List<Question> getAllQuestions(){
         
        return questionService.getAllQuestions();
    }
    
}
