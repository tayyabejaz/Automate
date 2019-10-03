package com.innovidio.androidbootstrap.entity;

import androidx.lifecycle.OnLifecycleEvent;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;


@Entity
public class Feed {

    @PrimaryKey
    @Json(name = "id")
    private Integer id;
    @Json(name = "category")
    private String category;
    @Json(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}