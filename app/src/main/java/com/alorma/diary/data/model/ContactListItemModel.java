package com.alorma.diary.data.model;

import android.net.Uri;
import java.util.List;
import polanski.option.Option;

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

  public Option<Uri> getPicture() {
    return Option.ofObj(picture);
  }

  public void setPicture(Uri picture) {
    this.picture = picture;
  }

  public Option<String> getPhone() {
    return Option.ofObj(phone);
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Option<List<String>> getComments() {
    return Option.ofObj(comments);
  }

  public void setComments(List<String> comments) {
    this.comments = comments;
  }
}
