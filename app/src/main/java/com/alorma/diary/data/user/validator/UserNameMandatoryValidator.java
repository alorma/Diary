package com.alorma.diary.data.user.validator;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.exception.DiaryValidationContactException;
import com.alorma.diary.data.exception.user.validation.UserValidationNameException;
import com.alorma.diary.data.model.ContactItemModel;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class UserNameMandatoryValidator implements Validator<ContactItemModel> {

  private Scheduler scheduler;

  public UserNameMandatoryValidator(@ComputationScheduler Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  @Override
  public Completable validate(ContactItemModel model) {
    return Completable.defer(() -> {
      if (model == null) {
        return Completable.error(new DiaryValidationContactException());
      }
      if (model.getName() == null) {
        return Completable.error(new UserValidationNameException());
      }
      return Completable.complete();
    }).subscribeOn(scheduler);
  }
}
