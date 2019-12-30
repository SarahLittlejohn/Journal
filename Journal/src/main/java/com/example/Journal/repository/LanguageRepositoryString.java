package com.example.Journal.repository;

import com.example.Journal.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepositoryString extends JpaRepository<Language, String> {

}
