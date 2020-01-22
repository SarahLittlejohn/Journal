package com.example.Journal.Service;
import com.example.Journal.DAO.StackDao;
import com.example.Journal.errors.MyException;
import com.example.Journal.models.Stack;
import com.example.Journal.repository.StackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StackService {

    private StackRepository stackRepository;

    @Autowired
    public StackService(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    public List<Stack> getAllStacks() {
        return stackRepository.findAll();
    }

    public StackDao createStack(StackDao stackDao) throws MyException {
        Stack stack = new Stack();
        stack.setName(stackDao.getName());
        stack.setStack(stackDao.getStack());
        stackRepository.save(stack);
        if(stack != null){
            return stackDao;
        } else {
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "stack not created");
        }
    }

    public void deleteStack(Integer stackId){
        stackRepository.deleteById(stackId);
    }

    public Optional<Stack> getStackById(Integer stackId) {
        return stackRepository.findById(stackId);
    }

    public StackDao updateStack(Integer stackId, StackDao stackDao) throws MyException {
        Optional<Stack> stack = stackRepository.findById(stackId);
        if (!stack.isPresent()) {
            throw new MyException(HttpStatus.NOT_FOUND, "stack " + Integer.toString(stackId) + " not found");
        } else {
            stack.get().setName(stackDao.getName());
            stack.get().setStack(stackDao.getStack());
            stackRepository.save(stack.get());
        }
        return stackDao;
    }

}
