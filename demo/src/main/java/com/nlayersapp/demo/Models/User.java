package com.nlayersapp.demo.Models;

import org.springframework.hateoas.RepresentationModel;

/**
 * Users
 */
public class User extends RepresentationModel<User> {

    
    public User(final String name, final Long salary) {
        this.name = name;
        this.salary = salary;
    }

    /* Name property private field, getter & setter */
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(final String value) {
        this.name = value;
    }

    /* Salary property private field, getter & setter */
    private Long salary;

    public Long getSalary() {
        return this.salary;
    }

    public void setSalary(final Long value) {
        this.salary = value; 
    } 

}