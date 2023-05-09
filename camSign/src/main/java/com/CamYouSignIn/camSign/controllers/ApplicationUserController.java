package com.CamYouSignIn.camSign.controllers;

import com.CamYouSignIn.camSign.models.ApplicationUser;
import com.CamYouSignIn.camSign.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationUserController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String firstName,
                         @RequestParam String lastName,
                         @RequestParam String dateOfBirth,
                         @RequestParam String bio) {

        String encodedPassword = passwordEncoder.encode(password);
        ApplicationUser newUser = new ApplicationUser(username, encodedPassword, firstName, lastName, dateOfBirth, bio);
        applicationUserRepository.save(newUser);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
