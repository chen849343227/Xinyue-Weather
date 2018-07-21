package com.chen.xinyueweather.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Alarm {

    /**
     * alarmId : "10a5660a3fdebfd6f436ad0ede74fdeb"
     * alarmType : "02"
     * alarmTypeDesc : "暴雨橙色"
     * alarmLevelNo : "03"
     * alarmLevelNoDesc : "橙色"
     * alarmContent : "武汉中心气象台2018年07月21日17时02分发布暴雨橙色预警信号：预计未来3小时，竹山局部有50毫米以上降水，伴有雷电和冰雹，阵风8-10级，山区山洪、地质灾害、中小河流洪水气象风险较高，请注意防范。（预警信息来源：国家预警信息发布中心）"
     * publishTime : "2018-07-21 17:02:00"
     * alarmDesc : "湖北省气象台发布暴雨橙色预警"
     * precaution : "1.不要在积水中行走，防止跌入窨井.地坑等； 2.驾驶车辆遇到路面积水过深时，应尽量绕行； 3.危险地带的学校和单位应当停课.停业。"
     */

    @Id(autoincrement = true)
    private Long id;
    private String areaId;
    private String alarmId;
    private String alarmType;
    private String alarmTypeDesc;
    private String alarmLevelNo;
    private String alarmLevelNoDesc;
    private String alarmContent;
    private Date publishTime;
    private String alarmDesc;
    private String precaution;

    @Generated(hash = 1162518465)
    public Alarm(Long id, String areaId, String alarmId, String alarmType, String alarmTypeDesc, String alarmLevelNo, String alarmLevelNoDesc,
            String alarmContent, Date publishTime, String alarmDesc, String precaution) {
        this.id = id;
        this.areaId = areaId;
        this.alarmId = alarmId;
        this.alarmType = alarmType;
        this.alarmTypeDesc = alarmTypeDesc;
        this.alarmLevelNo = alarmLevelNo;
        this.alarmLevelNoDesc = alarmLevelNoDesc;
        this.alarmContent = alarmContent;
        this.publishTime = publishTime;
        this.alarmDesc = alarmDesc;
        this.precaution = precaution;
    }

    @Generated(hash = 1972324134)
    public Alarm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmTypeDesc(String alarmTypeDesc) {
        this.alarmTypeDesc = alarmTypeDesc;
    }

    public String getAlarmTypeDesc() {
        return alarmTypeDesc;
    }

    public void setAlarmLevelNo(String alarmLevelNo) {
        this.alarmLevelNo = alarmLevelNo;
    }

    public String getAlarmLevelNo() {
        return alarmLevelNo;
    }

    public void setAlarmLevelNoDesc(String alarmLevelNoDesc) {
        this.alarmLevelNoDesc = alarmLevelNoDesc;
    }

    public String getAlarmLevelNoDesc() {
        return alarmLevelNoDesc;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setAlarmDesc(String alarmDesc) {
        this.alarmDesc = alarmDesc;
    }

    public String getAlarmDesc() {
        return alarmDesc;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
    }

    public String getPrecaution() {
        return precaution;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmId='" + alarmId + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", alarmTypeDesc='" + alarmTypeDesc + '\'' +
                ", alarmLevelNo='" + alarmLevelNo + '\'' +
                ", alarmLevelNoDesc='" + alarmLevelNoDesc + '\'' +
                ", alarmContent='" + alarmContent + '\'' +
                ", publishTime=" + publishTime +
                ", alarmDesc='" + alarmDesc + '\'' +
                ", precaution='" + precaution + '\'' +
                '}';
    }
}
