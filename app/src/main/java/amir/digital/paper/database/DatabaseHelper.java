package amir.digital.paper.database;

import amir.digital.paper.modelAndSchema.NewsModelAndSchema;
import amir.digital.paper.schema.Article;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Article.class},version = 2)
public abstract class DatabaseHelper extends RoomDatabase {
    public abstract  NewsDao getNewsDao();
}
