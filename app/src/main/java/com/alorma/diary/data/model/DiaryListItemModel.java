package com.alorma.diary.data.model;

import polanski.option.Option;

public class DiaryListItemModel {
  private int id;
  private ContactItemModel contact;
  private EntryItemModel lastEntry;

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
    if (obj instanceof DiaryListItemModel) {
      return id == ((DiaryListItemModel) obj).getId();
    }
    return false;
  }
}
