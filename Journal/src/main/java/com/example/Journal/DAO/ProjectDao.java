package com.example.Journal.DAO;

public class ProjectDao {
    private String name;
    private String description;
    private String start_date;
    private String stack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStack() { return stack; }

    public void setStack(String stack) { this.stack = stack; }

    public String getStart_date() { return start_date; }

    public void setStart_date(String start_date) { this.start_date = start_date; }

}
