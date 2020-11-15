package com.joush.domain;

import java.io.Serializable;
import java.util.List;


public class User implements Serializable {

    private int id;
    private String username;
    private String address;
    private String sex;
    private String birthday;



//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", address='" + address + '\'' +
//                ", sex='" + sex + '\'' +
//                ", birthday='" + birthday + '\'' +
//                '}';
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
