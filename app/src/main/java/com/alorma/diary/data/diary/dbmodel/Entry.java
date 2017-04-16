package com.alorma.diary.data.diary.dbmodel;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.Table;
import java.util.Date;

@Table
public class Entry {

  @Column(name = "_id", primaryKey = true, notNull = true, autoIncrement = true)
  private long id;

  @Column
  private long diaryId;

  @Column
  private Date date;

  @Column
  private String subject;

  @Column
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

  public long getDiaryId() {
    return diaryId;
  }
}
