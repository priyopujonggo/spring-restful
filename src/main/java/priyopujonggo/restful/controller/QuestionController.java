package priyopujonggo.restful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import priyopujonggo.restful.entity.Question;
import priyopujonggo.restful.model.CreateQuestionRequest;
import priyopujonggo.restful.model.QuestionResponse;
import priyopujonggo.restful.model.WebResponse;
import priyopujonggo.restful.service.QuestionService;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @PostMapping(
        path = "add",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<QuestionResponse> create(@RequestBody CreateQuestionRequest request){
        QuestionResponse questionResponse = questionService.create(request);
        return WebResponse.<QuestionResponse>builder().data(questionResponse).build();
    }

    @GetMapping(path = "allQuestions")
    public List<Question> getAllQuestions(){
         
        return questionService.getAllQuestions();
    }

    @GetMapping(path = "category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category){

        return questionService.getQuestionsByCategory(category);
    }

    

    
}
