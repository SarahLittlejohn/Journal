package com.example.Journal.Service;
import com.example.Journal.DAO.ProjectDao;
import com.example.Journal.errors.MyException;
import com.example.Journal.models.Project;
import com.example.Journal.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectDao createProject(ProjectDao projectDao) throws MyException {
        Project project = new Project();
        project.setName(projectDao.getName());
        project.setDescription(projectDao.getDescription());
        project.setStart_date(projectDao.getStart_date());
        project.setStack(projectDao.getStack());
        projectRepository.save(project);
        if(project != null){
            return projectDao;
        } else {
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "project not created");
        }
    }

    public void deleteProject(Integer projectId){
        projectRepository.deleteById(projectId);
    }

    public Optional<Project> getProjectById(Integer projectId) {
        return projectRepository.findById(projectId);
    }

    public ProjectDao updateProject(Integer projectId, ProjectDao projectDao) throws MyException {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new MyException(HttpStatus.NOT_FOUND, "project " + Integer.toString(projectId) + " not found");
        } else {
            project.get().setName(projectDao.getName());
            project.get().setDescription(projectDao.getDescription());
            project.get().setStart_date(projectDao.getStart_date());
            project.get().setStack(projectDao.getStack());
            projectRepository.save(project.get());
        }
        return projectDao;
    }

}
