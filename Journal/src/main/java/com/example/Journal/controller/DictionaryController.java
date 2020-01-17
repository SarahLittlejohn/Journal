package com.example.Journal.controller;
import com.example.Journal.DAO.DictionaryDao;
import com.example.Journal.Service.DictionaryService;
import com.example.Journal.errors.MyException;
import com.example.Journal.models.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/definitions")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public List<Dictionary> getDictionarys() {
        return dictionaryService.getAllDictionarys();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "")
    public ResponseEntity<DictionaryDao> createDictionary(@RequestBody DictionaryDao dictionary) throws MyException {
        dictionaryService.createDictionary(dictionary);
        DictionaryDao dictionaryDao = new DictionaryDao();
        return new ResponseEntity<>(dictionaryDao, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/{dictionaryId}")
    public void deleteDictionary(@PathVariable Integer dictionaryId) {
        dictionaryService.deleteDictionary(dictionaryId);
    }

    @GetMapping(value = "/{dictionaryId}")
    public Optional<Dictionary> getDictionaryById(@PathVariable Integer dictionaryId) {
        return dictionaryService.getDictionaryById(dictionaryId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{dictionaryId}")
    public void updateDictionary(@PathVariable(value="dictionaryId") Integer dictionaryId, @RequestBody DictionaryDao dictionary) throws MyException {
        dictionaryService.updateDictionary(dictionaryId, dictionary);
        DictionaryDao dictionaryDao = new DictionaryDao();
    }

}
