package com.gidraph.redisDemo.controller;

import com.gidraph.redisDemo.domains.Message;
import com.gidraph.redisDemo.services.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class controller {

    final RedisService redisService;

    @PostMapping("/set")
    public ResponseEntity<String> setMessage(@RequestBody Message message){
        boolean b = redisService.saveMessage(message);
        if(b){
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/set").toUriString());
            return  ResponseEntity.created(uri).body("Saved successfully");
        }else{
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    @PostMapping("/get")
    public ResponseEntity<Message>  getMessage(@RequestBody Message message){
        String uniqueKey = message.getSender();
        Message serviceMessage = redisService.getMessage(uniqueKey);
        if(serviceMessage != null){
            return ResponseEntity.ok(serviceMessage);
        }else {
            message.setMessageText("Message not found");
            return ResponseEntity.internalServerError().body(message);
        }

    }
    @GetMapping("/all")
    public ResponseEntity<List<Message>>  getAllMessages(){
        List<Message> allMessages = redisService.getAllMessages();
        if(allMessages != null){
            return ResponseEntity.ok(allMessages);
        }
        return null;
    }

}
