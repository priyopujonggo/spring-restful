package priyopujonggo.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuranTranslationData {
    private int number;
    private String text;
    private QuranTranslationEdition edition;
    private QuranTranslationSurah surah;
    private int numberInSurah;
    private int juz;
    private int manzil;
    private int page;
    private int ruku;
    private int hizbQuarter;
    private boolean sajda;
}
