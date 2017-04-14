package com.alorma.diary.data.model;

import polanski.option.Option;

public class DiaryListItemModel {
  private int id;
  private ContactListItemModel contact;
  private EntryItemModel lastEntry;

  public Option<ContactListItemModel> getContact() {
    return Option.ofObj(contact);
  }

  public void setContact(ContactListItemModel contact) {
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
