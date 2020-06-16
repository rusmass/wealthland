package com.ascn.richlife.model.login;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户模型
 */
public class User {

    /**
     * 账户id
     */
    private String id;

    /**
     * 账户的手机号
     */
    private String user_phone;

    /**
     * 账户的密码
     */
    private String user_pwd;

    /**
     * 微信ID
     */
    private String weChat_id;

    /**
     * 账户状态
     */
    private String user_status;

    /**
     * 账户类型0手机用户，1微信用户
     */
    private String user_type;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 修改时间
     */
    private String modify_time;

    public User() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.create_time = sdf.format(new Date());
    }

    public User(String id, String phone, String password, String weChat_id, String user_status, String user_type, String modify_time) {
        this.id = id;
        this.user_phone = phone;
        this.user_pwd = password;
        this.weChat_id = weChat_id;
        this.user_status = user_status;
        this.user_type = user_type;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.create_time = sdf.format(new Date());
        this.modify_time = modify_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getWeChat_id() {
        return weChat_id;
    }

    public void setWeChat_id(String weChat_id) {
        this.weChat_id = weChat_id;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
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

        User user = (User) o;

        if (user_phone != null ? !user_phone.equals(user.user_phone) : user.user_phone != null) return false;
        return weChat_id != null ? weChat_id.equals(user.weChat_id) : user.weChat_id == null;
    }

    @Override
    public int hashCode() {
        int result = user_phone != null ? user_phone.hashCode() : 0;
        result = 31 * result + (weChat_id != null ? weChat_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                ", weChat_id='" + weChat_id + '\'' +
                ", user_status='" + user_status + '\'' +
                ", user_type='" + user_type + '\'' +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                '}';
    }
}
