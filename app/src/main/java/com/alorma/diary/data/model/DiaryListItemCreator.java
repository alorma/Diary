package com.alorma.diary.data.model;

public class DiaryListItemCreator {
  private Integer id;
  private ContactItemModel contact;
  private String name;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
