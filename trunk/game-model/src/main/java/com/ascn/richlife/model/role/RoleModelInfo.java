package com.ascn.richlife.model.role;

/**
 * 角色模型信息
 */
public class RoleModelInfo {

    /**
     * 头像信息
     */
    private String headImgInfo;

    /**
     * 人物信息
     */
    private String personImgInfo;

    /**
     * 模型
     */
    private int modelId;

    /**
     * 模型路径
     */
    private String modelPath;

    public String getHeadImgInfo() {
        return headImgInfo;
    }

    public RoleModelInfo setHeadImgInfo(String headImgInfo) {
        this.headImgInfo = headImgInfo;
        return this;
    }

    public String getPersonImgInfo() {
        return personImgInfo;
    }

    public RoleModelInfo setPersonImgInfo(String personImgInfo) {
        this.personImgInfo = personImgInfo;
        return this;
    }

    public int getModelId() {
        return modelId;
    }

    public RoleModelInfo setModelId(int modelId) {
        this.modelId = modelId;
        return this;
    }

    public String getModelPath() {
        return modelPath;
    }

    public RoleModelInfo setModelPath(String modelPath) {
        this.modelPath = modelPath;
        return this;
    }
}
