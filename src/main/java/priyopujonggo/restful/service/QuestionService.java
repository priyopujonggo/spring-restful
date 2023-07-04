package priyopujonggo.restful.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import priyopujonggo.restful.entity.Question;
import priyopujonggo.restful.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();


    }
}
