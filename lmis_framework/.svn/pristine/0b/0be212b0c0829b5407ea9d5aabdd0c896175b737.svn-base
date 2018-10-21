package com.lmis.common.dataFormat;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * @ClassName: JsonUtils
 * @Description: TODO(JSON工具类)
 * @author Ian.Huang
 * @date 2018年1月17日 下午5:17:16 
 * 
 */
public class JsonUtils {

	// 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    /**
     * @Title: object2Json
     * @Description: TODO(将对象转换成json字符串)
     * @param data
     * @throws JsonProcessingException
     * @return: String
     * @author: Ian.Huang
     * @date: 2018年1月17日 下午5:19:48
     */
    public static String object2Json(Object data) throws JsonProcessingException {
		return MAPPER.writeValueAsString(data);
    }
    
    /**
     * @Title: jsonToPojo
     * @Description: TODO(json字符串转为对象实体)
     * @param jsonStr
     * @param beanType
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @return: T
     * @author: Ian.Huang
     * @date: 2018年1月17日 下午5:21:12
     */
    public static <T> T json2Pojo(String jsonStr, Class<T> beanType) throws JsonParseException, JsonMappingException, IOException {
    	return MAPPER.readValue(jsonStr, beanType);
    }
    
    /**
     * @Title: json2List
     * @Description: TODO(将json数组字符串转换成pojo对象list)
     * @param jsonArrayStr
     * @param beanType
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @return: List<T>
     * @author: Ian.Huang
     * @date: 2018年1月17日 下午5:23:36
     */
    public static <T> List<T> json2List(String jsonArrayStr, Class<T> beanType) throws JsonParseException, JsonMappingException, IOException {
    	return MAPPER.readValue(jsonArrayStr, MAPPER.getTypeFactory().constructParametricType(List.class, beanType));
    }
    
}
