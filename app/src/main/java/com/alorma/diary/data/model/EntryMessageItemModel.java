package com.alorma.diary.data.model;

import com.alorma.diary.data.diary.dbmodel.EntryType;

public class EntryMessageItemModel extends EntryItemModel {
  private String content;

  public EntryMessageItemModel() {
    super(EntryType.MESSAGE);
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
