package com.alorma.diary.data.diary;

import com.alorma.diary.data.diary.agent.DiariesAgent;
import com.alorma.diary.data.model.DiaryListItemCreator;
import com.alorma.diary.data.model.DiaryListItemModel;
import io.reactivex.Completable;
import javax.inject.Inject;

public class AddDiaryUseCase {

  private DiariesAgent diariesAgent;

  @Inject
  public AddDiaryUseCase(DiariesAgent diariesAgent) {
    this.diariesAgent = diariesAgent;
  }

  public Completable addDiary(DiaryListItemCreator model) {
    return diariesAgent.addDiary(model);
  }
}
