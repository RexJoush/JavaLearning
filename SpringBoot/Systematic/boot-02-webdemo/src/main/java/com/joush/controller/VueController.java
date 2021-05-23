package com.joush.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Rex Joush
 * @time 2021.04.29 19:15
 */

@CrossOrigin
@RestController
public class VueController {

    @GetMapping("/testGet/{id}")
    public String getMethod(@PathVariable("id") int id, @RequestParam("message") String message){
        System.out.println(id);
        System.out.println(message);

        return "getMethod";
    }

    @PostMapping("/testPost/{id}")
    public String postMethod(@PathVariable("id") int id, @RequestParam("message") String message){
        System.out.println(id);
        System.out.println(message);

        return "potMethod";
    }

    @PutMapping("/testPut/{id}")
    public String putMethod(@PathVariable("id") int id, @RequestBody Map<String, String> message){
        System.out.println(id);
        System.out.println(message.get("message"));

        return "putMethod";
    }

    @DeleteMapping("/testDelete/{id}")
    public String deleteMethod(@PathVariable("id") int id, @RequestParam("message") String message){
        System.out.println(id);
        System.out.println(message);

        return "deleteMethod";
    }
}
