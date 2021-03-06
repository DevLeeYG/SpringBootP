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

    public List<TodoEntity> create(final TodoEntity entity) { //생성
        //변환된 entity를 받아옴
        validate(entity);
        repository.save(entity);
        log.info("Entity Id:{} is saved.",entity.getId());

        return repository.findByUserId(entity.getUserId()); //->약식에 맞춘 엔티티가 들어온다 (DTO) -> 여기서 ->persistence 즉 DAO로 연결

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

    public List<TodoEntity> delete(final TodoEntity entity){
        //(1) 저장할 엔티티가 유효한지 확인한다
        validate(entity);

        try{
            //(2)엔티티 삭제
            repository.delete(entity);
        }catch(Exception e){
            //(3)exception 발생 시 id와 exception 을 로깅
            log.error("error deleting entity",entity.getId(),e);

            //(4)컨트롤러로 exception 을 보낸다. 데이터 베이스 내부 로직을 캡슐화하려면 e를 리턴하지 않고 새
            //exception 오브젝트를 리턴
            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieve(entity.getUserId());
    }


}
