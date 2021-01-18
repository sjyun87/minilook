package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum ChallengeType {
    OPEN(0),         // 진행중
    COMING(1),       // 오픈예정
    END(2);          // 종료

    private final int value;

    public static ChallengeType toType(int $value) {
        for (ChallengeType type : ChallengeType.values()) {
            if (type.value == $value) {
                return type;
            }
        }
        return null;
    }
}