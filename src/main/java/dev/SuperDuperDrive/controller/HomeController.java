package dev.SuperDuperDrive.controller;

import dev.SuperDuperDrive.entity.Credential;
import dev.SuperDuperDrive.entity.File;
import dev.SuperDuperDrive.entity.Note;
import dev.SuperDuperDrive.service.CredentialService;
import dev.SuperDuperDrive.service.FileService;
import dev.SuperDuperDrive.service.NoteService;
import dev.SuperDuperDrive.service.PagingService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private NoteService noteService;
    private PagingService pagingService;
    private CredentialService credentialService;
    private FileService fileService;

    public HomeController(NoteService noteService,
            PagingService pagingService,
            CredentialService credentialService,
            FileService fileService) {
        this.noteService = noteService;
        this.pagingService = pagingService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping("home")
    public String goHome(Model model, Authentication authentication,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String keyword) {
        if (!pagingService.isValidPageSize(pageSize)) {
            // throw new error
            return "redirect:/home";
        }

        String email = authentication.getName();
        int totalPages = noteService.calculateTotalPages(email, pageSize);
        if (!pagingService.isValidPageNumber(pageNumber, totalPages)) {
            // throw new error
            return "redirect:/home";
        }

        List<Note> notes = noteService.pagingNotes(email, pageNumber, pageSize);
        if (notes == null) {
            model.addAttribute("hasNoteData", false);
            return "home";
        }

        model.addAttribute("hasNoteData", true);
        model.addAttribute("notes", notes);
        model.addAttribute("pageSizes", pagingService.PAGE_SIZES);
        model.addAttribute("selectedPageSize", pageSize);

        model.addAttribute("pNumber", pageNumber);
        model.addAttribute("isFirst", pageNumber == 1);
        model.addAttribute("isLast", pageNumber == totalPages);

        int startPage = pagingService.calculateStartPage(pageNumber, totalPages);
        int endPage = pagingService.calculateEndPage(startPage, totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<Credential> credentials = credentialService.loadCredentials(email, keyword);
        model.addAttribute("credentials", credentials);

        List<File> files = fileService.getFiles(email);
        model.addAttribute("files", files);

        return "home";
    }

}
