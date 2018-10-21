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

package com.jumbo.wms.exception;



public interface ErrorCode {
    String BUSINESS_EXCEPTION = "business_exception_";
    String EMS_ERROR = "EMS_trun";
    int SYSTEM_ERROR = 1;
    int OMS_SYSTEM_ERROR = 2;
    int SYSTEM_ERROR_EXPORT_LINE_OUTOF_LIMIE = 4;
    int SYSTEM_ERROR_IMPORT_FILE_IS_NULL = 20;
    int ERROR_NOT_SPECIFIED = -1;
    int SESSION_TIMEOUT = -2;
    int NO_SUFFICICENT_PRIVILEGE = -10;

    int BRAND_LIMIT_THE_FUNCTION = 51;

    int OMS_ORDER_CANACEL = 101; // oms订单取消

    int RETURN_ORDER_ERROR = 909; // 保质期商品[{0}] 过期时间[{1}] 不存在销售出库记录中
    /**
     * PDA ERROR CODE
     */
    int PDA_SYS_ERROR = 1000; // 系统错误
    int PDA_IS_LIMIT = 1001; // PDA机器未认证
    int PDA_CODE_NOT_FOUND = 1002; // 单据未找到
    int PDA_EXECUTE_ERROR = 1003; // 无法执行PDA
    int PDA_SKU_NOT_FOUND = 1010; // 商品未找到 或者 数量不正确
    int PDA_SKU_NOT_IN_ORDER = 1011; // 商品不再单据中
    int PDA_SKU_NOT_SHELF_MANAGEMENT = 1012; // PDA 暂时不支持保质期商品入库。商品{0}
    int PDA_LOCATION_NOT_FOUND = 1020; // 库位未找到[{0}]
    int PDA_LOCATION_NOT_ENOUGHT_CAPACITY = 1021; // 库容不足
    int PDA_LOCATION_NOT_SUPPORT_TRANSTYPE = 1022; // 不支持作业类型
    int PDA_LOCATION_NOT_SUPPORT_INBOUND_MODE = 1023; // 单批隔离无法存放
    int PDA_LOCATION_IS_LOCKED = 1024; // 库位已锁定
    int PDA_PLAN_QTY_LQ_ACTUAL = 1025; // 数量已大于总量

    int SO_CODE_IS_NULL = 2001; // 接口未找到订单号
    int SO_TO_WH_INVENTORY_IS_NULL = 2002; // 无可用库存
    int NO_ROLE_TO_ACCESS = 2003; // 无权访问

    int OUT_BOUND_NEED_WRAP_STUFF = 4050; // 当前订单出口需要输入包材条码
    // 作业申请单{0}，状态已经被修改,不能重复操作
    int STA_STATUS_ERROR = 10000;// 执行失败：作业单状态异常
    int STA_PO_ERROR = 10001;
    int STA_STALINE_EMPTY = 10002;
    int STA_STALINE_NUMBER_ERROR = 10038;// 判断数量 不等于计划量
    int STA_STALINE_LOCATION_QUANTITY_EMPTY = 10003;
    // business_exception_10004=作业申请单Excel第[{0}]库位[编码={1}]不存在
    int STA_STALINE_LOCATION_NOT_EXIST = 10004;
    int STA_SKU_BARCODE_CODE_EMPTY = 10005;
    int STA_QUANTITY_NOT_SAME = 10006;
    // business_exception_10007=作业申请条码[{0}]JMCode[{1}]实际上架数量与计划执行量不相等
    int STA_QUANTITY_ERROR = 10007;
    int STA_QUANTITY_UNPLANNED = 10008;
    // business_exception_10009=作业申请单有未完成上架的作业明细单,在未完成之前不能进行收货操作
    int STA_HAS_CREATED_STV = 10009;
    // business_exception_10010=根据采购单创建作业申请单时,采购单{0}的状态必须是新建[=1],但是目前状态是{1}.
    int PO_STATUS_CREATE_ERROR = 10010;
    // business_exception_10011=作业申请单收货时,采购单{0}的状态必须是已确认[=5]/部分收货[=10],但是目前状态是{1}.
    int PO_STATUS_RECEIVE_ERROR = 10011;
    // business_exception_10012=作业申请单完成时,采购单{0}的状态必须是已确认[=5]/部分收货[=10],但是目前状态是{1}.
    int PO_STATUS_CLOSE_ERROR = 10012;
    // business_exception_10013=作业申请单第{0}行收货数量是{1},但是SN序列号的数量是{2}.
    int ERROR_QUANTITY_SNS = 10013;
    int SKU_NUMBER_IS_NULL = 10015;
    int SKU_EXPIRE_DATE_IS_NULL = 10016; // 保质期商品 条码{0} 未发现保过期时间。
    int SKU_PODUCTION_DATE_IS_ERROR = 10017; // 保质期商品 条码{0} 生产日期格式错误{1}。
    int SKU_SHELF_MANAGEMENT_VALID_DATE = 10018; // 保质期商品 条码{0} 过期时间未维护。
    int SYSTEM_SKU_SHELF_MANAGEMENT_IS_NULL = 10019; // 系统数据异常，保质期商品 条码{0} 未找到出库商品过期时间。
    int SKU_PD_AND_ED_IS_NULL = 10020; // 保质期商品 条码{0} 生成日期和过期时间至少提供其一。
    int SKU_EXPIRE_DATE_IS_ERROR = 10021; // 保质期商品 条码{0} 过期时间格式错误{1}。
    int SKU_SHELF_MANAGEMENT_ERROR = 10022; // {0} {1}不是保质期商品 无法修改状态为临近保质期
    int SKU_SHELF_MANAGEMENT_EXPIREDATE_ERROR = 10023; // {0} {1} 过期时间不正确！
    int SKU_TIME_TYPE_IS_NULL = 10028; // 保质期商品{0} ,过期时间类型未维护。
    int SKU_SHELF_MANAGEMENT_TYPE_ERROR = 10029; // {0} {1}不是保质期商品 库存状态不能为临近保质期
    int EXECL_SHEET_ERROR = 10030; // 单元格 {0} 错误汇总
    int INVENTORY_NULL_ERROR = 10031; // {0} 无库存记录。
    int SKU_WAREHOUSE_CUSTOMER_ERROR = 10032; // {0} {1} 不是该仓库对应客户下的商品。
    int SKU_NOT_SHELF_MANAGEMENT_ERROR = 10033; // {0} {1} 不是保质期商品。
    int SKU_NOT_SHELF_LOCATION_DATE_ERROR = 10034; // 存在有效期商品，请设置仓库信息勾选 是否不校验有效期
    int SKU_NOT_SHELF_LOCATION_SN_ERROR = 10035; // 存在SN商品，请设置仓库信息勾选 是否不校验SN
    int SKU_NOT_SHELF_LOCATION_DATE_ERROR1 = 10036; // 存在有效期商品，库间移动类型，请设置出库仓勾选 是否不校验有效期
    int SKU_NOT_SHELF_LOCATION_SN_ERROR1 = 10037; // 存在SN商品，库间移动类型，请设置出库仓信息勾选 是否不校验SN

    int STA_SKU_ISINVSTATUS_ERROR = 10053;
    // business_exception_stv_10101=作业明细单的状态错误,已被其他用户修改
    int STV_STATUS_ERROR = 10101;
    // business_exception_stv_10102=当前仓库库位不足,请修复后再尝试此操作
    int STV_LOCATION_NOT_ENOUGH = 10102;
    // business_exception_stv_10103=请初始化可销售的库存状态数据后再尝试此操作
    int STV_NO_INVENTORY_STATUS = 10103;
    // business_exception_stv_10104=商品条码{0}对应编码为{1}的库位不适合当前操作,请自选合适的库位
    int STV_LOCATION_ERROR = 10104;
    // business_exception_stv_10105=商品条码{0}对应编码为{1}的库位的库容不适合当前操作,请自选合适的库位
    int STV_LOCATION_QUANTITY_ERROR = 10105;
    // business_exception_stv_10106=商品条码{0}对应编码为{1}的库位已经被占用,请自选合适的库位
    int STV_LOCATION_OCCUPY_ERROR = 10106;
    // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
    int STV_LOCATION_LINE_ERROR = 10107;
    // business_exception_10108=系统找不到适合作业单类型[{0}]相对应的作业类型
    int STV_TRAN_TYPE_ERROR = 10108;
    // SN商品不能填写在此处
    int SKU_SN_TYPE_ERROR = 10108;
    // 非SN商品不能填写在此处
    int SKU_NOT_SN_TYPE_ERROR = 10108;
    // business_exception_10109= 行[{0}] 商品{0} 批次未找到
    int STV_BATCH_CODE_IS_NULL = 10109;
    // 数据不一致
    int SKU_INPUT_TYPE_ERROR = 10129;
    // SN编码不一致
    int SKU_SN_CODE_ERROR = 10130;
    int PARTLY_OUTBOUND_ERROR = 10131;

    // 商品正在创建，等待OMS反馈，稍等10分钟后再重试！
    int CREATE_SKU = 10132;
    // 商品不存在，请联系品牌
    int BRAND_SKU_NOT_EXITS = 10139;
    // OMS无法创建，请在界面上直接创建
    int CREATE_SKU_ERROR = 10133;
    // 作业单状态：已核对，O2O+QS作业单核对完成后不能取消[{0}]
    int CANCEL_O2O_QS_ORDER_ERROR = 10138;


    // business_exception_10023=商品{0} 店铺{1} 库存状态{2} 对应库间移动出库数据未找到
    int ERROR_SKU_OWNER_STATUS_NOT_FOUND = 10023;

    // business_exception_10024=收货数量有误，订单{0},类型为[{1}]
    int VMI_FLITTING_TRANSIT_CROSS_ERROR = 10024;
    // business_exception_10105=导入作业单数必须等于作业单详情总单数
    int STV_LOCATION_LINE_NUMBER_ERROR = 10025;
    // business_exception_10026=上架数量有误，订单{0},类型为[{1}]
    int VMI_FLITTING_TRANSIT_CROSS_NUMERROR = 10026;

    int STA_INBOUND_SKU_NUM_ERROR = 10027;// 当前审核商品为0。请取消此单或重新调整。


    // business_exception_10014=sheet{0} SN号 [{1}]有重复.
    int ERROR_SN_IS_NOT_UNIQUE = 30014;
    int ERROR_SN_IS_EXIT = 30015;
    int ERROR_SN_ISNUMBER = 30016;
    int PDA_NO_LOG = 10014;

    // 系统错误或SN号有重复
    int PO_SYSTEM_ERROR = 10100;

    int STA_CREATE_ERROR = 10200;
    int NO_INVENTORY = 10201;
    int PICKING_LIST_NOT_FOUND = 10202;
    int SKU_NO_HHW = 10255;
    int USER_NOT_FOUND = 10203;
    int OPERATION_UNIT_NOT_FOUNT = 10204;
    int STA_NOT_FOUND = 10205;
    
    
    int STA_NOT_FOUND1 = 910205;//取消失败-订单未找到
    int STA_CANNOT_CANCEL1 = 2100000094;// 取消失败-订单无法取消;作业单已核对
    int STA_CANNOT_CANCEL2 = 2100000994;// 取消失败-订单无法取消;作业单已转出
    int STA_CANNOT_CANCEL3 = 2100009994;// 取消失败-订单无法取消;作业单已完成


    
    int STA_CANCELED_ERROR = 10206;
    int PICKING_LIST_IN_STA_IS_NOT_NULL = 10207;
    int STA_OUTBOUND_ERROR = 10208;
    int PICKING_LIST_CANCEL_ERROR = 10209;
    int STV_NOT_FOUND = 10210;
    int STV_LINE_NOT_FOUND = 10233;
    int NO_OCCUPIED_INVENTORY = 10211;
    int PACKAGE_INFO_NOT_FOUND = 10212;
    int PACKAGE_IS_HAND_OVER = 10213;
    int HAND_OVER_LIST_CREATE_ERROR = 10214;
    int HAND_OVER_NOT_FOUND = 10215;
    int HAND_OVER_NOT_ENOUGNT_MESSAGE = 10216;
    int HAND_OVER_LIST_LINE_NOT_FOUND = 10217;
    int HAND_OVER_LIST_IS_FINISHED = 10218;
    int OPERATION_UNIT_TYPE_ERROR = 10219;
    int PACKAGE_INFO_NO_ENOUGHT_MESSAGE = 10220;
    int PACKAGE_INFO_IS_EXIST = 10221;// 执行【{0}】失败：快递单号【{1}】已存在
    int HAND_OVER_STA_NOT_FUND = 10222;
    int HAND_OVER_STA_NOT_HAND_OVER = 10223;
    int SKU_NOT_FOUND = 10300;
    int OUT_BOUND_NEED_WRAP_STUFF_NOT_FOUND = 10333;
    int LOCATION_NOT_FOUND = 10301;
    int TRANSTACTION_TYPE_NOT_FOUND = 10302;
    int INVENTORY_STATUS_NOT_FOUND = 10303;
    int INVENTORY_STATUS_NOT_FOUND_BY_NAME = 103031;
    int TRANSTACTION_TYPE_IS_SYSTEM = 10304;
    int SKU_NOT_ENOUGHT_INVNENTORY = 10310;
    int STA_PACKAGE_INFO_NOT_ALL_CHECKED = 10311;

