package priyopujonggo.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuranTranslationSurah {
    private int number;
    private String name;
    private String englishName;
    private String englishNameTranslation;
    private int numberOfAyahs;
    private String revelationType;
}
