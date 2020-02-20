package com.example.Journal.controller;
import com.example.Journal.DAO.ProjectDao;
import com.example.Journal.Service.ProjectService;
import com.example.Journal.errors.MyException;
import com.example.Journal.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public List<Project> getProjects() {
         return projectService.getAllProjects();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "")
    public ResponseEntity<ProjectDao> createProject(@RequestBody ProjectDao project) throws MyException {
        projectService.createProject(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/{projectId}")
    public void deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
    }

    @GetMapping(value = "/{projectId}")
    public Optional<Project> getProjectById(@PathVariable Integer projectId) {
        return projectService.getProjectById(projectId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{projectId}")
    public void updateProject(@PathVariable(value="projectId") Integer projectId, @RequestBody ProjectDao project) throws MyException {
        projectService.updateProject(projectId, project);
        ProjectDao projectDao = new ProjectDao();
    }

}
