package com.jumbo.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数值格式化
 * 
 * @author jinlong.ke
 * 
 */
public class NumberFormatUtil implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3422106182712414864L;

    public static String formatNumberAccordingPattern(String pattern, Object o) {
        NumberFormat n = new DecimalFormat(pattern);
        return n.format(o);
    }
}
