package com.asr.springboot_mongodb.controller;

import com.asr.springboot_mongodb.collection.Person;
import com.asr.springboot_mongodb.service.PersonService;
import io.swagger.models.auth.In;
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

    @PostMapping
    public String save(@RequestBody Person person){
        return personService.save(person);
    }

    @GetMapping
    public List<Person> getPersonNameStartsWith(@RequestParam("name") String name){
        return personService.getPersonNameStartsWith(name);
    }

    @DeleteMapping
    public void delete(@PathVariable String id){
        personService.delete(id);
    }

    @GetMapping("/age")
    public List<Person> getByPersonAge(@RequestParam Integer minAge, @RequestParam Integer maxAge){
        return personService.getAgeByMinAgeMaxAge(minAge,maxAge);
    }

    @GetMapping("/search")
    public Page<Person> searchPerson(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String minAge,
                                     @RequestParam(required = false) String maxAge,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        return personService.search(name, minAge,maxAge,city,page,size,pageable);
    }
}
