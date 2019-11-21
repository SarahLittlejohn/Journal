package com.example.Journal.Service;
import com.example.Journal.DAO.ResourceDao;
import com.example.Journal.models.Resource;
import com.example.Journal.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public void createResource(ResourceDao resourceDao) {
        Resource resource = new Resource();
        resource.setName(resourceDao.getName());
        resource.setUrl(resourceDao.getUrl());
        resourceRepository.save(resource);
    }

    public void deleteResource(Integer resourceId){
        resourceRepository.deleteById(resourceId);
    }

    public Optional<Resource> getResourceById(Integer resourceId) {
        return resourceRepository.findById(resourceId);
    }

    public void updateResource(Integer resourceId, ResourceDao resourceDao) {
        Optional<Resource> resource = resourceRepository.findById(resourceId);
        if (resource.isPresent()) {
            resource.get().setName(resourceDao.getName());
            resource.get().setUrl(resourceDao.getUrl());
            resourceRepository.save(resource.get());
        }
    }

}
