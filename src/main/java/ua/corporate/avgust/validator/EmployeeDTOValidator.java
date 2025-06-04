package ua.corporate.avgust.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.corporate.avgust.dto.EmployeeDTO;
import ua.corporate.avgust.model.Employee;
import ua.corporate.avgust.service.EmployeeService;

import java.time.LocalDate;

@Component
public class EmployeeDTOValidator implements Validator {
    private final EmployeeService userService;
    @Autowired
    public EmployeeDTOValidator(EmployeeService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO employeeToValidate = (EmployeeDTO) target;
        checkIfEmployeeExist(employeeToValidate,errors);
        checkIfAgeIsCorrect(employeeToValidate,errors);
    }
    private void checkIfEmployeeExist (EmployeeDTO employeeToValidate, Errors errors) {
        if (userService.readByFullName(employeeToValidate.getFirstName()
                , employeeToValidate.getMiddleName()
                , employeeToValidate.getLastName()).isPresent()
        ) {
            errors.rejectValue("firstName", "",
                    "Співробітник з таким ПІБ вже існує");
        }
    }
    private void checkIfAgeIsCorrect (EmployeeDTO employeeToValidate, Errors errors) {
        if (employeeToValidate.getDateOfBirth() != null) {
        if (LocalDate.now().getYear() - employeeToValidate.getDateOfBirth().getYear() < 18 ||
                LocalDate.now().getYear() -  employeeToValidate.getDateOfBirth().getYear() > 80) {
            errors.rejectValue("dateOfBirth", "",
                    "Вік співробітника не може бути більшим за 80 років чи меншим за 18 років");
        }}
    }
}
