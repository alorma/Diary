package com.alorma.diary.data.diary.dbmodel;

import java.util.List;

public class User {
  public long id;
  public long diaryId;
  public List<String> comments;
  public String name;
  public String phone;
  public String picture;

  public User() {

  }

  public List<String> getComments() {
    return comments;
  }

  public void setComments(List<String> comments) {
    this.comments = comments;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public long getId() {
    return id;
  }

  public long getDiaryId() {
    return diaryId;
  }
}
