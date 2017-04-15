package com.alorma.diary.data.diary.ds;

import java.util.List;

public class Diary {
  private int id;
  private Contact contact;
  private List<Entry> entries;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }

  public List<Entry> getEntries() {
    return entries;
  }

  public void setEntries(List<Entry> entries) {
    this.entries = entries;
  }
}
