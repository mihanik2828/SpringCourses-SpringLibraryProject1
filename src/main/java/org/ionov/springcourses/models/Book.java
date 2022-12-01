package org.ionov.springcourses.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Название кнги должно быть не пустым")
    @Size(min=2,message = "Название книги должно содержать более 2-х символов")
    private String name;
    @NotEmpty(message = "Имя автора должно быть не пустым")
    @Size(min=2,message = "Размер имени автора должен содержать более 2-х символов")
    private String author;
    private int year;

    private int occupier;

    public int getOccupier() {
        return occupier;
    }

    public void setOccupier(int occupier) {
        this.occupier = occupier;
    }

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.occupier = -1;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
