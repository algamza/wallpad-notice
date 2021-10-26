package com.wallpad.notice.view.common;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class DataBindingAdapter {
    @BindingAdapter("goneUnless")
    public static void goneUnless(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("isBold")
    public static void isBold(TextView view, boolean bold) {
        if ( bold ) view.setTypeface(null, Typeface.BOLD);
        else view.setTypeface(null, Typeface.NORMAL);
    }

    @BindingAdapter("background")
    public static void setBackground(View view, int id) {
        if ( id == 0 ) return;
        view.setBackgroundResource(id);
    }

    @BindingAdapter("text")
    public static void setText(TextView view, int id) {
        if ( id == 0 ) return;
        view.setText(id);
    }

    @BindingAdapter("thumbnail")
    public static void setThumbnail(ImageView view, String path) {
        if ( path == null || path.length() == 0 ) return;
        try {
            if ( path.contains("mp4") ) {
                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
                Matrix matrix = new Matrix();
                assert thumb != null;
                Bitmap bitmap = Bitmap.createBitmap(thumb, 0, 0,
                        thumb.getWidth(), thumb.getHeight(), matrix, true);
                Glide.with(view.getContext()).load(bitmap).into(view);
            } else {
                Glide.with(view.getContext()).load(path).override(view.getWidth(), view.getHeight()).into(view);
            }
        } catch (Exception e) {
            Log.d("DataBindingAdapter", e.toString());
        }
    }

    @BindingAdapter("src")
    public static void setSrc(ImageView view, int resId) {
        if ( resId == 0 ) return;
        Glide.with(view.getContext()).load(resId).into(view);
    }

    @BindingAdapter("recycler_width")
    public static void recyclerWidth(ViewGroup viewGroup, float width) {
        if ( width == 0 || viewGroup == null ) return;
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        layoutParams.width = (int)width; //(int)viewGroup.getContext().getResources().getDimension(width);
        viewGroup.setLayoutParams(layoutParams);
    }

    @BindingAdapter("textStyle")
    public static void textStyle(TextView textView, int type) {
        switch (type) {
            case 0: {
                textView.setTypeface(null, Typeface.NORMAL);
                break;
            }
            case 1: {
                textView.setTypeface(null, Typeface.BOLD);
                break;
            }
        }
    }
}