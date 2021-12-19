package com.movies.api.jwt;

import com.movies.api.support.LoginCache;
import com.movies.db.entity.User;
import com.movies.db.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;

@RestController
public class GoogleSSOController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @GetMapping(path = "/api/userinfo")
    public JwtResponse getUserInfo(Principal principal){
        String userName = (String) ((OAuth2AuthenticationToken)principal).getPrincipal().getAttributes().get("email");
        User user = jwtUserDetailsService.getUserByUsername(userName);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userName, "",
                new ArrayList<>());

        String token = jwtTokenUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUserName(userName);
        jwtResponse.setRole(user.getRole());
        jwtResponse.setToken(token);

        LoginCache.set(userName,token);

        return jwtResponse;
    }
}
