package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Slf4j
@Service

public class UserService {

    @Autowired
    private UserRepository userRepository; //UserRepository라는 인터페이스 변수에 userRepository 인터페이스를 재정의한 클래스캑체 userRepository를 주입
    public UserEntity create(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invaild arguments");
        }
        final String email = userEntity.getEmail(); // 엔티티에 겟이메일을 가져온것을 email 에 주입 하고 레포지인터페이스 재정의
        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists");
            throw new RuntimeException(("Email already exists"));
        }
        return userRepository.save(userEntity); //Db저장
    }
      public UserEntity getByCredentials(final String email, final String password){
        return userRepository.findAllByEmailAndPassword(email,password);

    }

    public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder){
        final UserEntity originalUser = userRepository.findByEmail(email);

        //matches 메서들르 이용해 패스워드가 같은지 확인
       if(originalUser != null && encoder.matches(password,originalUser.getPassword())){
           return originalUser;
       }
       return null;

    }
}
