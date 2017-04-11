package com.alorma.diary.data.model;

import polanski.option.Option;

public class DiaryListItemModel {
  private Option<ContactListItemModel> contact;

  public Option<ContactListItemModel> getContact() {
    return contact;
  }

  public void setContact(ContactListItemModel contact) {
    this.contact = Option.ofObj(contact);
  }
}
