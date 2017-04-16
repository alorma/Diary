package com.alorma.diary.data.model;

import java.util.List;
import polanski.option.Option;

public class DiaryItemModel {
  private int id;
  private ContactItemModel contact;
  private EntryItemModel lastEntry;
  private List<EntryItemModel> entries;

  public Option<ContactItemModel> getContact() {
    return Option.ofObj(contact);
  }

  public void setContact(ContactItemModel contact) {
    this.contact = contact;
  }

  public Option<EntryItemModel> getLastEntry() {
    return Option.ofObj(lastEntry);
  }

  public void setLastEntry(EntryItemModel lastEntry) {
    this.lastEntry = lastEntry;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DiaryItemModel) {
      return id == ((DiaryItemModel) obj).getId();
    }
    return false;
  }

  public Option<List<EntryItemModel>> getEntries() {
    return Option.ofObj(entries);
  }

  public void setEntries(List<EntryItemModel> entries) {
    this.entries = entries;
  }
}
