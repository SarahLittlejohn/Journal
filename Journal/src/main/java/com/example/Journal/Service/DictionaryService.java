package com.example.Journal.Service;
import com.example.Journal.DAO.DictionaryDao;
import com.example.Journal.errors.MyException;
import com.example.Journal.models.Dictionary;
import com.example.Journal.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DictionaryService {

    private DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public List<Dictionary> getAllDictionarys() {
        return dictionaryRepository.findAll();
    }

    public DictionaryDao createDictionary(DictionaryDao dictionaryDao) throws MyException {
        Dictionary dictionary = new Dictionary();
        dictionary.setName(dictionaryDao.getName());
        dictionary.setInfo(dictionaryDao.getInfo());
        dictionaryRepository.save(dictionary);
        if(dictionary != null){
            return dictionaryDao;
        } else {
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "dictionary not created");
        }
    }

    public void deleteDictionary(Integer dictionaryId){
        dictionaryRepository.deleteById(dictionaryId);
    }

    public Optional<Dictionary> getDictionaryById(Integer dictionaryId) {
        return dictionaryRepository.findById(dictionaryId);
    }

    public DictionaryDao updateDictionary(Integer dictionaryId, DictionaryDao dictionaryDao) throws MyException {
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionaryId);
        if (!dictionary.isPresent()) {
            throw new MyException(HttpStatus.NOT_FOUND, "dictionary " + Integer.toString(dictionaryId) + " not found");
        } else {
            dictionary.get().setName(dictionaryDao.getName());
            dictionary.get().setInfo(dictionaryDao.getInfo());
            dictionaryRepository.save(dictionary.get());
        }
        return dictionaryDao;
    }

}
