package com.ascn.richlife.model.login;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 玩家模型
 */
public class Player {

    /**
     * 玩家id
     */
    private String id;

    /**
     * 玩家昵称
     */
    private String name;

    /**
     * 玩家性别
     */
    private String gender;

    /**
     * 玩家头像
     */
    private String avatar;

    /**
     * 玩家生日
     */
    private String birthday;

    /**
     * 玩家星座
     */
    private String constellation;

    /**
     * 玩家游戏次数
     */
    private int gameNumber;

    /**
     * 玩家的用户ID
     */
    private String user_Id;

    /**
     * 玩家是否掉线
     */
    private String ifBreak;

    /**
     * 玩家状态
     */
    private String status;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 修改时间
     */
    private String modify_time;

    public Player(){
        ifBreak = "1";
        SimpleDateFormat sdf = new SimpleDateFormat();
        this.create_time = sdf.format(new Date());
    }

    public Player(String id, String name, String gender, String avatar, int gameNumber, String user_Id) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.avatar = avatar;
        this.gameNumber = gameNumber;
        this.user_Id = user_Id;
        ifBreak = "1";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getIfBreak() {
        return ifBreak;
    }

    public void setIfBreak(String ifBreak) {
        this.ifBreak = ifBreak;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name != null ? name.equals(player.name) : player.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                ", birthday='" + birthday + '\'' +
                ", constellation='" + constellation + '\'' +
                ", gameNumber=" + gameNumber +
                ", user_Id='" + user_Id + '\'' +
                ", ifBreak='" + ifBreak + '\'' +
                ", status='" + status + '\'' +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                '}';
    }
}
