package ua.corporate.avgust.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.corporate.avgust.dto.DepartmentDTO;
import ua.corporate.avgust.model.Department;
import ua.corporate.avgust.service.DepartmentService;

@Component
public class DepartmentDTOValidator implements Validator {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentDTOValidator(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DepartmentDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DepartmentDTO departmentToValidate = (DepartmentDTO) target;
        checkIfDepartmentExists(departmentToValidate, errors);

    }
    private void checkIfDepartmentExists (DepartmentDTO departmentToValidate, Errors errors) {
        if (departmentService.readByName(departmentToValidate.getName()).isPresent()
        ) {
            errors.rejectValue("name", "",
                    "Департамент з такою назвою вже існує");
        }
    }
}
