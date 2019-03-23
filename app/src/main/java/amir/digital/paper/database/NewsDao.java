package amir.digital.paper.database;

import java.util.List;

import amir.digital.paper.schema.Article;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NewsDao {
    @Query("select * from article")
    List<Article> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(Article article);

    @Delete
    void delete(Article article);

    @Query("delete from article")
    void delete();
}
