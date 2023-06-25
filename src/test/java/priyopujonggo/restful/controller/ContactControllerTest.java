package priyopujonggo.restful.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import priyopujonggo.restful.entity.Contact;
import priyopujonggo.restful.entity.User;
import priyopujonggo.restful.model.ContactResponse;
import priyopujonggo.restful.model.CreateContactRequest;
import priyopujonggo.restful.model.UpdateContactRequest;
import priyopujonggo.restful.model.WebResponse;
import priyopujonggo.restful.repository.ContactRepository;
import priyopujonggo.restful.repository.UserRepository;
import priyopujonggo.restful.security.BCrypt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setEmail("priopujonggo@mail.com");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Prio");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000);
        userRepository.save(user);
    }

    @Test
    void createContactBadRequest() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
            post("/api/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createContactSuccess() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Prio");
        request.setLastName("Pujonggo");
        request.setEmail("priopujonggo@mail.com");
        request.setPhone("088886363736");

        mockMvc.perform(
            post("/api/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());
            assertEquals("Prio", response.getData().getFirstName());
            assertEquals("Pujonggo", response.getData().getLastName());
            assertEquals("priopujonggo@mail.com", response.getData().getEmail());
            assertEquals("088886363736", response.getData().getPhone());

            assertTrue( contactRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void getContactNotFound() throws Exception {
        mockMvc.perform(
            get("/api/contacts/2223434343")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getContactSuccess() throws Exception {
        User user = userRepository.findById("priopujonggo@mail.com").orElseThrow();

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("Prio");
        contact.setLastName("Pujonggo");
        contact.setEmail("priopujonggo@mail.com");
        contact.setPhone("088886363736");
        contactRepository.save(contact);

        mockMvc.perform(
            get("/api/contacts/"+contact.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());

            assertEquals(contact.getId(), response.getData().getId());
            assertEquals(contact.getFirstName(), response.getData().getFirstName());
            assertEquals(contact.getLastName(), response.getData().getLastName());
            assertEquals(contact.getEmail(), response.getData().getEmail());
            assertEquals(contact.getPhone(), response.getData().getPhone());
        });
    }

    @Test
    void updateContactBadRequest() throws Exception {
        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
            put("/api/contacts/1234")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateContactSuccess() throws Exception {
        User user = userRepository.findById("priopujonggo@mail.com").orElseThrow();

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("Prio");
        contact.setLastName("Pujonggo");
        contact.setEmail("priopujonggo@mail.com");
        contact.setPhone("088886363736");
        contactRepository.save(contact);

        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Fulan");
        request.setLastName("Bin Wahaboy");
        request.setEmail("fulanbinwahaboy@mail.com");
        request.setPhone("0886363755434");

        mockMvc.perform(
            put("/api/contacts/"+contact.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());
            assertEquals(request.getFirstName(), response.getData().getFirstName());
            assertEquals(request.getLastName(), response.getData().getLastName());
            assertEquals(request.getEmail(), response.getData().getEmail());
            assertEquals(request.getPhone(), response.getData().getPhone());

            assertTrue( contactRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void deleteContactNotFound() throws Exception {
        mockMvc.perform(
            delete("/api/contacts/2223434343")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void deleteContactSuccess() throws Exception {
        User user = userRepository.findById("priopujonggo@mail.com").orElseThrow();

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("Prio");
        contact.setLastName("Pujonggo");
        contact.setEmail("priopujonggo@mail.com");
        contact.setPhone("088886363736");
        contactRepository.save(contact);

        mockMvc.perform(
            delete("/api/contacts/"+contact.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
        });
    }

    @Test
    void searchNotFound() throws Exception {

        mockMvc.perform(
            get("/api/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());
            assertEquals(0, response.getData().size());
            assertEquals(0, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });
    }

    @Test
    void searchSuccess() throws Exception {
        User user = userRepository.findById("priopujonggo@mail.com").orElseThrow();

        for (int i = 0; i < 100; i++) {
            Contact contact = new Contact();
            contact.setId(UUID.randomUUID().toString());
            contact.setUser(user);
            contact.setFirstName("Prio " + i);
            contact.setLastName("Pujonggo");
            contact.setEmail("priopujonggo@mail.com");
            contact.setPhone("9238423432");
            contactRepository.save(contact);
        }

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("name", "Prio")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("name", "Pujonggo")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("email", "mail.com")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("phone", "38423")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("phone", "38423")
                        .queryParam("page", "1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(0, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(1000, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });
    }
    
}
