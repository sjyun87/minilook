package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public class NetworkType {
    public static final String OK = "200";
    public static final String BAD_REQUEST = "400";
    public static final String NOT_FOUND = "404";
    public static final String REQUEST_TIMEOUT = "408";
    public static final String INTERNAL_SERVER_ERROR = "500";
    public static final String BAD_GATEWAY = "502";
    public static final String NO_DATA = "512";
}
