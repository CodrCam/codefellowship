package com.CamYouSignIn.camSign.controllers;

import com.CamYouSignIn.camSign.models.ApplicationUser;
import com.CamYouSignIn.camSign.models.Post;
import com.CamYouSignIn.camSign.repositories.ApplicationUserRepository;
import com.CamYouSignIn.camSign.repositories.PostRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

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

        // Set the role
        applicationUser.setRole("ROLE_USER"); // Set to "ROLE_ADMIN" for admin users

        ApplicationUser newUser = siteUserDetailsService.saveUser(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");// Change this line maybe
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }



    @GetMapping("/welcome")
    public String showWelcomePage(Principal principal, Model model) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "welcome";
    }

    @GetMapping("/myprofile")
    public String showMyProfile(Principal principal, Model model) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "myprofile";
    }

    @GetMapping("/users/{id}")
    public String showUserProfile(@PathVariable("id") Long id, Model model) {
        ApplicationUser user = applicationUserRepository.findById(id).orElse(null);
        if (user != null) {
            model.addAttribute("user", user);
            return "userprofile";
        } else {
            return "error";
        }
    }

    @GetMapping("/createPost")
    public String showCreatePostForm(Model model) {
        model.addAttribute("newPost", new Post());
        return "createPost";
    }

    @PostMapping("/createPost")
    public RedirectView createNewPost(@ModelAttribute Post newPost, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return new RedirectView("/createPost");
        }
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        newPost.setApplicationUser(user);
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/myprofile/edit")
    public String showEditProfileForm(Principal principal, Model model) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "editProfile";
    }

    @PostMapping("/myprofile/edit")
    public RedirectView editProfile(@ModelAttribute ApplicationUser updatedUser, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return new RedirectView("/myprofile/edit");
        }
        ApplicationUser currentUser = applicationUserRepository.findByUsername(principal.getName());
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setDateOfBirth(updatedUser.getDateOfBirth());
        currentUser.setBio(updatedUser.getBio());
        applicationUserRepository.save(currentUser);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/admin/users/{id}/edit")
    public String showAdminEditProfileForm(@PathVariable("id") Long id, Model model) {
        ApplicationUser user = applicationUserRepository.findById(id).orElse(null);
        if (user != null) {
            model.addAttribute("user", user);
            return "adminEditProfile";
        } else {
            return "error";
        }
    }

    @PostMapping("/admin/users/{id}/edit")
    public RedirectView adminEditProfile(@PathVariable("id") Long id, @ModelAttribute ApplicationUser updatedUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new RedirectView("/admin/users/" + id + "/edit");
        }
        ApplicationUser user = applicationUserRepository.findById(id).orElse(null);
        if (user != null) {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setDateOfBirth(updatedUser.getDateOfBirth());
            user.setBio(updatedUser.getBio());
            applicationUserRepository.save(user);
            return new RedirectView("/users/" + id);
        } else {
            return new RedirectView("/error");
        }
    }
}
