package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.AddDiaryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.exception.ValidationException;
import com.alorma.diary.data.exception.user.validation.UserValidationNameException;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.data.model.DiaryListItemCreator;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.di.qualifiers.MainScheduler;
import com.alorma.diary.di.qualifiers.user.UserValidator;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class AddDiaryPresenter {

  private AddDiaryUseCase addDiaryUseCase;
  private Validator<ContactListItemModel> userValidator;
  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;
  private Screen screen;

  @Inject
  public AddDiaryPresenter(AddDiaryUseCase addDiaryUseCase,
      @UserValidator Validator<ContactListItemModel> userValidator,
      @MainScheduler Scheduler mainScheduler,
      ErrorTracker errorTracker) {
    this.addDiaryUseCase = addDiaryUseCase;
    this.userValidator = userValidator;

    this.mainScheduler = mainScheduler;
    this.errorTracker = errorTracker;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public Screen getScreen() {
    return screen != null ? screen : new Screen.Null();
  }

  public void addDiary(DiaryListItemCreator itemModel) {
    Single.fromCallable(() -> validate(itemModel))
        .flatMap(aBoolean -> map(itemModel))
        .flatMapCompletable(item -> addDiaryUseCase.addDiary(item))
        .doOnSubscribe(this::onAddItemStart)
        .doOnTerminate(this::onAddItemTerminate)
        .observeOn(mainScheduler)
        .subscribe(this::onAddItemComplete, this::onAddItemFail);
  }

  private boolean validate(DiaryListItemCreator itemModel)
      throws com.alorma.diary.data.exception.ValidationException {
    return userValidator.validate(itemModel.getContact());
  }

  private Single<DiaryListItemModel> map(DiaryListItemCreator itemModel) {
    return Single.fromCallable(() -> {
      ContactListItemModel contact = itemModel.getContact();
      DiaryListItemModel diaryListItemModel = new DiaryListItemModel();
      diaryListItemModel.setContact(contact);
      return diaryListItemModel;
    });
  }

  public void stop() {
    this.screen = new Screen.Null();
  }

  private void onAddItemStart(Disposable disposable) {
    getScreen().startLoading();
  }

  private void onAddItemTerminate() {
    getScreen().stopLoading();
  }

  private void onAddItemComplete() {
    getScreen().closeScreen();
  }

  private void onAddItemFail(Throwable throwable) {
    if (throwable instanceof ValidationException) {
      if (throwable instanceof UserValidationNameException) {
        getScreen().showInvalidName();
      }
    } else {
      getScreen().showError();
      errorTracker.trackError(throwable);
    }
  }

  public interface Screen {
    void startLoading();

    void stopLoading();

    void closeScreen();

    void showError();

    void showInvalidName();

    class Null implements Screen {

      @Override
      public void startLoading() {

      }

      @Override
      public void stopLoading() {

      }

      @Override
      public void closeScreen() {

      }

      @Override
      public void showError() {

      }

      @Override
      public void showInvalidName() {

      }
    }
  }
}
