package com.wallpad.parking.view.common;


import android.graphics.Typeface;
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
}