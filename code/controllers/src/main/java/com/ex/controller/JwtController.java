package com.ex.controller;

import java.awt.print.Printable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.models.JwtRequest;
import com.ex.models.JwtResponse;
import com.ex.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) {
    	try {
        return jwtService.createJwtToken(jwtRequest);
    	}catch(Exception ex){
    			System.out.println(ex);
    			 throw new UsernameNotFoundException("User not found with username: " + jwtRequest.getUserName());
    	}
    	}
}