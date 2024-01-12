package com.facturito.util;

import java.util.Arrays;
import java.util.List;


public enum Role {

    CUSTOMER(Arrays.asList(Permission.READ)),
    ADMINISTRATOR(Arrays.asList(Permission.SAVE, Permission.READ, Permission.ALL_ACCESS));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}