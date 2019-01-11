package amir.digital.paper.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

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
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    Context context;
    private ArrayList<NewsModel.Article> newsList;
    private NewsClickListener newsClickListener;
    private SaveClickListener saveClickListener;
    private ShareClickListener shareClickListener;
    private NewsModel.Article model;

    public HomeAdapter(Context context, ArrayList<NewsModel.Article> newsList, NewsClickListener newsClickListener, SaveClickListener saveClickListener, ShareClickListener shareClickListener) {
        this.context = context;
        this.newsList = newsList;
        this.newsClickListener = newsClickListener;
        this.saveClickListener = saveClickListener;
        this.shareClickListener = shareClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        model = newsList.get(position);

        holder.title.setText(model.getTitle());

        holder.description.setText(model.getDescription());
        if(model.getAuthor()!=null){
            holder.author.setText(model.getAuthor());
        }

        holder.url.setText(model.getUrl());

        if (model.getPublishTime() !=null) {
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
                        newsClickListener.onNewsClick(model);
                    }
                });

//        holder.save
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        saveClickListener.onSaveClick(model);
//                    }
//                });
//
//        holder.share
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        shareClickListener.onShareClick(model);
//                    }
//                });
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

        @BindView(R.id.txt_title)
        TextView title;

        @BindView(R.id.athor)
        TextView author;

        @BindView(R.id.url)
        TextView url;

        @BindView(R.id.txt_date)
        TextView date;

        @BindView(R.id.txt_description)
        TextView description;

        View itemView;

        @BindView(R.id.img_article)
        ImageView image;

//        @BindView(R.id.save)
//        ImageView save;
//
//        @BindView(R.id.share)
//        ImageView share;




        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            itemView = view;

        }
    }

//    @OnClick(R.id.save)
//    void save(){
//
//    }
//
//    @OnClick(R.id.share)
//    void share(){
//
//    }

    public interface NewsClickListener {
        void onNewsClick(NewsModel.Article article);
    }

    public interface SaveClickListener {
        void onSaveClick(NewsModel.Article article);
    }

    public interface ShareClickListener {
        void onShareClick(NewsModel.Article article);
    }

}

