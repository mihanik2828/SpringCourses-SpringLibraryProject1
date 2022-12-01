package org.ionov.springcourses.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Person {

    private int id;

    @NotEmpty(message = "Имя должно быть не пустым")
    private String name;

    @Min(value=1900,message = "Год рождения должен быть позднее 1900-го года")
    @Max(value=2022,message = "Год рождения должен быть ранее 2023-го года")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int birthYear) {
        this.year = birthYear;
    }
}
