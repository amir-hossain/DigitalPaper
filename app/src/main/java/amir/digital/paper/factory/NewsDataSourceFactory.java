package amir.digital.paper.factory;

import amir.digital.paper.DataSource.NewsDataSource;
import amir.digital.paper.model.NewsModel;
import androidx.paging.DataSource;

public class NewsDataSourceFactory extends DataSource.Factory<Long,NewsModel.Article> {
    private String newsSource;

    public NewsDataSourceFactory(String newsSource) {
        this.newsSource = newsSource;
    }

    @Override
    public DataSource<Long, NewsModel.Article> create() {
        return new NewsDataSource(newsSource);
    }
}
