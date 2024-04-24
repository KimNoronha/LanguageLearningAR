import com.example.arimagerecognizer.RegisterActivity;
import com.example.arimagerecognizer.LoginActivity;

import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegisterActivityTest {


    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void registerUser_PasswordsDoNotMatch() {
        String email = "user@example.com";
        String password = "password123";
        String confirmPassword = "password321";

        boolean result = RegisterActivity.passwordValidation(password, confirmPassword);

        // Assert
        assertFalse("Checking if the passwords match", result);
    }


    @Test
    public void registerUser_EmptyEmail() {
        String email = "";

        boolean result = LoginActivity.validateUsername(email);

        // Assert
        assertFalse("Email is empty", result);
    }

    @Test
    public void registerUser_InvalidEmail() {
        String email = "useremail.com";

        boolean result = LoginActivity.validateUsername(email);

        // Assert
        assertFalse("Email is invalid", result);
    }

    @Test
    public void registerUser_ValidEmail() {
        String email = "user@email.com";

        boolean result = LoginActivity.validateUsername(email);

        // Assert
        assertTrue("Email is valid", result);
    }

    @Test
    public void registerUser_EmptyPassword() {
        String email = "";
        String password = "";
        String confirmPassword = "password123";

        boolean result = LoginActivity.validatePassword(password);

        // Assert
        assertFalse("Password is empty", result);
    }

    @Test
    public void registerUser_PasswordTooShort() {

        String password = "pass";

        boolean result = LoginActivity.validatePassword(password);

        // Assert
        assertFalse("Password is too short", result);
    }

    @Test
    public void registerUser_ValidPasswordsButFailsOtherCriteria() {

        String password = "passwordnew";

        // Assuming there are other criteria for password validation
        boolean result = LoginActivity.validatePassword(password);

        // Assert
        assertFalse("Password meets length but fails other criteria", result);
    }

    @Test
    public void validateUsername_MultipleAtSymbols() {
        String email = "invalid@@email.com";
        assertFalse("Email with multiple '@' should be invalid", LoginActivity.validateUsername(email));
    }

    @Test
    public void validateUsername_EmailsWithSpecialCharacters() {
        String email = "user+mailbox/department=shipping@example.com";;

        assertFalse("Email with multiple '@' should be invalid", LoginActivity.validateUsername(email));
    }






//

}
