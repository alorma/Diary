package com.alorma.diary.data.diary.dbmodel;

import java.util.List;
import java.util.UUID;

public class Diary {
  public UUID id;
  public User user;
  private String name;
  public List<Entry> entries;

  public Diary() {

  }

  public UUID getId() {
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

  public void setId(UUID id) {
    this.id = id;
  }
}
