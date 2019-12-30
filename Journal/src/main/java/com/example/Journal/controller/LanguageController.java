package com.example.Journal.controller;

import com.example.Journal.DAO.LanguageDao;
import com.example.Journal.Service.LanguageService;
import com.example.Journal.models.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public List<Language> getLanguages() {
        return languageService.getAllLanguages();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "")
    public void createLanguage(@RequestBody LanguageDao language) {
        languageService.createLanguage(language);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/{languageId}")
    public void deleteLanguage(@PathVariable Integer languageId) {
        languageService.deleteLanguage(languageId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/{languageId}")
    public Optional<Language> getLanguageById(@PathVariable Integer languageId) {
        return languageService.getLanguageById(languageId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{languageId}")
    public void updateLanguage(@PathVariable(value="languageId") Integer languageId, @RequestBody LanguageDao language) {
        languageService.updateLanguage(languageId, language);
    }


}