    int SKU_NOT_ENOUGHT_INVNENTORY_2 = 10312;
    int SKU_NOT_MATCH_OUTBOUND_STVLINE = 10313;
    int BARCODE_AND_STATUS_IS_NOT_EQUAL = 10314;
    // 存在非已转出状态的订单，不能创建交接清单
    int OUVR_ORDER_STA_STATUS_NOT_INTRANSIT = 10315;

    int INVENTORY_CHECK_NOT_FOUND = 10320;// 盘点批未找到[{0}]
    int INVENTORY_CHECK_STATUS_NOT_UNEXECUTE = 10321;
    int INVENTORY_CHECK_CONFIRM_USER_EMPTY = 10322;
    int INVENTORY_CHECK_FINISHED = 10323;
    int INVENTORY_CHECK_LINE_NO_LOCATION = 10324;
    int INVENTORY_CHECK_STATUS_ERROR = 10325;// 操作失败：盘点批[{0}]状态已被修改

    int SALES_ORDER_STATUS_ERROR = 10350;
    int RA_SALES_ORDER_STATUS_ERROR = 10351;
    int RA_STATUS_ERROR_NO_INBOUND = 10352;
    int RA_STATUS_ERROR_NO_CHECKED = 10353;
    int UPDATE_K3INFO_FOR_SO_ERROR = 10354;
    int BRAND_SKU_REPEAT = 10356;

    int WH_DISTRICT_HAS_INVENTORY = 10401;
    int WH_LOCATION_HAS_INVENTORY = 10402;
    int WH_LOCATION_SAME_CODE = 10403;
    int WH_LOCATION_SAME_BAR_CODE = 10404;
    int WH_LOCATION_NO_TRANSACTION_TYPE = 10405;
    int WH_LOCATION_NO_SKU_FOUND = 10406;
    int WH_TRANTYPE_DIRECTION_NOT_MATCH = 10407;
    // business_exception_10408=第{0}行的库位编码格式错误,请按[行标识X/XY/XYZ/XYZC]格式填写
    int WH_LOCATION_SYSCOMPILECODE_ERROR = 10408;
    // business_exception_10415=弹出口区域编码[{0}]在仓库已经存在或已被禁用
    int WH_LOCATION_POPUPAREACODE_ERROR = 10415;
    // business_exception_10409=第{0}行的库位编码[{1}]在仓库已经存在
    int WH_LOCATION_SYSCOMPILECODE_EXISTS = 10409;
    // business_exception_10410=第{0}行的库位条码[{1}]在仓库已经存在
    int WH_LOCATION_BAR_CODE_EXISTS = 10410;
    // business_exception_10411=第{0}行的库位编码[{1}]与第{2}行库位编码重复
    int WH_LOCATION_SYSCOMPILECODE_EXISTS_REDUPLICATE = 10411;
    // business_exception_10411=第{0}行的库位条码[{1}]与第{2}行库位条码重复
    int WH_LOCATION_BAR_CODE_EXISTS_REDUPLICATE = 10412;
    int WH_DISTRICE_NOT_FOUND = 10413;
    int WH_LOCATION_IS_LOCKED_OR_OCCPUAID = 10414;

    int TRANSACTION_TYPE_TRANIST_INNER_NOT_FOUND = 10500;
    int TRANIST_INNER_LINE_EMPTY = 10501;
    int TRANSACTION_TYPE_INVENTORY_CHECK_OUT_NOT_FOUND = 10502;
    int TRANSACTION_TYPE_INVENTORY_CHECK_IN_NOT_FOUND = 10503;
    int OWNER_IS_NULL = 10504;
    int INVENTORY_STATUS_CHANGE_LINE_EMPTY = 10505;
    int TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT_NOT_FOUND = 10506;
    int TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN_NOT_FOUND = 10507;
    int TRANSACTION_TYPE_VMI_INVENTORY_CHECK_IN_NOT_FOUND = 10520;
    int TRANSACTION_TYPE_VMI_INVENTORY_CHECK_OUT_NOT_FOUND = 10521;

    int SKU_QTY_NOT_EQ_FOR_INV_STATUS_CHANGE = 10509;
    int SKU_QTY_NOT_EQ = 10510;
    int TRANIST_CROSS_STV_LINE_EMPTY = 10511;
    int TRANSACTION_TYPE_TRANSIT_CROSS_OUT_NOT_FOUND = 10512;
    int TRANSIT_INNER_CALCEL_ERROR = 10513;
    int TRANSIT_CROSS_CALCEL_ERROR = 10514;
    int INVENTORT_STATUS_CHANGE_CALCEL_ERROR = 10515;
    int OUT_OF_BOUND_FAILURE = 10550; // 操作失败：作业单[{0}]出库失败
    int INVENTORY_SKU_LOCATION_IS_SINGLE_STOREMODE_ERROR = 20704;

    int EXCEL_IMPORT_SKU_STOREMODE_ERROR = 20703;

    
    int EXCEL_IMPORT_MORE_THAN_1000 = 10748;
    int EXCEL_IMPORT_FILE_READER_ERROR = 10700;// 导入失败：Excel读取文件错误，请检查格式
    int EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE = 10701;
    int EXCEL_IMPORT_LOCATION_NOT_FOUND = 10702;
    int EXCEL_IMPORT_SKU_BARCODE_NOT_FOUND = 10703;
    int EXCEL_IMPORT_SKU_CODE_NOT_FOUND = 10704;
    int EXCEL_IMPORT_INVENTORY_NOT_EMPTY = 10705;
    int EXCEL_IMPORT_COMPANY_SHOP_NOT_FOUND = 10706;
    int EXCEL_IMPORT_INITIALIZE_TRANSATION_TYPE_NOT_FOUND = 10707;
    int EXCEL_IMPORT_LOCATION_NOT_SUPPORT_TRANSACTION_TYPE = 10708;
    int EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND = 10709;
    int EXCEL_IMPORT_BETWEENLIBARYMOVE_INITIALIZE_TYPE = 10710;
    int EXCEL_IMPORT_BETWEENLIBARYMOVE_NOT_QUANTITY = 10711;
    int EXCEL_IMPORT_BETWEENLIBARYMOVE_NOT_SKU = 10712;
    int EXCEL_IMPORT_TRANS_NO = 10741;
    int EXCEL_IMPORT_TRANS_NO2 = 10742;
    int EXCEL_IMPORT_TRANS_NO3 = 10743;
    int EXCEL_IMPORT_AREA_NO1 = 10744;
    int EXCEL_IMPORT_AREA_NO2 = 10745;
    int EXCEL_IMPORT_TRANS_NO4 = 10746;
    int START_OPERATION_UNIT_NOT_FOUNT = 10713;
    int END_OPERATION_UNIT_NOT_FOUNT = 10714;
    int BETWENLIBARY_STA_CREATE_ERROR = 10715;
    int BETWEENLIBARYMOVE_STA_CREATE_ERROR = 10716;
    int EXCEL_IMPORT_TRANSACTION_TYPE_NOT_FOUND = 10717;
    int BETWENLIBARY_STA_NOT_FOUND = 10718;
    int BETWENLIBARY_STA_OCCUPIED_ERROR = 10719;
    int EXCEL_IMPORT_TRANSIT_INNER_TRANSATION_TYPE_NOT_FOUND = 10720;
    int BETWENLIBARY_STV_NOT_FOUND = 10721;
    int BETWENLIBARY_NO_OCCUPIED_INVENTORY = 10722;
    int BETWENLIBARY_MAINWAREHOUSE_NOT_MOVE_SKU = 10723;
    int EXCEL_IMPORT_LPCODE_NOT_FOUND = 10724;
    int EXCEL_IMPORT_SEND_QTY_LS_ZERO = 10725;
    int EXCEL_IMPORT_SKU_NOT_FOUND = 10726;
    int EXCEL_IMPORT_STA_FONDU = 10727;
    int EXCEL_IMPORT_SLIP_CODE_IS_EXISTS = 10728;
    int EXCEL_IMPORT_SLIP_CODE_HEAD_NOT_MATCH = 10729;
    int EXCEL_IMPORT_STA_CREATE_NOT_ENOUNGT_INVENTORY = 10730;
    int EXCEL_IMPORT_SLIP_CODE_LINE_NOT_MATCH = 10731;
    int EXCEL_IMPORT_SN_EXISTS = 10732;
    int EXCEL_IMPORT_SN_NOT_EXISTS = 10733;
    int EXCEL_IMPORT_SN_HAS_SAME = 10734;
    int EXCEL_IMPORT_SKU_QTY_NOT_EQ = 10735;
    int EXCEL_IMPORT_EXCEL_IS_NULL = 10736;
    int EXCEL_IMPORT_SKU_SN_NOT_FOUND = 10737;
    int BETWENLIBARY_STV_OUTBOUND_NO_FINISH = 10738;
    int BETWENLIBARY_STA_QTY_IS_NOT_NULL = 10739;
    int EXCEL_IMPORT_SALE_MODE_ERROR = 10740;
    int EXCEL_IMPORT_QUANTITY_MINUS = 10747;// sheet{0} 单元格 {1} 数量 \t{2}\t 不正确
    int EXCEL_IMPORT_VMI_INVCK_SKU_NOT_FOUND = 10800;
    int EXCEL_IMPORT_VMI_INVCK_NOLOCATION = 10801;
    int EXCEL_IMPORT_VMI_INVCK_LOCATION_NOT_EXIST = 10802;
    int EXCEL_IMPORT_VMI_INVCK_SKU_QUANTITY_NOT_SAME = 10803;
    int EXCEL_IMPORT_VMI_INVCK_STATUS_NOT_EXIST = 10804;
    int EXCEL_IMPORT_VMI_INVCK_LOCKED = 10805;
    int EXCEL_IMPORT_VMI_INVCK_SKU_LACK = 10806;
    int END_OPERATION_TYPE_NOT_FOUNT1 = 200201;
    int ROOTSUM_LESS_THAN_CARTONSSUM = 200202;
    int CARTONS_LINE_IS_NULL = 200203;
    int CARTONS_LINE_IS_NOT_IN_PLAN = 200204;
    int CARTONS_LINE_QTY_MORE_THAN_PLAN = 200205;
    int CARTONS_LINE_QTY_IS_ZERO = 200206;
    int CARTONS_LINE_INV_STATUS_IS_ERROR = 200207;
    int NOT_SKU_CHILD_SN = 200214;// 缺失SN号绑定关系

    int EXCEL_IMPORT_INVENTORY_CHECK_NO_LOCATION = 10900;
    int EXCEL_IMPORT_IN_OUT_IS_NOT_EQUALS = 10910;
    int EXCEL_IMPORT_IN_OUT_IS_NOT_EQUALS_FOR_INV_STATUS_CHANGE = 10911;
    int EXCEL_PARSE_ERROR = 10912;
    int EXCEL_SORT_ERROR = 109122;
    int CODE_IS_EXIT_ERROR = 109123; // 集货编码已存在
    int SORT_IS_NOT_POSITIVE_INTEGER = 109124; // 顺序不是正整数
    int POPUPAREA_IS_NOT_EXIT = 109125; // 弹出口不存在
    int CODE_IS_REPEAT_ERROR = 109126; // 集货编码重复
    int SORT_IS_REPEAT = 109127;// exl导入的数据有重复
    int SORT_IS_REPEAT_EXIT = 109128; // exl与数据库中数据有重复

    int SOURCE_OWNER_NOT_EXIT = 109200;// 来源店铺{0}不存在
    int TARGET_OWNER_NOT_EXIT = 109201;// 目标店铺{0}不存在
    int TARGET_OWNER_RATIO = 109202;// 目标店铺比例{0}不正确
    int TARGET_SOURCE_EXIT = 109203;// 同一来源店铺{0}同一商品{1}下，目标店铺{2}重复
    int TARGET_OWNER_RATIO_HIGH = 109204;// 同一来源店铺{0}同一商品{1}下，目标店铺比例总和大于百分百

    int SKU_STYLE_NOT_EXIST = 10913;
    int TRANS_AREA_EXIST = 10914;
    int TRANS_CITY_NULL = 10915;
    int TRANS_IS_NULL = 10916;
    int TRANS_PRO_IS_NULL = 10917;
    int TRANS_ROLE_IS_EXIT = 10918;
    int TRANS_ROLE_IS_NOT_EXIT = 10919;
    int TRANS_ROLE_ACCORD_EXCEL_PARSE_ERROR = 10920;
    int WH_BOX_INBOUND_EXCEL_PARSE_ERROR = 10921;

    int CREATE_PO_STA_STA_ALREADY_EXISTS = 10999;

    int OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY = 11000;
    int SKU_NO_INVENTORY_QTY = 11001;
    int SKU_NO_INVENTORY_QTY_2 = 9911001;

    int MODIFT_TRANS_STA_STATUS_ERROR = 11002;

    int TRACKING_IS_NULL = 12001;
    int WEIGHT_IS_NULL = 12002;
    int WEIGHT_IS_ZERO = 12009;
    int PICKING_USER_IS_NULL = 12003;
    int LOCATION_IS_LOCKED_OR_OCCUPAID = 12004;
    int INVNETORY_CHECK_CREATE_ERROR = 12005;
    int DISTRICT_LOCATION_IS_LOCKED_OR_OCCUPAID = 12006;

    int SKU_BAR_CODE_NOT_NULL = 12007;
    int SON_SKU_BAR_SIMILAR = 12008;

