package com.alorma.diary.data.diary;

import com.alorma.diary.data.diary.agent.DiariesAgent;
import com.alorma.diary.data.model.DiaryListItemModel;
import io.reactivex.Flowable;
import javax.inject.Inject;

public class DiaryListUseCase {

  private DiariesAgent diariesAgent;

  @Inject
  public DiaryListUseCase(DiariesAgent diariesAgent) {
    this.diariesAgent = diariesAgent;
  }

  public Flowable<DiaryListItemModel> getDiaries() {
    return diariesAgent.getDiaries();
  }
}
