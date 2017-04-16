package com.alorma.diary.data.diary.dbmodel;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.ForeignKey;
import com.afollestad.inquiry.annotations.Table;
import java.util.List;

@Table
public class Diary {
  @Column(name = "_id", primaryKey = true, notNull = true, autoIncrement = true)
  private long id;

  @ForeignKey(tableName = "contacts", foreignColumnName = "diaryId")
  private Contact contact;

  @ForeignKey(tableName = "entrys", foreignColumnName = "diaryId")
  private List<Entry> entries;

  public Diary() {

  }

  public long getId() {
    return id;
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
