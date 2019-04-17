package com.artpropp.bakingapp.util;

import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

public class ViewBindings {

    @BindingAdapter("imageUrl")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {

            Picasso.get()
                    .load(imageUrl.trim())
                    .into(imageView, new Callback() {

                        @Override
                        public void onSuccess() {
                            // nothing else to do here
                        }

                        @Override
                        public void onError(Exception e) {
                            hideImageView(imageView);
                        }
                    });
        } else {
            hideImageView(imageView);
        }
    }

    private static void hideImageView(ImageView imageView) {
        imageView.setVisibility(View.GONE);
        ViewParent parent = imageView.getParent();
        if (parent instanceof View) {
            View cardView = (View) parent;
            cardView.setVisibility(View.GONE);
        }
    }
}
