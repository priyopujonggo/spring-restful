package priyopujonggo.restful.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import priyopujonggo.restful.entity.Question;
import priyopujonggo.restful.model.CreateQuestionRequest;
import priyopujonggo.restful.model.QuestionResponse;
import priyopujonggo.restful.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ValidationService validationService;
    
    public List<Question> getAllQuestions(){

        return questionRepository.findAll();

    }

    public List<Question> getQuestionsByCategory(String category){
        return questionRepository.findByCategory(category);
    }
    
    @Transactional
    public QuestionResponse create(CreateQuestionRequest request){
        validationService.validate(request);
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setQuestionTitle(request.getQuestionTitle());
        question.setOption1(request.getOption1());
        question.setOption2(request.getOption2());
        question.setOption3(request.getOption3());
        question.setOption4(request.getOption4());
        question.setRightAnswer(request.getRightAnswer());
        question.setDifficultyLevel(request.getDifficultyLevel());
        question.setCategory(request.getCategory());

        questionRepository.save(question);

        return toQuestionResponse(question);

    }

    private QuestionResponse toQuestionResponse(Question question){
        return QuestionResponse.builder()
                    .id(question.getId())
                    .questionTitle(question.getQuestionTitle())
                    .option1(question.getOption1())
                    .option2(question.getOption2())
                    .option3(question.getOption3())
                    .option4(question.getOption4())
                    .rightAnswer(question.getRightAnswer())
                    .difficultyLevel(question.getDifficultyLevel())
                    .category(question.getCategory())
                    .build();
    }
}
