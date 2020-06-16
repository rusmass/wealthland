package com.ascn.richlife.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.*;


/**
 * 定义游戏返回的数据工具类
 */
public class ResultUtil {

    private static Logger logger = Logger.getLogger(ResultUtil.class);

    private static Map<String,Object> params = new HashMap<String, Object>();

    public static String setResult(int status,String msg,Object data){

        params.clear(); //清空 map;
        if(msg==null || msg.length()==0){
            logger.debug("msg没有值");
            throw new NullPointerException("1");
        }
        params.put("status",status);
        params.put("msg",msg);
        params.put("data",data);
        /*if ( data instanceof JSONObject || data instanceof JSONArray || data.){
            params.put("data",data);
        }else{
            logger.debug("data不是jsonObject或者JSONArray");
            throw new NullPointerException("2");
        }*/
        return JSON.toJSONString(params);
    }

    public static String setJson(int status,String msg){
        params.clear();//清空map
        if(msg==null || msg.length()==0){
            logger.debug("msg没有值");
            throw new NullPointerException("1");
        }
        params.put("status",status);
        params.put("msg",msg);
        return JSON.toJSONString(params);
    }

    /**
     * 构建Json对象
     *
     * @param key
     * @param value
     * @return
     */
    public static JSONObject buildObject(String key, Object value){
        JSONObject object = new JSONObject();
        object.put(key,value);
        logger.debug("构建Json对象"+object);
        return object;
    }

    public static void main(String[] args) {
        Map<String,Integer> hs = new HashMap<>();
        Set<Map.Entry<String,Integer>> entrySet = hs.entrySet();
        LinkedList<Map.Entry<String,Integer>> linkedList = new LinkedList<>(entrySet);
        Collections.sort(linkedList, new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
    }
}
