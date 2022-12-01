package org.ionov.springcourses.dao;

import org.ionov.springcourses.models.Book;
import org.ionov.springcourses.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name,year) VALUES(?,?)",
                person.getName(),person.getYear());
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional show(String name){
         return jdbcTemplate.query("SELECT * FROM Person WHERE name = ?" , new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }


    public void update(int id , Person updatedPerson){
        jdbcTemplate.update("UPDATE person SET name=? , author=?, birthYear=? WHERE id=?",
                updatedPerson.getName(),updatedPerson.getYear(),id);
    }


    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?",id);
    }

    public List<Book> getBooks(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE occupier=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
