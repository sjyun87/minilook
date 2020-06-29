package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum CategoryType {
    TYPE_TOP("020000", "상의"),
    TYPE_PANTS("020001", "하의"),
    TYPE_SKIRT("020002", "스커트"),
    TYPE_SET("020003", "상하복"),
    TYPE_ONE_PIECE("020004", "원피스"),
    TYPE_OUTER("020005", "아우터"),
    TYPE_ACCESSORIES_BEAUTY("020006", "악세사리, 뷰티"),
    TYPE_UNDERWEAR_SOCKS("020007", "속옷, 양말"),
    TYPE_BABY("020008", "베이비"),
    TYPE_SWIMSUIT_RAINCOAT("020009", "수영복, 우비"),
    TYPE_BAG_SHOES("020010", "가방, 신발"),
    TYPE_LIVING("020011", "리빙");

    private String id;
    private String name;

    public static CategoryType toType(String $id) {
        for (CategoryType type : CategoryType.values()) {
            if (type.id.equals($id)) {
                return type;
            }
        }
        return null;
    }
}
