package ua.corporate.avgust.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.corporate.avgust.dto.PositionDTO;
import ua.corporate.avgust.model.Department;
import ua.corporate.avgust.model.Position;
import ua.corporate.avgust.service.PositionService;

@Component
public class PositionDTOValidator implements Validator {
    private final PositionService positionService;

    @Autowired
    public PositionDTOValidator(PositionService positionService) {
        this.positionService = positionService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PositionDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PositionDTO positionToValidate = (PositionDTO) target;
        checkIfPositionExists(positionToValidate, errors);
    }
    private void checkIfPositionExists (PositionDTO positionToValidate, Errors errors) {
        if (positionService.readByName(positionToValidate.getName()).isPresent()
        ) {
            errors.rejectValue("name", "",
                    "Позиція з такою назвою вже існує");
        }
    }
}
