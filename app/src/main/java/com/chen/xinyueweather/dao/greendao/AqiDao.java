package com.chen.xinyueweather.dao.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.chen.xinyueweather.dao.bean.Aqi;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "AQI".
*/
public class AqiDao extends AbstractDao<Aqi, Void> {

    public static final String TABLENAME = "AQI";

    /**
     * Properties of entity Aqi.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Advice = new Property(0, String.class, "advice", false, "ADVICE");
        public final static Property Aqi = new Property(1, String.class, "aqi", false, "AQI");
        public final static Property Citycount = new Property(2, int.class, "citycount", false, "CITYCOUNT");
        public final static Property Cityrank = new Property(3, int.class, "cityrank", false, "CITYRANK");
        public final static Property Co = new Property(4, String.class, "co", false, "CO");
        public final static Property Color = new Property(5, String.class, "color", false, "COLOR");
        public final static Property Level = new Property(6, String.class, "level", false, "LEVEL");
        public final static Property No2 = new Property(7, String.class, "no2", false, "NO2");
        public final static Property O3 = new Property(8, String.class, "o3", false, "O3");
        public final static Property Pm10 = new Property(9, String.class, "pm10", false, "PM10");
        public final static Property Pm25 = new Property(10, String.class, "pm25", false, "PM25");
        public final static Property Quality = new Property(11, String.class, "quality", false, "QUALITY");
        public final static Property So2 = new Property(12, String.class, "so2", false, "SO2");
        public final static Property Timestamp = new Property(13, String.class, "timestamp", false, "TIMESTAMP");
        public final static Property UpDateTime = new Property(14, String.class, "upDateTime", false, "UP_DATE_TIME");
    }


    public AqiDao(DaoConfig config) {
        super(config);
    }
    
    public AqiDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"AQI\" (" + //
                "\"ADVICE\" TEXT," + // 0: advice
                "\"AQI\" TEXT," + // 1: aqi
                "\"CITYCOUNT\" INTEGER NOT NULL ," + // 2: citycount
                "\"CITYRANK\" INTEGER NOT NULL ," + // 3: cityrank
                "\"CO\" TEXT," + // 4: co
                "\"COLOR\" TEXT," + // 5: color
                "\"LEVEL\" TEXT," + // 6: level
                "\"NO2\" TEXT," + // 7: no2
                "\"O3\" TEXT," + // 8: o3
                "\"PM10\" TEXT," + // 9: pm10
                "\"PM25\" TEXT," + // 10: pm25
                "\"QUALITY\" TEXT," + // 11: quality
                "\"SO2\" TEXT," + // 12: so2
                "\"TIMESTAMP\" TEXT," + // 13: timestamp
                "\"UP_DATE_TIME\" TEXT);"); // 14: upDateTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"AQI\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Aqi entity) {
        stmt.clearBindings();
 
        String advice = entity.getAdvice();
        if (advice != null) {
            stmt.bindString(1, advice);
        }
 
        String aqi = entity.getAqi();
        if (aqi != null) {
            stmt.bindString(2, aqi);
        }
        stmt.bindLong(3, entity.getCitycount());
        stmt.bindLong(4, entity.getCityrank());
 
        String co = entity.getCo();
        if (co != null) {
            stmt.bindString(5, co);
        }
 
        String color = entity.getColor();
        if (color != null) {
            stmt.bindString(6, color);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(7, level);
        }
 
        String no2 = entity.getNo2();
        if (no2 != null) {
            stmt.bindString(8, no2);
        }
 
        String o3 = entity.getO3();
        if (o3 != null) {
            stmt.bindString(9, o3);
        }
 
        String pm10 = entity.getPm10();
        if (pm10 != null) {
            stmt.bindString(10, pm10);
        }
 
        String pm25 = entity.getPm25();
        if (pm25 != null) {
            stmt.bindString(11, pm25);
        }
 
        String quality = entity.getQuality();
        if (quality != null) {
            stmt.bindString(12, quality);
        }
 
        String so2 = entity.getSo2();
        if (so2 != null) {
            stmt.bindString(13, so2);
        }
 
        String timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindString(14, timestamp);
        }
 
        String upDateTime = entity.getUpDateTime();
        if (upDateTime != null) {
            stmt.bindString(15, upDateTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Aqi entity) {
        stmt.clearBindings();
 
        String advice = entity.getAdvice();
        if (advice != null) {
            stmt.bindString(1, advice);
        }
 
        String aqi = entity.getAqi();
        if (aqi != null) {
            stmt.bindString(2, aqi);
        }
        stmt.bindLong(3, entity.getCitycount());
        stmt.bindLong(4, entity.getCityrank());
 
        String co = entity.getCo();
        if (co != null) {
            stmt.bindString(5, co);
        }
 
        String color = entity.getColor();
        if (color != null) {
            stmt.bindString(6, color);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(7, level);
        }
 
        String no2 = entity.getNo2();
        if (no2 != null) {
            stmt.bindString(8, no2);
        }
 
        String o3 = entity.getO3();
        if (o3 != null) {
            stmt.bindString(9, o3);
        }
 
        String pm10 = entity.getPm10();
        if (pm10 != null) {
            stmt.bindString(10, pm10);
        }
 
        String pm25 = entity.getPm25();
        if (pm25 != null) {
            stmt.bindString(11, pm25);
        }
 
        String quality = entity.getQuality();
        if (quality != null) {
            stmt.bindString(12, quality);
        }
 
        String so2 = entity.getSo2();
        if (so2 != null) {
            stmt.bindString(13, so2);
        }
 
        String timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindString(14, timestamp);
        }
 
        String upDateTime = entity.getUpDateTime();
        if (upDateTime != null) {
            stmt.bindString(15, upDateTime);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public Aqi readEntity(Cursor cursor, int offset) {
        Aqi entity = new Aqi( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // advice
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // aqi
            cursor.getInt(offset + 2), // citycount
            cursor.getInt(offset + 3), // cityrank
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // co
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // color
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // level
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // no2
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // o3
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // pm10
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // pm25
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // quality
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // so2
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // timestamp
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // upDateTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Aqi entity, int offset) {
        entity.setAdvice(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAqi(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCitycount(cursor.getInt(offset + 2));
        entity.setCityrank(cursor.getInt(offset + 3));
        entity.setCo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setColor(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLevel(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setNo2(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setO3(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPm10(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPm25(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setQuality(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setSo2(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setTimestamp(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setUpDateTime(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(Aqi entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(Aqi entity) {
        return null;
    }

    @Override
    public boolean hasKey(Aqi entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
