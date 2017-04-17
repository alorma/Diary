package com.alorma.diary.data.model;

import android.net.Uri;
import com.alorma.diary.data.diary.dbmodel.EntryType;
import polanski.option.Option;

public class EntryPhotoItemModel extends EntryItemModel {
  private String photoName;
  private String photoDescription;
  private Uri uri;

  public EntryPhotoItemModel() {
    super(EntryType.PHOTO);
  }

  public Uri getUri() {
    return uri;
  }

  public void setUri(Uri uri) {
    this.uri = uri;
  }

  public Option<String> getPhotoName() {
    return Option.ofObj(photoName);
  }

  public void setPhotoName(String photoName) {
    this.photoName = photoName;
  }

  public Option<String> getPhotoDescription() {
    return Option.ofObj(photoDescription);
  }

  public void setPhotoDescription(String photoDescription) {
    this.photoDescription = photoDescription;
  }
}
