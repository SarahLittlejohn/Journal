package com.example.Journal.controller;

import com.example.Journal.DAO.ResourceDao;
import com.example.Journal.JsonUtilities;
import com.example.Journal.Service.ResourceService;
import com.example.Journal.models.Resource;
import com.example.Journal.errors.MyException;
import com.example.Journal.errors.ExceptionHandlingController;

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
// Look for a main configuration class i.e. @SpringBootApplication
@AutoConfigureMockMvc
public class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;
    // Creates a mock of the service

    @InjectMocks
    private ResourceController resourceController;
    // Creates an instance of the class and injects the mocks described in @Mock

    @Autowired
    private MockMvc mockMvc;
    // MockMvc is injected before the test methods are run

    @Before
    // Every @Before section is repeated before each test.
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
        // Create first new test resource

        Resource testResource2 = new Resource();
        testResource2.setName("testResource2");
        testResource2.setUrl("http://testResource2.com");
        // Create second new test resource

        List<Resource> testResources = new ArrayList<>();
        testResources.add(testResource);
        testResources.add(testResource2);
        // Create array containing two test resources

        when(resourceService.getAllResources()).thenReturn(testResources);
        /* when() allows us to provide the actual mocking behaviour.
        It tells Mockito that when the getAllResources is called from the resourceService,
        the returned Resources should be stubbed. This means that the returned class is a
        fake one with preprogrammed return values and not a real returned object from the
        database. This allows us to test the unit without actually having to connect to the
        database or Spring at all.
         */

        mockMvc.perform(get("/resources"))
        // Performs a get request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("testResource")))
                .andExpect(jsonPath("$[0].url", is("http://testResource.com")))
                .andExpect(jsonPath("$[1].name", is("testResource2")))
                .andExpect(jsonPath("$[1].url", is("http://testResource2.com")));
                // Checks that the size and content of the data is correct

        verify(resourceService, times(1)).getAllResources();
        // Verify is used to that the mocked/stubbed method is actually called.

        verifyNoMoreInteractions(resourceService);
        // This method is used after all the verify methods to make sure that all the interactions are verified.
    }

    //=========================================== Test to Post resource ====================================================
    @Test
    public void PostResource_201Response_ShouldReturnResource() throws Exception {
        ResourceDao testResource = new ResourceDao();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");
        // Create test resource

        String testJson = JsonUtilities.asJsonString(testResource);
        // Convert test resource into a Json string
        when(resourceService.createResource(testResource)).thenReturn(testResource);
        // When the resource is created, return it

        mockMvc.perform(post("/resources")
        // Post the resource
                .contentType(MediaType.APPLICATION_JSON)
                // Decide on content type
                .content(testJson))
                // Choose the content
                .andExpect(status().isCreated());
                // Expect a 201 response
        verify(resourceService, times(1)).createResource(any(ResourceDao.class));
        // Check that the createResource was called
        verifyNoMoreInteractions(resourceService);
    }

    @Test
    public void PostResource_500Response_ShouldReturnError() throws Exception {
        ResourceDao testResource = new ResourceDao();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");

        String testJson = JsonUtilities.asJsonString(testResource);
        when(resourceService.createResource(any(ResourceDao.class))).thenThrow(new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "resource not created"));

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

        when(resourceService.updateResource(any(Integer.class), any(ResourceDao.class))).thenThrow(new MyException(HttpStatus.NOT_FOUND, "resource not found"));
        mockMvc.perform(put("/resources/{id}", (Integer) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtilities.asJsonString(testResource)))
                .andExpect(status().isNotFound());
        verify(resourceService, times(1)).updateResource(any(Integer.class), any(ResourceDao.class));
    }

}