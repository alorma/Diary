package com.alorma.diary.data.model;

import polanski.option.Option;

public class EntryItemModel {
  private String subject;
  private String content;
  private long postedDate;

  public Option<String> getSubject() {
    return Option.ofObj(subject);
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

  public long getPostedDate() {
    return postedDate;
  }

  public void setPostedDate(long postedDate) {
    this.postedDate = postedDate;
  }
}
