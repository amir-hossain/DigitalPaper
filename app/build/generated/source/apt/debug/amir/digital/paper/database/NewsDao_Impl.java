package amir.digital.paper.database;

import amir.digital.paper.schema.Article;
import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class NewsDao_Impl implements NewsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfArticle;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfArticle;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public NewsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfArticle = new EntityInsertionAdapter<Article>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `article`(`news_id`,`author`,`title`,`description`,`url`,`publish_time`,`article`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Article value) {
        stmt.bindLong(1, value.getNewsId());
        if (value.getAuthor() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAuthor());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUrl());
        }
        if (value.getPublishTime() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPublishTime());
        }
        if (value.getArticle() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getArticle());
        }
      }
    };
    this.__deletionAdapterOfArticle = new EntityDeletionOrUpdateAdapter<Article>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `article` WHERE `news_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Article value) {
        stmt.bindLong(1, value.getNewsId());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from article";
        return _query;
      }
    };
  }

  @Override
  public void insertNews(Article article) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfArticle.insert(article);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Article article) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfArticle.handle(article);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public List<Article> getAllNews() {
    final String _sql = "select * from article";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfNewsId = _cursor.getColumnIndexOrThrow("news_id");
      final int _cursorIndexOfAuthor = _cursor.getColumnIndexOrThrow("author");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfPublishTime = _cursor.getColumnIndexOrThrow("publish_time");
      final int _cursorIndexOfArticle = _cursor.getColumnIndexOrThrow("article");
      final List<Article> _result = new ArrayList<Article>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Article _item;
        _item = new Article();
        final int _tmpNewsId;
        _tmpNewsId = _cursor.getInt(_cursorIndexOfNewsId);
        _item.setNewsId(_tmpNewsId);
        final String _tmpAuthor;
        _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
        _item.setAuthor(_tmpAuthor);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item.setUrl(_tmpUrl);
        final String _tmpPublishTime;
        _tmpPublishTime = _cursor.getString(_cursorIndexOfPublishTime);
        _item.setPublishTime(_tmpPublishTime);
        final String _tmpArticle;
        _tmpArticle = _cursor.getString(_cursorIndexOfArticle);
        _item.setArticle(_tmpArticle);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
