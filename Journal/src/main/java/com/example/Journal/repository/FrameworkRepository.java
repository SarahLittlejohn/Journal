package com.example.Journal.repository;

import com.example.Journal.models.Framework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrameworkRepository extends JpaRepository<Framework, Integer> {

    List<Framework> findAll();

}
