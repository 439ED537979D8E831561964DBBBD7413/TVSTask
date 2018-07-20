package com.sample.marmu.tvstask.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class UserModel implements Serializable, Comparable<UserModel> {
    private String name, designation, city, code, dateOfJoin;
    private int salary;

    public UserModel(String name, String designation, String city, String code, String dateOfJoin, int salary) {
        this.name = name;
        this.designation = designation;
        this.city = city;
        this.code = code;
        this.dateOfJoin = dateOfJoin;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getDateOfJoin() {
        return dateOfJoin;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public int compareTo(@NonNull UserModel f) {
        return Integer.compare(salary, f.salary);
    }
}
