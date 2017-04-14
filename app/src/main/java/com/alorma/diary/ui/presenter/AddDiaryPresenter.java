package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.diary.AddDiaryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class AddDiaryPresenter {

  private AddDiaryUseCase addDiaryUseCase;
  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;
  private Screen screen;

  @Inject
  public AddDiaryPresenter(AddDiaryUseCase addDiaryUseCase, @MainScheduler Scheduler mainScheduler,
      ErrorTracker errorTracker) {
    this.addDiaryUseCase = addDiaryUseCase;

    this.mainScheduler = mainScheduler;
    this.errorTracker = errorTracker;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public Screen getScreen() {
    return screen != null ? screen : new Screen.Null();
  }

  public void addDiary(DiaryListItemModel itemModel) {
    addDiaryUseCase.addDiary(itemModel)
        .doOnSubscribe(this::onAddItemStart)
        .doOnTerminate(this::onAddItemTerminate)
        .subscribe(this::onAddItemComplete, this::onAddItemFail);
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
    getScreen().showError();
    errorTracker.trackError(throwable);
  }

  public interface Screen {
    void startLoading();

    void stopLoading();

    void closeScreen();

    void showError();

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
    }
  }
}
