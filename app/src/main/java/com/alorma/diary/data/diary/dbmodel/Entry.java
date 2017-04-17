package com.alorma.diary.data.diary.dbmodel;

import java.util.Date;

public class Entry {
  private long id;
  private EntryType entryType;
  private Date date;
  private String subject;
  private String content;

  public Entry() {

  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public EntryType getEntryType() {
    return entryType;
  }

  public void setEntryType(EntryType entryType) {
    this.entryType = entryType;
  }
}
