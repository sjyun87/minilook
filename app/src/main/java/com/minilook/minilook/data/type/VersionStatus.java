package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum VersionStatus {
    FORCE(-1),
    OPTIONAL(0),
    NEWEST(1);

    private int value;
}