package com.jumbo.webservice.ids.service;

import java.io.Serializable;

public class SyncConstants implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5732393005593446565L;

    public static final String RSP = "Rsp";

    public static final String OPCODE_IDS_PULL_SO = "IDSPullSo";
    public static final String OPCODE_IDS_PULL_SO_RESPONSE = "IDSPullSoRsp";
    public static final String OPCODE_IDS_CF_ORDER = "IDSCfOrder";
    public static final String OPCODE_IDS_CF_ORDER_RESPONSE = "IDSCfOrderRsp";
    public static final String OPCODE_IDS_OB_SO = "IDSObSo";
    public static final String OPCODE_IDS_OB_SO_RESPONSE = "IDSObSoRsp";
    public static final String OPCODE_IDS_PULL_CO = "IDSPullCo";
    public static final String OPCODE_IDS_PULL_CO_RESPONSE = "IDSPullCoRsp";
    public static final String OPCODE_IDS_CF_CO = "IDSCfCo";
    public static final String OPCODE_IDS_CF_CO_RESPONSE = "IDSCfCoRsp";
    public static final String OPCODE_IDS_PULL_RO = "IDSPullRo";
    public static final String OPCODE_IDS_PULL_RO_RESPONSE = "IDSPullRoRsp";
    public static final String OPCODE_IDS_IB_RO = "IDSIbRo";
    public static final String OPCODE_IDS_IB_RO_RESPONSE = "IDSIbRoRsp";

    public static final String ERROR_CODE_OPINVALID = "1002";
    public static final String ERROR_MESSAGE_OPINVALID = "opCode未定义";
    public static final String ERROR_CODE_FORMATERROR = "2001";
    public static final String ERROR_MESSAGE_FORMATERROR = "文档格式错误";
    public static final String ERROR_CODE_SYSTEMERROR = "3001";
    public static final String ERROR_MESSAGE_SYSTEMERROR = "系统错误";
    public static final String ERROR_CODE_CODEORBATCHIDNOTEXISTS = "4001";
    public static final String ERROR_MESSAGE_CODEORBATCHIDNOTEXISTS = "单据号/批次号不存在";
    public static final String ERROR_CODE_OUTORINBOUNDERROR = "4002";
    public static final String ERROR_MESSAGE_OUTORINBOUNDERROR = "执行出入库错误";
    public static final String ERROR_CODE_ORDERSTATUSERROR = "4003";
    public static final String ERROR_MESSAGE_ORDERSTATUSERROR = "单据状态不正确";
    public static final String IDS_SOURCE = "IDS";
    // public static Map<String, Class<?>> syncClassMap = new HashMap<String, Class<?>>();
    // public static Map<String, String> rspOpCodeMap = new HashMap<String, String>();
    //
    // static {
    // rspOpCodeMap.put(OPCODE_IDS_PULL_SO, OPCODE_IDS_PULL_SO_RESPONSE);
    // rspOpCodeMap.put(OPCODE_IDS_CF_ORDER, OPCODE_IDS_CF_ORDER_RESPONSE);
    // rspOpCodeMap.put(OPCODE_IDS_OB_SO, OPCODE_IDS_OB_SO_RESPONSE);
    // rspOpCodeMap.put(OPCODE_IDS_PULL_CO, OPCODE_IDS_PULL_CO_RESPONSE);
    // rspOpCodeMap.put(OPCODE_IDS_CF_CO, OPCODE_IDS_CF_CO_RESPONSE);
    // rspOpCodeMap.put(OPCODE_IDS_PULL_RO, OPCODE_IDS_PULL_RO_RESPONSE);
    // rspOpCodeMap.put(OPCODE_IDS_IB_RO, OPCODE_IDS_IB_RO_RESPONSE);
    // }


}
