package com.alorma.diary.data.model;

import java.util.UUID;

public class DiaryListItemCreator {
  private UUID id;
  private ContactItemModel contact;
  private String name;

  public ContactItemModel getContact() {
    return contact;
  }

  public void setContact(ContactItemModel contact) {
    this.contact = contact;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DiaryListItemCreator) {
      return id == ((DiaryListItemCreator) obj).getId();
    }
    return false;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
