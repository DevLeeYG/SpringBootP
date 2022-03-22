package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todo")
@Slf4j
public class TodoController{
    @Autowired
    private TodoService service;

    @GetMapping("/testTodos")
    public ResponseEntity<?> testTodo(){
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }

}

