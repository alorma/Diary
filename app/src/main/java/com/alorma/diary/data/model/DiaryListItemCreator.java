package com.alorma.diary.data.model;

public class DiaryListItemCreator {
  private Integer id;
  private ContactItemModel contact;

  public ContactItemModel getContact() {
    return contact;
  }

  public void setContact(ContactItemModel contact) {
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
