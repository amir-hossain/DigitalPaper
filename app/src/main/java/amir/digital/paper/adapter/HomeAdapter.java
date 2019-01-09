package amir.digital.paper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import amir.digital.paper.R;
import amir.digital.paper.model.NewsModel;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    Context context;
    private ArrayList<NewsModel.Article> newsList;
    private NewsClickListener listener;

    public HomeAdapter(Context context, ArrayList<NewsModel.Article> newsList, NewsClickListener listener) {
        this.newsList = newsList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewsModel.Article model = newsList.get(position);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.author.setText(model.getAuthor());
        holder.url.setText(model.getUrl());
        if (model.getPublishTime() == null || model.getPublishTime().isEmpty()) {
            holder.date.setText("");
        } else {
            String date = parseDate(model.getPublishTime());
            holder.date.setText(date);

        }

        if (model.getImage() != null) {

            Glide.with(context)
                    .load(model.getImage())
                    .into(holder.image);
        }

        holder.itemView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onNewsClick(model);
                    }
                });
    }

    private String parseDate(String unFormattedDate) {
        try {
            DateFormat df;
            if (unFormattedDate.endsWith("Z")) {
                df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            } else {
                df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            }

            Date date = df.parse(unFormattedDate);
            df = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            String d = df.format(date);
            return d;
        } catch (ParseException e) {
            Log.i("f", "f");
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, author, url;
        TextView date;
        TextView description;
        View itemView;
        ImageView image;


        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            title = view.findViewById(R.id.txt_title);
            author = view.findViewById(R.id.athor);
            url = view.findViewById(R.id.url);
            date = view.findViewById(R.id.txt_date);
            description = view.findViewById(R.id.txt_description);
            image = view.findViewById(R.id.img_article);
        }
    }

    public interface NewsClickListener {
        void onNewsClick(NewsModel.Article article);
    }

}

