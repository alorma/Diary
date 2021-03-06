package com.alorma.diary.data.diary;

import com.alorma.diary.data.diary.agent.DiariesAgent;
import com.alorma.diary.data.model.DiaryItemModel;
import io.reactivex.Single;
import java.util.UUID;
import javax.inject.Inject;

public class GetDiaryUseCase {

  private DiariesAgent diariesAgent;

  @Inject
  public GetDiaryUseCase(DiariesAgent diariesAgent) {
    this.diariesAgent = diariesAgent;
  }

  public Single<DiaryItemModel> getDiary(UUID id) {
    return diariesAgent.getDiary(id);
  }
}
