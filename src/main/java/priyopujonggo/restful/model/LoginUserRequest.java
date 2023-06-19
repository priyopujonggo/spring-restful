package priyopujonggo.restful.model;

import jakarta.validation.constraints.Email;
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
public class LoginUserRequest {
    @NotBlank
    @Size(max = 100)
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;
}
