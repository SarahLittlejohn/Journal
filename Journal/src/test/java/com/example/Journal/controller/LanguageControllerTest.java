package com.example.Journal.controller;

import com.example.Journal.DAO.LanguageDao;
import com.example.Journal.DAO.LanguageDao;
import com.example.Journal.JsonUtilities;
import com.example.Journal.Service.LanguageService;
import com.example.Journal.models.Language;
import com.example.Journal.errors.MyException;
import com.example.Journal.errors.ExceptionHandlingController;

import com.fasterxml.jackson.databind.ObjectMapper;


import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LanguageControllerTest {

    @Mock
    private LanguageService languageService;

    @InjectMocks
    private LanguageController languageController;

    @Autowired
    private MockMvc mockMvc;

    final private String ENDPOINT = "/languages";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void setup() throws MyException {
        mockMvc = MockMvcBuilders.standaloneSetup(languageController)
                .setControllerAdvice(new ExceptionHandlingController())
                .build();
    }

    //=========================================== Get All Languages ====================================================
    @Test
    public void GetLanguages_200Response_ShouldReturnAllLanguages() throws Exception {
        Language testLanguage = new Language();
        testLanguage.setName("testLanguage");
        testLanguage.setUrl("http://testLanguage.com");
        testLanguage.setLanguageId((Integer) 1);

        Language testLanguage2 = new Language();
        testLanguage2.setName("testLanguage2");
        testLanguage2.setUrl("http://testLanguage2.com");
        testLanguage2.setLanguageId((Integer) 2);

        List<Language> testLanguages = new ArrayList<>();
        testLanguages.add(testLanguage);
        testLanguages.add(testLanguage2);

        when(languageService.getAllLanguages()).thenReturn(testLanguages);
        mockMvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].languageId", is((Integer) 1)))
                .andExpect(jsonPath("$[0].name", is("testLanguage")))
                .andExpect(jsonPath("$[0].url", is("http://testLanguage.com")))
                .andExpect(jsonPath("$[1].languageId", is((Integer) 2)))
                .andExpect(jsonPath("$[1].name", is("testLanguage2")))
                .andExpect(jsonPath("$[1].url", is("http://testLanguage2.com")));

        verify(languageService, times(1)).getAllLanguages();
        verifyNoMoreInteractions(languageService);
    }

    //=========================================== Test to Post language ====================================================
    @Test
    public void PostLanguage_201Response_ShouldReturnLanguage() throws Exception {
        LanguageDao testLanguage = new LanguageDao();
        testLanguage.setName("testLanguage");
        testLanguage.setUrl("http://testLanguage.com");

        String testJson = JsonUtilities.asJsonString(testLanguage);
        when(languageService.createLanguage(testLanguage)).thenReturn(testLanguage);

        mockMvc.perform(post("/languages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJson))
                .andExpect(status().isCreated());
        verify(languageService, times(1)).createLanguage(any(LanguageDao.class));
        verifyNoMoreInteractions(languageService);
    }

    @Test
    public void PostLanguage_500Response_ShouldReturnError() throws Exception {
        LanguageDao testLanguage = new LanguageDao();
        testLanguage.setName("testLanguage");
        testLanguage.setUrl("http://testLanguage.com");

        String testJson = JsonUtilities.asJsonString(testLanguage);
        when(languageService.createLanguage(any(LanguageDao.class))).thenThrow(new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "no language created"));

        mockMvc.perform(post("/languages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJson))
                .andExpect(status().isInternalServerError());

        verify(languageService, times(1)).createLanguage(any(LanguageDao.class));
        verifyNoMoreInteractions(languageService);
    }


    //=========================================== Test to Put language ====================================================
    @Test
    public void PutLanguage_200Response_ShouldReturnLanguage() throws Exception {
        LanguageDao testLanguage = new LanguageDao();
        testLanguage.setName("testLanguage");
        testLanguage.setUrl("http://testLanguage.com");

        when(languageService.updateLanguage(1, testLanguage)).thenReturn(testLanguage);
        mockMvc.perform(put("/languages/{id}", (Integer) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtilities.asJsonString(testLanguage)))
                .andExpect(status().isOk());
        verify(languageService, times(1)).updateLanguage(any(Integer.class), any(LanguageDao.class));
        verifyNoMoreInteractions(languageService);
    }

    @Test
    public void PutLanguage_404Response_ShouldReturnError() throws Exception {
        LanguageDao testLanguage = new LanguageDao();
        testLanguage.setName("testLanguage");
        testLanguage.setUrl("http://testLanguage.com");

        when(languageService.updateLanguage(any(Integer.class), any(LanguageDao.class))).thenThrow(new MyException(HttpStatus.NOT_FOUND, "language 1 not found"));
        mockMvc.perform(put("/languages/{id}", (Integer) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtilities.asJsonString(testLanguage)))
                .andExpect(status().isNotFound());
        verify(languageService, times(1)).updateLanguage(any(Integer.class), any(LanguageDao.class));
    }
}