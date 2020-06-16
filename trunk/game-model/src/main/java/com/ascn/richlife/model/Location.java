package com.ascn.richlife.model;

/**
 * 轮盘位置
 */
public class Location {

    /**
     * 内圈还是外圈  0外圈 1内圈
     */
    private int outOrIn;

    /**
     * 玩家在转盘上的位置
     */
    private int placeByTurntable;

    /**
     * 玩家外圈累计点数
     */
    private int outerTotalPoint;

    /**
     * 玩家内圈累计点数
     */
    private int innerTotalPoint;

    public int getOutOrIn() {
        return outOrIn;
    }

    public Location setOutOrIn(int outOrIn) {
        this.outOrIn = outOrIn;
        return this;
    }

    public int getPlaceByTurntable() {
        return placeByTurntable;
    }

    public Location setPlaceByTurntable(int placeByTurntable) {
        this.placeByTurntable = placeByTurntable;
        return this;
    }

    public int getOuterTotalPoint() {
        return outerTotalPoint;
    }

    public Location setOuterTotalPoint(int outerTotalPoint) {
        this.outerTotalPoint = outerTotalPoint;
        return this;
    }

    public int getInnerTotalPoint() {
        return innerTotalPoint;
    }

    public Location setInnerTotalPoint(int innerTotalPoint) {
        this.innerTotalPoint = innerTotalPoint;
        return this;
    }
}
