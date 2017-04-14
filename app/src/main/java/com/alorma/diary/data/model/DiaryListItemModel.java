package com.alorma.diary.data.model;

import polanski.option.Option;

public class DiaryListItemModel {
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
}
