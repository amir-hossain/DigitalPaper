package amir.digital.paper.modelAndSchema;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class NewsModelAndSchema {
    private ArrayList<Article> articles;

    @SerializedName("totalResults")
    private int total;

    public ArrayList<Article> getArticles() {
        return articles;
    }

    @SerializedName("totalResults")
    public int getTotal() {
        return total;
    }

    public class Article implements Serializable {
        private int newsId;
        @SerializedName("author")
        private String author;
        @SerializedName("title")
        private String title;
        @SerializedName("description")
        private String description;
        @SerializedName("url")
        private String url;
        @SerializedName("urlToImage")
        @Ignore
        private String image;
        @SerializedName("publishedAt")
        private String publishTime;
        @SerializedName("content")
        private String article;

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getArticle() {
            return article;
        }

        public void setArticle(String article) {
            this.article = article;
        }
    }
}
