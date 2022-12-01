package org.ionov.springcourses.controllers;

import org.ionov.springcourses.dao.BookDAO;
import org.ionov.springcourses.dao.PersonDAO;
import org.ionov.springcourses.models.Person;
import org.ionov.springcourses.utill.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDAO personDAO;
    private BookDAO bookDAO;
    private PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", bookDAO.getListByIdOccupier(id));
        return "people/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id ,
                         Model model) {
        if (bookDAO.getListByIdOccupier(id).isEmpty()) {
            personDAO.delete(id);
            return "redirect:/people";
        } else {
            model.addAttribute("errorDel","Перед удалением пользователя он должен сдать все книги");
            model.addAttribute("person", personDAO.show(id));
            model.addAttribute("books", bookDAO.getListByIdOccupier(id));
            return "people/show";
        }
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }


}
