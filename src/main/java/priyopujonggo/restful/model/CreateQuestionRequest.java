package priyopujonggo.restful.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateQuestionRequest {
    @NotBlank
    @Size( max = 200)
    private String questionTitle;

    @Size( max = 200)
    private String option1;

    @Size( max = 200)
    private String option2;

    @Size( max = 200)
    private String option3;

    @Size( max = 200)
    private String option4;

    @Size( max = 200)
    private String rightAnswer;
    
    @Size( max = 100)
    private String difficultyLevel;

    @Size(max = 100)
    private String category;
    
}
