package com.example.springrestapp.repositories;

import com.example.springrestapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
