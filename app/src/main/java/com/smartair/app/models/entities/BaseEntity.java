package com.smartair.app.models.entities;

import com.google.gson.Gson;

public class BaseEntity {
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
