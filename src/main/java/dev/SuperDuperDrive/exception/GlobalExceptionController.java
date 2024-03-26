package dev.SuperDuperDrive.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import dev.SuperDuperDrive.util.FlashAttributeUtil;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadFileSizeExceededException(MaxUploadSizeExceededException exception,
            RedirectAttributes redirectAttributes) {
        FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Upload Failed!", "File too large.");
        return "redirect:/home";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException exception) {
        return "error";
    }

}
