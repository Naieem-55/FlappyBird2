package com.example.flappybird2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String listName[];
    int listImage[];
    String listScore[];
    LayoutInflater inflater;


    public CustomBaseAdapter(Context ctx,String[]name,int []image,String []score){
        this.context=ctx;
        this.listName=name;
        this.listImage=image;
        this.listScore=score;
        inflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        return MainActivity2.j;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView txtview= (TextView) view.findViewById(R.id.textView);
        ImageView imgview=(ImageView) view.findViewById(R.id.imageIcon);
        TextView txtView2=(TextView)view.findViewById(R.id.textView2);
        txtview.setText(listName[i]);
        imgview.setImageResource(listImage[i]);
        txtView2.setText(listScore[i]);
        return view;
    }
}

