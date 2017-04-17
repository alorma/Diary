package com.alorma.diary.data.diary.dbmodel;

import java.util.Date;

public class Entry {
  public long id;
  public Date date;
  public String subject;
  public String content;

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
}
