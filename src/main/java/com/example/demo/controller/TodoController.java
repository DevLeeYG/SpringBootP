package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")

public class TodoController{
    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temprary-user";

            //(1) TodoEntity 로 변환 -> 컨트롤러로 객체를 받아서 TodoDTO양식으로 엔티티를 구성함
            TodoEntity entity = TodoDTO.todoEntity(dto);

            //(2) id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문
            entity.setId((null));

            //(3) 임시 사용자 아이디를 설정해 준다.
            entity.setUserId(temporaryUserId);

            //(4) 서비스를 이용해 Todo 엔티티를 생성
            List<TodoEntity> entities = service.create(entity);

            //(5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //(6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //(7)ResponseDTO 리턴
            return ResponseEntity.ok().body(response);

        }catch(Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping

    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temprary-user";

        //(1) 서비스 메서드의 retrieve() 메서드를 사용해 Todo 리스트를 가져온다

        List<TodoEntity> entities = service.retrieve(temporaryUserId);

        //(2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //(3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO 를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();//리스폰스 빌더

        //(4) ResponseDTO를 리턴한다

        return ResponseEntity.ok().body(response);
    }
//리슨포스 DTO 에 TodoDTO 의 양식으로 빌더

    @PutMapping

    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        String temporaryUserId = "temprary-user";

        //(1) dto를 entity로 변환한다
        TodoEntity entity = TodoDTO.todoEntity(dto);

        //(2) id를 temporaryUserId로 초기화한다. 여기는 4장 인증과 인가에서 수정할 예정이다.
        entity.setUserId(temporaryUserId);

        //(3) 서비스를 이용해 entity를 업데이트한다.
        List<TodoEntity> entities = service.update(entity);

        //(4) 자바 스트림을 이용해 리튼된 엔티티 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //(5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //(6) ResponseDTO를 리턴

        return ResponseEntity.ok().body(response);




    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temprary-user";

            //(1) TodoEntity 로 변환
            TodoEntity entity = TodoDTO.todoEntity(dto);

            //(2) 임시 사용자 아이디를 설정해 준다. 현재는 temporary-user만 이용할수 있다.
            entity.setUserId(temporaryUserId);

            //(3) 서비스를 이용해 entity를 삭제한다
            List<TodoEntity> entities = service.delete(entity);

            //(4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO로 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //(5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화시킨다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
                    return ResponseEntity.ok().body(response);
        }catch (Exception e){
            //(7) 혹시 예외가 있는 경우 dto 대신 error에 메세지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
                    return ResponseEntity.badRequest().body(response);
        }
    }
//리스트 는 엔티티의 형태로 들어가있는데 builder는 그형식으로 리스폰스하게 해준다.

}

