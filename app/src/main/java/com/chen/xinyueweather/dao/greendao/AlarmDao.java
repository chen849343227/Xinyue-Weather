package com.chen.xinyueweather.dao.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.chen.xinyueweather.dao.bean.Alarm;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALARM".
*/
public class AlarmDao extends AbstractDao<Alarm, Long> {

    public static final String TABLENAME = "ALARM";

    /**
     * Properties of entity Alarm.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AreaId = new Property(1, String.class, "areaId", false, "AREA_ID");
        public final static Property AlarmId = new Property(2, String.class, "alarmId", false, "ALARM_ID");
        public final static Property AlarmType = new Property(3, String.class, "alarmType", false, "ALARM_TYPE");
        public final static Property AlarmTypeDesc = new Property(4, String.class, "alarmTypeDesc", false, "ALARM_TYPE_DESC");
        public final static Property AlarmLevelNo = new Property(5, String.class, "alarmLevelNo", false, "ALARM_LEVEL_NO");
        public final static Property AlarmLevelNoDesc = new Property(6, String.class, "alarmLevelNoDesc", false, "ALARM_LEVEL_NO_DESC");
        public final static Property AlarmContent = new Property(7, String.class, "alarmContent", false, "ALARM_CONTENT");
        public final static Property PublishTime = new Property(8, String.class, "publishTime", false, "PUBLISH_TIME");
        public final static Property AlarmDesc = new Property(9, String.class, "alarmDesc", false, "ALARM_DESC");
        public final static Property Precaution = new Property(10, String.class, "precaution", false, "PRECAUTION");
    }


    public AlarmDao(DaoConfig config) {
        super(config);
    }
    
    public AlarmDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALARM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"AREA_ID\" TEXT," + // 1: areaId
                "\"ALARM_ID\" TEXT," + // 2: alarmId
                "\"ALARM_TYPE\" TEXT," + // 3: alarmType
                "\"ALARM_TYPE_DESC\" TEXT," + // 4: alarmTypeDesc
                "\"ALARM_LEVEL_NO\" TEXT," + // 5: alarmLevelNo
                "\"ALARM_LEVEL_NO_DESC\" TEXT," + // 6: alarmLevelNoDesc
                "\"ALARM_CONTENT\" TEXT," + // 7: alarmContent
                "\"PUBLISH_TIME\" TEXT," + // 8: publishTime
                "\"ALARM_DESC\" TEXT," + // 9: alarmDesc
                "\"PRECAUTION\" TEXT);"); // 10: precaution
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALARM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Alarm entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String areaId = entity.getAreaId();
        if (areaId != null) {
            stmt.bindString(2, areaId);
        }
 
        String alarmId = entity.getAlarmId();
        if (alarmId != null) {
            stmt.bindString(3, alarmId);
        }
 
        String alarmType = entity.getAlarmType();
        if (alarmType != null) {
            stmt.bindString(4, alarmType);
        }
 
        String alarmTypeDesc = entity.getAlarmTypeDesc();
        if (alarmTypeDesc != null) {
            stmt.bindString(5, alarmTypeDesc);
        }
 
        String alarmLevelNo = entity.getAlarmLevelNo();
        if (alarmLevelNo != null) {
            stmt.bindString(6, alarmLevelNo);
        }
 
        String alarmLevelNoDesc = entity.getAlarmLevelNoDesc();
        if (alarmLevelNoDesc != null) {
            stmt.bindString(7, alarmLevelNoDesc);
        }
 
        String alarmContent = entity.getAlarmContent();
        if (alarmContent != null) {
            stmt.bindString(8, alarmContent);
        }
 
        String publishTime = entity.getPublishTime();
        if (publishTime != null) {
            stmt.bindString(9, publishTime);
        }
 
        String alarmDesc = entity.getAlarmDesc();
        if (alarmDesc != null) {
            stmt.bindString(10, alarmDesc);
        }
 
        String precaution = entity.getPrecaution();
        if (precaution != null) {
            stmt.bindString(11, precaution);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Alarm entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String areaId = entity.getAreaId();
        if (areaId != null) {
            stmt.bindString(2, areaId);
        }
 
        String alarmId = entity.getAlarmId();
        if (alarmId != null) {
            stmt.bindString(3, alarmId);
        }
 
        String alarmType = entity.getAlarmType();
        if (alarmType != null) {
            stmt.bindString(4, alarmType);
        }
 
        String alarmTypeDesc = entity.getAlarmTypeDesc();
        if (alarmTypeDesc != null) {
            stmt.bindString(5, alarmTypeDesc);
        }
 
        String alarmLevelNo = entity.getAlarmLevelNo();
        if (alarmLevelNo != null) {
            stmt.bindString(6, alarmLevelNo);
        }
 
        String alarmLevelNoDesc = entity.getAlarmLevelNoDesc();
        if (alarmLevelNoDesc != null) {
            stmt.bindString(7, alarmLevelNoDesc);
        }
 
        String alarmContent = entity.getAlarmContent();
        if (alarmContent != null) {
            stmt.bindString(8, alarmContent);
        }
 
        String publishTime = entity.getPublishTime();
        if (publishTime != null) {
            stmt.bindString(9, publishTime);
        }
 
        String alarmDesc = entity.getAlarmDesc();
        if (alarmDesc != null) {
            stmt.bindString(10, alarmDesc);
        }
 
        String precaution = entity.getPrecaution();
        if (precaution != null) {
            stmt.bindString(11, precaution);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Alarm readEntity(Cursor cursor, int offset) {
        Alarm entity = new Alarm( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // areaId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // alarmId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // alarmType
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // alarmTypeDesc
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // alarmLevelNo
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // alarmLevelNoDesc
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // alarmContent
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // publishTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // alarmDesc
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // precaution
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Alarm entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAreaId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAlarmId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAlarmType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAlarmTypeDesc(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAlarmLevelNo(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAlarmLevelNoDesc(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAlarmContent(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPublishTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAlarmDesc(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPrecaution(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Alarm entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Alarm entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Alarm entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
