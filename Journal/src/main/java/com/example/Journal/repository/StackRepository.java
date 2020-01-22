package com.example.Journal.repository;

import com.example.Journal.models.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Integer> {

    List<Stack> findAll();

}
