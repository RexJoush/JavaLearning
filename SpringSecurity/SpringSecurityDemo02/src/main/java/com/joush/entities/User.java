package com.joush.entities;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Rex Joush
 * @time 2021.08.05 21:08
 */
@Data
public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private int age;
    private String email;

}
