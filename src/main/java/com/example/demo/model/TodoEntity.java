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
@NoArgsConstructor //파라미터가 없는 기본생성자를 만들어준다
@AllArgsConstructor//멤버변수를 매개 변수로 받을수 있게 해준다
@Data // @Getter /@Setter/@ToString /@EqualsAndHashCode 와 @RequiredArgsConstructor 를 구현없이 바로 쓸수있게 만들어준다
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
