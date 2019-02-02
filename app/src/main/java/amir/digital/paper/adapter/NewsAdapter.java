package amir.digital.paper.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
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
import java.util.List;

import amir.digital.paper.R;
import amir.digital.paper.model.NewsModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends PagedListAdapter<NewsModel.Article,NewsAdapter.MyViewHolder> {
    Context context;
    
    private NewsClickListener newsClickListener;
    private SaveClickListener saveClickListener;
    private ShareClickListener shareClickListener;

    private static DiffUtil.ItemCallback<NewsModel.Article> itemCallback=new DiffUtil.ItemCallback<NewsModel.Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsModel.Article oldItem, @NonNull NewsModel.Article newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsModel.Article oldItem, @NonNull NewsModel.Article newItem) {
            return oldItem.getUrl().equals(newItem.getUrl());
        }
    };

    public NewsAdapter(NewsClickListener newsClickListener, SaveClickListener saveClickListener, ShareClickListener shareClickListener) {
        super(itemCallback);
        this.newsClickListener = newsClickListener;
        this.saveClickListener = saveClickListener;
        this.shareClickListener = shareClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);


        this.context=parent.getContext();
        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsModel.Article article=getItem(position);
        holder.bindTo(article);
        
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

        public void bindTo(NewsModel.Article article) {
            title.setText(article.getTitle());

            description.setText(article.getDescription());
            if(article.getAuthor()!=null){
                author.setText(article.getAuthor());
            }

            url.setText(article.getUrl());

            if (article.getPublishTime() !=null) {
                String date = parseDate(article.getPublishTime());
                this.date.setText(date);
            }

            if (article.getImage() != null) {

                Glide.with(context)
                        .load(article.getImage())
                        .into(image);
            }

            itemView
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newsClickListener.onNewsClick(article);
                        }
                    });

//        save
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        saveClickListener.onSaveClick(article);
//                    }
//                });
//
//        share
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        shareClickListener.onShareClick(article);
//                    }
//                });
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

