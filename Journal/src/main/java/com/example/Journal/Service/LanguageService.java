package com.example.Journal.Service;
import com.example.Journal.DAO.LanguageDao;
import com.example.Journal.models.Language;
import com.example.Journal.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public LanguageDao createLanguage(LanguageDao languageDao) {
        Language language = new Language();
        language.setName(languageDao.getName());
        language.setUrl(languageDao.getUrl());
        language.setDescription(languageDao.getDescription());
        languageRepository.save(language);
        return languageDao;
    }

    public void deleteLanguage(Integer languageId){
        languageRepository.deleteById(languageId);
    }

    public Optional<Language> getLanguageById(Integer languageId) {
        return languageRepository.findById(languageId);
    }

    public LanguageDao updateLanguage(Integer languageId, LanguageDao languageDao) {
        Optional<Language> language = languageRepository.findById(languageId);
        if (language.isPresent()) {
            language.get().setName(languageDao.getName());
            language.get().setUrl(languageDao.getUrl());
            language.get().setDescription(languageDao.getDescription());
            languageRepository.save(language.get());
        }
        return languageDao;
    }

}
