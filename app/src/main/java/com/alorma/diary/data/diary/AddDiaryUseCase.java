package com.alorma.diary.data.diary;

import com.alorma.diary.data.diary.agent.DiariesAgent;
import com.alorma.diary.data.diary.dbmodel.Diary;
import com.alorma.diary.data.model.DiaryListItemCreator;
import io.reactivex.Single;
import javax.inject.Inject;

public class AddDiaryUseCase {

  private DiariesAgent diariesAgent;

  @Inject
  public AddDiaryUseCase(DiariesAgent diariesAgent) {
    this.diariesAgent = diariesAgent;
  }

  public Single<Diary> addDiary(DiaryListItemCreator model) {
    return diariesAgent.addDiary(model);
  }
}
