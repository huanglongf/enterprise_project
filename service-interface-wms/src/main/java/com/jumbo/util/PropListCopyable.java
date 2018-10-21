package com.jumbo.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PropListCopyable implements CopyableInterface, Serializable {


    private static final long serialVersionUID = 4436474866531853581L;
    private Set<String> props;

    public PropListCopyable(String... propList) {
        props = new HashSet<String>();
        if (propList != null) {
            for (String prop : propList){
                props.add(prop);
            }
        }
    }

    @Override
    public boolean isCopyable(String propertyName, Object propertyValue, @SuppressWarnings("rawtypes") Class clazz) {
        if (props.contains(propertyName)){
            return true;
        }
        else{
            return false;
        }
    }
}
