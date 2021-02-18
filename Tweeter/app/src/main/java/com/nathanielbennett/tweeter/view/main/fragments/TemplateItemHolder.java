package com.nathanielbennett.tweeter.view.main.fragments;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

abstract public class TemplateItemHolder<Item> extends RecyclerView.ViewHolder {

    public static final int ITEM_VIEW_TYPE = 1;
    public static final int STORY_VIEW_TYPE = 2;

    public TemplateItemHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindItem(Item itemToBind);
}
