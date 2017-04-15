package com.alorma.diary.data.diary.entry;

import com.alorma.diary.data.diary.agent.EntryAgent;
import com.alorma.diary.data.model.EntryItemModel;
import io.reactivex.Completable;
import javax.inject.Inject;

public class AddDiaryEntryUseCase {

  private EntryAgent entryAgent;

  @Inject
  public AddDiaryEntryUseCase(EntryAgent entryAgent) {
    this.entryAgent = entryAgent;
  }

  public Completable getDiary(int id, EntryItemModel entryItemModel) {
    return entryAgent.addDiary(id, entryItemModel);
  }
}
