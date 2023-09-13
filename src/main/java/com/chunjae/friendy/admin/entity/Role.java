package com.chunjae.friendy.admin.entity;

import lombok.Getter;

@Getter
public enum Role {

    SUPER("ROLE_SUPER_ADMIN"),
    NORMAL("ROLE_NORMAL_ADMIN");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

}
