package com.it.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedAccessTokenResponse {
    //토큰 생성 요청 응답 DTO
    private String accessToken;
}
