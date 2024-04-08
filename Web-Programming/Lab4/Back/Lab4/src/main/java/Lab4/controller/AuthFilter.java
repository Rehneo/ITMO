package Lab4.controller;
import Lab4.authentication.AuthException;
import Lab4.authentication.AuthStatus;
import Lab4.authentication.Secured;
import Lab4.repository.UserRepository;
import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
    @EJB
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext context){
        try {
            String token = context.getHeaderString("A-Token");
            if(!verifyToken(token)){
                throw new AuthException(AuthStatus.INVALID_TOKEN);
            }
        }catch (Exception e){
            context.abortWith(Response.status(FORBIDDEN)
                    .build()
            );
        }
    }


    private boolean verifyToken(String token){
        return (token != null) &&
                (userRepository.getByToken(token) != null && !userRepository.getByToken(token).isEmpty());
    }
}
