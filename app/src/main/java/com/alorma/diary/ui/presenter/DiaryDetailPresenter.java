package com.alorma.diary.ui.presenter;

import com.alorma.diary.ResourceLifeCycle;
import com.alorma.diary.data.diary.GetDiaryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.exception.DiaryNotFoundException;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class DiaryDetailPresenter {

  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;
  private GetDiaryUseCase getDiaryUseCase;
  private ResourceLifeCycle resourceLifeCycle;
  private Screen screen;
  private DiaryItemModel diary;

  @Inject
  public DiaryDetailPresenter(GetDiaryUseCase getDiaryUseCase,
      @MainScheduler Scheduler mainScheduler,
      ResourceLifeCycle resourceLifeCycle,
      ErrorTracker errorTracker) {
    this.getDiaryUseCase = getDiaryUseCase;
    this.mainScheduler = mainScheduler;
    this.resourceLifeCycle = resourceLifeCycle;
    this.errorTracker = errorTracker;
  }

  public Screen getScreen() {
    return screen != null ? screen : new Screen.Null();
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public void load(int id) {
    getDiaryUseCase.getDiary(id)
        .observeOn(mainScheduler)
        .doOnSubscribe(disposable -> getScreen().startLoading())
        .subscribe(this::onDiaryLoaded, this::loadDiaryError);
  }

  private void onDiaryLoaded(DiaryItemModel diaryItemModel) {
    diary = diaryItemModel;
    getScreen().stopLoading();
    getScreen().showDiary(diaryItemModel);
  }

  private void loadDiaryError(Throwable throwable) {
    getScreen().showError();
    errorTracker.trackError(throwable);
  }

  public void stop() {
    this.screen = new Screen.Null();
  }

  private Completable validateDiary() {
    return Completable.defer(() -> {
      if (DiaryDetailPresenter.this.diary == null) {
        return Completable.error(new DiaryNotFoundException());
      }
      return Completable.complete();
    });
  }

  public void requestNewEntry() {
    validateDiary().observeOn(mainScheduler).subscribe(() -> {
      getScreen().openNewEntry(diary.getId());
    }, throwable -> getScreen().showError());
  }

  public void destroy() {
    resourceLifeCycle.destroy();
  }

  public interface Screen {
    void startLoading();

    void showDiary(DiaryItemModel itemModel);

    void showEntryAdded();

    void stopLoading();

    void showError();

    void showErrorAddEntry();

    void openNewEntry(long id);

    class Null implements Screen {

      @Override
      public void startLoading() {

      }

      @Override
      public void showDiary(DiaryItemModel itemModel) {

      }

      @Override
      public void showEntryAdded() {

      }

      @Override
      public void stopLoading() {

      }

      @Override
      public void showError() {

      }

      @Override
      public void showErrorAddEntry() {

      }

      @Override
      public void openNewEntry(long id) {

      }
    }
  }
}
