package dev.SuperDuperDrive.util;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class FlashAttributeUtil {
    public static void setFlashAttributes(RedirectAttributes redirectAttributes, boolean invalidForm, String title,
            String message) {
        redirectAttributes.addFlashAttribute("invalidForm", invalidForm);
        redirectAttributes.addFlashAttribute("errorTitle", title);
        redirectAttributes.addFlashAttribute("errorMessage", message);
    }
}
