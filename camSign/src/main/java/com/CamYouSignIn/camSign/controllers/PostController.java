package com.CamYouSignIn.camSign.controllers;


import com.CamYouSignIn.camSign.models.ApplicationUser;
import com.CamYouSignIn.camSign.models.Post;
import com.CamYouSignIn.camSign.repositories.ApplicationUserRepository;
import com.CamYouSignIn.camSign.repositories.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/createNewPost")
    public String getCreatePostPage(Model model) {
        model.addAttribute("post", new Post());
        return "createPost";
    }


    @PostMapping("/createNewPost")
    public RedirectView createPost(@ModelAttribute Post post, BindingResult bindingResult, Model model, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return new RedirectView("/createNewPost");
        }
        ApplicationUser user = applicationUserRepository.findByUsername(authentication.getName());
        post.setApplicationUser(user);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return new RedirectView("/myprofile");
    }


    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest request, Model model, Exception ex) {
        HttpStatus status = getStatus(request);
        model.addAttribute("errorCode", status.value());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

