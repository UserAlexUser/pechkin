package com.pechkin.model;

import lombok.Getter;

public enum Roles {
    ADMIN ("ADMIN"),
    USER ("USER");

    @Getter
    private String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }
}

