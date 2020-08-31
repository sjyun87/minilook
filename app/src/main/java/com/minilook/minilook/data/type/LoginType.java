package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum LoginType {
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private String value;
}
