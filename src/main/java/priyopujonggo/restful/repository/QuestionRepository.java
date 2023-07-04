package priyopujonggo.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import priyopujonggo.restful.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String>{
    
}
