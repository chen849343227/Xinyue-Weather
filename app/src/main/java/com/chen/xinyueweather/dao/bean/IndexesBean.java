package com.chen.xinyueweather.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author long
 * date 17-10-13
 * desc
 */
@Entity
public class IndexesBean {
    /**
     * abbreviation : pp
     * alias :
     * content : 建议用露质面霜打底，水质无油粉底霜，透明粉饼，粉质胭脂。
     * level : 控油
     * name : 化妆指数
     */

    private String abbreviation;
    private String alias;
    private String content;
    private String level;
    private String name;

    public IndexesBean() {
    }

    @Generated(hash = 2025095174)
    public IndexesBean(String abbreviation, String alias, String content,
            String level, String name) {
        this.abbreviation = abbreviation;
        this.alias = alias;
        this.content = content;
        this.level = level;
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
