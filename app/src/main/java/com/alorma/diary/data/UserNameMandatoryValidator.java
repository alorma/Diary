package com.alorma.diary.data;

import com.alorma.diary.data.exception.ValidationException;
import com.alorma.diary.data.exception.user.validation.UserValidationNameException;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class UserNameMandatoryValidator implements Validator<ContactListItemModel> {

  private Scheduler scheduler;

  public UserNameMandatoryValidator(@ComputationScheduler Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  @Override
  public Completable validate(ContactListItemModel model) {
    return Completable.defer(() -> {
      if (model == null) {
        return Completable.error(new ValidationException());
      } else if (model.getName() == null) {
        return Completable.error(new UserValidationNameException());
      }
      return Completable.complete();
    }).subscribeOn(scheduler);
  }
}
