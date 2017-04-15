package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.diary.GetDiaryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class GetDiaryPresenter {

  private GetDiaryUseCase getDiaryUseCase;
  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;
  private Screen screen;

  @Inject
  public GetDiaryPresenter(GetDiaryUseCase getDiaryUseCase,
      @MainScheduler Scheduler mainScheduler,
      ErrorTracker errorTracker) {
    this.getDiaryUseCase = getDiaryUseCase;

    this.mainScheduler = mainScheduler;
    this.errorTracker = errorTracker;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public Screen getScreen() {
    return screen != null ? screen : new Screen.Null();
  }

  public void load(int id) {

  }

  public void stop() {
    this.screen = new Screen.Null();
  }

  public interface Screen {
    void startLoading();

    void showDiary(DiaryItemModel itemModel);

    void stopLoading();

    void showError();

    class Null implements Screen {

      @Override
      public void startLoading() {

      }

      @Override
      public void showDiary(DiaryItemModel itemModel) {

      }

      @Override
      public void stopLoading() {

      }

      @Override
      public void showError() {

      }
    }
  }
}
