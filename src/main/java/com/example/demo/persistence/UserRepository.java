package com.example.demo.persistence;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository /// 빈으로 등록하기 위한

public interface UserRepository extends JpaRepository<UserEntity,String> {

    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    UserEntity findAllByEmailAndPassword(String email, String password);

}

//자바나 spring에서 의존성이란
//하나의 객체 내에서 필요에 의해 다른 객체를 사용해야할 경우 사용하는것