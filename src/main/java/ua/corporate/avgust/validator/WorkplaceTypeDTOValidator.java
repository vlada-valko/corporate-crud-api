package ua.corporate.avgust.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.corporate.avgust.dto.UserDTO;
import ua.corporate.avgust.dto.WorkplaceTypeDTO;
import ua.corporate.avgust.model.WorkplaceType;
import ua.corporate.avgust.service.WorkplaceTypeService;

@Component
public class WorkplaceTypeDTOValidator implements Validator {
    private final WorkplaceTypeService workplaceTypeService;
@Autowired
    public WorkplaceTypeDTOValidator(WorkplaceTypeService workplaceTypeService) {
        this.workplaceTypeService = workplaceTypeService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return WorkplaceTypeDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WorkplaceType workplaceType = (WorkplaceType) target;
        if (workplaceTypeService.readByName(workplaceType.getName()) != null
        ) {
            errors.rejectValue("name", "",
                    "Локація/тип роботи з такою назвою вже існує");
        }
    }
    private void checkIfUserExist(WorkplaceTypeDTO workplaceTypeToValidate, Errors errors) {
        if (workplaceTypeService.readByName(workplaceTypeToValidate.getName()).isPresent()) {
            errors.rejectValue("name", "",
                    "Тип/місце роботи з такою назвою вже існує");
        }
    }
}
