package com.example.Journal.models;

import javax.persistence.*;

@Entity
@Table(name = "stack")
public class Stack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stack_id")
    private Integer stackId;

    @Column(name = "name")
    private String name;

    @Column(name = "stack")
    private String stack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStackId() {
        return stackId;
    }

    public void setStackId(Integer stackId) {
        this.stackId = stackId;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}
