package amir.digital.paper.factory;

import amir.digital.paper.DataSource.NewsDataSource;
import amir.digital.paper.modelAndSchema.NewsModelAndSchema;
import androidx.paging.DataSource;

public class NewsDataSourceFactory extends DataSource.Factory<Long, NewsModelAndSchema.Article> {
    private String newsSource;

    public NewsDataSourceFactory(String newsSource) {
        this.newsSource = newsSource;
    }

    @Override
    public DataSource<Long, NewsModelAndSchema.Article> create() {
        return new NewsDataSource(newsSource);
    }
}