    int SKU_SN_NOT_FOUND = 12050;// 执行【{0}】失败：扫描SN号错误
    int SALES_OUTBOUND_STA_NOT_FOUNT = 12051;
    int EXPRESS_ORDER_TRACKINGNO_CHECK = 12052;
    int EXPRESS_ORDER_TRACKINGNO_NO_LEGAL = 12053;
    int EXPRESS_ORDER_TRACKINGNO_UNIQUENE = 12054;
    int EXPRESS_ORDER_TRACKINGNO_EXIST_UNIQUENE = 12055;
    // 操作失败：渠道名称已存在
    int CHANNEL_COMBINE_NAME_EXIST_ERROR = 12056;
    // 操作失败：对接码已存在
    int CHANNEL_COMBINE_CODE_EXIST_ERROR = 12057;
    // 渠道信息保存失败
    int CHANNEL_COMBINE_SAVE_ERROR = 12058;
    // 渠道行为信息保存失败
    int CHANNEL_COMBINE_SAVEACTION_ERROR = 12059;
    // 操作失败：对接码已存在
    int CHANNEL_COMBINE_EXIST_ERROR = 12060;
    // 操作失败：对接码已存在
    int CHANNEL_COMBINE_TYPE_ERROR = 12061;
    // 此作业单有非新建状态的包裹，不能修改
    int EXPRESS_ORDER_TRACKINGNO_NOT_CREATE = 12070;

    int VALIDATE_BI_CHANNEL_ERROR = 12500;// 验证渠道信息提供错误
    int VALIDATE_BI_CHANNEL_TYPE_ERROR = 12501;// 渠道{0}为虚拟渠道，不允许此操作
    int VALIDATE_BI_CHANNEL_STATUS_ERROR = 12502;// 渠道{0}已经作废，不允许此操作

    // business_exception_13001=SN序列号{0}不在当前计划内
    int SNS_SKU_NO_PLAN = 13001;
    // business_exception_13002=对应SKU编码{0},条码{1}的数量是{2},但是SN序列号的数量是{3}
    int SNS_ACTUAL_ERROR = 13002;
    // business_exception_13003=导入SN序列号的Excel必须选择SKU编码/条码方式导入
    int SNS_SKU_TYPE = 13003;
    // business_exception_13004=导入SN序列号的Excel必须有数据
    int SNS_NO_DATA = 13004;
    // business_exception_13009=非SN商品
    int SNS_NOT_SN = 13009;
    // business_exception_13005=第{0}行条码{1}对应编码为{2}的商品必须填写序列号
    int SNS_SKU_NO_DATA = 13005;

    int SN_INSERT_UQ_ERROR = 13010;

    int SNSKU_QTY_NOTEQUAL_LINESKU_QTY = 14001;

    // 导入的SN号{0}有重复
    int IMPORT_SN_ISNOT_UNIQUE = 14002;
    // 导入的SN号{0}有重复（数据库）
    int IMPORT_SN_ISNOT_UNIQUEO = 14008;
    // 入库需要SN号的商品数量{0}与当前系统内SN数量{1}不一致
    int SKU_QUANTITY_NOT_EQUALS_SN_QUANTITY = 14003;

    // SKU{0}需要SN号数量{1}与Excel中的SN号数量{2}不一致
    int SKU_QUANTITY_NOT_MATCHING_SN_QUANTITY = 14004;
    // SKU [ {0} ]在当前Excel中不存在
    int SKU_NOT_FOUND_IN_EXCEL = 14005;
    int SKU_NOT_FOUND_IN_CURRENT_STA = 14009; // sku[{0}]不在当前作业单中
    // 导入的SN号{0}不符合规则
    int IMPORT_SN_ISNOT_MEET_REGULATION = 14006;
    // 作业类型不正确
    int PREDEFINED_STA_TYPE_INCORRECT = 14501;
    // 事物类型未找到
    int PREDEFINED_TRANSACTION_TYPE_INCORRECT = 14502;

    int CLEAR_INV_ERRORCODE = 14503;

    int EXCEL_ERROR_TRANSPORTATOR_NULL = 15001; // 快递商未找到
    int EXCEL_ERROR_DISTRICT_NOT_FOUND = 15002;// 区域未找到
    int EXCEL_ERROR_TRANS_DISTRICT_NOT_FOUND = 15003; // 大区编码未找到
    int EXCEL_ERROR_TRANS_MIN_AND_BASE_ERROR = 15004; // 保底与基础不能都不为0
    int EXCEL_ERROR_TRANS_COST = 15005; // 首重价格与首重重复
    int EXCEL_ERROR_DEPARTURE_IS_NULL = 15006; // 发运地未找到
    int EXCEL_ERROR_DELIVERY_COST_ZERO = 15007; // 续重续重价格不能为0

    int INBOUND_TIME_IS_NULL = 15008; // 未找到 入库时间
    int INBOUND_BATCH_CODE_ERROR = 15009; // 入库商品{0} 未找到对应的出库商品的批次
    int INBOUND_BATCH_CODE_ERROR_1 = 15010; // 保质期商品{0} 入库过期时间不包含在出库过期时间内 或者 未找到对应的出库商品的批次

    int PACGING_IS_NOT_REGISTER = 15050; // 此快递【{}】包裹未登记，请登记后再做解锁（在集团——退换货包裹处理——退换货快递登记 功能下做包裹登记）。

    // 公司不能为空
    int TRANSPORTATOR_REF_CMPPANY_IS_NULL = 15101;
    // 店铺不能为空
    int TRANSPORTATOR_REF_SHOP_IS_NULL = 15102;
    // 物流商不能为空
    int TRANSPORTATOR_REF_TRANSPORTATOR_IS_NULL = 15103;
    // 物流商不支持COD操作
    int TRANSPORTATOR_REF_TRANSPORTATOR_IS_COD = 15107;
    // 仓库不能为空
    int TRANSPORTATOR_REF_WAREHOUSE_IS_NULL = 15104;

    // 时效类型填写错误
    int TIME_TYPE_IS_ERROR = 15108;

    // 作业类型填写错误
    int STA_TYPE_IS_ERROR = 15109;

    // 店铺填写错误
    int OWNER_IS_ERROR = 15110;

    // 是否COD填写错误
    int COD_IS_ERROR = 15111;

    // 集货名称不能为空
    int POINTNAME_IS_NULL = 15112;

    // 第{0}行优先级{1}填写错误，只能为数字
    int SORT_IS_NULL1 = 15113;
    // 第{0}行优先级{1}已存在
    int SORT_IS_NULL2 = 15114;
    // 第{0}行优先级不能为空
    int SORT_IS_NULL3 = 15115;
    // 第{0}行物流商不存在
    int LPCODE_IS_NULL = 15116;

    // 店铺还有业务没有完成
    int SHOP_REF_SHOP_BUSSINESS = 15105;
    // 仓库还有业务没有完成
    int SHOP_REF_SHOP_WAREHOUSE = 15106;

    // 计划发货量不正确
    int TRANS_DELIVERY_CFG_INCORRECT = 15200;

    int STA_TYPE_ERROR = 152001; // 作业单类型错误

    int NOT_FIND_STA = 152002; // 未找到作业单

    int NOT_FIND_STV = 152003; // 未找到仓库作业明细单

    int SKUSN_ALREADY_EXISTS = 152004; // SkuSn号已存在

    int SKUSN_WAITING_FOR_PROCESSING = 152005; // SkuSn号状态为采购销售出入库待处理

    int SKUSN_NOT_EXISTS = 152006; // SkuSn号不存在

    int EXCEL_TRANSACTION_DIRECTION_IS_NULL = 152007; // excel表格的事务方向值为空

    int SKUSN_RELATION_NOT_EXISTS = 152008; // 与商品编码[{0}]关联的SkuSn号不存在
    int STA_STATUS_NOT_COMPLETED = 152009; // 作业单编码[{0}]不是已完成状态,不满足导入条件,请检查
    int STA_IS_SUB_ORDER = 152010; // 此作业单编码[{0}]是子订单,不能导入.请使用编码为[{1}]的合并父订单导入

    int STV_LINE_SKU_STORE_MODE_NOT_FOUND = 15211; // 作业单中商品【{0}】存放模式未找到
    int LOCATION_INBOUND_NOT_SUPPORT = 15212; // 入库作业失败，商品批次隔离无法入库：
    int LOCATION_INBOUND_NOT_SUPPORT_DETIAL = 15213; // 库位[{0}]，商品名称[{1}]编码[{2}]，商品批次隔离无法上架
    int LOCATION_NOT_USING = 15214; // 库位[{0}]被锁定或不可使用
    int LOCATION_NOT_ENOUGHT_CAPACITY = 15215; // 库位[{0}]库容不足
    // 未找到该商品
    int IMPORT_SN_SKU_ISNOTFIND = 15216;
    // 导入SN失败
    int IMPORT_SN_ERROR = 15217;

    int LOCATION_INBOUND_NOT_DETIAL = 15218;// 库位[{0}]，商品名称[{1}]编码[{2}]，保质期商品无法上架

    int LOCATION_NOT_USING_OR_EXIT = 15219;// 库位[{0}]不存在或锁定不可用

    int NO_SUPPORT_TRANSPORTATOR = 20001; // 物流无法送达
    int NO_SUPPORT_TRANSPORTATOR_NO_COST = 20002; // 所有支持物流未维护价格组 {0}
    int TRANSPORTATOR_NOT_FOUND = 20003; // 物流供应商未找到[{0}]
    int WH_NO_DEPARTURE = 20004; // 仓库[{0}]未维护发运地
    int TRNANSPORTATOR_ALL_OUT_OF_LIMIT = 20005; // 该仓库中所有物流发货量均超过当日限制
    int PROVINCE_NOT_FOUND = 20006; // 省份未找到[{0}]

    int SALES_MODE_PAYMENT_SKU_COST_IS_NULL = 20010; // 付款经销商品出库必须含有成本


    int PREDEFINED_EXCEL_IMPORT_SKU_CODE_NOT_FOUND = 25000;// sheet{0} 单元格 {1} 商品 [{2}] 未找到
    int PAYMENT_SKU_NOT_BE_CREATED = 25001;// 当前作业类型不允许付款经销商品{0}、{1}
    int CAN_NOT_SETTLEMENT_SKU = 25002;// 当前作业类型不允许非结算经销商品{0}、{1}
    int CAN_NOT_CONSIGNMENT_SKU = 25003;// 当前作业类型不允许非代销商品{0}、{1}


    int NO_FINISH_STA = 30006;

    int STA_IS_NULL = 30010;

    int STV_NOT_INV_STATUS = 31001;// Excel第[{0}]行库位状态类型不能为空

    int STV_SN_NOT_NULL = 31002;// 当前仓库设置了不校验SN，Excel第[{0}]行SN不能有值
    int STV_DATE_NOT_NULL = 31003;// 当前仓库设置了不校验有效期，Excel第[{0}]行有效期不能有值

    int PDA_POST_LOG_STATUS_ERROR = 31010; // PDA日志状态错误
    int STA_NOT_EXECUTE = 31200; // 作业单[{0}]无法执行
    int INBOUND_PLAN_NOT_EQ_EXEQTY = 31201;// 商品[{0}]计划量[{1}]不等于执行量[{2}]
    int INBOUND_SN_PLAN_NOT_EQ_EXEQTY = 31202;// 商品[{0}] SN号导入计划量[{1}]不等于执行量[{2}]
    int INBOUND_PDA_NOT_INV_STATUS = 31203;// 未找到库存状态
    int INBOUND_PDA_NOT_CLERROR = 31204;// 请处理PDA错误日志
    int INBOUND_PDA_QTY_ERROR = 31205;// 商品[{0}]执行量[{1}]大于 计划量[{2}]

    int VMI_INSTRUCTION_TYPE_ERROR = 30020;
    int VMI_INSTRUCTION_STATUS_ERROR = 40012;
    int VMI_INSTRUCTION_SLIPCODE_ERROR = 40013;
    int VMI_INSTRUCTION_SLIPCODE1_ERROR = 40014;
    // 找不到盘点批
    int INV_CHECK_IS_NULL = 40000;

    int INV_CHECK_IMPORT_IS_NULL = 40001;// 导入的文件未找到数据

    int INV_CHECK_SKUCOST_IS_NULL = 40002;// 第{0}行库存成本成本不正确
    int INV_CHECK_OWNER_IS_NULL = 40003;// 第{0}行店铺不能为空或找不到店铺
    int INV_CHECK_IMPORT_COUNT_ERROR = 40004;// 导入数据量不等于预执行数据量
    int INV_CHECK_IMPORT_NOT_SKU = 40005;// 未找到需处理SKU: {0} 库位为：{1}
    int INV_CHECK_SKU_IS_NULL = 40006;// 第{0}行 商品不能为空
    int INV_CHECK_LOCATION_IS_NULL = 40007;// 第{0}行 库位不能为空
    int INV_CHECK_QUANTITY_ERROR = 40008;// 第{0}行 数量错误，必须和盘盈数量相等
    int INV_CHECK_IMPORT_NOT_FOUND = 40009;// 第{0}行商品：{1} 库位：{2} 的需处理数据未找到
    int INV_CHECK_IS_UNTREATED = 40010;// 还存在未处理的盘盈数据(店铺和库存成本)
    int VMI_INV_CHECK_CONFIRM_NUM_NOT_SAME = 40011;// VMI库存调整存数与库存占用数不相同

    int SKU_TOGETHER_LOC_IS_SKU = 41000;// 第{0}行 商品：{1} 是单批隔离的商品，而库位:{2} 已经存在批次不同的{3}商品

