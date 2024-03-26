package dev.SuperDuperDrive.controller;

import dev.SuperDuperDrive.entity.Credential;
import dev.SuperDuperDrive.service.CredentialService;
import dev.SuperDuperDrive.util.FlashAttributeUtil;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/credential")
    public String saveCredential(Authentication authentication,
            RedirectAttributes redirectAttributes,
            @Valid Credential credential,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Access denied!", "Something went wrong.");
            return "redirect:/home";
        }

        credential.setEmail(authentication.getName());
        credentialService.saveCredential(credential);

        FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Success.", "Yoo hoo!");

        return "redirect:/home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(Authentication authentication,
            RedirectAttributes redirectAttributes,
            @PathVariable Integer credentialId) {
        if (credentialService.deleteCredential(credentialId, authentication.getName()) > 0) {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Delete Success.", "Yoo hoo!");
        } else {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Delete Failed!.", "Oops!");
        }

        return "redirect:/home";
    }
}
