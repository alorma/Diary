package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class AddDiaryPresenter {

  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;

  @Inject
  public AddDiaryPresenter(@MainScheduler Scheduler mainScheduler,
      ErrorTracker errorTracker) {

    this.mainScheduler = mainScheduler;
    this.errorTracker = errorTracker;
  }
}