    int EXE_SKU_TOGETHER_LOC_IS_SKU = 41001;// 商品：{0} 是单批隔离的商品，而库位:{1} 已经存在批次不同的{2}商品

    int INBOUND_IMPORT_AMOUNT_CONFIRM_RECEIVE = 50010; // sheet{0} 单元格 {1} 商品 [{2}]
    // 当前收货数量[{3}]大于系统中未完成收货量 {4}

    int INBOUND_IMPORT_AMOUNT_CONFIRM_RECEIVE_NOT_FOUND = 50020; // sheet{0} 单元格 {1} 商品 [{2}]
    // 不在当前收货批中

    int INBOUND_IMPORT_AMOUNT_CONFIRM_RECEIVE_NEED_SN = 50030; // sheet{0} 单元格 {1} 商品 [{2}] 需要sn号,且
    // 数量为[{3}]

    int IMPUT_SNSKU_IS_NOT_SNSKU = 42000;// sheet{0} 单元格 {1} 商品 [{2}] 非SN号商品

    int SN_IS_NOT_OUT_SN = 43000; // {0} 商品 SN {1} 不是出库时的SN号商品
    int IN_SN_COUNT_IS_NOT_OUT_SN_COUNT = 43001; // 入库 SN号商品数量 不等于 SN号商品的出库数量

    int OU_NOT_FOUND = 43010;// 组织未找到
    int WAREHOUSE_SHOP_NO_REF = 43011;// 行[{0}]仓库[{1}店铺[{2}]未关联

    int PDA_KEY_IS_PRESENCE = 50000; // PDA 编码 {0} 已存在

    int ORDER_ACCOUNTING_SOCOUNTMODEL_NAME_IS_NULL = 51000; // sheet{0} 单元格 {1} 订单核算模式名称不能为空
    int ORDER_ACCOUNTING_SOCOUNTMODEL_NAME_NOT_FOUND = 51002;// sheet{0} 单元格 {1}
    // 订单核算模式名称[{2}]有误，系统不存在


    int WAREHOUSE_OU_CODE_IS_NULL = 51004; // sheet{0} 单元格 {1} 仓库编码不能为空
    int WAREHOUSE_NOT_FOUND_BY_OU_CODE = 51006; // sheet{0} 单元格 {1} 仓库编码[{2}]有误，系统不存在

    int RMI_SERVICE_DOES_NOTSUPPORT_TYPE = 52000;// 接口不支持该类型单据
    int RMI_SERVICE_STA_UNFINISHED = 52001;// 单据[{0},{1}]未完成
    int RMI_SERVICE_SKU_QTY_ERROR = 52002;// 商品CODE{0} 数量{1}不正确。
    int RMI_SERVICE_SKU_THE_COST_OF = 52003;// 商品{0} 数量{1} 不够数量被费用化。
    int RMI_SERVICE_GIFT_MEMO_TO_DATE = 52004;// Coache 保修卡日期计算出错 SKU:{0} 。

    int USER_PASSWORD_LENGTH_IS_ERROR = 101; // 密码长度不正确
    int USER_PASSWORD_LENGTH_IS_ERROR_2 = 100; // 密码长度不正确
    int USER_PASSWORD_AND_CONFIRMPASSWORD_NOT_EQUAL = 102; // 两次密码输入不正确
    int USER_RAW_PASSWORD_IS_ERROR = 103; // 原始密码输入错误
    int USER_PASSWORD_NEW_IS_NULL = 104; // 新密码不能为空
    int USER_PASSWORD_REGULATION_ERROR = 105; // 输入的新密码不符合密码规则
    int USER_PASSWORD_REGULATION_IS_NULL = 106; // 密码规则为空
    int USER_CONFIRMPASSWORD_IS_NULL = 107; // 确认密码不能为空
    int USER_LOGIN_NAME_IS_NULL = 108; // 用户名{0}系统不存在
    int USER_RAW_PASSWORD_IS_NULL = 109; // 原始密码不能为空
    int USER_JOB_NUMBER_IS_NULL = 110; // 登录账号{0}系统不存在
    int EXTERNAL_EXECUTION_NOT_EQ_PLAN = 60000; // sheet{0} 单元格 {1} 执行量不等于计划执行量
    int EXTERNAL_SN_NOT_OUT_SN = 60001; // sheet{0} 单元格 {1} 执行量不等于计划执行量
    int EXTERNAL_SN_SKU_IS_NOT_NULL = 60002; // 计划执行SN号{0} 未找到
    int EXTERNAL_SN_IS_NOT_NULL = 60003; // sheet{0} 单元格 SN 号 不能为空
    int EXTERNAL_SN_IS_NULL = 60004; // sheet{0} 单元格 SN：{0} 号不是移库出库的SN
    int RSORDER_CODE_IS_NULL = 60005; // sheet{0} 单元格 {1}单据{2}不在该对账单内
    int RSORDER_CODE_IS_EXIST = 60006; // sheet{0} 单元格 {1}订单{2}重复导入
    int RSORDER_UNITPRICE_IS_NULL = 60007; // sheet{0} 单元格 {1} 单价不能为空
    int RSORDER_TOTOLPRICE_IS_EXIST = 60008; // sheet{0} 单元格 {1} 总价不能为空
    int RSORDER_QTY_IS_EXIST = 60009; // sheet{0} 单元格 {1} 数量不能为空
    int RSORDERLINE__TOTALACTUAL_UNEQUALLY = 60010; // sheet12 单元格 {0}单据{1} 的订单总金额与sheet1中的销售总金额有差异
    int VMI_FLITTING_OUT_SHOP_REF = 60011; // 仓库[{0}]未绑定 店铺[{1}] 不能执行此动作
    int RETURN_REQUEST_INPUT_SKU_ERROR = 60012; // sheet{0} 单元格 {1} 作业单{2} 对应商品{3} 状态{4} 未在作业单内!
    int RETURN_REQUEST_INPUT_SKU_QTY_ERROR = 60013; // sheet{0} 单元格 {1} 作业单{2} 对应商品{3}
    // 状态{4}上架数量不等于执行数量!
    int RETURN_REQUEST_EXE_ERROR = 60014; // 作业单{0}执行失败!
    int RETURN_REQUEST_STA_TYPE_ERROR = 60015; // 作业单{0} 类型错误!
    int RETURN_REQUEST_STA_QTY_ERROE = 60016; // 作业单{0} 商品{1} 状态{2} 执行量不等于计划量!
    int RETURN_REQUEST_STA_ERROE = 60017; // 作业单{0} 执行量不等于计划量!
    int RETURN_REQUEST_INPUT_INBOUND_INFO_ERROR = 60018; // sheet{0} 单元格 {1} 作业单{2} 对应商品{3}
    // 状态{4}的入库情况是非“已入库”!
    int RETURN_REQUEST_INPUT_STA_NOT_WH = 60019; // sheet{0} 单元格 {1} 作业单{2} 非当前仓库单据!

    int START_OWNER_NOT_FOUNT = 60100;// 源头店铺[{0}]未找到
    int END_OWNER_NOT_FOUNT = 60101;// 目标店铺[{0}]未找到
    int COMAPNY_SHOP_IS_NOT_FOUND = 8500; // 所选店铺系统不存在
    int NIKE_IMPORT_SIZE = 8501; // 导入文件数据超过1000条，请做调整
    int SHOP_TYPE_IS_ERROR = 8502; // 两家店铺需要都存在品牌店铺映射编码或者都不存在品牌店铺映射编码，当前两家店铺不满足创单要求。
    int SHOP_TYPE_IS_PAYMENT_ERROR = 8503;
    int SHOP_TYPE_IS_PAYMENT_ERROR_1 = 8504;

    int TWO_COMAPNY_SHOP_IS_NOT_RELATIVE = 8510; // 所选店铺未关联同一家仓库
    int STA_IS_NOT_FROZEN = 3306; // 当前作业单{0}不是冻结状态，无需解冻
    int STA_IS_ALREADY_PDA_INBOUND = 1521; // 当前作业单[{0}] 已经PDA收货了,无需重新操作
    int STV_NOT_FOUND_GENERIC = 1522;

    int NOT_FIND_PDA_INFO = 70111; // pda上传记录未找到
    int INBOUND_RETURN_REQUEST_ERROR = 70113; // 退换货入库只能一次上架
    int INSKU_NOT_EQ_OUTSKU = 70114; // 入库商品数量与出库商品数量不一致
    int INFO_ERROR_NOT_SAVE = 70115; // 数据不全，操作失败
    int STRING_TO_DATE_FORMAT_ERROR = 70116; // 数据格式转换出错(字符传日期).

    int IMPORT_SN_NOT_FOUND = 70200; // 导入失败：sheet{0} 单元格{1} 商品[{2}] SN:[{3}]未找到
    int IMPORT_SN_BARCODE_IS_NULL = 70201; // 导入失败：sheet{0} 单元格{1} 条码不能为空
    int IMPORT_SN_IS_NULL = 70202; // 导入失败：sheet{0} 单元格{1} SN序列号不能为空
    int IMPORT_SN_TYPE_ERROR = 70203; // 导入失败：sheet{0} 单元格{1} 商品[{2}] 为后端验证SKU商品 不支持SN号导入操作
    int IMPORT_SN_EXECL_ERROR = 70204; // 导入失败：数据不能为空！


    int VMI_NOT_FOUND_SHOP = 70001; // 没有找到VMI转店或转仓店铺

    int EXCEL_LOCATION_NOT_FOUND = 1700;
    int EXCEL_QUANTITY_IS_NEGATIVE = 1702;
    int EXCEL_SKU_INVENTORY_ERROR = 1703;
    int EXCEL_SHEET_ERROR = 1704;
    int EXCEL_SHEET_SKU_ERROR = 1705;
    int EXCEL_DISTRICT_LOCATION_IS_LOCKED_OR_OCCUPAID = 1800;
    int WAREHOUSE_DISTRICT_NOT_FOUND = 1805;
    int PAMA_EXCEL_QUANTITY_IS_NOT_EQUAL = 1899;
    int PAMA_RTO_IS_NOT_EQUAL = 1900;
    int PAMA_RTO_SKU_IS_NOT_EQUAL = 1901; // puma 创单指令sku种类比指令多
    int PAMA_EXCEL_SKU_IS_NULL = 1902;
    int VMI_ADJUSTME_ERROR = 80001; // 反馈指令生成失败
    int VMI_ADJUSTMENT_PO_ERROR = 80002;// ESPRIT调整单PO号不存在 或者 调整单中存在指令中没有的SKU
    int VMI_ADJUSTMENT_QYT_ERROR = 80003;// ESPRIT调整单不允许存在负值
    int VMI_TRANSFER_STA_NULL = 80004;// 源入库单为空
    int VMI_INBOUND_PO_NO_IS_NULL = 80005;// 入库单PO为空
    int VMI_INBOUND_PO_TYPE_ERROR = 80006;// 单元格{0}PO类型{1}不存在。
    int VMI_INBOUND_PO_NUM_ERROR = 80007;// 单元格{0}PO号不能为空。
    int VMI_ADJUSTME_INSTRUCTION_NULL = 80008; // 找不到调整指令，无法确认差异。
    int VMI_ADJUST_NOT_FOUND = 80009;// 调整单未找到
    int VMI_ADJUST_HAS_FINISH = 80010;// 调整单[{0}]已经完成
    int VMI_ESPRIT_NOT_FOUND_SKU = 80011;// 此相关单据号没有找到要创建的SKU。
    int VMI_ESPRIT_SKU_CREATE_ERROR = 80012;// 商品[{0}]创建失败
    int VMI_NEED_NOT_CREATE_SKU = 80013;// 当前选择店铺无法创建商品
    int VMI_RETURN_TO_LOCATION_ERROR = 80014;// 为找到入库目标地址


    int PL_OUTPUT_COUNT_ERROR = 81001;// 配货单已导出，不能重复操作


    int PREDEFINED_OUT_CREATE_INV_ERROR = 82001;// 作业单：商品条码：[{0}]、商品编码：[{1}]、扩展属性：[{2}]
    // ，库存状态为{3}，没有足够库存[{4}]
    int PREDEFINED_OUT_BY_SN_IS_NOT_INFO = 82002;// 根据SN[{}]找到商品[SKU编码{0}],库存状态为{0} 不在出库单内
    int PREDEFINED_OUT_BY_SKU_NUB_ERROR = 82003;// 商品[SKU编码{0}],执行量不等于计划执行量
    int PREDEFINED_OUT_CREATE_OWNER_WH_ERROR = 82004;// 店铺[{0}] 和 仓库[{0}]不是同一公司
    int PREDEFINED_OUT_OUT_STATUS_ERROR = 82005;// 当前负向采购出库单前置单据[{0}] 状态错误
    int PREDEFINED_OUT_OUT_IS_NOT_SLIP_CODE = 82006;// 未找到前置单据号

    int STA_CRANCEL_ERROR = 82007;// 作业单[{0}],当前状态不允许取消。
    int STA_CRANCEL_ERROR1 = 82008;// 作业单[{0}],当前状态不允许此操作。
    int NOT_FOUND_ALIPAYID = 90001; // 流水号{0}在系统中不存在
    int EXISTS_ALIPAYID = 90002; // 流水号{0}在系统中已存在
    int SHOP_NOT_FOUND = 135;

    int STA_CANCELED_ERROR_VMI_WH = 83001;// 销售-作业单取消失败，外包仓无法取消
    int STA_CANCELED_ERROR_VMI_RO_WH = 83002;// 退货入库-作业单取消失败，外包仓无法取消



