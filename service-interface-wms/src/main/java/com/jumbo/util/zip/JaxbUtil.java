package com.jumbo.util.zip;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxbUtil implements Serializable {
    private static final long serialVersionUID = 5630150112550995577L;
    protected static final Logger log = LoggerFactory.getLogger(JaxbUtil.class);

    /**
     * 利用JAXB将XML解析成对象
     * 
     * @param clazz
     * @param xml
     * @return
     * @throws JAXBException
     */
    public static String marshal(Object object) throws JAXBException {
        String result = null;
        try {
            Class<?> clazz = object.getClass();
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(object, os);
            result = new String(os.toByteArray(), "UTF-8");
        } catch (JAXBException e) {
            log.error("", e);
            throw e;
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return result;
    }

    /**
     * 利用JAXB将XML解析成对象
     * 
     * @param <T>
     * 
     * @param clazz
     * @param xml
     * @return
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(Class<T> clazz, String xml) throws JAXBException {
        final byte[] bom = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
        T object = null;
        try {
            if (xml != null && xml.length() != 0) {
                byte[] sourceArray = xml.getBytes("UTF-8");
                if (sourceArray.length > 3) {
                    if (bom[0] == sourceArray[0] && bom[1] == sourceArray[1] && bom[2] == sourceArray[2]) {
                        byte[] destArray = new byte[sourceArray.length - 3];
                        System.arraycopy(sourceArray, 3, destArray, 0, destArray.length);
                        xml = new String(destArray, "UTF-8");
                    }
                }
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            if (unmarshaller != null) {
                object = (T) unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (JAXBException e) {
            log.error("", e);
            throw e;
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return object;
    }
}
