package com.alorma.diary.data.diary.dbmodel;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.Table;
import io.reactivex.annotations.Nullable;
import java.util.List;

@Table
public class Contact {

  @Column(name = "_id", primaryKey = true, notNull = true, autoIncrement = true)
  private long id;

  @Column
  private long diaryId;

  //@Column
  private List<String> comments;

  @Column
  private String name;

  @Column
  private String phone;

  @Column
  @Nullable
  private String picture;

  public Contact() {

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
