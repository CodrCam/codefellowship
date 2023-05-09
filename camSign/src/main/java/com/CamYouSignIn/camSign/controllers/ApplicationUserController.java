package com.CamYouSignIn.camSign.controllers;

import com.CamYouSignIn.camSign.models.ApplicationUser;
import com.CamYouSignIn.camSign.repositories.ApplicationUserRepository;
import com.CamYouSignIn.camSign.services.SiteUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    SiteUserDetailsServiceImpl siteUserDetailsService;

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("newUser", new ApplicationUser());
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView createNewUser(@ModelAttribute ApplicationUser applicationUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return new RedirectView("/signup");
        }
        ApplicationUser newUser = siteUserDetailsService.saveUser(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/login"); // Change this line
    }


    @GetMapping("/welcome")
    public String showWelcomePage(Principal principal, Model model) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "welcome";
    }
}
