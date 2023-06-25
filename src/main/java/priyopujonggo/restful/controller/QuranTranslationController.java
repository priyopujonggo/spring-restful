package priyopujonggo.restful.controller;


import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import priyopujonggo.restful.entity.User;
import priyopujonggo.restful.model.QuranTranslationResponse;
import priyopujonggo.restful.repository.UserRepository;
import priyopujonggo.restful.entity.Log;
import priyopujonggo.restful.repository.LogRepository;




@RestController
public class QuranTranslationController {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    public QuranTranslationController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    private Logger logger = LoggerFactory.getLogger(QuranTranslationController.class);
    

    @GetMapping(
        path = "/api/{verse}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getTranslation(
        @PathVariable("verse") String verse,
        @RequestHeader("Accept") String acceptHeader,
        @RequestHeader("X-API-TOKEN") String apiToken,
        @RequestHeader("X-USER-ID") String email
    ) {
    
        User user = userRepository.findByEmail(email);
        if (user == null || !apiToken.equals(user.getToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        String url = "https://api.alquran.cloud/v1/ayah/" + verse + "/id.indonesian";
        RestTemplate restTemplate = new RestTemplate();
        QuranTranslationResponse translationResponse = restTemplate.getForObject(url, QuranTranslationResponse.class);
        logger.info("User {} accessed /api/{} and retrieved translation", email, verse);
        Log log = new Log();
        log.setId(UUID.randomUUID().toString());
        log.setEmail(email);
        log.setAyahSurah(verse);
        log.setDatetime(LocalDateTime.now());
        logRepository.save(log);

        return ResponseEntity.ok(translationResponse);
    }



}
