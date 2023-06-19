package priyopujonggo.restful.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import priyopujonggo.restful.entity.User;
import priyopujonggo.restful.repository.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ExtendWith(MockitoExtension.class)
class QuranTranslationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private QuranTranslationController quranTranslationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(quranTranslationController).build();
    }

    @Test
    void getTranslationSuccess() throws Exception {
        String verse = "1:1";
        String token = "4c19453b-c258-44cd-adcb-40e7f93bee96";
        String email = "priopujonggo@gmail.com";

        User user = new User();
        user.setToken(token);
        when(userRepository.findByEmail(email)).thenReturn(user);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/{verse}", verse)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-API-TOKEN", token)
                .header("X-USER-ID", email)
        )
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    void getTranslationUnauthorized() throws Exception {
        String verse = "1:1";
        String token = "token";
        String email = "priopujonggo@gmail.com";


        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/{verse}", verse)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-API-TOKEN", token)
                .header("X-USER-ID", email)
        )
        .andExpect(status().isUnauthorized())
        .andDo(print());
    }
}