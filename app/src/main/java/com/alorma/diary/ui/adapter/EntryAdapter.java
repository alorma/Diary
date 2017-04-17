package com.alorma.diary.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.diary.dbmodel.EntryType;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.data.model.EntryMessageItemModel;
import com.alorma.diary.data.model.EntryPhotoItemModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.Holder> {

  private static final int VIEW_TYPE_MESSAGE = 1;
  private static final int VIEW_TYPE_PHOTO = 2;

  private List<EntryItemModel> models;

  private LayoutInflater inflater;

  public EntryAdapter(LayoutInflater inflater) {
    this.models = new ArrayList<>();
    this.inflater = inflater;
  }

  @Override
  public EntryAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_MESSAGE) {
      return new MessageHolder(inflater.inflate(R.layout.row_entry_message, parent, false));
    } else if (viewType == VIEW_TYPE_PHOTO) {
      return new PhotoHolder(inflater.inflate(R.layout.row_entry_photo, parent, false));
    }
    return new EmptyHolder(inflater.inflate(R.layout.row_entry_empty, parent, false));
  }

  @Override
  public void onBindViewHolder(EntryAdapter.Holder holder, int position) {
    holder.populate(models.get(position));
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  @Override
  public int getItemViewType(int position) {
    EntryType entryType = models.get(position).getEntryType();
    if (entryType == EntryType.MESSAGE) {
      return VIEW_TYPE_MESSAGE;
    } else if (entryType == EntryType.PHOTO) {
      return VIEW_TYPE_PHOTO;
    }
    return super.getItemViewType(position);
  }

  public void addAll(Collection<EntryItemModel> models) {
    int size = models.size();
    this.models.addAll(models);
    notifyItemRangeInserted(size - 1, this.models.size());
  }

  public class MessageHolder extends Holder {

    @BindView(R.id.messageSubject) TextView messageSubjectTextView;
    @BindView(R.id.messageContent) TextView messageContentTextView;

    public MessageHolder(View itemView) {
      super(itemView);
    }

    @Override
    public void populate(EntryItemModel model) {
      if (model instanceof EntryMessageItemModel) {
        model.getSubject().ifSome(s -> messageSubjectTextView.setText(s));
        String content = ((EntryMessageItemModel) model).getContent();
        messageContentTextView.setText(content);
      }
    }
  }

  public class PhotoHolder extends Holder {

    @BindView(R.id.photoImage) ImageView photoImage;
    @BindView(R.id.photoName) TextView photoName;
    @BindView(R.id.photoDescription) TextView photoDescription;

    public PhotoHolder(View itemView) {
      super(itemView);
    }

    @Override
    public void populate(EntryItemModel model) {
      if (model instanceof EntryPhotoItemModel) {
        EntryPhotoItemModel photoItemModel = (EntryPhotoItemModel) model;
        photoItemModel.getPhotoDescription().ifSome(s -> photoDescription.setText(s));
        photoItemModel.getPhotoName().ifSome(s -> photoName.setText(s));

        if (photoItemModel.getUri() != null) {
          Glide.with(photoImage.getContext())
              .load(photoItemModel.getUri())
              .diskCacheStrategy(DiskCacheStrategy.ALL)
              .dontAnimate()
              .into(photoImage);
        }
      }
    }
  }

  public class EmptyHolder extends Holder {

    public EmptyHolder(View itemView) {
      super(itemView);
    }

    @Override
    public void populate(EntryItemModel model) {

    }
  }

  public abstract class Holder extends RecyclerView.ViewHolder {
    public Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public abstract void populate(EntryItemModel model);
  }
}
