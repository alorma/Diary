package com.alorma.diary.data.model;

public class DiaryListItemCreator {
  private Integer id;
  private ContactListItemModel contact;

  public ContactListItemModel getContact() {
    return contact;
  }

  public void setContact(ContactListItemModel contact) {
    this.contact = contact;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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
