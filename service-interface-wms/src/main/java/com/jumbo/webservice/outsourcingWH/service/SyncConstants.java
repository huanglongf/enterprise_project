package com.jumbo.webservice.outsourcingWH.service;

import java.io.Serializable;

public class SyncConstants implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1881381616857949056L;

    public static final String RSP = "Rsp";

    public static final String OPCODE_PULL_SO = "PullSo";
    public static final String OPCODE_PULL_SO_RESPONSE = "PullSoRsp";
    public static final String OPCODE_CF_ORDER = "CfOrder";
    public static final String OPCODE_CF_ORDER_RESPONSE = "CfOrderRsp";
    public static final String OPCODE_OB_SO = "ObSo";
    public static final String OPCODE_OB_SO_RESPONSE = "ObSoRsp";
    public static final String OPCODE_PULL_CO = "PullCo";
    public static final String OPCODE_PULL_CO_RESPONSE = "PullCoRsp";
    public static final String OPCODE_CF_CO = "CfCo";
    public static final String OPCODE_CF_CO_RESPONSE = "CfCoRsp";
    public static final String OPCODE_PULL_RO = "PullRo";
    public static final String OPCODE_PULL_RO_RESPONSE = "PullRoRsp";
    public static final String OPCODE_IB_RO = "IbRo";
    public static final String OPCODE_IB_RO_RESPONSE = "IbRoRsp";
    public static final String OPCODE_PULL_IN_BOUND = "PullInbound";
    public static final String OPCODE_IB_IN_BOUND = "IbInbound";
    public static final String OPCODE_PULL_OUT_BOUND = "PullOutbound";
    public static final String OPCODE_OB_OUT_BOUND = "ObOutbound";
    public static final String OPCODE_PULL_SKU = "PullSku";
    public static final String OPCODE_CF_SKU = "CfSku";

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
