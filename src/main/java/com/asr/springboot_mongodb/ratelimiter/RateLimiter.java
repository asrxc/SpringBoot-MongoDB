package com.asr.springboot_mongodb.ratelimiter;

import lombok.Data;


public interface RateLimiter {
    boolean tryGet() throws Exception;
}
