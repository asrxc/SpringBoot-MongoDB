package com.asr.springboot_mongodb.repository;

import com.asr.springboot_mongodb.collection.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo,String> {

}
