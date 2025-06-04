package ua.corporate.avgust.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.corporate.avgust.dto.UserDTO;
import ua.corporate.avgust.model.User;
import ua.corporate.avgust.service.UserService;

@Component
public class UserDTOValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserDTOValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userToValidate = (UserDTO) target;
        checkIfUserExist(userToValidate, errors);
        checkPasswordsMatch(userToValidate, errors);
    }

    private void checkIfUserExist(UserDTO userToValidate, Errors errors) {
        if (userService.readByUsername(userToValidate.getUsername()).isPresent()) {
            errors.rejectValue("username", "",
                    "User з таким username вже існує");
        }
    }

    private void checkPasswordsMatch(UserDTO userToValidate, Errors errors) {
        if (!(userToValidate.getPassword().equals(userToValidate.getConfirmPassword())
        )) {
            errors.rejectValue("password", "", "Паролі не співпадають");
        }
    }
}
