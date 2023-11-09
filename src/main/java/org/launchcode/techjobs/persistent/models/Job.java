package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.*;
import org.launchcode.techjobs.persistent.models.Employer;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Job extends AbstractEntity{

//    @Id
//    @GeneratedValue
//    private int id;
//
//    private String name;

    @ManyToOne
    //@NotNull(message="Employer is required!") this not null caused my app to fail countless times!
    private Employer employer;

    @ManyToMany
    private List<Skill> skills = new ArrayList<>();




    // Initialize the id and value fields.
    public Job(Employer employer, List<Skill> skills) {
        //super();
        this.employer = employer;
        this.skills = skills;
    }

    public Job() {
    }

    // Getters and setters.
    
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

}
