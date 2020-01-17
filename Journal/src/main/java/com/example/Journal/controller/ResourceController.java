package com.example.Journal.controller;
import com.example.Journal.DAO.ResourceDao;
import com.example.Journal.Service.ResourceService;
import com.example.Journal.errors.MyException;
import com.example.Journal.models.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @CrossOrigin(origins = "*")
    @PostMapping(value = "")
    public ResponseEntity<ResourceDao> createResource(@RequestBody ResourceDao resource) throws MyException {
        resourceService.createResource(resource);
        ResourceDao resourceDao = new ResourceDao();
        return new ResponseEntity<>(resourceDao, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/{resourceId}")
    public void deleteResource(@PathVariable Integer resourceId) {
        resourceService.deleteResource(resourceId);
    }

    @GetMapping(value = "/{resourceId}")
    public Optional<Resource> getResourceById(@PathVariable Integer resourceId) {
        return resourceService.getResourceById(resourceId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{resourceId}")
    public void updateResource(@PathVariable(value="resourceId") Integer resourceId, @RequestBody ResourceDao resource) throws MyException {
        resourceService.updateResource(resourceId, resource);
        ResourceDao resourceDao = new ResourceDao();
    }

}
