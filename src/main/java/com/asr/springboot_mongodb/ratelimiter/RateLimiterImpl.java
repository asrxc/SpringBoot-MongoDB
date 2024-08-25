package com.asr.springboot_mongodb.ratelimiter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class RateLimiterImpl implements RateLimiter{
    int tokensAvailable;
    int numOfRequests;
    int bucketSize;
    int windowSizeInSecs;
    LocalDateTime nextRefillTime;
    LocalDateTime currTime;

    public RateLimiterImpl(){
        this(10,20,6);
    };

    public RateLimiterImpl(int numOfRequests, int bucketSize, int windowSizeInSecs) {
        this.tokensAvailable = numOfRequests;
        this.numOfRequests = numOfRequests;
        this.bucketSize = bucketSize;
        this.windowSizeInSecs = windowSizeInSecs;
        this.nextRefillTime = LocalDateTime.now().plusSeconds(this.windowSizeInSecs);
    }


    @Override
    public boolean tryGet() throws Exception {
        refill();
        return returnTokenAvailability();
    }

    public void refill(){
        this.currTime = LocalDateTime.now();
        if(currTime.isAfter(nextRefillTime)){
            this.tokensAvailable += Math.min(this.tokensAvailable + this.numOfRequests, this.bucketSize);
        }
        this.nextRefillTime = this.currTime.plusSeconds(this.windowSizeInSecs);

    }

    public boolean returnTokenAvailability(){
        if(this.tokensAvailable > 0){
            this.tokensAvailable--;
            return true;
        }else{
            return false;
        }
    }
}
