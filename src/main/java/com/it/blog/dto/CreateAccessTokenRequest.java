package com.it.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequest {
    //토큰 생성 요청 및 응답을 담당하는 DTO
    private String refreshToken;

}
