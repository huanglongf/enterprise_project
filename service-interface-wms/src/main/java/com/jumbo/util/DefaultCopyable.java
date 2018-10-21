package com.jumbo.util;

import java.io.Serializable;

public class DefaultCopyable implements CopyableInterface, Serializable {


    private static final long serialVersionUID = -3047383505223137750L;

    @Override
    public boolean isCopyable(String propertyName, Object propertyValue, Class<?> clazz) {
        return true;
    }

}
