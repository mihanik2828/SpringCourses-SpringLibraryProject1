package org.ionov.springcourses.dao;

import org.ionov.springcourses.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> getListByIdOccupier(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE occupier=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(name,author,year,occupier) VALUES(?,?,?,-1)",
                book.getName(),book.getAuthor(),book.getYear());
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id , Book updatedBook){
        jdbcTemplate.update("UPDATE book SET name=? , author=?, year=? WHERE id=?",
                updatedBook.getName(),updatedBook.getAuthor(),updatedBook.getYear(),id);
    }

    public void updateUser(int idBook , int idPerson){
        jdbcTemplate.update("UPDATE book SET occupier=? WHERE id=?",
                idPerson,idBook);
    }

    public void freeBook(int id){
        jdbcTemplate.update("UPDATE book SET occupier=-1 WHERE id=?",
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?",id);
    }

}
