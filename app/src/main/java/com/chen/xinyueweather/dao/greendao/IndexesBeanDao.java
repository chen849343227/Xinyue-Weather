package com.chen.xinyueweather.dao.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.chen.xinyueweather.dao.bean.IndexesBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "INDEXES_BEAN".
*/
public class IndexesBeanDao extends AbstractDao<IndexesBean, Void> {

    public static final String TABLENAME = "INDEXES_BEAN";

    /**
     * Properties of entity IndexesBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property AreaId = new Property(0, String.class, "areaId", false, "AREA_ID");
        public final static Property Abbreviation = new Property(1, String.class, "abbreviation", false, "ABBREVIATION");
        public final static Property Alias = new Property(2, String.class, "alias", false, "ALIAS");
        public final static Property Content = new Property(3, String.class, "content", false, "CONTENT");
        public final static Property Level = new Property(4, String.class, "level", false, "LEVEL");
        public final static Property Name = new Property(5, String.class, "name", false, "NAME");
    }


    public IndexesBeanDao(DaoConfig config) {
        super(config);
    }
    
    public IndexesBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INDEXES_BEAN\" (" + //
                "\"AREA_ID\" TEXT," + // 0: areaId
                "\"ABBREVIATION\" TEXT," + // 1: abbreviation
                "\"ALIAS\" TEXT," + // 2: alias
                "\"CONTENT\" TEXT," + // 3: content
                "\"LEVEL\" TEXT," + // 4: level
                "\"NAME\" TEXT);"); // 5: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INDEXES_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, IndexesBean entity) {
        stmt.clearBindings();
 
        String areaId = entity.getAreaId();
        if (areaId != null) {
            stmt.bindString(1, areaId);
        }
 
        String abbreviation = entity.getAbbreviation();
        if (abbreviation != null) {
            stmt.bindString(2, abbreviation);
        }
 
        String alias = entity.getAlias();
        if (alias != null) {
            stmt.bindString(3, alias);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(5, level);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, IndexesBean entity) {
        stmt.clearBindings();
 
        String areaId = entity.getAreaId();
        if (areaId != null) {
            stmt.bindString(1, areaId);
        }
 
        String abbreviation = entity.getAbbreviation();
        if (abbreviation != null) {
            stmt.bindString(2, abbreviation);
        }
 
        String alias = entity.getAlias();
        if (alias != null) {
            stmt.bindString(3, alias);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(5, level);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public IndexesBean readEntity(Cursor cursor, int offset) {
        IndexesBean entity = new IndexesBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // areaId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // abbreviation
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // alias
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // content
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // level
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, IndexesBean entity, int offset) {
        entity.setAreaId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAbbreviation(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAlias(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLevel(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(IndexesBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(IndexesBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(IndexesBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
