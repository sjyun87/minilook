package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum VersionStatus {
    FORCE(-1),      // 강제 업데이트
    OPTIONAL(0),    // 선택 업데이트
    NEWEST(1);      // 최신버전

    private final int value;
}