    int IDS_BASE_ERROR_CODE = 99990000;
    int IDS_BASE_ERROR_CODE_SYSTEM_ERROR = 99991002;
    int VMI_WH_RTN_OUTBOUND_MISS_MSG = 99992001;
    int IDS_STA_CANCELED_ERROR_VMI_WH = 99993001;// 销售-作业单取消失败，外包仓无法取消
    int OUTSOURCING_RTN_BATCH_TYPE_ERROR = 99993002;// 接收确认 批次 类型有误
    int OUTSOURCING_RTN_BATCH_ID_ERROR = 99993003;// 接收确认 批次 ID有误
    int OUTSOURCING_OUT_ORDER_IS_NULL = 99993004;// 未接收到相关出库单据 或者 XML格式有误 未解析出数据
    int OUTSOURCING_ORDER_CANCEL_IS_NULL = 99993005;// 未接收到相关确认取消单据 或者 XML格式有误 未解析出数据
    int OUTSOURCING_RETURN_IN_IS_NULL = 99993006;// 未接收到退换货入库单据 或者 XML格式有误 未解析出数据
    int OUTSOURCING_IN_BOUND_IS_NULL = 99993007;// 未接收到入库单据 或者 XML格式有误 未解析出数据
    int OUTSOURCING_BO_OUT_BOUND_IS_NULL = 99993008;// 未接收到其他出库单据 或者 XML格式有误 未解析出数据
    int OUTSOURCING_CF_SKU_IS_NULL = 99993009;// 未接收到确认接收SKU 或者 XML格式有误 未解析出数据
    int OUTSOURCING_CF_ORDER_IS_NULL = 99993010;// 未接收到确认接收单据 或者 XML格式有误 未解析出数据
    int OUTSOURCING_OP_CODE_ERROR = 99995010;// OP CODE 参数不正确{}

    int VMI_WH_XML_ERROR_MSG = 99994001;// 未获取到XML数据 或 XML 数据格式错误


    int MSGOUTBOUNDORDER_IS_NOT_FOUND = 5504;
    int STA_CANCEL_FAILURE_STA_HAS_OUT_BOUND = 5505;
    int STA_CANCEL_FAILURE_STA_HAS_CANCEL_CLOSE = 5506;
    int STA_CANCEL_FAILURE_STA_NOT_FOUND = 5507;
    int STA_CANCEL_FAILURE_TONKEN_INVALIDATION = 5508;
    int STA_CANCEL_FAILURE_STA_ORDER_CODE_IS_NULL = 5509;
    int STA_CANCEL_FAILURE_SYSTEM_ERROR = 5510;
    int STA_CANCEL_FAILURE_NO_FEEBACK_MESSAGE = 5511;
    int STA_CANCEL_FAILURE_FEEBACK_MESSAGE_IS_NULL = 5512;

    int WAREHOUSE_NOT_FOUND = 6610;
    int WAREHOUSE_OU_NOT_FOUND = 6611;
    int TRANSACTION_TYPE_NOT_FOUND = 6612;
    int OPERATION_CENTER_NOT_FOUND = 6613;

    int IDS_STA_NOT_PURVIEW = 99992002;// 无权限访问IDS接口

    int PURCHASE_SKU_TYPE = 9001; // Excel必须选择SKU编码/条码方式导入
    int PURCHASE_NO_DATA = 9002; // Excel导入数据不能为空
    int IMPORT_SKU_ISNOT_UNIQUE = 9003; // 导入的Sku编码/条码不能重复
    int IMPORT_SKU_ISNOT_MEET_REGULATION = 9004; // 商品[SKU编码/条码{0}],不存在
    int IMPORT_QTY_IS_OVER = 9005; // 商品[SKU编码/条码{0}],本次上架数量与输入上架数量不相等
    int IMPORT_STVLINE_QTY_IS_NOT_MATCH = 9006; // 导入商品数量与本次上架商品数量不相等



    int IO_EXCEPTION = 1000001;// IO 异常
    int FILE_NOT_FOUND = 1000002;// 文件未找到

    int STA_CANCELED = 80015; // 执行【{0}】失败：作业单已取消
    int STA_CANCELED_QTY = 80016; // 执行失败,作业单已取消,系统自动更改核对量
    int WAREHOUSELOCATION_IS_NULL = 83002;// 库位不存在

    int NIKE_WEBSERVERCE_INVENTORY_STATUS_NOT_FOUND = 19915;// 15
    int NIKE_WEBSERVERCE_SKU_NOT_FOUND = 19914; // 14
    int NIKE_WRAEHOUSE_NOT_FOUND = 20000;// 100
    int NIKE_SHOP_NOT_FOUND = 20000;// 100
    int NIKE_WEBSERVERCE_TRANSACTIONTYPE_NOT_FOUND = 20000;// 100
    int NIKE_WEBSERVERCE_BUSINESS_EXCEPTION = 20000;// 100
    // int NIKE_WEBSERVERCE_INVENTORYSTATUS_NOT_FOUND = 9950;
    int NIKE_WEBSERVERCE_OUTBOUND_INFO_IS_NULL = 19912; // 12
    int NIKE_WEBSERVERCE_INVENTORY_IS_LACK = 19911; // 11

    int HANDOVER_LIST_TRACKING_NO_IS_MULTIPLE = 30025;

    int HANDOVER_LIST_TRACKING_NO_IS_PRE = 30026;// 以下快递单号是预售订单，不允许交接:\r\n{0}

    int STA_STATUS_ERROR_PRE = 30027;// 预售订单取消 失败



    int INVENTORY_CHECK_IMPORT_BY_LOCATION_IS_NULL = 40056;
    int LOCATION_IS_INVALID = 40088;

    int STV_LINE_IN_BOUND_TIME_IS_NULL = 11111111;// 找不到入库时间
    int CARTON_NOT_FOUND = 70010;
    int CARTON_IS_FINISHED = 70011;
    int CARTON_SKU_ERROR = 70012;
    int CARTON_SKU_NOT_IN_PLAN = 70013;
    int CURRENT_STA_IS_NOT_OCCUPIED = 70014;
    int CURRENT_STA_IS_NOT_PACKING = 70015;// 当前作业单[{0}]不是装箱中状态，不能执行取消装箱
    int EXCEL_IMPORT_PRODUCT_NOT_FOUND = 70016;
    int EXCEL_IMPORT_PRODUCT_BOX_QUANTITY_ERROR = 70017;

    int SKU_NOT_FOUND_FOR_EXCEL_IMPORT = 83010;
    int WAREHOUSE_DISTRICT_NOT_FOUND_FOR_EXCEL_IMPORT = 83011;
    int WAREHOUSE_DISTRICT_NOT_PICKING_DISTRICT = 83012;
    int SKU_PROVIDE_WARNINGPRE_IS_ERROR = 83013;
    int BETWEENLIBARYMOVE_STA_CREATE_ERROR_NOT_SUFFICIENTINVENTORY = 84014;
    int CROSS_OUT_MAINHOUSE_IS_NULL = 84015;
    int CROSS_IN_MAINHOUSE_IS_NULL = 84016;
    int MSGRTNOUTBOUNDLINE_IS_NULL = 84017;
    int THREEPL_OUT_BOUND_FAILED_MSG_RETURN_SKU_MATCH = 84018;
    int MSG_SKU_QUANTITY_GREATE_OUT_BOUND_SKU_QUANTITY = 84019;
    int SKU_CREATE_ERROR = 84020;

    int NOT_PICKING_LIST = 85000;// 未找到配货清单

    int OMS_OUT_BOUND_ERROR = 86001;// 存在工作流，调用OMS出库接口失败，请联系后台。

    int OMS_SF_BOUND_ERROR = 86021;// 存在工作流，调用OMS出库接口失败，请联系后台。
    int OMS_SF_LOGIN_ERROR = 86022;// 存在工作流，调用OMS出库接口失败，请联系后台。

    int TRANS_CAN_NOT_SEND = 86002;// {0}快递无法送达!{1}
    int OMS_OUT_ERROR = 86003;// 调用OMS出库接口【{0}】失败，请联系后台。
    int PROVIDER_NO_TRANS_OR_ERROR = 86004;// {0}物流商站点无运单号!{1}

    int WORK_LINE_NO_IS_NULL = 86100;// 执行【{0}】失败：无法流水开票


    int RETURN_PACKAGE_TYPE_IS_NULL = 86200;// 退换货包裹登记类型不能为空
    int RETURN_PACKAGE_LPCODE_IS_NULL = 86201;// 退换货包裹登记物流商不能为空
    int RETURN_PACKAGE_TRACKINGNO_IS_NULL = 86202;// 退换货包裹登记物流单号不能为空
    int RETURN_PACKAGE_TRACKINGNO_REPEAT = 86203;// 操作失败：快递单号{0}所属包裹已在仓库[{1}]{2},不允许再次{3}！
    int MEARGE_PACKAGE_IS_NULL = 86204;// 合并订单无包裹信息，请重新核对！

    int SF_TIME_TYPE_ERROR = 900001;// 导入失败：省{0}市{1},时效类型不唯一！


    int ESP_PO_NOT_TYPE = 86300;// PO{0} 类型未找到
    int ESP_PO_NOT_TYPE_INV = 86350;// PO{0}发票号未找到,请在[ESP单据类型导入]中更新发票号。
    int ESP_PO_NOT_INV = 86400;// PO单{0} 对应的发票未找到，请先维护好发票号以及发票系数并且绑定。
    int ESP_PO_TYPE_ERROR = 86450;// PO{0} 是非进口类型，操作失败。
    int ESP_PO_IS_NOT_NULL = 86451;// PO{0} 不存在。
    int ESP_PO_INV_IS_NOT_NULL = 86452;// 发票号{0} 不存在。

    int WH_DISTRICT_TYPE_ERROR = 87000;// 仓库库区类型错误！联系后台。
    int INV_STATUS_LOC_ERROR = 87100;// 库存状态修改中存在暂存的库位{0}，并且还存在其他库位，库存状态修改单里面如果存在暂存区里面库位，那么只能针对这一个暂存区库位上的商品进行修改。

    int OUT_BOUND_GI_LOCATION = 88000;// 商品{0}有{1}件，是占用暂存区{2}库位上面，暂存区商品不接受此出库方式，占用失败。
    int ERROR_INSERT_INFO = 88001; // 操作失败：该用户关联配货批次号{0}正在配货中
    int ERROR_INSERT_INFO2 = 88002; // 批次号{0}不是配货中的批次。
    int ERROR_INSERT_INFO3 = 88003; // 批次号{0}不存在。
    int ERROR_INSERT_INFO4 = 88004; // 批次号{0}已记录。
    int ERROR_INSERT_INFO5 = 88005; // 批次号{0}未开始拣货，不能直接设置拣货完成。
    int ERROR_INSERT_INFO6 = 88006; // 批次号{0}未拣货完成，不能直接操作！ 请至[拣货批次开始]页面配置

    // "店铺所属公司不是仓库所属公司"
    int SHOP_COMPANY_NOT_WH_COMPANY = 91000;
    // "店铺所属公司不是仓库所属公司"
    int EMS_ORDER_ERROR = 91001;
    // 实际补货不等于建议补货数量
    int SKU_MOVE_SUGGEST_QTY_ERROR = 100000;

    int PICKING_LIST_STSTUS_ERROR = 199999;// 当前配货清单[{0}]状态不允许导出
    int PDA_IN_BOUND_ERROR = 200000;// 数据错误失败{0}
    int IN_BOUND_AFFIRM = 200001;// 非可确认单据！请导入收货数据，再做确认。
    int NOT_FOUND_CONFIRM_STV = 200002;// 未找到可核对入库单
    int STV_STRUTS_ERROR = 200003;// 作业明细单状态错误{}
    int SKU_IS_NOT_DIFFERENCE = 200004;// 商品{0}不在调整商品范围内
    int DIFFERENCE_NUMBER_ERROR = 200005;// 商品{0},{1}不在调整后收货数量大于剩余计划量
    int SKU_NOT_ESSENTIAL = 200006;// 商品{0}基本信息(长、宽、高、净重)没有维护
    int IN_BOUND_SHELVES = 200007;// 非可上架单据
    int IS_NOT_INBOUND_ORDER_A = 200008;// 非可收货单，存在未完成的上架单
    int IS_NOT_INBOUND_ORDER_B = 200009;// 非可收货单，存在未审核的收货单
    int IS_NOT_INBOUND_ORDER_C = 200010;// 非可收货单，存在已审核但未完成的收货单
    int SKU_NOT_PLOT = 200011;// 商品{0}，不在计划内
    int SKU_QTY_IS_NOT_NULL = 200012;// 商品{0}，数量不能为空！
    int SKU_QTY_IS_ERROR = 200013;// 商品{0}，{1}数量错误，不能小于0
    int IS_INBOUND_SHELVES_QTY_ERROR = 200014;// 商品{0} 超出计划量
    int PDA_INBOUND_SKU_SN_QTY_ERROR = 200015;// 商品{0} SN数量不符合计划量

    int NOT_INBOUND_ORDER = 200016;// 非入库单据{0}！数据错误！
    int STA_ALREADY_FINISHED = 200017;// 作业单{0} 已完成！无需执行！
    int PDA_STA_STATUS_ERROR = 200018;// 作业单{0} 状态异常！
    int PDA_NOT_FIND_STV = 200019;// 未找到对应已核对待审核状态{0}
    int PDA_NOT_FUND_DATA = 200020;// 未找到须要处理的PDA数据
    int CANCEL_STA_STATUS_ERROR = 200021;// 作业单[{0}] 状态是[{1}] 不允许取消！


