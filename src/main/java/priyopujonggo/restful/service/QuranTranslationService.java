package priyopujonggo.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import priyopujonggo.restful.model.QuranTranslationResponse;

@Service
public class QuranTranslationService {
    private String BASE_URL = "https://api.alquran.cloud/v1/ayah/";

    private final RestTemplate restTemplate;

    @Autowired
    public QuranTranslationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public QuranTranslationResponse getTranslation(String verse) {
        String url = BASE_URL + verse + "/id.indonesian";
        return restTemplate.getForObject(url, QuranTranslationResponse.class);
    }
}