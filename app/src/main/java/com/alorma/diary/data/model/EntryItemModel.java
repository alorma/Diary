package com.alorma.diary.data.model;

import com.alorma.diary.data.diary.dbmodel.EntryType;
import polanski.option.Option;

public class EntryItemModel {
  private String subject;
  private long postedDate;
  private final EntryType entryType;

  public EntryItemModel(EntryType entryType) {
    this.entryType = entryType;
  }

  public Option<String> getSubject() {
    return Option.ofObj(subject);
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public long getPostedDate() {
    return postedDate;
  }

  public void setPostedDate(long postedDate) {
    this.postedDate = postedDate;
  }

  public EntryType getEntryType() {
    return entryType;
  }
}
