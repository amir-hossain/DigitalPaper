package amir.digital.paper.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class NewsDatabase {
    private static NewsDao newsDao;

    private NewsDatabase(){}

    private static Migration migration_1_2=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static NewsDao getNewsDao(Context context){
        if(newsDao==null){
            DatabaseHelper helper= Room.databaseBuilder(context,DatabaseHelper.class,"news-db")
                    .addMigrations(migration_1_2)
                    .build();
            newsDao=helper.getNewsDao();
        }
        return newsDao;
    }
}
