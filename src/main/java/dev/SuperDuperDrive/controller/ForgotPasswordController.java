package dev.SuperDuperDrive.controller;

import dev.SuperDuperDrive.service.MailService;
import dev.SuperDuperDrive.service.TokenService;
import dev.SuperDuperDrive.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    private MailService mailService;
    private TokenService tokenService;
    private UserService userService;

    public ForgotPasswordController(MailService mailService, TokenService tokenService, UserService userService) {
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/reset-pass")
    public String sendLinkReset(@RequestParam String email, RedirectAttributes redirectAttributes) {
        if (!userService.doesUserExist(email)) {
            return "redirect:/login";
        }
        try {
            mailService.sendMail(email);
        } catch (MessagingException e) {
            System.out.println("Can't send mail");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Can't send mail");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        redirectAttributes.addFlashAttribute("successSendmail", true);
        return "redirect:/login";
    }

    @GetMapping("/reset-pass/{token}")
    public String resetPassword(@PathVariable String token, Model model) {
        String tokenStatus = tokenService.validateToken(token);
        if (tokenStatus.equals("Token is valid")) {
            model.addAttribute("tokenValid", true);
            model.addAttribute("token", token);
        } else if (tokenStatus.equals("Token has expired")) {
            model.addAttribute("tokenExpired", true);
        } else if (tokenStatus.equals("Token has been used")) {
            model.addAttribute("tokenUsed", true);
        } else {
            model.addAttribute("tokenInValid", true);
        }
        return "resetpassword";
    }

    @PostMapping("/reset-pwd")
    public String resetPwd(@RequestParam("newpassword") String newPassword,
            @RequestParam("confirmpassword") String confirmPassword,
            @RequestParam String token,
            @RequestParam(name = "sign-out-all", required = false) Boolean signOutAll,
            Model model) {
        String tokenStatus = tokenService.validateToken(token);

        if (tokenStatus.equals("Token is valid")) {
            if (newPassword.isBlank() || !newPassword.equals(confirmPassword)) {
                model.addAttribute("tokenValid", true);
                model.addAttribute("hasError", true);
                model.addAttribute("token", token);
            } else if (newPassword.equals(confirmPassword)) {
                String email = tokenService.getEmailFromToken(token);
                if (userService.doesUserExist(email)) {
                    userService.changePasswordUser(email, newPassword);
                    tokenService.markAsUsed(token);
                    model.addAttribute("successChangePass", true);
                } else {
                    model.addAttribute("tokenInValid", true);
                }
            } else {
                model.addAttribute("tokenInValid", true);
            }
        } else if (tokenStatus.equals("Token has expired")) {
            model.addAttribute("tokenExpired", true);
        } else if (tokenStatus.equals("Token has been used")) {
            model.addAttribute("tokenUsed", true);
        } else {
            model.addAttribute("tokenInValid", true);
        }
        return "resetpassword";
    }

}
