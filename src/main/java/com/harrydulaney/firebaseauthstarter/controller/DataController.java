package com.harrydulaney.firebaseauthstarter.controller;

import com.harrydulaney.firebaseauthstarter.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/data")
public class DataController {

    private DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }


    @GetMapping("data")
    public Map<String, Object> getBasic() {
        Map<String, Object> model = new HashMap<>();
        model.put("content", "Secure Message sent Back!");
        return model;
    }
}