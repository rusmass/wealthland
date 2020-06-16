package com.ascn.richlife.model.login;

import java.util.Arrays;

/**
 * 图片公告模型
 */
public class NoticeImg {

    /**
     * 公告id
     */
    private int id;

    /**
     * 公告图片
     */
    private byte[] img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "NoticeImg{" +
                "id=" + id +
                ", img=" + Arrays.toString(img) +
                '}';
    }
}
