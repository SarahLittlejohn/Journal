package com.example.Journal.models;

import javax.persistence.*;

@Entity
@Table(name = "framework")
public class Framework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "framework_id")
    private Integer frameworkId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFrameworkId() {
        return frameworkId;
    }

    public void setFrameworkId(Integer frameworkId) {
        this.frameworkId = frameworkId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
