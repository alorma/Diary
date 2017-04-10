package com.alorma.diary.data.model;

import android.net.Uri;
import java.util.List;

public class ContactListItemModel {
  private String name;
  private Uri picture;
  private String phone;
  private List<String> comments;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Uri getPicture() {
    return picture;
  }

  public void setPicture(Uri picture) {
    this.picture = picture;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<String> getComments() {
    return comments;
  }

  public void setComments(List<String> comments) {
    this.comments = comments;
  }
}
