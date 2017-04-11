package com.alorma.diary.data.model;

import android.net.Uri;
import java.util.List;
import polanski.option.Option;

public class ContactListItemModel {
  private String name;
  private Option<Uri> picture;
  private Option<String> phone;
  private List<String> comments;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Option<Uri> getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = Option.ofObj(picture).map(Uri::parse);
  }

  public Option<String> getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = Option.ofObj(phone);
  }

  public List<String> getComments() {
    return comments;
  }

  public void setComments(List<String> comments) {
    this.comments = comments;
  }
}
