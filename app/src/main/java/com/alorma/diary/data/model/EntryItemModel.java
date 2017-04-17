package com.alorma.diary.data.model;

import com.alorma.diary.data.diary.dbmodel.EntryType;
import polanski.option.Option;

public class EntryItemModel {
  private String subject;
  private String content;
  private long postedDate;
  private EntryType entryType;

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

  public void setEntryType(EntryType entryType) {
    this.entryType = entryType;
  }

  public EntryType getEntryType() {
    return entryType;
  }
}
