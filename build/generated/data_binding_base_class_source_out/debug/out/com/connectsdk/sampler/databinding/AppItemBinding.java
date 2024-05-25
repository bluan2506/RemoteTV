// Generated by view binder compiler. Do not edit!
package com.connectsdk.sampler.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.connectsdk.sampler.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AppItemBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView itemCheck;

  @NonNull
  public final TextView itemSubTitle;

  @NonNull
  public final TextView itemTitle;

  private AppItemBinding(@NonNull RelativeLayout rootView, @NonNull ImageView itemCheck,
      @NonNull TextView itemSubTitle, @NonNull TextView itemTitle) {
    this.rootView = rootView;
    this.itemCheck = itemCheck;
    this.itemSubTitle = itemSubTitle;
    this.itemTitle = itemTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AppItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AppItemBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.app_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AppItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.itemCheck;
      ImageView itemCheck = ViewBindings.findChildViewById(rootView, id);
      if (itemCheck == null) {
        break missingId;
      }

      id = R.id.itemSubTitle;
      TextView itemSubTitle = ViewBindings.findChildViewById(rootView, id);
      if (itemSubTitle == null) {
        break missingId;
      }

      id = R.id.itemTitle;
      TextView itemTitle = ViewBindings.findChildViewById(rootView, id);
      if (itemTitle == null) {
        break missingId;
      }

      return new AppItemBinding((RelativeLayout) rootView, itemCheck, itemSubTitle, itemTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
