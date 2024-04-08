package Lab4.authentication;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCredentials {
    @NotNull(message = "Username must not be null or empty")
    @NotEmpty(message = "Username must not be null or empty")
    private String username;
    @NotNull(message = "Password must not be null or empty")
    @NotEmpty(message = "Password must not be null or empty")
    private String password;
}
