package com.jcpv.example.entity;

import javax.persistence.*;

/**
 * Created by JanCarlo on 29/06/2017.
 */
@Entity
@Table(name= "PERSONS")
public class Person {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name= "NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
