package com.example.nahumsin.donadif;

/**
 * Created by nahumsin on 25/11/16.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaImagen extends ArrayAdapter<String> {
    private String[] urls;
    private Bitmap[] bitmaps;
    private Activity context;

    public ListaImagen(Activity context, String[] urls, Bitmap[] bitmaps) {
        super(context, R.layout.list_item_view_familia, urls);
        this.context = context;
        this.urls= urls;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_view_familia, null, true);
        TextView textViewURL = (TextView) listViewItem.findViewById(R.id.textViewURL);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageDownloaded);

        textViewURL.setText(urls[position]);
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],180,120,false));
        return  listViewItem;
    }
}