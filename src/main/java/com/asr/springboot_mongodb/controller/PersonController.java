package com.asr.springboot_mongodb.controller;

import com.asr.springboot_mongodb.collection.Person;
import com.asr.springboot_mongodb.exceptions.ApiLimitExceedException;
import com.asr.springboot_mongodb.ratelimiter.RateLimiter;
import com.asr.springboot_mongodb.service.PersonService;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private RateLimiter rateLimiter;

    @PostMapping
    @SneakyThrows
    public String save(@RequestBody Person person){
        return personService.save(person);
    }

    @GetMapping
    @SneakyThrows
    public List<Person> getPersonNameStartsWith(@RequestParam("name") String name) {
        if( !rateLimiter.tryGet() ) throw new ApiLimitExceedException("Too many requests");
        return personService.getPersonNameStartsWith(name);
    }

    @DeleteMapping
    @SneakyThrows
    public void delete(@PathVariable String id) throws Exception {
        if( !rateLimiter.tryGet() ) throw new ApiLimitExceedException("Too many requests");
        personService.delete(id);
    }

    @GetMapping("/age")
    @SneakyThrows
    public List<Person> getByPersonAge(@RequestParam Integer minAge, @RequestParam Integer maxAge){
        if( !rateLimiter.tryGet() ) throw new ApiLimitExceedException("Too many requests");
        return personService.getAgeByMinAgeMaxAge(minAge,maxAge);
    }

    @GetMapping("/search")
    @SneakyThrows
    public Page<Person> searchPerson(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String minAge,
                                     @RequestParam(required = false) String maxAge,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "5") Integer size
    ) {
        if( !rateLimiter.tryGet() ) throw new ApiLimitExceedException("Too many requests");
        Pageable pageable = PageRequest.of(page,size);
        return personService.search(name, minAge,maxAge,city,page,size,pageable);
    }

    @GetMapping("/oldestPerson")
    @SneakyThrows
    public List<Document> getOldestPerson(){
        if( !rateLimiter.tryGet() ) throw new ApiLimitExceedException("Too many requests");
        return personService.getOldestPersonByCity();
    }

    @GetMapping("/populationByCity")
    @SneakyThrows
    public List<Document> getPopulationByCity(){
        if( !rateLimiter.tryGet() ) throw new ApiLimitExceedException("Too many requests");
        return personService.getPopulationByCity();
    }
}
