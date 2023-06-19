package priyopujonggo.restful.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogResponse {
    
    private String id;

    private String email;

    private String ayahSurah;

    private LocalDateTime datetime;
    
}
