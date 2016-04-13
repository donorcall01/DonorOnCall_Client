package com.donor.oncall.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.donor.oncall.R;
import com.donor.oncall.RequestItem;

import java.util.List;

/**
 * Created by prashanth on 4/4/16.
 */
public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.CustomViewHolder>{

    private List<RequestItem> mRequestItem;
    private Context mContext;

    public MyRequestAdapter(Context context, List<RequestItem> feedItemList) {
        this.mRequestItem = feedItemList;
        this.mContext = context;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public CustomViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_request_list, null);
        return  new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        RequestItem feedItem = mRequestItem.get(i);
        customViewHolder.title.setText(feedItem.getTitle());
        customViewHolder.genre.setText(feedItem.getGenre());
        customViewHolder.year.setText(feedItem.getYear());

        //Setting text view title
       // customViewHolder.textView.setText(Html.fromHtml(mRequestItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != mRequestItem ? mRequestItem.size() : 0);
    }
}
