package com.jumbo.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToStringUtil implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(ToStringUtil.class);

    private static final long serialVersionUID = -6232224376298209810L;
    private static Set<Class<?>> primitiveClass = new HashSet<Class<?>>();

    static {
        primitiveClass.add(int.class);
        primitiveClass.add(byte.class);
        primitiveClass.add(char.class);
        primitiveClass.add(short.class);
        primitiveClass.add(long.class);
        primitiveClass.add(float.class);
        primitiveClass.add(double.class);
        primitiveClass.add(boolean.class);

        primitiveClass.add(Integer.class);
        primitiveClass.add(Byte.class);
        primitiveClass.add(Character.class);
        primitiveClass.add(Short.class);
        primitiveClass.add(Long.class);
        primitiveClass.add(Float.class);
        primitiveClass.add(Double.class);
        primitiveClass.add(Boolean.class);

        primitiveClass.add(String.class);

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String toStr(Object obj, boolean useSimpleClassName) {
        StringBuilder sb = new StringBuilder(128);
        sb.append("{");

        if (obj != null) {
            try {
                Class<?> clazz = obj.getClass();
                String clazzName = useSimpleClassName ? clazz.getSimpleName() : clazz.getName();
                Set<Class<?>> scis = lookupSuperClassAndInterfaces(clazz);
                sb.append(clazzName).append("->");

                if (primitiveClass.contains(clazz)) {
                    sb.append(obj.toString());
                } else if (scis.contains(Collection.class)) {
                    sb.append("[");
                    Object[] _co = ((Collection) obj).toArray();
                    for (int i = 0; i < _co.length; i++) {
                        sb.append(toStr(_co[i], useSimpleClassName));
                        if (i != _co.length - 1) {
                            sb.append(",");
                        }
                    }
                    sb.append("]");
                } else if (scis.contains(Map.class)) {
                    sb.append("[");

                    List<Object> _mp = new ArrayList<Object>();
                    _mp.addAll(((Map) obj).keySet());
                    for (int i = 0; i < _mp.size(); i++) {
                        Object k = _mp.get(i);
                        sb.append(toStr(k, useSimpleClassName));
                        sb.append(":");
                        sb.append(toStr(((Map) obj).get(k), useSimpleClassName));
                        if (i != _mp.size() - 1) {
                            sb.append(",");
                        }
                    }

                    sb.append("]");
                } else {
                    sb.append("{");
                    Field[] fs = clazz.getDeclaredFields();
                    for (int i = 0; i < fs.length; i++) {
                        Field f = fs[i];
                        f.setAccessible(true);
                        sb.append(f.getName()).append("=").append(toStr(f.get(obj), useSimpleClassName));
                        if (i != fs.length - 1) {
                            sb.append(",");
                        }
                    }
                    sb.append("}");
                }

            } catch (Exception e) {
                // e.printStackTrace();
                if (logger.isErrorEnabled()) {
                    logger.error("toStringUtil toStr Exception！", e);
                }
            }
        } else {
            sb.append("<NULL>");
        }
        sb.append("}");
        return sb.toString();
    }

    private static Set<Class<?>> lookupSuperClassAndInterfaces(Class<?> clazz) {
        Set<Class<?>> res = new HashSet<Class<?>>();
        if (clazz != null) {
            List<Class<?>> tmp = new ArrayList<Class<?>>();

            Class<?> superClazz = clazz.getSuperclass();
            if (superClazz != null) {
                tmp.add(superClazz);
            }
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces != null && interfaces.length != 0) {
                tmp.addAll(Arrays.asList(interfaces));
            }

            for (Class<?> c : tmp) {
                if (c != null) {
                    res.add(c);
                    res.addAll(lookupSuperClassAndInterfaces(c));
                }
            }

        }
        return res;
    }
}
