package com.alorma.diary.data.diary.dbmodel;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.ForeignKey;
import com.afollestad.inquiry.annotations.Table;
import java.util.List;

@Table
public class Diary {
  @Column(name = "_id", primaryKey = true, notNull = true, autoIncrement = true)
  public long id;

  @ForeignKey(tableName = "user", foreignColumnName = "diaryId")
  public User user;

  @Column
  private String name;

  @ForeignKey(tableName = "entrys", foreignColumnName = "diaryId")
  public List<Entry> entries;

  public Diary() {

  }

  public long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Entry> getEntries() {
    return entries;
  }

  public void setEntries(List<Entry> entries) {
    this.entries = entries;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
