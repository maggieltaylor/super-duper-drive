package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/notes")
public class NotesController {
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping
    public String addNote(Note note, Model model, Principal principal) {
        int rowsAdded = notesService.addNote(note, principal.getName());
        if (rowsAdded < 0) {
            model.addAttribute("dbError", true);
        }
        return "result";
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable("id") Long id, Model model) {
        int rowsDeleted = notesService.deleteNote(id);
        if (rowsDeleted < 0) {
            model.addAttribute("dbError", true);
        }
        return "result";
    }
}
