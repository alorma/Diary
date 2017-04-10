package com.alorma.diary.data.diary;

import com.alorma.diary.data.diary.agent.DiaryListAgent;
import com.alorma.diary.data.model.DiaryListItemModel;
import io.reactivex.Flowable;
import javax.inject.Inject;

public class DiaryListUseCase {

  private DiaryListAgent diaryListAgent;

  @Inject
  public DiaryListUseCase(DiaryListAgent diaryListAgent) {
    this.diaryListAgent = diaryListAgent;
  }

  public Flowable<DiaryListItemModel> getDiaries() {
    return diaryListAgent.getDiaries();
  }
}
