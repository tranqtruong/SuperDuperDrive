package dev.SuperDuperDrive.controller;

import dev.SuperDuperDrive.entity.Note;
import dev.SuperDuperDrive.service.NoteService;
import dev.SuperDuperDrive.util.FlashAttributeUtil;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String saveNote(Note note, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (note.getNotetitle().isBlank() || note.getNotedescription().isBlank()
                || note.getNotetitle().length() > 20 || note.getNotedescription().length() > 1000) {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Access denied!", "Something went wrong.");
            return "redirect:/home";
        }

        String email = authentication.getName();
        note.setEmail(email);
        noteService.saveNote(note);

        FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Success.", "Yoo hoo!");

        return "redirect:/home";
    }

    @GetMapping("/note/delete/{noteid}")
    public String deleteNote(Authentication authentication,
            @PathVariable Integer noteid,
            RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        if (noteService.deleteNote(noteid, email) > 0) {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Delete Success.", "Yoo hoo!");
        } else {
            FlashAttributeUtil.setFlashAttributes(redirectAttributes, true, "Delete Failed.", "haha");
        }

        return "redirect:/home";
    }
}
