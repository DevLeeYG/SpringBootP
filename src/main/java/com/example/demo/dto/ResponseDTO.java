package com.example.demo.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResponseDTO<T> { //빌더를 했을때 이형식으로 들어간다

   private String error;
   private List<T> data;


}
