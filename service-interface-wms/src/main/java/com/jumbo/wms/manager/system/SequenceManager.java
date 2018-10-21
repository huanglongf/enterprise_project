/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.manager.system;

import java.util.Calendar;
import java.util.Map;

import com.jumbo.wms.model.BaseModel;

public interface SequenceManager {

    public static final String PREFIX = "prefix";
    public static final String SUFFIX = "suffix";
    public static final String SEQ_NAME = "seqName";
    public static final String SEQ_CATEGORY = "seqCategory";
    public static final String CATEGORY = "category";
    public static final String REWIND = "rewind";
    public static final String DATEFORMAT = "dateformat";
    public static final String SEQ_LENGTH = "seqLength";
    public static final String SEPERATOR = "seperator";
    public static final String PROPERTY = "property";
    public static final String GEN_CONDITION = "genCondition";
    public static final String SQL_SEQUENCE_NAME = "sqlSequenceName";

    public int DEFAULT_INIT_VALUE = 1;
    public int DEFAULT_INCREMENT = 1;
    public int DEFAULT_MAX_VALUE = Integer.MAX_VALUE;

    /**
     * 重置模式：每天重置计数器
     */
    public static final byte RESET_BY_DAY = 'd';
    /**
     * 重置模式：以年度周数为单位重置计数器。周的第一天以改国日历为准，中国第一天是周日
     */
    public static final byte RESET_BY_WEEK_OF_YEAR = 'w';
    /**
     * 重置模式：当月改变，和周改变时都重置计数器。 周的第一天以改国日历为准，中国第一天是周日
     */
    public static final byte RESET_BY_WEEK_OF_MONTH = 'o';
    /**
     * 重置模式：每月重置计数器
     */
    public static final byte RESET_BY_MONTH = 'm';
    /**
     * 重置模式：每年重置计数器
     */
    public static final byte RESET_BY_YEAR = 'y';
    /**
     * 重置模式：不按时间重置计数器
     */
    public static final byte RESET_NEVER = 'n';

    /**
     * 溢出模式：当计数器超过最大置时重新计数
     */
    public static final byte OVERFLOW_RESET = 'r';
    /**
     * 溢出模式：当计数器超过最大值时报错
     */
    public static final byte OVERFLOW_ERROR = 'e';

    Map<String, Map<String, String>> getCodePattern();

    void setCodePattern(Map<String, Map<String, String>> codePattern);

    /**
     * 获取流水号当前值
     * 
     * @param name 流水号的名字, 建议是类名称，在系统中不应该重复。除非要共用流水号 如果重新计数器方式为从不重新计数，当计数超过最大值时会抛出Exception.
     *        default: initValue = 1; increment = 1;
     *        maxValue=Long.maxValue();resetMode=RESET_NERVER;overflowMode=OVERFLOW_ERROR;
     *        currentCalendar = null,;
     * @exception RuntimeException when the returns sequence is greater than max value but
     *            overfolwMode is OVERFLOW_ERROR.
     * @return the current sequence.
     */
    public int getSequence(String name);


    /**
     * 获取流水号当前值
     * 
     * @param name 流水号的名字, 建议是类名称，在系统中不应该重复。除非要共用流水号
     * @param resetMode ，计数器重新计数方式，包括每日重新计数，每周重新计数，每月重新计数，每年重新计数和从不重新计数。
     * @param overflowMode ，计数器溢出处理方式，当计数器超过最大值时有两种处理方式 1 OVERFLOW_RESET,则当溢出时从初始值重新开始； 2
     *        OVERFLOW_ERROR,则报出RuntimeException
     * @param initValue ，初始值
     * @param increment ，每次增量值
     * @param maxValue ，允许最大值，如果没有特殊要求，可以是Long.MaxValue()
     * @param currentCalendar 当前日立
     * @exception RuntimeException when the returns sequence is greater than max value but
     *            overfolwMode is OVERFLOW_ERROR.
     * @return the current sequence.
     */
    public int getSequence(String name, byte resetMode, byte overflowMode, int initValue, int increment, int maxValue, Calendar currentCalendar);

    /**
     * 获取分类计数流水号当前值，有些对象可能分多个层次有计数器，比如公司要求产品代码流水号，按照品类计算流水号。
     * 
     * @param name 流水号的名字, 建议是类名称，在系统中不应该重复。除非要共用流水号
     * @param category 分类计数的分类名称
     * @param resetMode ，计数器重新计数方式，包括每日重新计数，每周重新计数，每月重新计数，每年重新计数和从不重新计数。
     * @param overflowMode ，计数器溢出处理方式，当计数器超过最大值时有两种处理方式 1 OVERFLOW_RESET,则当溢出时从初始值重新开始； 2
     *        OVERFLOW_ERROR,则报出RuntimeException
     * @param initValue ，初始值
     * @param increment ，每次增量值
     * @param maxValue ，允许最大值，如果没有特殊要求，可以是Long.MaxValue()
     * @param currentCalendar 当前日立
     * @exception RuntimeException when the returns sequence is greater than max value but
     *            overfolwMode is OVERFLOW_ERROR.
     * @return the current sequence.
     */
    public int getSequence(String name, String category, byte resetMode, byte overflowMode, int initValue, int increment, int maxValue, Calendar currentCalendar);

    /**
     * 根据编码规则获取编码
     * 
     * @param type
     * @param model
     * @return
     */
    String getCode(String type, BaseModel model);
    
    String getCode(String name, String seqCategory, char fillChar, int length, String px);

    /**
     * 获取占用批编码
     * 
     * @return
     */
    String getOcpBatchCode();

    /**
     * 获取自动化入库货箱编码
     * 
     * @return
     */
    public String getAutoInboundBoxCode();
    /**
     * 获取得到运单号的批次号
     * @param lpCode
     * @return
     */
    public String getTransNoCode(String lpCode);

    /**
     * 获取报关编码
     * 
     * @return
     */
    public String getCustomsDeclarationCode();
}
