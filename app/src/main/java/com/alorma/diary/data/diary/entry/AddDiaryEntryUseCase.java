package com.alorma.diary.data.diary.entry;

import com.alorma.diary.data.diary.agent.EntryAgent;
import com.alorma.diary.data.model.EntryItemModel;
import io.reactivex.Completable;
import java.util.UUID;
import javax.inject.Inject;

public class AddDiaryEntryUseCase {

  private EntryAgent entryAgent;

  @Inject
  public AddDiaryEntryUseCase(EntryAgent entryAgent) {
    this.entryAgent = entryAgent;
  }

  public Completable addEntry(UUID id, EntryItemModel entryItemModel) {
    return entryAgent.addEntry(id, entryItemModel);
  }
}
