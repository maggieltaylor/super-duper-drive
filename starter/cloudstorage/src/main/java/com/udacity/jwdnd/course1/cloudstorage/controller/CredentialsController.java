package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/creds")
public class CredentialsController {
    private final CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping
    public String addCred(Credential credential, Model model, Principal principal) {
        int rowsAdded = credentialsService.addCred(credential, principal.getName());
        if (rowsAdded < 0) {
            model.addAttribute("dbError", true);
        }
        return "result";
    }

    @DeleteMapping("/{id}")
    public String deleteCred(@PathVariable("id") Long id, Model model) {
        int rowsDeleted = credentialsService.deleteCred(id);
        if (rowsDeleted < 0) {
            model.addAttribute("dbError", true);
        }
        return "result";
    }
}
