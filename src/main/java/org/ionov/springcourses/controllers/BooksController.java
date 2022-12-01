package org.ionov.springcourses.controllers;

import org.ionov.springcourses.dao.BookDAO;
import org.ionov.springcourses.dao.PersonDAO;
import org.ionov.springcourses.models.Book;
import org.ionov.springcourses.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/library")
@Controller
public class BooksController {
    private BookDAO bookDAO;
    private PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",bookDAO.index());
        return "library/index";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book",new Book());
        return "library/new";
    }
    @GetMapping("/{id}")
    //Получим одного человека по id з DAO  передадим на отображение в представление
    public String show(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.show(id);
        model.addAttribute("book", book);
        if(book.getOccupier()==-1) {
            model.addAttribute("people", personDAO.index());
            model.addAttribute("person",new Person());
        }
        else{
            model.addAttribute("owner",personDAO.show(bookDAO.show(id).getOccupier()));
        }
        return "library/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "library/new";
        bookDAO.save(book);
        return "redirect:/library";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model , @PathVariable("id") int id){
        model.addAttribute("book",bookDAO.show(id));
        return "library/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/library";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book ,
                         BindingResult bindingResult,
                         @PathVariable("id")int id){

        if(bindingResult.hasErrors()) {
            return "people/edit";
        }
        bookDAO.update(id,book);
        return "redirect:/library";
    }

    @PatchMapping("/{id}/updateUser")
    public String updateUser(@PathVariable("id") int id,
                             @ModelAttribute("person") Person person){
        bookDAO.updateUser(id,person.getId());
        return "redirect:/library";
    }

    @PatchMapping("/{id}/freeBook")
    public String freeBook(@PathVariable("id") int id){
        bookDAO.freeBook(id);
        return "redirect:/library";
    }

}
