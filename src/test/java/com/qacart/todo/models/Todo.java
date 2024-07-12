package com.qacart.todo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Todo {
//    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("isCompleted")
    private Boolean isCompleted;
    private String item;
    @JsonProperty("_id")
    private String id;
    private String userID;
    private String createdAt;
    @JsonProperty("__v")
    private String v;

    public Todo(Boolean isCompleted, String id, String item, String userID, String createdAt, String v) {
        this.isCompleted = isCompleted;
        this.item = item;
        this.id = id;
        this.userID = userID;
        this.createdAt = createdAt;
        this.v = v;
    }
    public Todo(){}

    public Todo(Boolean isCompleted, String item) {
        this.isCompleted = isCompleted;
        this.item = item;
    }
    public Todo(String item){
        this.item = item;
    }

    @JsonProperty("isCompleted")
    public Boolean getIsCompleted() {
        return isCompleted;
    }
    @JsonProperty("isCompleted")
    public void setIsCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
    @JsonProperty("_id")

    public String getId() {
        return id;
    }
    @JsonProperty("_id")

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    @JsonProperty("__v")

    public String getV() {
        return v;
    }
    @JsonProperty("__v")

    public void setV(String v) {
        this.v = v;
    }
}
