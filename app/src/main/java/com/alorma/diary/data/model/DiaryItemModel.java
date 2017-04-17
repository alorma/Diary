package com.alorma.diary.data.model;

import java.util.List;
import java.util.UUID;
import polanski.option.Option;

public class DiaryItemModel {
  private UUID id;
  private ContactItemModel contact;
  private EntryItemModel lastEntry;
  private List<EntryItemModel> entries;
  private String name;

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

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
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

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
