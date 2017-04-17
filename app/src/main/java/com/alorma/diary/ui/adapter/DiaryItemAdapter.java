package com.alorma.diary.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.ContactItemModel;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import polanski.option.Option;

public class DiaryItemAdapter extends RecyclerView.Adapter<DiaryItemAdapter.Holder> {

  private final List<DiaryItemModel> items;
  private LayoutInflater inflater;
  private Callback callback;

  public DiaryItemAdapter(LayoutInflater inflater) {
    this.inflater = inflater;
    items = new ArrayList<>();
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new Holder(inflater.inflate(R.layout.row_diary_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    onBindViewHolder(holder, items.get(position));
  }

  private void onBindViewHolder(Holder holder, DiaryItemModel model) {
    holder.bind(model);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public void add(DiaryItemModel item) {
    items.add(item);
    notifyItemInserted(items.size() - 1);
  }

  public void addAll(Collection<? extends DiaryItemModel> items) {
    int size = items.size();
    this.items.addAll(items);
    notifyItemRangeInserted(size - 1, this.items.size());
  }

  public void remove(int position) {
    items.remove(position);
    notifyItemRemoved(position);
  }

  public void clear() {
    int size = items.size();
    items.clear();
    notifyItemRangeRemoved(0, size);
  }

  public Option<Callback> getCallback() {
    return Option.ofObj(callback);
  }

  public void setCallback(Callback callback) {
    this.callback = callback;
  }

  public interface Callback {
    void onDiaryItemCLick(DiaryItemModel itemModel);
  }

  public class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.text) TextView contactName;
    @BindView(R.id.entryTitle) TextView entryTitle;
    @BindView(R.id.entryContent) TextView entryContent;

    public Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(v -> onDiaryClick(items.get(getAdapterPosition())));
    }

    private void onDiaryClick(DiaryItemModel itemModel) {
      getCallback().ifSome(callback -> callback.onDiaryItemCLick(itemModel));
    }

    public void bind(DiaryItemModel model) {
      model.getContact().ifSome(this::handleContact);
      model.getLastEntry().ifSome(this::handleEntry).ifNone(this::handleNoEntries);
    }

    // region User
    private void handleContact(ContactItemModel contactItemModel) {
      setContactName(contactItemModel);

      contactItemModel.getComments()
          .filter(strings -> !strings.isEmpty())
          .ifSome(this::handleComments).ifNone(this::handleNoComments);
    }

    private void setContactName(ContactItemModel contactItemModel) {
      contactName.setText(contactItemModel.getName());
    }

    @NonNull
    private void handleComments(List<String> strings) {
      contactName.setText(contactName.getText() + " -> " + strings.size() + " comments");
    }

    @NonNull
    private void handleNoComments() {
      contactName.setText(contactName.getText() + " -> No comments");
    }
    //endregion

    //region Entry
    private void handleEntry(EntryItemModel entryModel) {
      entryModel.getSubject().ifSome(title -> entryTitle.setText(title)).ifNone(() -> {
        entryTitle.setText("No subject");
      });
      entryContent.setText(entryModel.getEntryType().name());
    }

    private void handleNoEntries() {
      entryContent.setText("No entries");
    }
    //endregion
  }
}
