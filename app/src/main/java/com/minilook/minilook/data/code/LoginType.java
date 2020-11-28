package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum LoginType {
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String value;
}
