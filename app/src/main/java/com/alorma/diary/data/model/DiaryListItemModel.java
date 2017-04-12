package com.alorma.diary.data.model;

import polanski.option.Option;

public class DiaryListItemModel {
  private ContactListItemModel contact;

  public Option<ContactListItemModel> getContact() {
    return Option.ofObj(contact);
  }

  public void setContact(ContactListItemModel contact) {
    this.contact = contact;
  }
}
