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
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import polanski.option.Option;

public class DiaryItemAdapter extends RecyclerView.Adapter<DiaryItemAdapter.Holder> {

  private final List<DiaryListItemModel> items;
  private LayoutInflater inflater;

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

  private void onBindViewHolder(Holder holder, DiaryListItemModel model) {
    holder.bind(model);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public void add(DiaryListItemModel item) {
    items.add(item);
    notifyItemInserted(items.size() - 1);
  }

  public void addAll(Collection<? extends DiaryListItemModel> items) {
    int size = items.size();
    this.items.addAll(items);
    notifyItemRangeInserted(size - 1, this.items.size());
  }

  public void remove(int position) {
    items.remove(position);
    notifyItemRemoved(position);
  }

  public class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.text) TextView contactName;
    @BindView(R.id.entryTitle) TextView entryTitle;
    @BindView(R.id.entryContent) TextView entryContent;

    public Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(DiaryListItemModel model) {
      model.getContact().ifSome(this::handleContact);
      model.getLastEntry().ifSome(this::handleEntry);
    }

    // region Contact
    private void handleContact(ContactListItemModel contactListItemModel) {
      setContactName(contactListItemModel);

      contactListItemModel.getComments()
          .filter(strings -> !strings.isEmpty())
          .match(this::handleComments, this::handleNoComments);
    }

    private void setContactName(ContactListItemModel contactListItemModel) {
      contactName.setText(contactListItemModel.getName());
    }

    @NonNull
    private Option<List<String>> handleComments(List<String> strings) {
      contactName.setText(contactName.getText() + " -> " + strings.size() + " comments");
      return Option.ofObj(strings);
    }

    @NonNull
    private Option<Object> handleNoComments() {
      contactName.setText(contactName.getText() + " -> No comments");
      return Option.none();
    }
    //endregion

    //region Entry
    private void handleEntry(EntryItemModel entryModel) {
      entryModel.getSubject().ifSome(title -> entryTitle.setText(title));
      entryContent.setText(entryModel.getContent());
    }
    //endregion
  }
}
