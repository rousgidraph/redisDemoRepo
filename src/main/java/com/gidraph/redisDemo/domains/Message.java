package com.gidraph.redisDemo.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String sender;
    private String messageText;
    private long expireIn;
}
