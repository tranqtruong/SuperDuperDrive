package dev.SuperDuperDrive.controller;

import dev.SuperDuperDrive.entity.File;
import dev.SuperDuperDrive.service.FileService;
import dev.SuperDuperDrive.util.FlashAttributeUtil;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public String uploadFile(Authentication authentication,
            RedirectAttributes redirectAttributes,
            @RequestParam MultipartFile fileUpload) throws IOException {
        String email = authentication.getName();
        String errorMessage = "";
        if (fileUpload == null) {
            errorMessage = "file empty!";
        } else if (fileService.isFileAlreadyExists(fileUpload.getOriginalFilename(), email)) {
            errorMessage = "file already exists.";
        }
        if (!errorMessage.equals("")) {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Failed.", errorMessage);
            return "redirect:/home";
        }

        if (fileService.storeFile(fileUpload, email) <= 0) {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Failed.", "Access denied!");
        } else {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Success.", "Yoo hoo!");
        }

        return "redirect:/home";
    }

    @ResponseBody
    @GetMapping("/file/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(Authentication authentication,
            @PathVariable Integer fileId) {
        String email = authentication.getName();
        File file = fileService.getFile(fileId, email);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable Integer fileId,
            RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        if (fileService.deleteFile(fileId, email) > 0) {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Delete Success.", "Yoo hoo!");
        } else {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Delete Failed!.", "Oops!");
        }
        return "redirect:/home";
    }

}
