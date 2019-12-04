package com.example.Journal.controller;

import com.example.Journal.DAO.ResourceDao;
import com.example.Journal.Service.ResourceService;
import com.example.Journal.models.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public List<Resource> getResources() {
         return resourceService.getAllResources();
    }

    @PostMapping(value = "")
    public void createResource(@RequestBody ResourceDao resource) {
        resourceService.createResource(resource);
    }

    @DeleteMapping(value = "/{resourceId}")
    public void deleteResource(@PathVariable Integer resourceId) {
        resourceService.deleteResource(resourceId);
    }

    @GetMapping(value = "/{resourceId}")
    public Optional<Resource> getResourceById(@PathVariable Integer resourceId) {
        return resourceService.getResourceById(resourceId);
    }

    @PutMapping("/{resourceId}")
    public void updateResource(@PathVariable(value="resourceId") Integer resourceId, @RequestBody ResourceDao resource) {
        resourceService.updateResource(resourceId, resource);
    }

}
