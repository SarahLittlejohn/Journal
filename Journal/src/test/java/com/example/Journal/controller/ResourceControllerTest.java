package com.example.Journal.controller;

import com.example.Journal.DAO.ResourceDao;
import com.example.Journal.JsonUtilities;
import com.example.Journal.Service.ResourceService;
import com.example.Journal.models.Resource;
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
public class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    @Autowired
    private MockMvc mockMvc;

    final private String ENDPOINT = "/resources";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void setup() throws MyException {
        mockMvc = MockMvcBuilders.standaloneSetup(resourceController)
                .setControllerAdvice(new ExceptionHandlingController())
                .build();
    }


    //=========================================== Test to Get All Resources ====================================================
    @Test
    public void GetResources_200Response_ShouldReturnAllResources() throws Exception {
        Resource testResource = new Resource();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");
        testResource.setResourceId((Integer) 1);
        // Create first new test resource

        Resource testResource2 = new Resource();
        testResource2.setName("testResource2");
        testResource2.setUrl("http://testResource2.com");
        testResource2.setResourceId((Integer) 2);
        // Create second new test resource

        List<Resource> testResources = new ArrayList<>();
        testResources.add(testResource);
        testResources.add(testResource2);
        // Add new test resources to ArrayList

        when(resourceService.getAllResources()).thenReturn(testResources);
        // When the .getAllResources() is called, expect the testResources to be returned
        mockMvc.perform(get("/resources"))
        // Performs a get request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].resourceId", is((Integer) 1)))
                .andExpect(jsonPath("$[0].name", is("testResource")))
                .andExpect(jsonPath("$[0].url", is("http://testResource.com")))
                .andExpect(jsonPath("$[1].resourceId", is((Integer) 2)))
                .andExpect(jsonPath("$[1].name", is("testResource2")))
                .andExpect(jsonPath("$[1].url", is("http://testResource2.com")));
                // Checks that the size and content of the data is correct

        verify(resourceService, times(1)).getAllResources();
        // Verify is used to check if a certain method of a mock object has been called a specific number of times.

        verifyNoMoreInteractions(resourceService);
        // This method is used after all the verify methods to make sure that all the interactions are verified.
    }

    //=========================================== Test to Post resource ====================================================
    @Test
    public void PostResource_201Response_ShouldReturnResource() throws Exception {
        ResourceDao testResource = new ResourceDao();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");

        String testJson = JsonUtilities.asJsonString(testResource);
        when(resourceService.createResource(testResource)).thenReturn(testResource);

        mockMvc.perform(post("/resources")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJson))
                .andExpect(status().isCreated());
        verify(resourceService, times(1)).createResource(any(ResourceDao.class));
        verifyNoMoreInteractions(resourceService);
    }

    @Ignore
    public void PostResource_500Response_ShouldReturnError() throws Exception {
        ResourceDao testResource = new ResourceDao();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");

        String testJson = JsonUtilities.asJsonString(testResource);
        when(resourceService.createResource(any(ResourceDao.class))).thenThrow(new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "no resource created"));

        mockMvc.perform(post("/resources")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJson))
                .andExpect(status().isInternalServerError());

        verify(resourceService, times(1)).createResource(any(ResourceDao.class));
        verifyNoMoreInteractions(resourceService);
    }

    //=========================================== Test to Put resource ====================================================
    @Test
    public void PutResource_200Response_ShouldReturnResource() throws Exception {
        ResourceDao testResource = new ResourceDao();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");

        when(resourceService.updateResource(1, testResource)).thenReturn(testResource);
        mockMvc.perform(put("/resources/{id}", (Integer) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtilities.asJsonString(testResource)))
                .andExpect(status().isOk());
        verify(resourceService, times(1)).updateResource(any(Integer.class), any(ResourceDao.class));
        verifyNoMoreInteractions(resourceService);
    }

    @Ignore
    public void testPutResourceReturns404Response() throws Exception {
        ResourceDao testResource = new ResourceDao();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");

        when(resourceService.updateResource(any(Integer.class), any(ResourceDao.class))).thenThrow(new MyException(HttpStatus.NOT_FOUND, "resource 1 not found"));
        mockMvc.perform(put("/resources/{id}", (Integer) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtilities.asJsonString(testResource)))
                .andExpect(status().isNotFound());
        verify(resourceService, times(1)).updateResource(any(Integer.class), any(ResourceDao.class));
    }
}