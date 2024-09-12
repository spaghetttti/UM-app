package com.example.um.Component;
import jakarta.persistence.*;

@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String acronym;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String responsiblePerson;

    // Constructors
    public Component() {}

    public Component(String acronym, String name, String responsiblePerson) {
        this.acronym = acronym;
        this.name = name;
        this.responsiblePerson = responsiblePerson;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
}