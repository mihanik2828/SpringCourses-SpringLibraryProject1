package org.ionov.springcourses.utill;

import org.ionov.springcourses.dao.PersonDAO;
import org.ionov.springcourses.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // посмоtреть есть ли человек с таким же name в БД
        if (personDAO.show(person.getName()).isPresent()) {
            errors.rejectValue("email", "","This email is already taken");
        }
    }
}
