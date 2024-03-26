package dev.SuperDuperDrive.controller;

import dev.SuperDuperDrive.entity.User;
import dev.SuperDuperDrive.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String goSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute User user, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.doesUserExist(user.getEmail())) {
            model.addAttribute("errorSignup", "The email already exists.");
            return "register";
        }

        if (userService.createUser(user) > 0) {
            redirectAttributes.addFlashAttribute("successSignup", true);
            return "redirect:/login";
        }

        model.addAttribute("errorSignup", "There was an error signing you up. Please try again.");
        return "register";
    }
}
