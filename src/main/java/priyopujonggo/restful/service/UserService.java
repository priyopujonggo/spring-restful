package priyopujonggo.restful.service;


import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import priyopujonggo.restful.entity.User;
import priyopujonggo.restful.model.RegisterUserRequest;
import priyopujonggo.restful.model.UpdateUserRequest;
import priyopujonggo.restful.model.UserResponse;
import priyopujonggo.restful.repository.UserRepository;
import priyopujonggo.restful.security.BCrypt;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterUserRequest request){
        validationService.validate(request);

        if(userRepository.existsById(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);

    }

    public UserResponse get(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
    
    @Transactional
    public UserResponse update(User user, UpdateUserRequest request){
        validationService.validate(request);

        if(Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }

        if(Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);

        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

     public boolean isTokenValid(String email, String apiToken) {
        User user = userRepository.findByEmail(email);
        return user != null && apiToken.equals(user.getToken());
    }
}
