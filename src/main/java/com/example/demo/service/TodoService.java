package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
//가공 해줄 영역 // 비지니스 로직
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public List<TodoEntity> create(final TodoEntity entity) {
        //변환된 entity를 받아옴





        validate(entity);
        repository.save(entity);
        log.info("Entity Id:{} is saved.",entity.getId());

        return repository.findByUserId(entity.getUserId());

    }

    private void validate(final TodoEntity entity){ //검증
        if(entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }

    public List<TodoEntity> retrieve(final String userId){ //겟요청
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity){
        //수정요청이기에 저장할 엔티티가 유효한지 확인한다.
        validate(entity);

        //(2) 넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트할 수 없기 때문이다.

        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent((todo)->{
            //(3) 반환된 TodoEntity 가 존재하면 새 entity 값으로 덮어 씌운다
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            //(4)데이터베이스에 새 값을 저장한다.
            repository.save(todo);

        });
        return retrieve(entity.getUserId());
    }
}
