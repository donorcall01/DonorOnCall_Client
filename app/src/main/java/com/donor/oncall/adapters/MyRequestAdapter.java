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
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_request_list, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        RequestItem feedItem = mRequestItem.get(i);


        //Setting text view title
       // customViewHolder.textView.setText(Html.fromHtml(mRequestItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != mRequestItem ? mRequestItem.size() : 0);
    }
}
