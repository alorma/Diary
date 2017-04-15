package com.alorma.diary.data.diary;

import com.alorma.diary.data.diary.agent.DiariesAgent;
import com.alorma.diary.data.model.DiaryItemModel;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetDiaryUseCase {

  private DiariesAgent diariesAgent;

  @Inject
  public GetDiaryUseCase(DiariesAgent diariesAgent) {
    this.diariesAgent = diariesAgent;
  }

  public Single<DiaryItemModel> addDiary(int id) {
    return diariesAgent.getDiary(id);
  }
}
