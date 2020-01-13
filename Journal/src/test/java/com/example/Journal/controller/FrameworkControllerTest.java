package com.example.Journal.controller;

import com.example.Journal.DAO.FrameworkDao;
import com.example.Journal.JsonUtilities;
import com.example.Journal.Service.FrameworkService;
import com.example.Journal.models.Framework;
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
public class FrameworkControllerTest {

    @Mock
    private FrameworkService frameworkService;

    @InjectMocks
    private FrameworkController frameworkController;

    @Autowired
    private MockMvc mockMvc;

    final private String ENDPOINT = "/frameworks";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void setup() throws MyException {
        mockMvc = MockMvcBuilders.standaloneSetup(frameworkController)
                .setControllerAdvice(new ExceptionHandlingController())
                .build();
    }

    //=========================================== Get All Framework ====================================================
    @Test
    public void GetFrameworks_200Response_ShouldReturnAllFrameworks() throws Exception {
        Framework testFramework = new Framework();
        testFramework.setName("testFramework");
        testFramework.setUrl("http://testFramework.com");
        testFramework.setFrameworkId((Integer) 1);

        Framework testFramework2 = new Framework();
        testFramework2.setName("testFramework2");
        testFramework2.setUrl("http://testFramework2.com");
        testFramework2.setFrameworkId((Integer) 2);

        List<Framework> testFrameworks = new ArrayList<>();
        testFrameworks.add(testFramework);
        testFrameworks.add(testFramework2);

        when(frameworkService.getAllFrameworks()).thenReturn(testFrameworks);
        mockMvc.perform(get("/frameworks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].frameworkId", is((Integer) 1)))
                .andExpect(jsonPath("$[0].name", is("testFramework")))
                .andExpect(jsonPath("$[0].url", is("http://testFramework.com")))
                .andExpect(jsonPath("$[1].frameworkId", is((Integer) 2)))
                .andExpect(jsonPath("$[1].name", is("testFramework2")))
                .andExpect(jsonPath("$[1].url", is("http://testFramework2.com")));

        verify(frameworkService, times(1)).getAllFrameworks();
        verifyNoMoreInteractions(frameworkService);
    }


    //=========================================== Test to Post framework ====================================================
    @Test
    public void PostFramework_201Response_ShouldReturnFramework() throws Exception {
        FrameworkDao testFramework = new FrameworkDao();
        testFramework.setName("testFramework");
        testFramework.setUrl("http://testFramework.com");

        String testJson = JsonUtilities.asJsonString(testFramework);
        when(frameworkService.createFramework(testFramework)).thenReturn(testFramework);

        mockMvc.perform(post("/frameworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJson))
                .andExpect(status().isCreated());
        verify(frameworkService, times(1)).createFramework(any(FrameworkDao.class));
        verifyNoMoreInteractions(frameworkService);
    }

    @Test
    public void PostFramework_500Response_ShouldReturnError() throws Exception {
        FrameworkDao testFramework = new FrameworkDao();
        testFramework.setName("testFramework");
        testFramework.setUrl("http://testFramework.com");

        String testJson = JsonUtilities.asJsonString(testFramework);
        when(frameworkService.createFramework(any(FrameworkDao.class))).thenThrow(new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "no framework created"));

        mockMvc.perform(post("/frameworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJson))
                .andExpect(status().isInternalServerError());

        verify(frameworkService, times(1)).createFramework(any(FrameworkDao.class));
        verifyNoMoreInteractions(frameworkService);
    }

    //=========================================== Test to Put framework ====================================================
    @Test
    public void PutFramework_200Response_ShouldReturnFramework() throws Exception {
        FrameworkDao testFramework = new FrameworkDao();
        testFramework.setName("testFramework");
        testFramework.setUrl("http://testFramework.com");

        when(frameworkService.updateFramework(1, testFramework)).thenReturn(testFramework);
        mockMvc.perform(put("/frameworks/{id}", (Integer) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtilities.asJsonString(testFramework)))
                .andExpect(status().isOk());
        verify(frameworkService, times(1)).updateFramework(any(Integer.class), any(FrameworkDao.class));
        verifyNoMoreInteractions(frameworkService);
    }

    @Ignore
    public void testPutFrameworkReturns404Response() throws Exception {
        FrameworkDao testFramework = new FrameworkDao();
        testFramework.setName("testFramework");
        testFramework.setUrl("http://testFramework.com");

        when(frameworkService.updateFramework(any(Integer.class), any(FrameworkDao.class))).thenThrow(new MyException(HttpStatus.NOT_FOUND, "framework 1 not found"));
        mockMvc.perform(put("/frameworks/{id}", (Integer) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtilities.asJsonString(testFramework)))
                .andExpect(status().isNotFound());
        verify(frameworkService, times(1)).updateFramework(any(Integer.class), any(FrameworkDao.class));
    }
}