    int CREATE_GIFTLINE_STALINE_IS_NULL = 200050;// 创建特殊处理数据[{0}]时缺少作业单明细！
    int CREATE_GIFTLINE_TYPE_IS_NULL = 200051;// 创建特殊处理数据[{0}]时缺特殊处理类型！

    int NIKE_CHECK_IMPORT_OWNER_CODE_ERROR = 200100;// 店铺编号{}错误
    int NIKE_CHECK_IMPORT_QTY_ERROR = 200101;// 商品数量错误
    int NIKE_VMI_INBOUND_READER = 200200;// 读取文件错误

    int NIKE_VMI_INBOUND_ORDERKEY_ERROR = 200301;// 第{0}行,ExternOrderKey 为空!
    int NIKE_VMI_INBOUND_LABELNO_ERROR = 200302;// 第{0}行,LabelNo 为空!
    int NIKE_VMI_INBOUND_QTY_ERROR = 200303;// 第{0}行,QtyShipped 为空!
    int NIKE_VMI_INBOUND_QTY_INFO_ERROR = 200304;// 第{0}行,QtyShipped[{1}] 非数据类型!
    int NIKE_VMI_INBOUND_UPC_NULL = 200305;// 第{0}行,UPC 为空!
    int NIKE_VMI_INBOUND_UPC_NOT_FIUND_SKU = 200306;// 第{0}行,未找到对应UPC[{1}]的商品信息!
    int NO_EMS_ACCOUNT = 200307;// EMS帐号信息没有维护
    int NO_STO_ACCOUNT = 200309;// STO帐号信息没有数据
    int SF_INTERFACE_ERROR = 200308;// 顺丰接口无法调用
    int LOGISTICS_INTERFACE_ERROR = 200317;// 物流服务接口无法调用
    int YTO_INTERFACE_ERROR = 200312;// 圆通接口无法调用
    int KERRY_INTERFACE_ERROR = 200313;// KERRY接口无法调用
    int RFD_INTERFACE_ERROR = 200314;// RFD接口无法调用
    int NO_JD_ACCOUNT = 200310;// JD帐号信息没有数据
    int NOT_ENOUGHT_TRANS_NO = 200311;// 没有足够运单号

    int EMS_INTERFACE_ERROR = 200311;// EMS 接口调用失败

    int NO_EXPRESS_ACCOUNT = 200315;// 物流快递商帐号信息没有数据
    int EXPRESS_INTERFACE_ERROR = 200316;// 物流快递商接口无法调用


    String CONNECT_TAOBAO_CLIENT_ERROR = "CONNECT_TAOBAO_CLIENT_ERROR";
    int IMPORT_DATA_BRAND_ERROR = 1200;// 导入的商品编码{0}对应的品牌不正确
    int IMPOERT_DATA_NOTUNIQUE = 1201;
    int IMPORT_DATA_NOTEXISTS = 1202;
    int IMPORT_CATAGORY_NOTEXISTS = 1203;
    int IMPORT_PACKAGESKU_ERROR = 1101;// 编码{0}对应的箱型不存在/与当前维护商品一致
    int IMPORT_SKUTYPENAME_ERROR = 1110;// 商品类型名称{0}不存在
    int TURNOVERBOX_EXISTS = 1111;// 该名称对应周转箱已存在，请检查！
    int TURNOVERBOX_STA_NOT_EXISTS = 1112;// 扫描的前置单据号找不到对应的单据和批次,或者单据非配货中状态
    int TURNOVERBOX_STA_NOT_PCIKING = 1113;// 扫描的前置单据号对应的单据还未加入配货批
    int TURNOVERBOX_STA_HAS_BIND = 1114;// 该单已做过绑定，请查证！
    int TURNOVERBOX_NOT_EXISTS = 1115;// 扫描的条码对应的周装箱不存在
    int TURNOVERBOX_BIND_OTHER_ORDER = 1116;// 该周转箱已绑定其他批次！
    int TURNOVERBOX_PL_NOT_EXISTS = 1117;// 扫描的编码找不到对应的拣货批,或者是非多件的批次
    int TURNOVERBOX_PL_END = 1118;// 该批次已经捡货完成，无法绑定
    int TURNOVERBOX_PL_NOT_EXISTS_END = 1119;// 此配货批不存在，或已经配货完成
    int TURNOVERBOX_ZONE_NOT_EXISTS = 1120;// 此配货批中不存在此仓储区域
    int TURNOVERBOX_OVER_NOT_EXISTS = 1121;// 此周转箱不存在或是已经拣货完成
    int TURNOVERBOX_PICKING_LIMIT = 1122;// 仓库中配货批次数量已经达到上限，请稍后再为该批次绑定周转箱
    int TURNOVERBOX_CODE_NOT_EXISTS = 1123;// 编码为{0}的周转箱不存在！
    int COLLECTION_CODE_NOT_EXISTS = 1124;// 集货区域编码不存在！
    int TURNOVERBOX_CODE_NOTFOUND = 1125;// 此编码不存在！
    int TURNOVERBOX_UNUSED = 1126;// 此周转箱未被使用！
    int TURNOVERBOX_PICKING_AUTO_EXIST = 1127;// 此批次已经推荐到自动化集货区域，无需再次推荐！
    int TURNOVERBOX_PICKING_NOT_AUTO_EXIST = 1128;// 此批次已经推荐到人工集货区域，集货区域编码{}！
    int TURNOVERBOX_COLLECTION_PICKING_LIMIT = 1129;// 集货区域已达到上限，无法推荐！
    int TURNOVERBOX_PICKING_BATCH_NOT_OVER = 1130;// 此批次没有拣货区域拣货完成！
    int PRO_NO_DATA = 1204;
    int UPDATE_PRO_INFO_ERROR = 1205;
    int ISRAILWAY_MUST_TRUE_FALSE = 1206;
    int ERROR_SLIPCODE = 1207;// 以下相关单据不能重复维护：{0}
    int NULL_SET_ERROR = 1208;// {0}
    int ERROR_TRANS = 1209;// 以下物流单不能重复维护:{0}
    int ERROR_OUT_PROCEDURE = 1210;// 出库过程失败
    int HAVE_NO_CREATE = 1211;// 存在不可创建的单据
    int CREATE_ERROE_HAND = 1212;// 创建交接清单过程出现错误
    int CREATE_FAILURE_HAND = 1213;// 未能成功创建交接清单
    int CREATE_ERROR_HANDOVER = 1214;// 创建交货清单过程出现错误
    int CREATE_FALIURE_HANDOVER = 1215;// 未能成功创建交货清单
    int PDA_LINE_BARCODE_SKU_NOT_EXISTS = 1216;// PDA上传明细中明细行{0}中条形码{1}对应的SKU不存在!
    int PDA_LINE_LOCATION_ERROR = 1217;// PDA上传明细中明细行{0}中库位{1}非本次盘点库位!
    int PDA_ORDER_NOTEXIST_OR_ERROR = 1218;// PDA上传单据对应的作业单不存在或者状态已变更!
    int PDA_FINISHED = 1219;// 执行成功！
    int FORBIDDEN_CHANGE = 1220;// 存在已交接包裹，不允许修改物流商
    int PDA_UPLOAD_SUCCESS = 1221;// 数据提交成功!
    int PDA_UPLOAD_FAILED = 1222;// 数据保存失败!
    int PDA_EXECUTE_FAILED = 1223;// 执行失败!
    int PDA_DETAIL_NOT_FOUND = 1224;// 执行失败，PDA单据明细不存在!
    int PDA_HAVE_ERROR_ORDER = 1225;// 执行失败，存在不可创建的单据!
    int PDA_HAND_OVER_SUCCESS = 1226;// 执行成功,生成物流交接单为:{0}
    int PDA_RETURN_STA_ERROR = 1227;// PDA上传的STA:{0}不存在或者已执行!
    int PDA_RETURN_EXE_BIGTHAN_PLAN = 1228;// PDA上传数据中商品{0}在{1}库位上的执行量大于计划量!
    int PDA_RETURN_HAVE_SKU_OUTPLAN = 1229;// PDA上传数据中商品{0}为计划外数据!
    int PDA_RETURN_LINE_QTY_ERROR = 1230;// PDA上传数据明细行{0}数量信息有误！
    int PDA_RETURN_LINE_SN_ERROR = 1231;// PDA上传数据明细行{0}中sn无效！
    int PDA_RETURN_SN_REPEAT = 1232;// PDA上传数据明细行{0}中sn号{1}存在重复!
    int PDA_RETURN_SN_ERROR = 1233;// PDA上传数据中明细行{0}SN号：{1}无效!
    int PDA_RETURN_OCCUPIED_SN_ERROR = 1234;// SN号占用失败
    int EXCEL_PICKINGLIST_CODE_ERROR = 1235;// {0}:对应作业单不存在
    int EXCEL_SLIPCODE_CODE_ERROR = 1236;// 相关单据号与作业单不一致;
    int EXCEL_PICKINGLIST_SN_REPEAT = 1237;// SN号单元格存在重复
    int EXCEL_PICKINGLIST_SN_ERROR = 1238;// 上传的SN号不争确或者跟计划量不一致
    int EXCEL_PICKINGLIST_NEEDSN_BUTNULL = 1239;// 有SN号商品但未上传SN号；
    int EXCEL_PICKINGLIST_NOT_NEEDSN = 1240;// 无SN号商品但上传了SN号；
    int PACKAGESKU_HAVE_EXISTS = 1241;// 该套装组合商品已存在；
    int PACKAGESKU_EXPIRETIMEBIG = 1242;// 过期时间不能小于创建时间;
    int PACKAGESKU_FAILED = 1243;// 创建套装组合商品失败
    int SECKILLSKU_FAILED = 1244;// 没有找到该条码对应的商品，请核对后重新输入！
    int SECKILLSKU_SNNOT = 1245;// SN号商品不能计入秒杀
    int SECKILLSKU_ERROR = 1246;// 创建秒杀商品失败
    int NOT_SUPPORT_PACKAGESKU = 1247;// 该仓库不支持套装组合商品
    int NOT_SUPPORT_SECKILLSKU = 1248;// 该仓库不支持秒杀商品
    int EI_SALES_INV_NOT_ENOUGH = 1249;// [{0}]，缺{1}件;
    int EI_SKU_NOTEXISTS = 1250;// 编码{0}对应的商品不存在！
    int EI_DETAIL_LINE_IS_NULL = 1251;// 传入接口的请求明细行为空！
    int EI_EXISTS_NULL_SKU = 1252;// 存在商品为空的明细行！
    int EI_SOURCE_WH_SHOP_NOREF = 1253;// 接口数据对应的仓库组织和店铺没有绑定!
    int EI_NOT_ENOUGH_SALES_INVENTORY = 1254;// 销售可用量不足:{0}
    int EI_SOURCE_TO_WH_SAME = 1255;// 仓库组织和附加仓库组织一致!
    int EI_INVSTATUS_NOT_EXISTS = 1256;// 明细行中库存状态ID{0}对应的库存状态不存在!
    int EI_SUCCESS = 1257;// 接口创单成功！
    int EI_NOT_SUPPORT_TRANSIT_CROSS = 1258;// 作业类型库间移动出库不被支持!
    int EI_NOT_SUPPORT_VMI_FLITTING_OUT = 1259;// 作业类型同公司调拨出库不被支持!
    int EI_NOT_SUPPORT_DIFF_COMPANY_TRANSFER = 1260;// 作业类型不同公司调拨出库不被支持!
    int EI_NOT_SUPPORT_VMI_TRANSFER_OUT = 1261;// 作业类型VMI转店出库不被支持!
    int EI_TARGET_WH_SHOP_NOREF = 1262;// 附加仓库和附加店铺没有绑定!
    int EI_TARGET_ORG_SHOP_ERROR = 1263;// 店铺和附加店铺一致!
    int EI_ORWH_TARGETWH_NOTSAME_COMPANY = 1264;// 仓库组织和附加仓库组织不在同一公司下
    int EI_ORWH_TARGETWH_SAME_COMPANY = 1265;// 仓库和附加仓库在同一公司下!
    int EI_MAIN_WH_IS_NULL = 1266;// 接口数据中仓库组织为空!
    int EI_START_OWNER_IS_NULL = 1267;// 接口数据中店铺为空!
    int EI_DATA_INVSTATUASID_IS_NULL = 1268;// 接口数据中头信息库存状态为空
    int EI_DETAIL_EXISTS_NO_INVSTATUS_LINE = 1269;// 接口中存在没有库存状态的明细行!
    int EI_DETAIL_INVSTATUS_NOT_EXISTS = 1270;// 接口数据明细行中库存状态ID{0}对应的库存状态不存在!
    int NOT_SUPPORT_SAMPLE_INBOUND_STATYPE = 1271;// 作业类型样品领用入库不被支持!
    int NOT_SUPPORT_SKU_EXCHANGE_INBOUND_STATYPE = 1272;// 作业类型商品置换入库不被支持!
    int NOT_SUPPORT_REAPAIR_INBOUND_STATYPE = 1273;// 作业类型送修入库不被支持!
    int NOT_SUPPORT_SERIAL_NUMBER_INBOUND_STATYPE = 1274;// 作业类型串号拆分入库不被支持!
    int EI_MAIN_WH_NOT_EXISTS = 1275;// 接口数据对应的仓库组织不存在!
    int EI_OWNER_NOT_EXISTS = 1276;// 接口数据对应的店铺不存在!
    int EI_INVENTORY_STATUS_NOT_FOUND = 1277;// 接口数据中头信息对应的库存状态不存在!
    int EI_ADD_OWNER_IS_NULL = 1278;// 接口数据中附加店铺为空!
    int EI_ADD_OWNER_NOT_EXISTS = 1279;// 接口数据对应的附加店铺不存在!
    int EI_ADD_WH_IS_NULL = 1280;// 接口数据中附加仓库组织ID为空!
    int EI_ADD_WH_NOT_EXISTS = 1281;// 接口数据中附加仓库组织ID对应的仓库不存在!
    int EI_ADD_STATUSID_IS_NULL = 1282;// 接口数据中附加库存状态ID为空!
    int EI_ADD_STATUS_NOT_FOUND = 1283;// 接口数据中附加库存状态ID对应的库存状态不存在!
    int EI_SLIP_CODE_IS_NULL = 1284;// 接口数据中前置单据号为空
    int EI_EXISTS_NULL_QTY = 1285;// 接口数据中存在数量为空/0/负数的明细行!
    int EI_ALLREADY_SUCCESS = 1286;// 之前单据已经创建过!
    int EI_NOT_IN_OR_OUT = 1287;// 接口明细中不能只有出或者入!
    int EI_DIRECTION_ERROR = 1288;// 接口中事务方向{0}为非法的，只能在1,2中选择!
    int NOT_SUPPORT_SERIAL_NUMBER_GROUP_INBOUND_STATYPE = 1289;// 作业类型串号组合入库不被支持!
    int EI_EXISTS_NULL_DIRECTION = 1290;// 接口明细中存在空的作业方向!
    int EI_CANNOT_SKU_GT_ONE = 1291;// 出库商品只能为一种!
    int EI_IN_MUST_OUT_INTEGER = 1292;// 入库商品必须是出库商品数量的整数倍!
    int EI_NOT_EXISTS_OUTBOUND = 1293;// 不存在对应的样品出库作业单!
    int RMI_OMS_FAILURE = 1294;// 调用OMS接口失败!{0}
    int EI_NOT_EXISTS_INV_CHECK = 1295;// 不存在对应的盘点批次!
    int EI_TWO_SHOP_MUST_VMI_SAME = 1296;// 转店，调拨要求转入转出店铺必须同时为品牌店铺或非品牌店铺!
    int STA_NOT_PART_INBOUND = 1297;// 当前作业单要求一次性入库，数量严格一致!
    int SKU_NOT_FIND_BY_BARCODE = 1298;// 当前条码对应的商品不存在!
    int SKU_NOT_IN_PLAN = 1299;// 当前商品不在计划范围之内!
    int SKU_QTY_BIGTHAN_PLAN = 1300;// 错误操作，执行量不能大于计划量。
    int GIFT_LINE_IS_NULL = 1301;// Coach 店铺商品[{0}] 缺少保修卡信息 或者 保修卡信息有误。错误点{1}
    int PDA_SHELF_MANAGEMENT_ERROR = 1302;// {0} 为保质期商品 PDA不知道保质期商品盘点。
    int ORDER_LINE_TYPE_ERROR = 1327;// 接口数据中明细行type不能为5
    int SF_WAREHOUSE_INTERFACE_ERROR = 1600;// 权限验证异常！错误编码:{0},错误描述：{1}
    int SF_INBOUND_ORDER_LINE_IS_NULL = 1601;// 入库通知单{0}明细为空!
    int SF_OUTBOUND_ORDER_LINE_IS_NULL = 1602;// 出库通知单{0}明细为空!
    int SF_ORDER_NOT_FOUND = 1603;// 未找到对应入库单据!
    int SF_INTERFACE_INVSTATUS_ERROR = 1604;// 接口库存状态非指定数据!
    int DOUBLE_INVSTATUS_ERROR = 1637;// 作业单存在多种库存状态
    int INVSTATUS_ERROR = 1640;// 残次退仓必填残次原因
    int IMPERFECT_CODE_ERROR = 1635;// 残次编码和条形码对应不上{}
    int IMPERFECT_CARTON_CODE_ERROR = 1636;// 残次编码已经操作过了
    int SF_INVSTATUS_WMS_ERROR = 1605;// 库存状态映射过程错误!
    int SF_SN_ERROR = 1606;// 接口SN号与数量不一致!
    int SF_ADJUST_LINE_ERROR = 1607;// 库存调整明细行为空!
    int SF_ADJUST_LINE_DIRECTION_ERROR = 1608;// 库存调整方向非指定数据(From/To)!
    int SF_INTERFACE_SKU_ERROR = 1609;// 接口数据商品编码错误!
    int SF_OUT_DETAIL_IS_NULL = 1610;// 出库状态单据必须有明细(900)!
    int SF_OUT_CONTAINER_IS_NULL = 1611;// 出库箱明细必须有数据(900)!
    int SF_CARRIER_ERROR = 1612;// 接口物流商不能为空!
    int SF_INTERFACE_XML_IS_NULL = 1613;// 接口报文为空!
    int SF_CHECk_NOT_PASS = 1614;// 报文格式不正确!
    int SF_RTN_SN_NOT_REQUIRE = 1615;// 反馈SN与要求不符!
    int SF_RTN_SN_NOT_EXISTS = 1616;// {0}在系统中不存在或者不可用。
    int SF_RTN_OUTBOUND_HAVE_EXISTS = 1617;// 单据已经推送，不能重复推送!
    int NO_SKU_WITH_THIS_SUPPLIERCODE = 1618;// 当前供应商编码不存在或没有对应的SKU!
    int CURRENT_WAREHOUSE_HAVE_THIS_SKU = 1619;// 当前仓库当前商品已维护!
    int EI_SKUCODE_NOTEXISTS = 1620;// {0}{1}对应的商品不存在！
    int EI_SKU_WARNINGDATE = 1621;// {0}{1}商品预警天数必填！
    int EI_SKU_TIMETYPE_ERROR = 1622;// {0}{1}商品时间维护类型必填！
    int EI_SKU_TIMETYPE_TYPE_ERROR = 1623;// {0}{1}商品时间维护类型不正确！
    int SF_INBOUND_ORDER_FLAG_IS_NULL = 1624;// 入库通知单{0}对应货主为空！
    int SF_OUTBOUND_ORDER_FLAG_IS_NULL = 1625;// 出库通知单{0}对应货主为空！
    int DISTRIBUTION_RULE_NOT_FOUND = 1626;// 配货规则维护{0}不存在！
    int TRANS_ROLE_ACCORD_IS_NULL = 1627;// 渠道快递规则｛0｝为空！
    int TRANS_ROLE_CODE_IS_NULL = 1628;// 渠道快递规则编码｛0｝为空！
    int TRANS_CHANGE_TIME_IS_NULL = 1629;// 渠道快递规则执行时间｛0｝为空！
    int TRANS_PRIORITY_IS_NULL = 1630;// 渠道快递规则优先级｛0｝为空！
    int TRANS_STATUS_IS_NULL = 1631;// 渠道快递规则启用状态｛0｝为空！
    int EXCEL_IMPORT_STATUS_ERROR = 1632;// Excel数据是否启用｛0｝不正确,请填写‘是’或者‘否’！
    int PRIORITY_ERROR = 1633;// 优先级的范围为1到9999的整数！
    int DELETE_OCP_INV_ERROR = 1634;// 出库删除占用库存错误！
    int CHECK_INV_ERROR = 1638; // 库存跟库存日志不一致
    int SHOP_OWNER_NO_DATA = 83014;// sheet{0}单元格{1}店铺没有维护
    int SHOP_OWNER_ERROR = 83015;// sheet{0}单元格{1}所属店铺[{2}]非当前仓库关联店铺


