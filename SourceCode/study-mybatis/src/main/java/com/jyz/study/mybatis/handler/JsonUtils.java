package com.jyz.study.mybatis.handler;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;


/**
 * 
 * @author eason
 *
 */
@SuppressWarnings("unchecked")
public class JsonUtils {
	
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";  
	
	/**
	 * Object转换成jsonString，为了http传输
	 * @param obj 可以是基本类型，对象，嵌套对象，数组，Collection，Map
	 * 基本类型，对象，嵌套对象Map用JSONObject处理
	 * 数组，Collection用JSONArray处理
	 * @return
	 */
	public static String getJsonStringForTransport(Object obj) {
		return getJsonString(obj, JsonEnum.TRANSPORT);
	}
	
	/**
	 * Object转换成jsonString，为了在页面显示
	 */
	public static String getJsonStringForShow(Object obj) {
		return getJsonString(obj, JsonEnum.SHOW);
	}
	
	/**
	 * Object转换成jsonString，为了在EasyUI控件里面显示
	 * @param total 总记录数
	 */
	public static String getJsonStringForEasyUI(Object obj, int total) {
		return getJsonString(obj, total);
	}
	
	private static String getJsonString(Object obj, int total) {
		String jsonString = getJsonString(obj, JsonEnum.SHOW);
		JSONObject jsonObjectEasyUI = new JSONObject();
		jsonObjectEasyUI.put("rows", jsonString);
		jsonObjectEasyUI.put("total", total);
		return jsonObjectEasyUI.toString();
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static String getJsonString(Object obj, JsonEnum jsonEnum) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerDefaultValueProcessor(Integer.class,
				new DefaultValueProcessor() {
					public Object getDefaultValue(Class type) {
						return JSONNull.getInstance();
					}
				});
		jsonConfig.registerDefaultValueProcessor(Long.class,
				new DefaultValueProcessor() {
					public Object getDefaultValue(Class type) {
						return JSONNull.getInstance();
					}
				});
		jsonConfig.registerDefaultValueProcessor(Short.class,
				new DefaultValueProcessor() {
					public Object getDefaultValue(Class type) {
							return JSONNull.getInstance();
			}
		});
		
		if(jsonEnum == JsonEnum.SHOW) {
			jsonConfig.registerJsonValueProcessor(Timestamp.class,  
					new DateJsonValueProcessor(DEFAULT_DATE_PATTERN)
			);
			jsonConfig.registerJsonValueProcessor(Date.class,  
					new DateJsonValueProcessor(DEFAULT_DATE_PATTERN)
			); 
		}
		
		if(JSONUtils.isArray(obj)) {
			JSONArray jsonArray = JSONArray.fromObject(obj, jsonConfig);
			return jsonArray.toString();
		}else{
			JSONObject jsonObject = JSONObject.fromObject(obj, jsonConfig);
			return jsonObject.toString();
		}
	}
	
	/**
	 * jsonString转换成Object
	 * @param <T> Object类型
	 * @param jsonStr
	 * @param clazz Object的class类型
	 * @return
	 */
	public static <T> T getObject(String jsonStr, Class<T> clazz) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return (T) JSONObject.toBean(jsonObject, clazz); 
	}
	 
	/**
	 * jsonString转换成Object
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @param clazzMap Object中list和map属性的名称和类型
	 * @return
	 */
	public static <T> T getObject(String jsonStr, Class<T> clazz, Map<String, Class> clazzMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return (T) JSONObject.toBean(jsonObject, clazz, clazzMap); 
	}
	
	/**
	 * jsonString转换成数组
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T[] getArray(String jsonStr, Class<T> clazz){
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		return (T[]) JSONArray.toArray(jsonArray, clazz);
	} 

	/**
	 * jsonString转换成数组
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @param clazzMap
	 * @return
	 */
	public static <T> T[] getArray(String jsonStr, Class<T> clazz, Map<String, Class> clazzMap){
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		return (T[]) JSONArray.toArray(jsonArray, clazz, clazzMap);
	} 
	
	/**
	 * jsonString转换成list
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static <T> List<T> getList(String jsonStr, Class<T> clazz){
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		return  JSONArray.toList(jsonArray, clazz);
	} 
	
	/**
	 * jsonString转换成list
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static <T> List<T> getList(String jsonStr, Class<T> clazz, Map<String, Class> clazzMap){
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		return  JSONArray.toList(jsonArray, clazz, clazzMap);
	} 
	
	/**
	 * jsonString转换成map
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, T> getMap(String jsonStr, Class<T> clazz) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Map<String, T> map = new HashMap<String, T>();
        for(Iterator iter = jsonObject.keys(); iter.hasNext();){
            String key = (String)iter.next();
            JSONObject object = (JSONObject) jsonObject.get(key);
            map.put(key, (T) JSONObject.toBean(object));
        }
        return map;
    }
	
	/**
	 * jsonString转换成map
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, T> getMap(String jsonStr, Class<T> clazz, Map<String, Class> clazzMap) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Map<String, T> map = new HashMap<String, T>();
        for(Iterator iter = jsonObject.keys(); iter.hasNext();){
            String key = (String)iter.next();
            JSONObject object = (JSONObject) jsonObject.get(key);
            map.put(key, (T) JSONObject.toBean(object, clazz, clazzMap));
        }
        return map;
    } 
	
	/**
	 * 
	 * @param jsonString
	 * @param key 属性
	 * @return 属性值
	 */
	public static String getValue(String jsonString, String key) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		if(jsonObject == null) {
			return null;
		}
		return jsonObject.getString(key);
	}
	
	private static class DateJsonValueProcessor implements JsonValueProcessor {  
	    private DateFormat dateFormat;  
	  
	    /** 
	     * 构造方法. 
	     * 
	     * @param datePattern 日期格式 
	     */  
	    public DateJsonValueProcessor(String datePattern) {  
	        try {  
	            dateFormat = new SimpleDateFormat(datePattern);  
	        } catch (Exception ex) {  
	            dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);  
	        }  
	    }  
	  
	    public Object processArrayValue(Object value, JsonConfig jsonConfig) {  
	        return process(value);  
	    }  
	  
	    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {  
	        return process(value);  
	    }  
	  
	    private Object process(Object value) {
	    	if(value == null) {
	    		return null;
	    	}
	        return dateFormat.format((Date) value);  
	    }
	}
	
	private enum JsonEnum {

		TRANSPORT(),
		SHOW()
		
	}
	
	   
}
