package com.alorma.diary.ui.presenter.validator;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.model.ContactListItemModel;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class UserTestValidator implements Validator<ContactListItemModel> {
  @Override
  public Completable validate(ContactListItemModel contactListItemModel) {
    return Completable.complete().subscribeOn(Schedulers.trampoline());
  }
}
