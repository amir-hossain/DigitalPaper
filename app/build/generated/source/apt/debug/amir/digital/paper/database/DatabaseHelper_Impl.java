package amir.digital.paper.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class DatabaseHelper_Impl extends DatabaseHelper {
  private volatile NewsDao _newsDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `article` (`news_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `author` TEXT, `title` TEXT, `description` TEXT, `url` TEXT, `publish_time` TEXT, `article` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"07443f33c6511a1114c7caef1f3e9bdb\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `article`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsArticle = new HashMap<String, TableInfo.Column>(7);
        _columnsArticle.put("news_id", new TableInfo.Column("news_id", "INTEGER", true, 1));
        _columnsArticle.put("author", new TableInfo.Column("author", "TEXT", false, 0));
        _columnsArticle.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsArticle.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsArticle.put("url", new TableInfo.Column("url", "TEXT", false, 0));
        _columnsArticle.put("publish_time", new TableInfo.Column("publish_time", "TEXT", false, 0));
        _columnsArticle.put("article", new TableInfo.Column("article", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysArticle = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesArticle = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoArticle = new TableInfo("article", _columnsArticle, _foreignKeysArticle, _indicesArticle);
        final TableInfo _existingArticle = TableInfo.read(_db, "article");
        if (! _infoArticle.equals(_existingArticle)) {
          throw new IllegalStateException("Migration didn't properly handle article(amir.digital.paper.schema.Article).\n"
                  + " Expected:\n" + _infoArticle + "\n"
                  + " Found:\n" + _existingArticle);
        }
      }
    }, "07443f33c6511a1114c7caef1f3e9bdb", "2d3d0d0fd39c3f8e8793a5c3071c46f0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "article");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `article`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public NewsDao getNewsDao() {
    if (_newsDao != null) {
      return _newsDao;
    } else {
      synchronized(this) {
        if(_newsDao == null) {
          _newsDao = new NewsDao_Impl(this);
        }
        return _newsDao;
      }
    }
  }
}
