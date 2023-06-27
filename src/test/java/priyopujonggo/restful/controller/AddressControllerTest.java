package priyopujonggo.restful.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import priyopujonggo.restful.entity.Address;
import priyopujonggo.restful.entity.Contact;
import priyopujonggo.restful.entity.User;
import priyopujonggo.restful.model.AddressResponse;
import priyopujonggo.restful.model.CreateAddressRequest;
import priyopujonggo.restful.model.UpdateAddressRequest;
import priyopujonggo.restful.model.WebResponse;
import priyopujonggo.restful.repository.AddressRepository;
import priyopujonggo.restful.repository.ContactRepository;
import priyopujonggo.restful.repository.UserRepository;
import priyopujonggo.restful.security.BCrypt;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setEmail("priopujonggo@mail.com");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Prio");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("test");
        contact.setUser(user);
        contact.setFirstName("Prio");
        contact.setLastName("Pujonggo");
        contact.setEmail("priopujonggo@mail.com");
        contact.setPhone("088886363736");
        contactRepository.save(contact);
    }

    @Test
    void createAddressBadRequest() throws Exception{
        CreateAddressRequest request = new CreateAddressRequest();
        request.setCountry("");
        mockMvc.perform(
            post("/api/contacts/test/addresses")
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
    void createAddressSuccess() throws Exception{
        CreateAddressRequest request = new CreateAddressRequest();
        request.setStreet("Jl. Sempu");
        request.setCity("Purbalingga");
        request.setProvince("Jawa Tengah");
        request.setCountry("Indonesia");
        request.setPostalCode("53393");
        mockMvc.perform(
            post("/api/contacts/test/addresses")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());
            assertEquals(request.getStreet(), response.getData().getStreet());
            assertEquals(request.getCity(), response.getData().getCity());
            assertEquals(request.getProvince(), response.getData().getProvince());
            assertEquals(request.getCountry(), response.getData().getCountry());
            assertEquals(request.getPostalCode(), response.getData().getPostalCode());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void getAddressNotFound() throws Exception{
        mockMvc.perform(
            get("/api/contacts/test/addresses/test")
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
    void getAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setContact(contact);
        address.setStreet("jalan");
        address.setCity("Purbalingga");
        address.setProvince("Jawa Tengah");
        address.setCountry("Indonesia");
        address.setPostalCode("53393");
        addressRepository.save(address);

        mockMvc.perform(
            get("/api/contacts/test/addresses/test")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());
            assertEquals(address.getId(), response.getData().getId());
            assertEquals(address.getStreet(), response.getData().getStreet());
            assertEquals(address.getCity(), response.getData().getCity());
            assertEquals(address.getProvince(), response.getData().getProvince());
            assertEquals(address.getCountry(), response.getData().getCountry());
            assertEquals(address.getPostalCode(), response.getData().getPostalCode());
        });
    }

    @Test
    void updateAddressBadRequest() throws Exception{
        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCountry("");
        mockMvc.perform(
            put("/api/contacts/test/addresses/test")
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
    void updateAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setContact(contact);
        address.setStreet("jalan");
        address.setCity("Purbalingga");
        address.setProvince("DKI");
        address.setCountry("Indonesia");
        address.setPostalCode("45393");
        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setStreet("Jl. Sempu");
        request.setCity("Purbalingga");
        request.setProvince("Jawa Tengah");
        request.setCountry("Indonesia");
        request.setPostalCode("53393");
        mockMvc.perform(
            put("/api/contacts/test/addresses/"+address.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());
            assertEquals(request.getStreet(), response.getData().getStreet());
            assertEquals(request.getCity(), response.getData().getCity());
            assertEquals(request.getProvince(), response.getData().getProvince());
            assertEquals(request.getCountry(), response.getData().getCountry());
            assertEquals(request.getPostalCode(), response.getData().getPostalCode());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void deleteAddressNotFound() throws Exception{
        mockMvc.perform(
            delete("/api/contacts/test/addresses/test")
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
    void deleteAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setContact(contact);
        address.setStreet("jalan");
        address.setCity("Purbalingga");
        address.setProvince("Jawa Tengah");
        address.setCountry("Indonesia");
        address.setPostalCode("53393");
        addressRepository.save(address);

        mockMvc.perform(
            delete("/api/contacts/test/addresses/test")
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
            assertFalse(addressRepository.existsById("test"));
        });
    }

    @Test
    void listAddressNotFound() throws Exception{
        mockMvc.perform(
            get("/api/contacts/salah/addresses")
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
    void listAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("test").orElseThrow();

        for (int i = 0;i < 5; i++){
            Address address = new Address();
            address.setId("test-" + i);
            address.setContact(contact);
            address.setStreet("jalan");
            address.setCity("Purbalingga");
            address.setProvince("Jawa Tengah");
            address.setCountry("Indonesia");
            address.setPostalCode("53393");
            addressRepository.save(address);
        }

        mockMvc.perform(
            get("/api/contacts/test/addresses")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<List<AddressResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                
            });
            assertNull(response.getErrors());
            assertEquals(5, response.getData().size());
            
        });
    }

}
