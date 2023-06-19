package priyopujonggo.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuranTranslationEdition {
    private String identifier;
    private String language;
    private String name;
    private String englishName;
    private String format;
    private String type;
    private String direction;
}
