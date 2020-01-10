package com.example.Journal.controller;

import com.example.Journal.DAO.FrameworkDao;
import com.example.Journal.Service.FrameworkService;
import com.example.Journal.models.Framework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/frameworks")
public class FrameworkController {

    @Autowired
    private FrameworkService frameworkService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public List<Framework> getFrameworks() {
        return frameworkService.getAllFrameworks();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "")
    public ResponseEntity<FrameworkDao> createFramework(@RequestBody FrameworkDao framework) {
        frameworkService.createFramework(framework);
        FrameworkDao frameworkDao = new FrameworkDao();
        return new ResponseEntity<>(frameworkDao, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/{frameworkId}")
    public void deleteFramework(@PathVariable Integer frameworkId) {
        frameworkService.deleteFramework(frameworkId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/{frameworkId}")
    public Optional<Framework> getFrameworkById(@PathVariable Integer frameworkId) {
        return frameworkService.getFrameworkById(frameworkId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{frameworkId}")
    public void updateFramework(@PathVariable(value="frameworkId") Integer frameworkId, @RequestBody FrameworkDao framework) {
        frameworkService.updateFramework(frameworkId, framework);
    }

}
