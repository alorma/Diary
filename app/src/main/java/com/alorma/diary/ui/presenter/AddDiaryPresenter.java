package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.AddDiaryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.exception.DiaryValidationContactException;
import com.alorma.diary.data.exception.ValidationException;
import com.alorma.diary.data.exception.user.validation.UserValidationNameException;
import com.alorma.diary.data.model.ContactItemModel;
import com.alorma.diary.data.model.DiaryListItemCreator;
import com.alorma.diary.di.qualifiers.MainScheduler;
import com.alorma.diary.di.qualifiers.user.UserValidator;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class AddDiaryPresenter {

  private AddDiaryUseCase addDiaryUseCase;
  private Validator<DiaryListItemCreator> diaryCreatorValidator;
  private Validator<ContactItemModel> userValidator;
  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;
  private Screen screen;

  @Inject
  public AddDiaryPresenter(AddDiaryUseCase addDiaryUseCase,
      Validator<DiaryListItemCreator> diaryCreatorValidator,
      @UserValidator Validator<ContactItemModel> userValidator,
      @MainScheduler Scheduler mainScheduler,
      ErrorTracker errorTracker) {
    this.addDiaryUseCase = addDiaryUseCase;
    this.diaryCreatorValidator = diaryCreatorValidator;
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
    validate(itemModel)
        .toSingleDefault(itemModel)
        .flatMapCompletable(item -> addDiaryUseCase.addDiary(item))
        .doOnSubscribe(this::onAddItemStart)
        .doOnTerminate(this::onAddItemTerminate)
        .observeOn(mainScheduler)
        .toSingleDefault(itemModel.getId())
        .subscribe(this::onAddItemComplete, this::onAddItemFail);
  }

  private Completable validate(DiaryListItemCreator itemModel) {
    Completable diary = diaryCreatorValidator.validate(itemModel);
    Completable user = userValidator.validate(itemModel.getContact());
    return Completable.mergeArray(diary, user);
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

  private void onAddItemComplete(int itemId) {
    getScreen().openDiaryScreenAndClose(itemId);
  }

  private void onAddItemFail(Throwable throwable) {
    if (throwable instanceof ValidationException) {
      if (throwable instanceof UserValidationNameException) {
        getScreen().showInvalidName();
      } else if (throwable instanceof DiaryValidationContactException) {
        getScreen().showContactInvalid();
      } else {
        getScreen().showError();
      }
    } else {
      getScreen().showError();
      errorTracker.trackError(throwable);
    }
  }

  public interface Screen {
    void startLoading();

    void stopLoading();

    void openDiaryScreenAndClose(int id);

    void showError();

    void showInvalidName();

    void showContactInvalid();

    class Null implements Screen {

      @Override
      public void startLoading() {

      }

      @Override
      public void stopLoading() {

      }

      @Override
      public void openDiaryScreenAndClose(int id) {

      }

      @Override
      public void showError() {

      }

      @Override
      public void showInvalidName() {

      }

      @Override
      public void showContactInvalid() {

      }
    }
  }
}
