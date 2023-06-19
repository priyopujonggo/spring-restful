package priyopujonggo.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuranTranslationResponse {
    private int code;
    private String status;
    private QuranTranslationData data;
}