    int EBS_QUANTITY_ERROR = 83016; // 执行量和数量不一致
    int EBS_IMPORT = 83017; // 修改占用库存成功
    int STA_NOT_USING = 83018; // 单据被锁定或不可使用
    int ERROR_STA_NOT_USING = 83019; // 单据解锁失败
    int ERROR_STA_NOT_CODE = 83020; // SKU编码或编号不对
    int ERROR_STA_NOT_STATUS = 83021; // 当前状态不对
    int ERROR_NOT_STATUS = 83022; // 不支持该操作
    int ERROR_STALINE_ISCHECK = 88801; // 未找到扫描商品匹配的作业单，请确认重新扫描！

    int ERROR_SPECIAL_TEMPLATE_TYPE_ERROR = 83023; // 特殊处理 类型错误！
    int ERROR_TOTAL_ACTUAL = 83024; // TotalActual小于0
    int ERROR_PRO_CITY = 8888801; // 过仓销售与退换货单省市必填
    int ERROR_TRANS_PORTATOR = 83025; // 物流商不支持COD
    int ERROR_JD_RETURN = 83026; // JD或JDCOD不支持退换货
    int ERROR_COD_AMOUNT = 83027; // TotalActual小于0
    int EBS_STATUS_ERROR = 83028; // 库存状态不一致
    int ERROR_OWNER_NULL = 83029; // 渠道或明细OWNER为空
    int ERROR_LPCODE_ISNULL = 83030; // 物流商或快递单号为空
    int ERROR_DELIINFO_ISNULL = 83031; // 物流信息为空
    int ERROR_DELIINFO_LOCODE = 83032; // 不支持SF、JD
    int ERROR_DELIINFO_LOCODE2 = 83033;// 只支持ZTO、STO、EMS、 YTO、RFD
    int PL_SKU_SIZE_NOT_FOUND = 83034;// 配货清单找不到对应的商品大小件分类
    int SHOP_STORE_INFO_NOT_FOUND = 83035;// O2O信息表没有维护配置
    int AGV_OUTBOUND = 999999;// agv出库同步错误


    int ERROR_CARTON_LINE_ISNULL = 900000;// 无装箱明细，无法执行出库

    int VMI_ESPRIT_PICK_NO_ISNULL = 900500;// 相关单据号pick_no为空无法创入库单
    int VMI_ESPRIT_OUTBOUND_TYPE_ERROR = 900501;// Esprit品牌只支持转店退仓
    int VMI_SKU_CODE_ERROR = 900502;// SKU编码异常
    int VMI_GENERATE_RESPONSE_DATA_ERROR = 900503;// 生成反馈数据异常
    int VMI_ESPRIT_INBOUND_TO_PACS_ERROR = 900504;// Esprit入库反馈调PACS异常
    int VMI_ESPRIT_OUTBOUND_TO_PACS_ERROR = 900505;// Esprit出库反馈调PACS异常
    int VMI_ESPRIT_PURCHASE_DATA_ERROR = 900506;// Esprit收货数据异常
    int VMI_ESPRIT_BATCH_NO_ISNULL = 900507;// 批次号batch_no为空无法创入库单
    int VMI_ESPRIT_TO_PACS_ERROR = 900508;// Esprit反馈调PACS异常
    int PICKING_LIST_PACKAGE_NOT_FOUND = 900509;// 执行失败：可用批次包裹未找到或状态异常,请联系IT人员处理
    int PICKING_LIST_STATUS_ERROR = 900510;// 执行失败：当前配货批次状态异常,请联系IT人员处理
    int CANCEL_CARD_NOT_STATBUCKS_ERROR = 900511;// 非星巴克卡券，无法执行卡作废
    int CANCEL_CARD_SN_INFO_ERROR = 900512;// 卡券作废失败，SN号信息异常，请联系IT人员处理
    int PURCHASE_SN_INFO_ERROR = 900513;// SN号{0}信息异常，无法执行入库
    int STARBUCKS_INVOKE_HUB_UNLOCK_ERROR = 900514;// 卡券解冻，调HUB接口异常，错误编码为：{},反馈信息为：{}
    int STARBUCKS_INVOKE_HUB_UNLOCK_FAIL = 900515;// 卡券解冻，调HUB接口失败，错误编码为：{},反馈信息为：{}
    int STARBUCKS_INVOKE_HUB_CANCEL_ERROR = 900516;// 卡券作废，调HUB接口异常，错误编码为：{},反馈信息为：{}
    int STARBUCKS_INVOKE_HUB_CANCEL_FAIL = 900517;// 卡券作废，调HUB接口失败，错误编码为：{},反馈信息为：{}
    int RETURN_ORDER_CODE_IS_NULL = 900518;// 退仓指令单号不能为空
    int RETURN_ORDER_STA_IS_CREATED = 900519; // 退仓指令单号【{0}】已创作业单，无法再次创建
    int RETURN_ORDER_DATA_ERROR_CONTACT_IT = 900520; // 退仓指令单号【{0}】已创建或数据异常，请检查指令数据或联系IT处理
    int RETURN_ORDER_LINE_NOT_EQUALS_IMPORT_LINE = 900521;// 导入明细行与指令明细行数据不匹配，请检查如下商品:{0}
    int WAREHOUSE_COVERAGE_AREA_PRIORITY_ERROR = 900522;// 仓库覆盖区域优先级必须大于等1
    int QUERY_INVENTORY_LOG_MUST_IN_ONE_MONTH = 900523;// 库存日志查询只允许查询1个月内数据
    int RETURN_ORDER_STA_IS_BINDING = 900524;// 该退仓指令已存在【{0}】
    int RETURN_ORDER_STA_BINDING_IS_EXECUTE = 900525;// 该退仓指令已存在【{0}】
    int RETURN_ORDER_STA_BINDING_ERROR = 900526;// 该退仓作业单【{0}】需要绑定退仓指令
    int RETURN_ORDER_STA_BINDING_SUCCESS = 900527;// 该退仓作业单【{0}】已经执行完成,不能操作！

