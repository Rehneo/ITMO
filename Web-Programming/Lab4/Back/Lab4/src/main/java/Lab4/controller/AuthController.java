package Lab4.controller;


import Lab4.authentication.AuthException;
import Lab4.authentication.UserCredentials;
import Lab4.authentication.utils.PasswordService;
import Lab4.authentication.utils.TokenService;
import Lab4.entity.User;
import Lab4.repository.UserRepository;
import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

import static Lab4.authentication.AuthStatus.*;
import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;

@Path("/auth")
public class AuthController {
    @EJB
    private UserRepository userRepository;


    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid UserCredentials userCredentials){
        String username = userCredentials.getUsername();
        System.out.println(username);
        String password = userCredentials.getPassword();
        System.out.println(password);
        try {
            if(!validateCredentials(username, password)){
                throw new AuthException(INVALID_CREDENTIALS);
            }
            List<User> users = userRepository.getByUsername(username);
            if(users.isEmpty()){
                throw new AuthException(USER_DOES_NOT_EXIST);
            }
            User user = users.get(0);
            if(!PasswordService.checkPassword(password, user.getPasswordHash())){
                throw new AuthException(WRONG_PASSWORD);
            }
            String token = TokenService.generateToken(32, username);
            user.setToken(token);
            userRepository.update(user);
            return Response.ok().entity(new Gson().toJson(Collections.singletonMap(
                    "token", token
            ))).build();
        }catch (Exception e){
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Valid UserCredentials userCredentials){
        String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
        try {
            if(!validateCredentials(username, password)){
                throw new AuthException(INVALID_CREDENTIALS);
            }
            List<User> users = userRepository.getByUsername(username);
            if(!users.isEmpty()){
                throw new AuthException(USER_ALREADY_EXISTS);
            }
            String passwordHash = PasswordService.encryptPassword(password);
            String token = TokenService.generateToken(32, username);
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPasswordHash(passwordHash);
            newUser.setToken(token);
            userRepository.save(newUser);
            return Response.ok().entity(new Gson().toJson(Collections.singletonMap(
                    "token", token
            ))).build();
        }catch (Exception e){
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @DELETE
    public Response logout(@HeaderParam("A-Token") String token){
        List<User> userList = userRepository.getByToken(token);
        if(userList != null && !userList.isEmpty()){
            User user = userList.get(0);
            user.setToken(null);
            userRepository.update(user);
        }
        return Response.ok().build();
    }
    private boolean validateCredentials(String username, String password){
        return (username != null) && (password != null) &&
                username.matches("^[0-9A-Za-z]{4,20}$") &&
                password.matches("^(?=.*?[0-9])(?=.*?[A-Za-z]).{6,32}$");
    }
}
