package com.example.demo.dto;


import com.example.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //오브젝트 생성을 위한 디자인 패턴 중 하나
@NoArgsConstructor
@AllArgsConstructor //매개변수가 없는 생성자를 구현
@Data



public class TodoDTO {

    private String id;
    private String title;
    private boolean done;


    public  TodoDTO(final TodoEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }


}
