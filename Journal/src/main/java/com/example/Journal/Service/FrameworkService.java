package com.example.Journal.Service;
import com.example.Journal.DAO.FrameworkDao;
import com.example.Journal.models.Framework;
import com.example.Journal.repository.FrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrameworkService {

    private FrameworkRepository frameworkRepository;

    @Autowired
    public FrameworkService(FrameworkRepository frameworkRepository) {
        this.frameworkRepository = frameworkRepository;
    }

    public List<Framework> getAllFrameworks() {
        return frameworkRepository.findAll();
    }

    public void createFramework(FrameworkDao frameworkDao) {
        Framework framework = new Framework();
        framework.setName(frameworkDao.getName());
        framework.setUrl(frameworkDao.getUrl());
        framework.setDescription(frameworkDao.getDescription());
        frameworkRepository.save(framework);
    }

    public void deleteFramework(Integer frameworkId){
        frameworkRepository.deleteById(frameworkId);
    }

    public Optional<Framework> getFrameworkById(Integer frameworkId) {
        return frameworkRepository.findById(frameworkId);
    }

    public void updateFramework(Integer frameworkId, FrameworkDao frameworkDao) {
        Optional<Framework> framework = frameworkRepository.findById(frameworkId);
        if (framework.isPresent()) {
            framework.get().setName(frameworkDao.getName());
            framework.get().setUrl(frameworkDao.getUrl());
            framework.get().setDescription(frameworkDao.getDescription());
            frameworkRepository.save(framework.get());
        }
    }

}
