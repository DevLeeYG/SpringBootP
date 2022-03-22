package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;

//데이터베이스에 쓰일 필드와 여러 엔티티간 연관관걔를 정의
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")

public class TodoEntity {
    @Id
    @GeneratedValue(generator="system-uuid")//system-uuid 는 @GenericGenerator에 정의된
    //generator의 이름으로 @GnericGenerator는 Hibernate가 제공하는 기본 Generator가 아니라 나만의 제네레ㅇ터를 사용하고 싶을 경우 이용
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    private String id;
    private String userId;
    private String title;
    private boolean done;
}
