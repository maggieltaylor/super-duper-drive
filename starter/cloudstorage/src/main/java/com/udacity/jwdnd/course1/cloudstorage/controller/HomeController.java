package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FilesService filesService;
    private final NotesService notesService;
    private final CredentialsService credentialsService;

    public HomeController(FilesService filesService, NotesService notesService, CredentialsService credentialsService) {
        this.filesService = filesService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public String viewHomepage(Model model, Principal principal) {
        model.addAttribute("files", filesService.getFiles(principal.getName()));
        model.addAttribute("notes", notesService.getNotes(principal.getName()));
        model.addAttribute("creds", credentialsService.getCreds(principal.getName()));

        return "home";
    }
}
