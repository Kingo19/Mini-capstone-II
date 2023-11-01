package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class UserService {
   private String url;
   RestTemplate restTemplate = new RestTemplate();

    public UserService(String url) {
        this.url = url;
    }
    public double getAccountBalance(int userId){
        return restTemplate.getForObject(url + "/account_balance/" + userId, Double.class);

    }
}
