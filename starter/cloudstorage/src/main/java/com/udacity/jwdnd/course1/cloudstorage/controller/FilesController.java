package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/files")
public class FilesController {
    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostMapping()
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile, Model model, Principal principal) throws IOException {
        if (multipartFile.isEmpty()) {
            model.addAttribute("errorMessage", "No file to upload.");
        } else if (filesService.duplicateFilename(multipartFile.getOriginalFilename(), principal.getName())) {
            model.addAttribute("errorMessage", "The filename already exists.");
        } else {
            int rowsAdded = filesService.addFile(multipartFile, principal.getName());
            if (rowsAdded < 0) {
                model.addAttribute("dbError", true);
            }
        }
        return "result";
    }

    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable("id") Long id, Model model, Principal principal) {
        int rowsDeleted = filesService.deleteFile(id, principal.getName());
        if (rowsDeleted < 0) {
            model.addAttribute("dbError", true);
        }
        return "result";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Resource> viewFile(@PathVariable("id") Long id, Principal principal) {
        File file = filesService.getFile(id, principal.getName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }
}
