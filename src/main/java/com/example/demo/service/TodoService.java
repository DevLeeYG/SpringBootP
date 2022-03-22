package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

@Slf4j
@Service
//가공 해줄 영역 // 비지니스 로직
public class TodoService {

    @Autowired
    private  TodoRepository repository;


        public String testService(){

            TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
            repository.save(entity);
            TodoEntity saveEntity = repository.findById(entity.getId()).get();
            return saveEntity.getTitle();

        }

}
