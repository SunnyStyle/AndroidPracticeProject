package com.amigo.ai.rxjavatest;

import java.util.List;

/**
 * Created by wf on 18-3-29.
 */

public class Student {
    public String name;
    public List<Course> courses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
