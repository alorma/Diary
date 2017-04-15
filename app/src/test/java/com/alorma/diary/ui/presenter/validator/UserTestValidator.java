package com.alorma.diary.ui.presenter.validator;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.model.ContactItemModel;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class UserTestValidator implements Validator<ContactItemModel> {
  @Override
  public Completable validate(ContactItemModel contactItemModel) {
    return Completable.complete().subscribeOn(Schedulers.trampoline());
  }
}
