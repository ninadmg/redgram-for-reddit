package com.matie.redgram.ui.thread.views.widgets.comment;

import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.annotation.ExpandableItemStateFlags;
import com.matie.redgram.R;
import com.matie.redgram.ui.common.views.widgets.ExpandableIndicator;

/**
 * Created by matie on 2016-01-30.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    @ExpandableItemStateFlags
    private int flags;

    //main parent view
    private CommentBaseItemView commentBaseItemView;
    //child view
    private FrameLayout container;

    public CommentViewHolder(CommentBaseItemView itemView) {
        super(itemView);
        this.commentBaseItemView = itemView;
        this.container = (FrameLayout)itemView.findViewById(R.id.container);
    }

    public CommentBaseItemView getCommentItemView() {
        return commentBaseItemView;
    }

    public void setCommentItemView(CommentBaseItemView commentBaseItemView) {
        this.commentBaseItemView = commentBaseItemView;
    }

    public FrameLayout getContainer() {
        return container;
    }

    public void setContainer(FrameLayout container) {
        this.container = container;
    }

}
