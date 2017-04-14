package com.alorma.diary.data.model;

public class DiaryListItemCreator {
  private int id;
  private ContactListItemModel contact;

  public ContactListItemModel getContact() {
    return contact;
  }

  public void setContact(ContactListItemModel contact) {
    this.contact = contact;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DiaryListItemCreator) {
      return id == ((DiaryListItemCreator) obj).getId();
    }
    return false;
  }
}
