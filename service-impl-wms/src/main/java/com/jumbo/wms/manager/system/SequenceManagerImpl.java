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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import com.jumbo.dao.system.SequenceCounterDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.system.SequenceCounter;

import loxia.utils.DateUtil;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SequenceManagerImpl extends BaseManagerImpl implements SequenceManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5110628712613226092L;

    private static final Logger logger = LoggerFactory.getLogger(SequenceManager.class);
    @Autowired
    private SequenceCounterDao sequenceCounterDao;

    private Map<String, Map<String, String>> codePattern;

    public Map<String, Map<String, String>> getCodePattern() {
        return codePattern;
    }

    public void setCodePattern(Map<String, Map<String, String>> codePattern) {
        this.codePattern = codePattern;
    }

    public int getSequence(String name) {
        return getSequenceNative(name, null, RESET_NEVER, OVERFLOW_ERROR, DEFAULT_INIT_VALUE, DEFAULT_INCREMENT, DEFAULT_MAX_VALUE, null);
    }

    public int getSequence(String name, byte resetMode, byte overflowMode, int initValue, int increment, int maxValue, Calendar currentCalendar) {
        return getSequenceNative(name, null, resetMode, overflowMode, initValue, increment, maxValue, currentCalendar);
    }

    public int getSequence(String name, String category, byte resetMode, byte overflowMode, int initValue, int increment, int maxValue, Calendar currentCalendar) {
        return getSequenceNative(name, category, resetMode, overflowMode, initValue, increment, maxValue, currentCalendar);
    }

    private synchronized int getSequenceNative(String name, String category, byte resetMode, byte overflowMode, int initValue, int increment, int maxValue, Calendar _currentCalendar) {
        SequenceCounter sequence = null;
        if (category == null)
            sequence = sequenceCounterDao.findSequenceWithNullCategory(name);
        else
            sequence = sequenceCounterDao.findSequenceWithCategory(category, name);

        if (_currentCalendar == null) {
            _currentCalendar = Calendar.getInstance();
        }

        Calendar currentCalendar = (Calendar) _currentCalendar.clone();
        currentCalendar.clear(Calendar.HOUR);
        currentCalendar.clear(Calendar.MINUTE);
        currentCalendar.clear(Calendar.SECOND);
        currentCalendar.clear(Calendar.MILLISECOND);

        int counter = initValue;
        if (sequence == null) {
            sequence = new SequenceCounter(name, category, currentCalendar.getTime(), initValue);
            sequenceCounterDao.save(sequence);
        } else {
            counter = sequence.getCounter();

            Date lastChangeDate = sequence.getChangeDate();

            Calendar lastCalendar = Calendar.getInstance();
            lastCalendar.clear();
            lastCalendar.setTime(lastChangeDate);
            lastCalendar.clear(Calendar.HOUR);
            lastCalendar.clear(Calendar.MINUTE);
            lastCalendar.clear(Calendar.SECOND);
            lastCalendar.clear(Calendar.MILLISECOND);

            switch (resetMode) {
                case RESET_BY_DAY:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)
                            && lastCalendar.get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH)) {
                        counter = sequence.getCounter() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_BY_WEEK_OF_YEAR:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) && lastCalendar.get(Calendar.WEEK_OF_YEAR) == currentCalendar.get(Calendar.WEEK_OF_YEAR)) {
                        counter = sequence.getCounter() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_BY_WEEK_OF_MONTH:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)
                            && lastCalendar.get(Calendar.WEEK_OF_MONTH) == currentCalendar.get(Calendar.WEEK_OF_MONTH)) {
                        counter = sequence.getCounter() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_BY_MONTH:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)) {
                        counter = sequence.getCounter() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_BY_YEAR:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)) {
                        counter = sequence.getCounter() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_NEVER:
                    counter = sequence.getCounter() + increment;
                    break;
                default:
                    break;
            }

            if (counter > maxValue) {
                if (overflowMode == OVERFLOW_ERROR) {
                    throw new RuntimeException("Sequence with name [" + name + "] and category [" + category + "] returns value " + counter + " is greater than allowed maximum value " + maxValue);
                } else if (overflowMode == OVERFLOW_RESET) {
                    counter = initValue;
                }
            }
            sequence.setCounter(counter);
            sequence.setChangeDate(currentCalendar.getTime());
        }

        return counter;
    }

    private Object invokeMethod(BaseModel model, String method) {
        Method m = ClassUtils.getMethodIfAvailable(model.getClass(), method, (Class[]) null);
        if (m != null) {
            try {
                return m.invoke(model, (Object[]) null);
            } catch (Exception e) {
                logger.error("Code generator encounts one error:{}", e);
            }
        }
        return null;
    }

    public String getCode(String type, BaseModel model) {
        Map<String, String> patternMap = codePattern.get(type);
        byte rewind = RESET_NEVER;
        logger.debug("pattern Map:{}", patternMap);
        // rewind
        try {
            if (patternMap.containsKey(REWIND)) {
                String rewindStr = patternMap.get(REWIND);
                if (rewindStr.startsWith("$")) {
                    Object result = invokeMethod(model, rewindStr.substring(1));
                    if (result != null) {
                        rewindStr = result == null ? "RESET_NEVER" : result.toString();
                    }
                }
                Field field = getClass().getField(rewindStr);
                rewind = field.getByte(null);
            }
        } catch (Exception e) {
            // do nothing
            logger.error("Code generator encounts one error:{}", e);
        }

        // sequence name
        String seqName = type;
        if (patternMap.containsKey(SEQ_NAME)) {
            seqName = patternMap.get(SEQ_NAME);
            if (seqName.startsWith("$")) {
                Object result = invokeMethod(model, seqName.substring(1));
                seqName = result == null ? type : result.toString();
            }
        }

        // seq category
        String seqCateName = null;
        if (patternMap.containsKey(SEQ_CATEGORY)) {
            seqCateName = patternMap.get(SEQ_CATEGORY);
            if (seqCateName.startsWith("$")) {
                Object result = invokeMethod(model, seqCateName.substring(1));
                seqCateName = result == null ? null : result.toString();
            }
        }

        String sqlSequenceName = null;
        if (patternMap.containsKey(SQL_SEQUENCE_NAME)) {
            sqlSequenceName = patternMap.get(SQL_SEQUENCE_NAME);
            if (sqlSequenceName.startsWith("$")) {
                Object result = invokeMethod(model, sqlSequenceName.substring(1));
                sqlSequenceName = result == null ? null : result.toString();
            }
        }
        logger.debug("sqlSequenceName:{}", sqlSequenceName);

        // seq category
        String cateName = null;

        if (patternMap.containsKey(CATEGORY)) {
            cateName = patternMap.get(CATEGORY);
            if (cateName.startsWith("$")) {
                Object result = invokeMethod(model, cateName.substring(1));
                cateName = result == null ? null : result.toString();
            }
        }
        logger.debug("cateName:{}", cateName);

        String seqNo = null;

        if (sqlSequenceName != null) {
            Long sqlSeq = sequenceCounterDao.getNextBySequence(sqlSequenceName, new SingleColumnRowMapper<Long>(Long.class));
            seqNo = sqlSeq.toString();
            if (patternMap.containsKey(SEQ_LENGTH)) {
                String seqLength = patternMap.get(SEQ_LENGTH);
                if (seqLength.startsWith("$")) {
                    Object result = invokeMethod(model, seqLength.substring(1));
                    seqLength = result == null ? null : result.toString();
                }
                if (seqLength != null) {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(0);
                    nf.setGroupingUsed(false);
                    nf.setMinimumIntegerDigits(Integer.parseInt(seqLength));
                    seqNo = nf.format(sqlSeq);
                }
            }
        } else {
            int seq;
            if (seqCateName == null) {
                seq = getSequenceNative(seqName, null, rewind, OVERFLOW_RESET, 1, 1, Integer.MAX_VALUE, null);
            } else {
                seq = getSequenceNative(seqName, seqCateName, rewind, OVERFLOW_RESET, 1, 1, Integer.MAX_VALUE, null);
            }
            seqNo = Integer.toString(seq);
            if (patternMap.containsKey(SEQ_LENGTH)) {
                String seqLength = patternMap.get(SEQ_LENGTH);
                if (seqLength.startsWith("$")) {
                    Object result = invokeMethod(model, seqLength.substring(1));
                    seqLength = result == null ? null : result.toString();
                }
                if (seqLength != null) {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(0);
                    nf.setGroupingUsed(false);
                    nf.setMinimumIntegerDigits(Integer.parseInt(seqLength));
                    seqNo = nf.format(seq);
                }
            }
        }

        String seperator = "";
        if (patternMap.containsKey(SEPERATOR)) {
            seperator = patternMap.get(SEPERATOR);
        }

        String prefix = "";
        if (patternMap.containsKey(PREFIX)) {
            prefix = patternMap.get(PREFIX);
            if (prefix.startsWith("$")) {
                Object result = invokeMethod(model, prefix.substring(1));
                prefix = result == null ? "" : result.toString();
            }
        }

        String suffix = "";
        if (patternMap.containsKey(SUFFIX)) {
            suffix = patternMap.get(SUFFIX);
            if (suffix.startsWith("$")) {
                Object result = invokeMethod(model, suffix.substring(1));
                suffix = result == null ? "" : result.toString();
            }
        }

        String dateStr = null;
        if (patternMap.containsKey(DATEFORMAT)) {
            dateStr = patternMap.get(DATEFORMAT);
            if (dateStr.startsWith("$")) {
                Object result = invokeMethod(model, dateStr.substring(1));
                dateStr = result == null ? null : result.toString();
            }
        }

        StringBuffer buf = new StringBuffer();
        buf.append(prefix);
        if (cateName != null) {
            buf.append(seperator);
            buf.append(cateName);
        }
        if (dateStr != null) {
            buf.append(seperator);
            buf.append(DateUtil.format(new Date(), dateStr));
        }

        if (suffix != null && suffix.length() > 0) {
            buf.append(seperator);
            buf.append(suffix);
        }

        buf.append(seperator);
        buf.append(seqNo);

        if (seperator.length() > 0 && buf.toString().indexOf(seperator) == 0)
            return buf.toString().substring(seperator.length());
        else
            return buf.toString();
    }

    public String getCode(String name, String seqCategory, char fillChar, int length, String px) {
        if (seqCategory == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        int seq = getSequenceNative(name, seqCategory, RESET_NEVER, OVERFLOW_RESET, 1, 1, Integer.MAX_VALUE, null);
        String pxString = "";
        if (px != null) {
            pxString = px;
        }
        return pxString + FormatUtil.addCharForString(seq + "", fillChar, length, 1);
    }

    /**
     * 获取占用批编码
     * 
     * @return
     */
    public String getOcpBatchCode() {
        String first = "OB";
        Long seq = sequenceCounterDao.getNextBySequence("S_T_WH_OCP_ORDER_CODE", new SingleColumnRowMapper<Long>(Long.class));
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
        Date d = new Date();
        String date = DateFormat.format(d);

        return first + date + seq;
    }

    /**
     * 获取自动化入库货箱编码
     * 
     * @return
     */
    public String getAutoInboundBoxCode() {
        String first = "";
        Long seq = sequenceCounterDao.getNextBySequence("S_T_WH_STA_CONTAINER_CODE", new SingleColumnRowMapper<Long>(Long.class));
        String s = seq.toString();
        for (int i = 0; i < 8 - s.length(); i++) {
            first += "0";
        }
        return first + s;
    }
    
    /**
     * 获取得到物流单号批次号
     * 
     * @return
     */
    public String getTransNoCode(String lpCode) {
        String first = lpCode;
        Long seq = sequenceCounterDao.getNextBySequence("S_T_WH_GETTRANSNO", new SingleColumnRowMapper<Long>(Long.class));
        String s = seq.toString();
        for (int i = 0; i < 8 - s.length(); i++) {
            first += "0";
        }
        return first + s;
    }

    /**
     * 获取报关编码
     * 
     * @return
     */
    public String getCustomsDeclarationCode() {
        String first = "CD";
        Long seq = sequenceCounterDao.getNextBySequence("S_CUSTOMS_DECLARATION_CODE", new SingleColumnRowMapper<Long>(Long.class));
        String s = seq.toString();

        return first + s;
    }
}
