package com.asr.springboot_mongodb.service;

import com.asr.springboot_mongodb.collection.Person;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PersonService {
    String save(Person person);

    List<Person> getPersonNameStartsWith(String name);

    void delete(String id);

    List<Person> getAgeByMinAgeMaxAge(Integer minAge, Integer maxAge);
    Page<Person> search(String name, String minAge, String maxAge, String city, Integer page, Integer size, Pageable pageable);

    List<Document> getOldestPersonByCity();

    List<Document> getPopulationByCity();
}