    int CUSTOMER_NOT_FOUND = 1000000001;// 客户未找到
    int SKU_NOT_CURRENT_CUSTOMER = 1000000002;// 商品【】在当前客户【】中未找到
    int CHANNEL_NOT_FOUNT = 1000000003; // 渠道未找到【】
    int CHANNEL_NOT_IN_CUSTOMER = 1000000004; // 渠道【】不属于当前客户【】
    int OU_WHAREHOUSE_NOT_FOUNT = 1000000005; // 仓库【{0}】未找到
    int WAREHOUSE_INFO_NOT_SETTING = 1000000006; // 仓库【{0}】基础信息未维护
    int WAREHOUSE_NOT_IN_CUSTOMER = 1000000007; // 仓库【{0}】不属于当前客户【{1}】
    int CHANNEL_WAREHOUSE_NO_REF = 1000000008; // 渠道【{1}】无法于仓库【{0}】作业
    int INV_STATUS_NOT_FOUND = 1000000009; // 库存状态【{0}】未找到
    int CUSTOMER_NOT_ERROR = 1000000010;// 商品客户ID不匹配
    int ERROR_NOT_SNSKU_OR_NOT_STORESKU = 1000000011;// 作业单号【{0}】包含SN商品或保质期商品
    int ERROR_NOT_STALINE_SKU = 1000000012;// 作业单号【{0}】Sku条码：【{1}】不在当前采购计划
    int ERROR_WRONG_LOC_CODE = 1000000013;// 作业单号【{0}】库位：【{1}】可能被锁定、禁用或未找到
    int ERROR_STA_IMPORT_ERROR = 1000000014;// 作业单号【{0}】导入失败
    int ERROR_STA_SKU_QTY_NOT_SAME = 1000000015;// 作业单号【{0}】实际上架商品及数量与计划执行量不相等
    int INVENTORY_LOCK_LINE_EMPTY = 1000010001;// 创建库存锁定作业单错误：无明细行
    int TRANSACTION_TYPE_INVENTORY_LOCK_NOT_FOUND = 1000010002;// 无作业类型：库存锁定
    int ERROR_STA_NOT_LOCK = 1000010003; // 该作业单未锁定库存，无法执行解锁
    int EXCEL_DATA_PROVINCE_CITY_NOT_NONE = 1000010004;// Excel导入数据省，市及优先级不能为空
    int EXCEL_DATA_PROVINCE_CITY_NOT_NONE_TIME = 90000002;// Excel导入数据省，市及时效不能为空

    int LPCODE_ERROR_WEIGHT_PERCENTAG = 1000010010; // 出库 百分比大于配置报错
    int LPCODE_ERROR_MAX_WEIGHT = 1000010011; // 出库 最大重量大于配置报错
    int LPCODE_ERROR_MIN_WEIGHT = 1000010099; // 出库 最小重量大于配置报错
    int EXCEL_DATA_TIME_ERROR = 1000010005;// Excel导入时间数据异常，示例:10:30
    int CHILD_STA_NOT_FOUND = 1000000100;// 执行【{0}】失败：合并订单信息异常（无子订单）

    int SKU_TAG_NOT_FOUND = 1000000101;// 商品标签信息未找到
    int SKU_REF_IMPORT_FAIL = 1000000102;// 导入失败：共【{0}】件商品无法导入，商品编码分别为【{1}】
    int CHANNEL_FORBID_POST_PACKING_PAGE = 1000000103;// 渠道【{0}】不允许维护后置打印装箱清单
    int ON_SALES_STA_ERROR = 1000000016; // 退换货单据AddWhOuCode为空 slip_code1获取销售订单失败
    int ON_SALES_STA = 1000000017; // 退换货单据AddWhOuCode为空 slip_code1获取到多个销售订单
    int LOG_QUEUE_ERROR = 1000000018; // 增量数据同步失败
    int ERROR_STA_SKU_LOCATON = 1000000020; // 导入的信息不完整，作业单{0}

    int VMI_OP_ERROR = 2000000001; // VMI操作权限错误 【{0}】品牌不支持{1}操作
    int ERROR_STA_ISMERGE = 2000000002; // 批次包含合并作业单，配货失败！

    int SKU_SN_STATUS_ERROR = 2000000003;// 【{0}】 卡状态异常，卡状态为：{1} ！请更换卡片进行核对！

    int SKU_SN_STARTNUM_ERROR = 2100000001; // 录入【{0}】卡号不正确
    int SKU_MAPPING_SN_ISNOT_UNIQUEO = 2100000002;// 录入【{0}】卡号不能有重复
    int SKU_SN_STARTNUM_AND_STOPNUM = 2100000003;// 录入的起始卡号【{0}】不能大于终止卡号【{1}】

    int SKU_MAPPING_SN_BEN = 2100000009;// 录入本【{0}】卡号 起始卡号要等于终止卡号


    int PICK_ZONE_AND_SORT_REQUIRED = 1500000002; // 拣货区域和拣货顺序必须同时不为空或者同时为空
    int PICK_ZONE_LOCATION_DISTRICT_IS_NULL = 1500000003; // 导入的库区或者库位不存在
    int PICK_ZONE_LOCATION_DISTRICT_IS_EXIST = 1500000004; // 导入的库区或者库位已经存在拣货区域
    int LOCATION_OR_DISTRICT_IS_NULL = 1500000005; // 库位或库区缺失
    int PICK_ZONE_IS_NULL = 1500000006; // 拣货区域不存在
    int STA_CANNOT_CANCEL = 2100000004;// 作业单不能被取消

    int OCP_ORDER_BATCH_ERROR = 220000001;// 占用批占用库存失败

    int HW_WAREHOUSE_NOT_EXISTS = 2100;// 仓库{0}不存在
    int HW_DS_ERROE_WHEN_EXISTS_WAREHOUSE = 2101;// 存在仓库不允许分仓逻辑
    int HW_DSLOCKED_ERROR_WHEN_NOT_DS = 2102;// 非分仓不支持分仓锁定
    int HW_COD_MUST_HAVE_CODAMT = 2103;// COD订单必须有COD金额
    int HW_DS_ERROE_WHEN_COD = 2104;// COD订单不支持分仓逻辑
    int HW_NO_ORDER_LINE_EXISTS = 2105;// 不存在明细行
    int HW_LINE_WARHOUSE_ERROR = 2106;// 头指定仓，明细行必须为空或者跟头上仓库一致
    int HW_LINE_WARHOUSE_NOT_FOUND = 2107;// 明细行对应的仓库编码{0}系统找不到
    int HW_ORDER_CUSTOMER_ERROR = 2108;// 整单客户不一致
    int HW_LINE_NO_OWNER_SET = 2109;// 明细行{0}未指定店铺
    int HW_LINE_OWNER_NOT_FOUND = 2110;// 明细行{0}对应的店铺编码{0}WMS找不到对应渠道
    int HW_LINE_NO_SKU_SET = 2111;// 明细行{0}未指定商品
    int HW_LINE_SKU_NOT_FOUND = 2112;// 明细行{0}对应的商品{1}未找到
    int HW_DS_ERROE_WHEN_RETURN = 2113;// 换出业务不支持分仓
    int HW_DS_ERROE_CANCEL_TIME = 211311;// 请联系上位系统此单已经取消
    int HW_ORDER_VS_ERROR = 2114;// 订单头增值服务类型{0}非系统定义类型
    int HW_ORDER_LINE_VS_ERROR = 2115;// 订单行增值服务类型{0}非系统定义类型
    int HW_ORDER_EXISTS = 2116;// 订单已接收，不能重复发送。
    int HW_LINE_NO_REPETITION = 2118;// 订单明细重复
    int HW_HAVE_PAYMENT_CANNOT_DS = 2117;// 有支付信息业务不允许分仓！
    int FILL_IN_INVOICE_NO_ORDER = 1102;
    int FILL_IN_INVOICE_ORDER_MUST_SAME_LPCODE = 1103;
    int FILL_IN_INVOICE_ORDER_MUST_SAME_CHANNEL = 1104;
    int FILL_IN_INVOICE_INTERFACE_ERROR = 1105;
    int FILL_IN_INVOICE_SYSTEM_ERROR = 1106;
    int ERROR_NOT_SNSKU = 2000000000;// 作业单号【{0}】不包含保质期商品
    int AXSH_BOX_EXISTS = 1303;
    int AXSH_SKU_NOT_EXISTS = 1304;
    int AXSH_QUANTITY_HAS_PROBLEM = 1305;
    int AXSH_SLIPCODESKU_EXISTS = 1306;
    int OUT_SN_ERROR = 1307;
    int RECOMMAND_LOCATION_BYSKU_ERROR = 1308;// 存在商品{0}没有推荐到合适库位/弹出口,请检查基础配置！
    int WAREHOUSE_CHECK_AREA_NOT_EXISTS = 1309;// 仓库默认复核区编码不存在！
    int PICKINGLIST_CODE_ERROR = 1310;// 扫描的单号不正确或者目前流程节点不对！
    int EXISTS_PB_NOT_BIND = 1311;// 该批次没有完成对应的周转箱绑定操作！
    int NOW_WEIGHT_TRANS_ERROR = 1312;// 当前称重单号状态不对！
    int STA_NOT_EXISTS_OR_ERROR = 1313;// 关键作业单不存在或者状态不对
    int AD_CONFIRM_ERROR = 1314;// 订单确认失败：{0}！
    int AD_PARTLY_OUTBOUND = 1315;// 部分出库异常，自行检查后不能解决可联系IT！
    int AD_PARTLY_PROHIBIT = 1316;// 当前单据店铺不允许此类操作!
    int AD_RELEASY_INVENOTRY_ERROR = 1317;// 存在行取消库存释放错误！
    int AD_NOT_AD_CUS = 1318;// 当前仓库不支持此操作!
    int AD_REPORT_MISSING_ERROR = 1319;// 报缺异常!
    int AD_EXISTS_LINE_CANCEL = 1320;// 存在订单行取消，需要再次核对确认!
    int AD_NO_PRIVILEGT_TODO_THIS = 1321;// 你不拥有操作部分发货的权限!
    // business_exception_13006 = 导入作业单编码的Excel必须有数据
    int STA_NO_DATA = 13006;
    int TRACKING_NO_IS_ERROR = 1322;// 当前单号{0}不存在或者状态不正确!
    int RPC_ORDER_ERROR = 1323;//
    int TIME_INPUT_ERROR = 1324; // 失效日期必须大于生产日期
    int VALI_DATE_NOT_EXISTS = 1325;// 有效期天数不存在！

    int SHIPPING_POINT_ALREADY_RELATION = 1326; // 集货口数据已经被关联,不能删除,请重新选择!

    int STA_IS_CANCEL = 15000;// 该单据已经被取消，无法出库

    // Cj
    int RETRY_FAILd = 1000020002;// 重试三次请求返回失败结果
    int MUST_PARAM_LOST = 1000020003;// 请求的必要参数丢失

    int OCCUPATION_ERROR_OCCUR = 1333;

    // wms数据导出semicolon;
    int CONTAINS_ZH_SEMICOLON = 77001;// 输入的平台单据号包含中文分号,请使用英文分号！
    int STA_NOT_EXIST = 77002;// 单号【{0}】对应作业单不存在！
    int CHANNEL_OR_SLIPCODES_NOT_EXIST = 77003;// 店铺或者平台单据号至少有一个不能为空！
    int SKU_NOT_EXIST = 77004;// staLine【{0}】对应商品【{1}】不存在！

    // RocketMQ
    int MESSAGE_CONGIF_MISSING = 111001;// RocketMQ消息缺失相关配置【{0}】
    int MESSAGE_SEND_ERROR = 111002;// RocketMQ消息发送失败【{0}】

    // 星巴克MSR卡相关
    int LOST_STACODE_OR_SN = 87001;// 星巴克MSR卡入库缺失staCode或者sn数据！
    int EXIST_SN_HAS_STA = 87002;// 该入库sn【{0}】已经存在,且已绑定作业单id【{1}】，和需要绑定的作业单id【{2}】不符合!
    int SN_NOT_MATCH_STA = 87003;// 该出库sn【{0}】和绑定作业单据不一致！
    int MSR_SKU_NOT_EXIST = 87004;// 星巴克MSR卡入库商品【{0}】不存在！
    int SKU_CHECK_MODE_NOT_EXIST = 87005;// 星巴克MSR卡入库商品【{0}】snCheckMode不存在！
    int SN_REGULAR_CHECK_NOT_PASS = 87006;// 星巴克MSR卡入库商品【{0}】正则校验【{1}】未通过！

    // 退大仓
    int RTO_INFO_IMPORT_ERROR = 89001;// 退仓信息维护失败：{}

    int NIKE_RELATION_IMPORT_SAME = 528535;// 第{0}行和第{1}行数据重复
    int NIKE_RELATION_NO_DATA = 528536;// 导入Excel必须有数据
    int NIKE_RELATION_NO_SYSID = 528537;// 第{0}行系统箱号不存在
    int NIKE_RELATION_DATA_SAME = 528538;// 第{0}行已存在数据
}
