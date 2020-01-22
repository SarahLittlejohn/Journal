package com.example.Journal.controller;
import com.example.Journal.DAO.StackDao;
import com.example.Journal.Service.StackService;
import com.example.Journal.errors.MyException;
import com.example.Journal.models.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stacks")
public class StackController {

    @Autowired
    private StackService stackService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public List<Stack> getStacks() {
        return stackService.getAllStacks();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "")
    public ResponseEntity<StackDao> createStack(@RequestBody StackDao stack) throws MyException {
        stackService.createStack(stack);
        StackDao stackDao = new StackDao();
        return new ResponseEntity<>(stackDao, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/{stackId}")
    public void deleteStack(@PathVariable Integer stackId) {
        stackService.deleteStack(stackId);
    }

    @GetMapping(value = "/{stackId}")
    public Optional<Stack> getStackById(@PathVariable Integer stackId) {
        return stackService.getStackById(stackId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{stackId}")
    public void updateStack(@PathVariable(value="stackId") Integer stackId, @RequestBody StackDao stack) throws MyException {
        stackService.updateStack(stackId, stack);
        StackDao stackDao = new StackDao();
    }

}
