package amir.digital.paper;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.model.NewsModel;
import amir.digital.paper.other.InternetConnection;

public class TempDetailsActivity extends AppCompatActivity {
    private TextView title, author,url;
    private TextView date;
    private TextView description;
    private ImageView image;
    private NewsModel.Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_news);
        article= (NewsModel.Article) getIntent().getSerializableExtra(StaticDataManager.article_key);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(article.getAuthor());
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        initializeView();
        setDataToView(article);
        showAlertDialog();

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Load content")
                .setMessage("Do you want to load content in browser?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                        runActivity(browserIntent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(TempDetailsActivity.this,DetailsActivity.class);
                        intent.putExtra(StaticDataManager.url_key,article.getUrl());
                        intent.putExtra(StaticDataManager.title_key,article.getTitle());
                        runActivity(intent);
                        finish();

                    }
                })
                .setIcon(R.drawable.ic_about)
                .setCancelable(false)
                .show();

    }

    private void runActivity(Intent intent) {
        if(InternetConnection.isNetworkConnected(TempDetailsActivity.this)){
            startActivity(intent);
        }else {
            InternetConnection.showError(TempDetailsActivity.this);
        }
    }

    private void setDataToView(NewsModel.Article article) {
        title.setText(article.getTitle());
        description.setText(article.getDescription());
        author.setText(article.getAuthor());
        url.setText(article.getUrl());
        if (article.getPublishTime()==null|| article.getPublishTime().isEmpty()) {
            date.setText("");
        } else {

            String date=parseDate(article.getPublishTime());
            this.date.setText(date);
        }

        if (article.getImage()!=null) {

            Glide.with(this)
                    .load(article.getImage())
                    .into(image);
        }
    }

    private String parseDate(String unFormattedDate) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date=df.parse(unFormattedDate);
            df=new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            return df.format(date);
        } catch (ParseException e) {

        }
        return null;
    }

    private void initializeView() {
        title =findViewById(R.id.txt_title);
        author =findViewById(R.id.athor);
        url =findViewById(R.id.url);
        date =findViewById(R.id.txt_date);
        description =findViewById(R.id.txt_description);
        image =findViewById(R.id.img_article);
    }
}
