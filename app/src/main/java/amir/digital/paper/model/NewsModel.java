package amir.digital.paper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsModel {
    private ArrayList<Article> articles;

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public class Article implements Serializable {

        @SerializedName("author")
        private String author;
        @SerializedName("title")
        private String title;
        @SerializedName("description")
        private String description;
        @SerializedName("url")
        private String url;
        @SerializedName("urlToImage")
        private String image;
        @SerializedName("publishedAt")
        private String publishTime;
        @SerializedName("content")
        private String article;

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getImage() {
            return image;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public String getArticle() {
            return article;
        }
    }
}
