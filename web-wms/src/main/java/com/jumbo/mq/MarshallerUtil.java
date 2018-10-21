package com.jumbo.mq;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

public class MarshallerUtil {
    protected static final Logger log = LoggerFactory.getLogger(MarshallerUtil.class);


    /**
     * 利用JAXB将XML解析成对象
     * 
     * @param object
     * @param charsetName
     * @return
     */
    public static String buildJaxb(Object object, String charsetName) {
        String result = null;
        try {
            Class<?> clazz = object.getClass();
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charsetName);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(object, os);
            result = new String(os.toByteArray(), charsetName);
        } catch (JAXBException e) {
            log.error("", e);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return result;
    }

    /**
     * 利用JAXB将XML解析成对象
     * 
     * @param clazz
     * @param xml
     * @return
     * @throws JAXBException
     */
    public static String buildJaxb(Object object) {
        return buildJaxb(object, "UTF-8");
    }

    /**
     * 利用JAXB将XML解析成对象
     * 
     * @param clazz
     * @param xml
     * @return
     * @throws JAXBException
     */
    public static String buildJaxbWithEncoiding(Object object, String encoding) {
        String result = null;
        try {
            Class<?> clazz = object.getClass();
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(object, os);
            result = new String(os.toByteArray(), "UTF-8");
        } catch (JAXBException e) {
            log.error("", e);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return result;
    }

    /**
     * 利用JAXB将XML解析成对象
     * 
     * @param clazz
     * @param xml
     * @return
     * @throws JAXBException
     */
    public static Object buildJaxb(Class<?> clazz, String xml) {
        Object object = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            object = unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            log.error("", e);
        }
        return object;
    }

    /**
     * JSON字符串转换成JAVA对象
     * 
     * @param jsonStr
     * @param cla
     * @return
     * @throws MapperException
     * @throws TokenStreamException
     * @throws RecognitionException
     */
    @SuppressWarnings({"unchecked"})
    public static Object jsonStrToObject(String jsonStr, Class<?> cla) throws MapperException, TokenStreamException, RecognitionException {
        Object obj = null;
        try {
            JSONParser parser = new JSONParser(new StringReader(jsonStr));
            JSONValue jsonValue = parser.nextValue();
            if (jsonValue instanceof com.sdicons.json.model.JSONArray) {
                @SuppressWarnings("rawtypes")
                List list = new ArrayList();
                JSONArray jsonArray = (JSONArray) jsonValue;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONValue jsonObj = jsonArray.get(i);
                    Object javaObj = JSONMapper.toJava(jsonObj, cla);
                    list.add(javaObj);
                }
                obj = list;
            } else if (jsonValue instanceof com.sdicons.json.model.JSONObject) {
                obj = JSONMapper.toJava(jsonValue, cla);
            } else {
                obj = jsonValue;
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return obj;
    }

    public static String decodeBase64StringWithUTF8(String sourceStr) {
        String decodeStr = null;
        byte[] decodeBytes = Base64.decodeBase64(sourceStr);// .decodeBase64(sourceStr.getBytes());
        try {
            decodeStr = new String(decodeBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return decodeStr;
    }

    public static String encodeBase64StringWithUTF8(String sourceStr) {
        String encodeStr = null;
        try {
            byte[] encodeBytes = Base64.encodeBase64(sourceStr.getBytes("UTF-8"));
            encodeStr = new String(encodeBytes, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // e1.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("UnsupportedEncodingException！", e1);
            }
        }
        return encodeStr;
    }

}
