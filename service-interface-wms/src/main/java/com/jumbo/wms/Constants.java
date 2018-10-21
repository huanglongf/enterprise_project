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
package com.jumbo.wms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.automaticEquipment.ZoonOcpSort;


public class Constants implements Serializable {
    private static final long serialVersionUID = 1892576505183128105L;

    /**
     * 装箱清单加密/解密密钥
     */
    public static final String CODE_KEY = "上海宝尊电子商务有限公司";
    // 打印占位符（解决内容打印不全）
    public static final String PRINT_PLACEHOLDER = "\t\n\t";
    public static final long TASK_LOCK_TIMEOUT = 1000 * 60 * 30L;
    public static final String TASK_LOCK_VALUE = "";
    // 邮件发件人地址
    public static final String RECEIVER_ADDRESS = "RECEIVER_ADDRESS";
    // 邮件抄送人地址
    public static final String CARBON_COPY = "CARBONCOPY_ADDRESS";

    public static final String HTML_LINE_BREAK = "\r\n";

    public static final String FILE_EXTENSION_XML = ".xml";
    public static final String FILE_EXTENSION_TXT = ".txt";
    public static final String FILE_EXTENSION_OK = ".ok";

    public static final Integer OCP_AREA_STATUS_ERROR = 0; // 失败
    public static final Integer OCP_AREA_STATUS_INIT = 1; // 初始化
    public static final Integer OCP_AREA_STATUS_PICK = 2; // 计算中
    public static final Integer OCP_AREA_STATUS_ROW_FINISH = 3; // 区域计算完成
    public static final Integer OCP_AREA_STATUS_FINISH = 5;



    public static final String LPCODE_EMS = "EMS";
    public static final String LPCODE_ZTO = "ZTO";
    public static final String LPCODE_SF = "SF";
    public static final Long SF_QUEUE_TYP_COUNT = 5L;
    public static final String SYSTEM_CODE = "WMS";
    public static final String STATUS_TRUE = "1";
    public static final String STATUS_FALSE = "0";
    public static final String RS_TRUE = "true";
    public static final String RS_FALSE = "false";
    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final String EXCEL_CSV = ".csv";
    public static final String EXCEL_XLS = ".xls";
    public static final String ZIP = ".zip";
    public static final String EXCEL_XLSX = ".xlsx";
    public static final String STA_SKUS_SLIPT_STR = ":";
    public static final String STA_INV_SKUS_SLIPT_STR = "_";
    public static final String ISO_8859_1 = "ISO_8859_1";
    public static final Integer LOCATION_SIZE = 10;
    public static final String IT_VENDER = "i.t";
    public static final int WEBSERVICE_RETURN_NUMBER = 10;
    public static final Long LEVIS_SKU_TEMPLATE_ID = 501L;
    // 流水号加1后返回，流水号长度为5
    public static final String STR_FORMAT = "00000";
    /**
     * 标杆出库反馈明细类型
     */
    // 耗材
    public static final String BIAOGAN_OUT_RTN_LINE_ADDITIONAL = "ADD";
    // 销售
    public static final String BIAOGAN_OUT_RTN_LINE_SALES = "ADD";

    public static final String VIM_WH_SOURCE_BIAOGAN = "BiaoGan";
    public static final String VIM_WH_SOURCE_HD = "HD";
    public static final String VIM_WH_SOURCE_OMS = "OMS";
    public static final String VIM_WH_SOURCE_SF = "SF";
    public static final String VIM_WH_SOURCE_SF_TW = "SFTW";
    public static final String VIM_WH_SOURCE_BIAOGAN_JIANCAI = "JIANCAI";// 标杆建材仓
    public static final String VIM_WH_SOURCE_IDS = "IDS";

    public static final String THREE_PL_USER_CONFIG = "threePLConfig"; // 外包仓通用接口配置编码
    public static final String MK_FEE_CONFIG = "mkFeeConfig"; // mk 金额店铺配置
    public static final String VIM_WH_SOURCE_ETAM = "ETAM";
    public static final String VIM_WH_SOURCE_ITOCHU = "ILC-SH";
    public static final String VIM_WH_SOURCE_ITOCHU_UA = "ILC-SH-UA";
    public static final String VIM_WH_SOURCE_IDS_UA = "IDS-SH-UA"; // UA
    public static final String VIM_WH_SOURCE_WLB = "WLB";
    public static final String VIM_WH_SOURCE_GQS = "gqsscm";
    public static final String VIM_WH_SOURCE_YH = "YH"; // 粤海
    public static final String VIM_WH_SOURCE_GQS_SHC = "SHC";// 高绮诗-贝佳斯
    public static final String VIM_WH_SOURCE_GQS_BZC = "BZC";// 高绮诗-美心
    public static final String VIM_WH_SOURCE_GQS_GNC = "GNC";// 高绮诗-健安喜官方旗舰店
    public static final String VIM_WH_SOURCE_GQS_BRITA = "BRITA";// 高绮诗-brita碧然德旗舰店
    public static final String VIM_WH_SOURCE_GQS_ZRM = "ZRM";// 高绮诗-自然美官方旗舰店
    public static final String VIM_WH_SOURCE_GQS_CQ = "GQSSCM_SHOP";
    public static final String VMI_WH_SOURCE_GYMBOREE = "GYMBOREE";// 金宝贝品牌外包仓
    public static final String VIM_WH_SOURCE_GUESS = "guess";
    public static final String VIM_WH_SOURCE_GUESS_RETAIL = "guess001";
    public static final String VIM_WH_SOURCE_KERRYEAS = "kerryeas";// 嘉里大通
    public static final String VIM_WH_SOURCE_KERRYEAS_BJ = "GODIVABJ";// 嘉里大通bj
    public static final String VIM_WH_SOURCE_KERRYEAS_GZ = "GODIVAGZ";// 嘉里大通GZ
    public static final String VIM_WH_SOURCE_KERRYEAS_SH = "GODIVASH";// 嘉里大通SH
    public static final String VIM_WH_SOURCE_FOLLIE = "follie";// follie
    public static final String VIM_WH_SOURCE_NOVO = "novo";// NOVO
    public static final String VIM_WH_SOURCE_ARVATO_WMF = "wmf";// 欧维特物流
    public static final String VIM_WH_SOURCE_AEO_IDS = "AEO_IDS";
    public static final String VIM_WH_SOURCE_AF_IDS = "af";
    public static final String VIM_WH_SOURCE_NIKE_IDS = "IDS-NIKE";
    public static final String VIM_WH_SOURCE_CONVERSE_IDS = "IDS-CONVERSE";
    public static final String VIM_WH_SOURCE_NIKE_IDS_TM = "IDS-NIKE001";
    public static final String VIM_WH_SOURCE_NIKE_IDS_002 = "IDS-NIKE002";
    public static final String VIM_WH_SOURCE_NIKE_IDS_003 = "IDS-NIKE003";
    public static final String VIM_WH_SOURCE_NIKE_IDS_004 = "IDS-NIKE004";
    public static final String VIM_WH_SOURCE_NIKE_IDS_005 = "IDS-NIKE005";
    public static final String VIM_WH_SOURCE_NATURE_CARE = "naturecare";
    public static final String VIM_WH_SOURCE_UA_IDS = "IDS-SH-UA";
    public static final String VIM_WH_SOURCE_NBAUA_IDS = "IDS-SH-NBAUA";
    public static final String VIM_WH_SOURCE_NBJ01UA_IDS = "NBJ01";
    public static final String VIM_WH_SOURCE_NEWLOOK_IDS = "NewLook"; // ecargo外包仓
    public static final String VIM_WH_SOURCE_GODIVA_HAVI = "GODIVA-HAVI"; // "GODIVA-HAVI";
    public static final String VIM_WH_SOURCE_NEWLOOKJD_IDS = "NewLookJD"; //
    public static final String WCS = "WCS"; // 自动化对接WCS
    public static final String VIM_WH_STORER_AEO_IDS = "18438";
    public static final String VIM_WH_STORER_AEO_JD_IDS = "18439";
    public static final String VIM_WH_STORER_AEO_RTN_IDS = "18437";
    public static final String VIM_WH_FACILITY_AEO_IDS = "08002";
    public static final String VIM_WH_FACILITY_AEO_JD_IDS = "08004";
    public static final String VIM_WH_FACILITY_AEO_RTN_IDS = "08000";
    // public static final String VIM_WH_STORER_AEO_CODE_IDS = "1AEO官方旗舰店";
    // public static final String VIM_WH_STORER_AEO_JD_CODE_IDS = "AEO京东旗舰店";
    // public static final String VIM_WH_STORER_AEO_RTN_CODE_IDS = "aeo库存调拨";
    public static final String VIM_WH_SOURCE_IDS_VS = "IDS-VS";

    // public static final String VIM_WH_STORER_AEO_CODE_IDS = "1AEO店铺";
    // public static final String VIM_WH_STORER_AEO_JD_CODE_IDS = "1AEO京东旗舰店";
    // public static final String VIM_WH_STORER_AEO_RTN_CODE_IDS = "AEO库存调拨";

    public static final List<String> VIM_WH_SOURCE_LF_LIST;
    public static final String AD_CONFIG = "AD_CONFIG";
    public static final String AD_OUID = "AD_OUID";
    public static final String AD_SYSTEMKEY = "AD_SYSTEMKEY";
    public static final String AD_CUSTOMER_CODE = "AD_CUSTOMER_CODE";
    public static final String AD_INTREFER_EFFECTIVENESS = "AD_INTREFER_EFFECTIVENESS";
    public static final String AD_RETURN_OUTBOUND_KEY = "AD_RETURN_OUTBOUND_KEY";
    public static final String AD_TMALL_OWNER = "AD_TMALL_OWNER";
    static {
        VIM_WH_SOURCE_LF_LIST = new ArrayList<String>();
        VIM_WH_SOURCE_LF_LIST.add(Constants.VIM_WH_SOURCE_AF_IDS);
        VIM_WH_SOURCE_LF_LIST.add(Constants.VIM_WH_SOURCE_AEO_IDS);
        VIM_WH_SOURCE_LF_LIST.add(Constants.VIM_WH_SOURCE_NIKE_IDS);
        VIM_WH_SOURCE_LF_LIST.add(Constants.VIM_WH_SOURCE_NIKE_IDS_TM);
        VIM_WH_SOURCE_LF_LIST.add(Constants.VIM_WH_SOURCE_UA_IDS);
        VIM_WH_SOURCE_LF_LIST.add(Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
    }


    public static final String PICKING_LIST = "配货清单";// 配货清单
    public static final String INBOUND_INFO = "已入库";// 已入库
    // 系统导出文件存放路径
    public static final String FILE_PATH_EXPORT_FILE = "./ExportFile";
    public static final String FILE_PATH_INVOICE = FILE_PATH_EXPORT_FILE + "/Invoice";
    public static final String FILE_PATH_EXPRESS_BILL = FILE_PATH_EXPORT_FILE + "/ExpressBill";


    /**
     * ----------------------------------SF SERVICE END----------------------------
     */

    public static final String SMS_SERIAL_NUMBER = "3SDK-VBT-0130-MFQTR";
    public static final String SMS_KEY = "377471";

    // -- 仓库"运营模式"
    public static final String CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_WHOPMODE = "whOpMode";
    // -- 仓库"管理模式"
    public static final String CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_WHMANAGEMODE = "whManageMode";
    // 库区类型
    public static final String CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_DISTRICT_TYPE = "WarehousDistrictType";
    // -- 状态：使用中|已禁用
    public static final String CHOOSEOPTION_CATEGORY_CODE_STATUS = "status";
    // -- 权限：组织结构管理
    public static final String PRIVILEGE_ACL_ROOT_OU_MAINTAIN = "ACL_ROOT_OU_MAINTAIN";
    // --仓库：作业方向
    public static final String CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_DIRECTION = "whDirectionMode";
    // --仓库：计划和执行控制
    public static final String CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_TRABSACTIONCONTROL = "whTransactionControl";
    // 仓库：拣货方式
    public static final String CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_PICKUP = "whPickupModel";
    // 仓库：批次上架模式
    public static final String CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_INMODE = "whInboundStoreMode";
    // 仓库：作业单状态
    public static final String CHOOSEOPTION_CATEGORY_CODE_STA_STATUS = "whSTAStatus";
    // 物流发运地
    public static final String CHOOSEOPTION_CATEGORY_CODE_TRANS_DEPARTURE = "TRANS_DEPARTURE";
    /**************************************** 系统预定义作业事务类型 ******************************/
    // 取消释放统一库位作业类型新增
    public static final String TRANSACTION_TYPE_CANCEL_RELEASE_INVENTORY_OUT = "CANCEL_RELEASE_INVENTORY_OUT";
    public static final String TRANSACTION_TYPE_CANCEL_RELEASE_INVENTORY_IN = "CANCEL_RELEASE_INVENTORY_IN";
    public static final String TRANSACTION_TYPE_EXPENSE_INBOUND = "INBOUND_EXPENSE";
    // 采购入库
    public static final String TRANSACTION_TYPE_CODE_PURCHASE_INBOUND = "PURCHASE_INBOUND";
    // 结算经销入库
    public static final String TRANSACTION_TYPE_SETTLEMENT_INBOUND = "INBOUND_SETTLEMENT";
    // 其他入库
    public static final String TRANSACTION_TYPE_OTHERS_INBOUND = "OTHERS_INBOUND";
    // 代销入库
    public static final String TRANSACTION_TYPE_CONSIGNMENT_INBOUND = "INBOUND_CONSIGNMENT";
    // 赠品入库
    public static final String TRANSACTION_TYPE_GIFT_INBOUND = "INBOUND_GIFT";
    // 移库入库
    public static final String TRANSACTION_TYPE_MOBILE_INBOUND = "INBOUND_MOBILE";
    // 货返入库
    public static final String TRANSACTION_TYPE_SKU_RETURN_INBOUND = "INBOUND_SKU_RETURN";
    // 销售出库(外部订单销售出库)
    public static final String TRANSACTION_TYPE_CODE_SALES_OUTBOUND = "SALES_OUTBOUND";
    // 其他出库
    public static final String TRANSACTION_TYPE_OTHERS_OUTBOUND = "OTHERS_OUTBOUND";
    // 库内移动-出库
    public static final String TRANSACTION_TYPE_TRANSIT_INNER_OUT = "TRANSIT_INNER_OUT";
    // 库内移动-入库
    public static final String TRANSACTION_TYPE_TRANSIT_INNER_IN = "TRANSIT_INNER_IN";
    // 库间移动-入库
    public static final String TRANSACTION_TYPE_TRANSIT_CROSS_IN = "TRANSIT_CROSS_IN";
    // 库间移动-出库
    public static final String TRANSACTION_TYPE_TRANSIT_CROSS_OUT = "TRANSIT_CROSS_OUT";
    // 库存锁定
    public static final String TRANSACTION_TYPE_INVENTORY_LOCK = "INVENTORY_LOCK";
    // 退换货申请-退货入库
    public static final String TRANSACTION_TYPE_INBOUND_RETURN_REQUEST = "INBOUND_RETURN_REQUEST";
    // 退换货申请-换货出库
    public static final String TRANSACTION_TYPE_OUTBOUND_RETURN_REQUEST = "OUTBOUND_RETURN_REQUEST";
    // 库存状态修改-出库
    public static final String TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT = "INVENTORY_STATUS_CHANGE_OUT";
    // 库存状态修改-入库
    public static final String TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN = "INVENTORY_STATUS_CHANGE_IN";
    // 库存初始化-入库
    public static final String TRANSACTION_TYPE_INBOUND_INITIALIZE = "INBOUND_INITIALIZE";
    // GI上架入库
    public static final String TRANSACTION_TYPE_GI_PUT_SHELVES_INBOUND = "GI_PUT_SHELVES_INBOUND";
    // GI出库上架
    public static final String TRANSACTION_TYPE_GI_PUT_SHELVES_OUTBOUND = "GI_PUT_SHELVES_OUTBOUND";
    // 采购出库（采购退货出库）
    public static final String PURCHASE_OUTBOUND = "PURCHASE_OUTBOUND";
    // 结算经销出库
    public static final String SETTLEMENT_OUTBOUND = "SETTLEMENT_OUTBOUND";
    // 包材领用出库
    public static final String PACKAGING_MATERIALS_OUTBOUND = "PACKAGING_MATERIALS_OUTBOUND";
    // 代销出库
    public static final String CONSIGNMENT_OUTBOUND = "CONSIGNMENT_OUTBOUND";
    // VMI代销入库
    public static final String TRANSACTION_TYPE_VMI_INBOUND_CONSIGNMENT = "VMI_INBOUND_CONSIGNMENT";
    // VMI库存调整入库
    public static final String TRANSACTION_TYPE_VMI_ADJUSTMENT_INBOUND_CONSIGNMENT = "VMI_ADJUSTMENT_INBOUND";
    // VMI库存调整出库
    public static final String TRANSACTION_TYPE_VMI_ADJUSTMENT_OUTBOUND_CONSIGNMENT = "VMI_ADJUSTMENT_OUTBOUND";
    // VMI转店入库
    public static final String VMI_TRANSFER_IN = "VMI_TRANSFER_IN";
    // VMI转店出库
    public static final String VMI_TRANSFER_OUT = "VMI_TRANSFER_OUT";
    // VMI调拨入库
    public static final String VMI_FLITTING_IN = "VMI_FLITTING_IN";
    // VMI调拨出库
    public static final String VMI_FLITTING_OUT = "VMI_FLITTING_OUT";
    // 不同公司调拨出库
    public static final String DIFF_COMPANY_TRANSFER_OUT = "DIFF_COMPANY_TRANSFER_OUT";
    // 不同公司调拨入库
    public static final String DIFF_COMPANY_TRANSFER_IN = "DIFF_COMPANY_TRANSFER_IN";
    // vmi 退仓
    public static final String VMI_RETURN_OUT = "VMI_RETURN_OUT";
    // VMI_转店退仓
    public static final String VMI_TRANSFER_RETURN_OUT = "VMI_TRANSFER_RETURN_OUT";
    public static final String WELFARE_USE = "WELFARE_USE"; // 福利领用
    public static final String FIXED_ASSETS_USE = "FIXED_ASSETS_USE";// 固定资产领用
    public static final String SCARP_OUTBOUND = "SCARP_OUTBOUND";// 报废出库
    public static final String SALES_PROMOTION_USE = "SALES_PROMOTION_USE";// 促销领用
    public static final String LOW_VALUE_CONSUMABLE_USE = "LOW_VALUE_CONSUMABLE_USE";// 低值易耗品
    // 样品领用出库
    public static final String SAMPLE_OUTBOUND = "SAMPLE_OUTBOUND";
    // 样品领用入库
    public static final String SAMPLE_INBOUND = "SAMPLE_INBOUND";
    // 商品置换出库
    public static final String SKU_EXCHANGE_OUTBOUND = "SKU_EXCHANGE_OUTBOUND";
    // 商品置换入库
    public static final String SKU_EXCHANGE_INBOUND = "SKU_EXCHANGE_INBOUND";
    // 送修出库
    public static final String REAPAIR_OUTBOUND = "REAPAIR_OUTBOUND";
    // 送修入库
    public static final String REAPAIR_INBOUND = "REAPAIR_INBOUND";
    // 串号拆分出库
    public static final String SERIAL_NUMBER_OUTBOUND = "SERIAL_NUMBER_OUTBOUND";
    // 串号拆分入库
    public static final String SERIAL_NUMBER_INBOUND = "SERIAL_NUMBER_INBOUND";
    // 串号组合出库
    public static final String SERIAL_NUMBER_GROUP_OUTBOUND = "SERIAL_NUMBER_GROUP_OUTBOUND";
    // 串号组合入库
    public static final String SERIAL_NUMBER_GROUP_INBOUND = "SERIAL_NUMBER_GROUP_INBOUND";
    // 库存调整出
    public static final String INVENTORY_ADJUSTMENT_UPDATE_OUTBOUND = "INVENTORY_ADJUSTMENT_UPDATE_OUTBOUND";
    // 库存调整入
    public static final String INVENTORY_ADJUSTMENT_UPDATE_INBOUND = "INVENTORY_ADJUSTMENT_UPDATE_INBOUND";
    // 采购退货调整入库
    public static final String TRANSACTION_RETURN_PURCHASE_INBOUND = "INBOUND_RETURN_PURCHASE";

    /**************************************** 系统预定义作业事务类型 ******************************/
    // 盘点-盘亏
    public static final String TRANSACTION_TYPE_INVENTORY_CHECK_OUT = "INVENTORY_CHECK_OUT";
    // 盘点-盘盈
    public static final String TRANSACTION_TYPE_INVENTORY_CHECK_IN = "INVENTORY_CHECK_IN";
    // IDS反馈收货信息的中间表里面状态 的临时直
    public static final Integer IDS_TEMP_STATUS = -203;
    // -仓库: 配货清单状态
    public static final String CHOOSEOPTION_CATEGORY_CODE_PICKINGLIST_STATUS = "pickingListStatus";
    public static final String DF_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    // purchase order status
    public static final Integer PURCHASE_ORDER_STATUS_NEW = 8; // 新建
    public static final Integer PURCHASE_ORDER_STATUS_CONFIRM = 5; // 已确认
    public static final Integer PURCHASE_ORDER_STATUS_RECEIVE = 10; // 部分入库
    public static final Integer PURCHASE_ORDER_STATUS_RECEIVE_CLOSED = 12; // 部分入库已关闭(不再入库)
    public static final Integer PURCHASE_ORDER_STATUS_WAREHOUSING = 15; // 全部入库
    public static final String BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE = "SKU条码";
    public static final String BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE = "SKU编码";
    public static final String BETWEENLIBARY_MOVE_SKU_SHELF_MANAGEMENT = "保质期管理";
    public static final String BETWEENLIBARY_MOVE_SKU_TOGETHER = "批次混放";
    public static final String IMPORT_EXL_ERROR = "EXLERROR";
    public static final String IMPORT_EXL_SKU_CHOICE_ERROR = "EXLERROR";
    public static final String IMPORT_EXL_RESULT = "EXL_RESULT";
    public static final String SYSTEM_ERROR = "系统错误";
    // 盘点库位被占用错误查询行限制
    public static final Long LOCATION_LOCKED_RESULT_LIMIT = 30L;
    // 客户编码
    public static final String PAYMENTTYPE_001TOWN = "104"; // 一城支付方式
    public static final String SO_PAYMENT_TYPE_COD = "1"; // 货到付款
    public static final String YICHEN_BIG_CUSTOMER_CODE = "K00014"; // 一城卡支付的大客户以对应的客户编码
    public static final String SANKE_CUSTOMER_CODE = "SK001"; // 散客对应的客户编码
    public static final String PRINT_TEMPLATE_FLIENAME = "jasperprint/";
    public static final String INSTRUCTION_TYPE_DN = "VMI_INBOUND";
    public static final String INSTRUCTION_TYPE_ADJUSTMENT = "VMI_ADJUSTMENT";
    // =====================VMI=============================
    public static final String CONFIG_GROUP_NIKE_FTP = "NIKEFTP";
    public static final String CONFIG_GROUP_GYMBOREE_FTP = "GYMBOREEFTP";
    public static final String CONFIG_GROUP_GYMSC_FTP = "GYMTMALLFTP";
    public static final String CONFIG_GROUP_CONVERSE_YX_FTP = "CONVERSEYXFTP";

    public static final String TESTFILE_FTP_DOWNLOAD_FILE_START_STR = "fileStartStr";

    public static final String NIKE_FTP_DOWNLOAD_FILE_START_STR = "fileStartStr";
    public static final String NIKE_FTP_DOWN_LOCALPATH_BACKUP = "localDownPathBackup";
    public static final String NIKE_FTP_UP_LOCALPATH_BACKUP = "localUpPathBackup";

    public static final String CONFIG_GROUP_VMIFTP = "VMIFTP";
    public static final String VMI_FTP_REMOTE_BAK_PATH = "remotedownBakPath";
    public static final String VMI_FTP_URL = "url";
    public static final String VMI_FTP_PORT = "port";
    public static final String VMI_FTP_USERNAME = "username";
    public static final String VMI_FTP_PASSWORD = "password";
    public static final String VMI_FTP_DOWNPATH = "remotedownPath";
    public static final String VMI_FTP_REMOTEBAKPATH = "remoteBakPath";
    public static final String VMI_FTP_DOWN_LOCALPATH = "localDownPath";
    public static final String VMI_FTP_UP_LOCALPATH = "localUpPath";
    public static final String VMI_FTP_UPPATH = "remoteUPPath";
    public static final String VMI_FTP_TIMEOUT = "timeout";
    public static final String IT_FTP_FINISH_DIR = "BAK";
    public static final String VMI_FTP_UP_ZIP_LOCALPATH = "localUpZipPath";
    public static final String VMI_FTP_UP_ZIP_LOCALPATH_BACKUP = "localUpZipPathBackup";
    public static final String VMI_FTP_UP_ZIP_PATH = "localUpZipPath";
    public static final String VMI_FTP_PATH = "ftpPath";
    public static final String RTN_IN_DOC_PATH = "rtnInDocPath"; // ftp退货入文档目录
    public static final String LOC_DOC_PATH = "localDocPath"; // 本地退货入文档目录


    public static final String ESPRIT_RD_GROUP = "ESPRITRD";
    public static final String ESPRIT_RD_FTP_URL = "url";
    public static final String ESPRIT_RD_FTP_PORT = "port";
    public static final String ESPRIT_RD_FTP_USERNAME = "username";
    public static final String ESPRIT_RD_FTP_PASSWORD = "password";
    public static final String ESPRIT_RD_FTP_UPPATH = "remoteUpPath";
    public static final String ESPRIT_RD_FTP_DOWNPATH = "remoteDownPath";
    public static final String ESPRIT_RD_FTP_DOWN_LOCALPATH = "localDownPath";
    public static final String ESPRIT_RD_FTP_DOWN_LOCALPATH_BK = "localDownBackupPath";
    public static final String ESPRIT_RD_FTP_UP_LOCALPATH = "localUpPath";
    public static final String ESPRIT_RD_FTP_UP_LOCALPATH_BK = "localUpBackupPath";
    public static final String ESPRIT_RD_FTP_DOWNLOAD_FILE_START_STR = "fileStartStr";
    public static final String TESTFILE_RD_FTP_DOWNPATH = "remoteDownPath";


    public static final String TESTFILE_FTP_URL = "url";
    public static final String TESTFILE_FTP_PORT = "port";
    public static final String TESTFILE_FTP_USERNAME = "username";
    public static final String TESTFILE_FTP_PASSWORD = "password";

    public static final String ESPRIT_GROUP = "ESPRIT";
    public static final String TESTFILE_GROUP = "TestFile";
    public static final String ESPRIT_FTP_URL = "url";
    public static final String ESPRIT_FTP_PORT = "port";
    public static final String ESPRIT_FTP_USERNAME = "username";
    public static final String ESPRIT_FTP_PASSWORD = "password";
    public static final String ESPRIT_FTP_UPPATH = "remoteUpPath";
    public static final String ESPRIT_FTP_DOWNPATH = "remoteDownPath";
    public static final String ESPRIT_FTP_DOWN_LOCALPATH = "localDownPath";
    public static final String TESTFILE_FTP_DOWN_LOCALPATH = "localDownPath";
    public static final String ESPRIT_FTP_DOWN_LOCALPATH_BK = "localDownBackupPath";
    public static final String TESTFILE_FTP_DOWN_LOCALPATH_BK = "localDownBackupPath";
    public static final String ESPRIT_FTP_UP_LOCALPATH = "localUpPath";
    public static final String ESPRIT_FTP_UP_LOCALPATH_BK = "localBackupPath";

    public static final String CATHKIDSTON_GROUP = "CATHKIDSTON";
    public static final String CATHKIDSTON_FTP_URL = "url";
    public static final String CATHKIDSTON_FTP_PORT = "port";
    public static final String CATHKIDSTON_FTP_USERNAME = "username";
    public static final String CATHKIDSTON_FTP_PASSWORD = "password";
    public static final String CATHKIDSTON_FTP_UPPATH = "remoteUPPath";
    public static final String CATHKIDSTON_FTP_DOWNPATH = "remotedownPath";
    public static final String CATHKIDSTON_FTP_DOWN_LOCALPATH = "localDownPath";
    public static final String CATHKIDSTON_FTP_DOWN_LOCALPATH_BK = "localDownPathBackup";
    public static final String CATHKIDSTON_FTP_UP_LOCALPATH = "localUpPath";
    public static final String CATHKIDSTON_FTP_UP_LOCALPATH_BK = "localUpPathBackup";
    public static final String CATHKIDSTON_RD_FTP_DOWNLOAD_FILE_START_STR = "fileStartStr";

    public static final String EDW_GROUP = "EDW";
    public static final String EDW_FTP_URL = "url";
    public static final String EDW_FTP_PORT = "port";
    public static final String EDW_FTP_USERNAME = "username";
    public static final String EDW_FTP_PASSWORD = "password";
    public static final String EDW_FTP_UPPATH = "remoteUPPath";
    public static final String EDW_FTP_DOWNPATH = "remotedownPath";
    public static final String EDW_FTP_DOWN_LOCALPATH = "localDownPath";
    public static final String EDW_FTP_DOWN_LOCALPATH_BK = "localDownPathBackup";
    public static final String EDW_FTP_UP_LOCALPATH = "localUpPath";
    public static final String EDW_FTP_UP_LOCALPATH_BK = "localUpPathBackup";
    public static final String EDW_FTP_DOWNPATH_RD_FTP_DOWNLOAD_FILE_START_STR = "fileStartStr";

    public static final String CONVERSE_RP_GROUP = "CONVERSERP";
    public static final String CONVERSE_RP_FTP_URL = "url";
    public static final String CONVERSE_RP_FTP_PORT = "port";
    public static final String CONVERSE_RP_FTP_USERNAME = "username";
    public static final String CONVERSE_RP_FTP_PASSWORD = "password";
    public static final String CONVERSE_RP_FTP_UPPATH = "remoteUPPath";
    public static final String CONVERSE_RP_FTP_UP_LOCALPATH = "localUpPath";
    public static final String CONVERSE_RP_FTP_UP_LOCALPATH_BK = "localBackPath";

    public static final String CONFIG_GROUP_ZHDINVFTP = "ZHDINV";
    public static final String ZHDINV_FTP_URL = "ZHDINVFtpUrl";// 库存FTP服务器地址
    public static final String ZHDINV_FTP_PORT = "ZHDINVFtpPort";// 端口
    public static final String ZHDINV_FTP_UPPATH = "ZHDINVFtpUpPath";// 库存FTP上传目录
    public static final String ZHDINV_FTP_USERNAME = "ZHDINVFtpUserName";// 用户名
    public static final String ZHDINV_FTP_PASSWORD = "ZHDINVFtpPassword";// 密码
    public static final String ZHDINV_LOCAL_PATH = "ZHDINVLocalPath";// 库存文件本地生成路径
    public static final String ZHDINV_DOWNLOAD_LOCAL_PATH = "ZHDINVDownLoadLocalPath";// 库存文件本地生成路径


    public static final String CONFIG_GROUP_CONVERSEFTP = "CONVERSEFTP";
    public static final String CONVERSE_FTP_URL = "url";
    public static final String CONVERSE_FTP_PORT = "port";
    public static final String CONVERSE_FTP_USERNAME = "username";
    public static final String CONVERSE_FTP_PASSWORD = "password";
    public static final String CONVERSE_FTP_DOWNPATH = "remotedownPath";
    public static final String CONVERSE_FTP_DOWN_LOCALPATH = "localDownPath";
    public static final String CONVERSE_FTP_UP_LOCALPATH = "localUpPath";
    public static final String CONVERSE_FTP_UPPATH = "remoteUPPath";
    public static final String CONVERSE_FTP_TIMEOUT = "timeout";
    public static final String CONVERSE_FTP_FINISH_DIR = "BAK";
    public static final String CONVERSE_FTP_ASN = "ASN";
    public static final String CONVERSE_FTP_STYLE = "style";
    public static final String CONVERSE_FTP_PRICE = "price";
    public static final String CONVERSE_FTP_EAN = "EAN";
    public static final String CONVERSE_FTP_ASN_RECEIVE = "ASNReceive";
    public static final String CONVERSE_FTP_TRANSFER = "transfer";
    public static final String CONVERSE_FTP_SALES = "sales";
    public static final String CONVERSE_FTP_IA = "IA";
    public static final String CONVERSE_FTP_RA = "RA";
    public static final String CONVERSE_FTP_RIB = "RIB";
    public static final String CONVERSE_FTP_BINTOBIN = "bintobin";
    public static final String CONVERSE_FTP_PRODUCTINFO = "productinfo";
    public static final String CONVERSE_FTP_LISTPRCE = "DUPSTYLE";

    public static final String INBOUND_CERTIFICATE_SAVE_GROUP = "INBOUND_CERTIFICATE";
    public static final String INBOUND_CERTIFICATE_SAVE_PATH = "savePath";

    public static final String IT_VMI_CODE_TB = "EITCN";
    public static final String IT_VMI_CODE_BS = "EIXCN";

    public static final String CONFIG_GROUP_IDSFTP = "IDSFTP";
    public static final String IDS_FTP_IN = "in_";
    public static final String IDS_FTP_OUT = "out_";
    public static final String IDS_FTP_URL = "url";
    public static final String IDS_FTP_PORT = "port";
    public static final String IDS_FTP_USERNAME = "username";
    public static final String IDS_FTP_PASSWORD = "password";
    public static final String IDS_FTP_DOWNLOAD_PATH = "ftpDownloadPath";
    public static final String IDS_FTP_LOCAL_SAVE_PATH = "localSavePath";
    public static final String IDS_FTP_FILE_TYPE_REC = "WMSREC";
    public static final String IDS_FTP_FILE_TYPE_SHP = "WMSSHP";
    public static final String IDS_FTP_FILE_TYPE_ADJ = "WMSADJ";

    public static final String IDS_FTP_FILE_TYPE_RECHD = "RECHD";
    public static final String IDS_FTP_FILE_TYPE_RECDT = "RECDT";
    public static final String IDS_FTP_FILE_TYPE_SHPHD = "SHPHD";
    public static final String IDS_FTP_FILE_TYPE_SHPDT = "SHPDT";
    public static final String IDS_FTP_FILE_TYPE_ADJHD = "ADJHD";
    public static final String IDS_FTP_FILE_TYPE_ADJDT = "ADJDT";


    public static final String CONFIG_GROUP_GUESSFTP = "GUESSFTP";
    public static final String EBS_INVENTORY = "OMS";
    public static final String PAC_INVENTORY = "PAC";
    public static final String GUESS_FTP_UP_LOCALPATH_BACKUP = "localUpPathBackup";

    /**
     * 仓库区域占用优先级缓存
     */
    public static Map<Long, List<ZoonOcpSort>> zoonOcpMap = new HashMap<Long, List<ZoonOcpSort>>();

    /**
     * 仓库占用库存缓存
     */
    public static Map<Long, Date> WAREHOUSE_OCP_TIME_MAP;
    static {
        WAREHOUSE_OCP_TIME_MAP = new HashMap<Long, Date>();
    }

    public static final Map<String, String> IT_VMI_WH_STATION;

    static {
        IT_VMI_WH_STATION = new HashMap<String, String>();
        IT_VMI_WH_STATION.put(IT_VMI_CODE_TB, "01");
        IT_VMI_WH_STATION.put(IT_VMI_CODE_BS, "02");
    }

    public static final Map<String, String> PHILIPS_DELIVERY_TYPE;
    static {
        PHILIPS_DELIVERY_TYPE = new HashMap<String, String>();
        PHILIPS_DELIVERY_TYPE.put("ZCNEXP", "商城快递");
        PHILIPS_DELIVERY_TYPE.put("ZCNEMS", "EMS");
    }

    public static final Map<String, String> PHILIPS_DELIVERY_TIME;
    static {
        PHILIPS_DELIVERY_TIME = new HashMap<String, String>();
        PHILIPS_DELIVERY_TIME.put("WD_8TO18", " 仅工作日-8AM到18PM");
        PHILIPS_DELIVERY_TIME.put("WKN_8TO18", " 仅双休日-8AM到18PM");
        PHILIPS_DELIVERY_TIME.put("WD_WKN_8TO18", " 工作日双休日均可-8AM到18PM");
    }

    public static final String PHILIPS_INVENTORY_FOR_SALE = "可销售";
    public static final String PHILIPS_INVENTORY_DEFECTIVE = "不可销售";
    public static final String PHILIPS_INVENTORY_PENDING = "待处理品";

    public static final Map<String, String> PHILIPS_INVENTROY_TYPE;
    static {
        PHILIPS_INVENTROY_TYPE = new HashMap<String, String>();
        PHILIPS_INVENTROY_TYPE.put(PHILIPS_INVENTORY_FOR_SALE, "WHS");
        PHILIPS_INVENTROY_TYPE.put(PHILIPS_INVENTORY_PENDING, "REBL");
        PHILIPS_INVENTROY_TYPE.put(PHILIPS_INVENTORY_DEFECTIVE, "DAM");
    }


    public static final String ESPRIT_TO_NODE_TMALL = "EPRCO";
    public static final String ESPRIT_TO_NODE_CN = "EPRCE";
    public static final String ESPRIT_BAOZUN_GLN = "4046655000664";
    public static final String ESPRIT_ESPRIT_GLN = "4046655000480";
    public static final String ESPRIT_TMALL = "4046655009032";
    public static final String ESPRIT_CN = "4046655009049";
    public static final String ESPRIT_VIM_CODE = "4046655000664";
    public static final String BAOZUN_NODE = "CREDREWW";
    public static final String ESPRIT_NODE = "CREDR";


    public static final Map<String, String> ESP_SHOP_GLN_TO_VMICODE;
    static {
        ESP_SHOP_GLN_TO_VMICODE = new HashMap<String, String>();
        ESP_SHOP_GLN_TO_VMICODE.put("EPRCO", ESPRIT_TMALL);
        ESP_SHOP_GLN_TO_VMICODE.put("EPRCE", ESPRIT_CN);
    }

    public static final Map<String, String> ESP_BAO_COMPANY_GLN_CODE;
    static {
        ESP_BAO_COMPANY_GLN_CODE = new HashMap<String, String>();
        ESP_BAO_COMPANY_GLN_CODE.put(ESPRIT_BAOZUN_GLN, "BAO");
        ESP_BAO_COMPANY_GLN_CODE.put(ESPRIT_ESPRIT_GLN, "CES");
    }

    // 官方商城MQ接口相关常量
    public static final Long PHILIPS_OUT_SHOP_ID = 0L;
    // 正式
    public static final Long IT_BS_SHOP_ID = 1863L; // IT官方商城店铺ID
    public static final Long ESP_BS_SHOP_ID = 1842L; // ESP官方商城店铺ID
    public static final Long GDV_BS_SHOP_ID = 1902L; // GODIVA官方商城店铺ID
    public static final Long TMALL_GDV_BS_SHOP_ID = 2362L; // GODIVA TMALL店铺ID
    public static final Long CNV_BS_SHOP_ID = 2306L; // converse官方商城店铺ID
    public static final Long CNV_TB_SHOP_ID = 2516L; // converse Tmall
    public static final Long COACH_SHOP_ID = 2442L;
    public static final Long CCH_SHOP_ID = 2542L;
    public static final Long MAS_SHOP_ID = 2622L;
    public static final Long MAS_FOOD_SHOP_ID = 3702L;
    public static final Long CK_BS_SHOP_ID = 1462L; // CK淘宝ID
    public static final Long CK_OU_ID = 2262L; // CK OU ID
    public static final Long MIC_BS_SHOP_ID = 2522L; // Microsoft BS

    // OMS_官方商城MQ队列名
    public static final String OMS_ITBS_MQ_SKU_QUEQUE = "oms.itbs.mq.sku";
    public static final String OMS_ITBS_MQ_PRICE_QUEQUE = "oms.itbs.mq.price";
    public static final String OMS_ITBS_MQ_NOTIFY_QUEQUE = "oms.itbs.mq.notify";
    public static final String ITBS_OMS_MQ_SO_QUEQUE = " itbs.oms.mq.so";
    public static final String OMS_ITBS_MQ_INV_QUEQUE = "oms.itbs.mq.inventory";


    public static final String MQ_ORDER_QUEQUE = "order";
    public static final String MQ_SKU_QUEQUE = "sku";
    public static final String MQ_PRICE_QUEQUE = "price";
    public static final String MQ_NOTIFY_QUEQUE = "notify";
    public static final String MQ_SO_QUEQUE = "so";
    public static final String MQ_INV_QUEQUE = "inv";
    public static final String MQ_MDPRICE_QUEQUE = "mdprice";

    public static final Map<String, Long> BS_VMI_CODE_REF_SHOP_ID;
    static {
        BS_VMI_CODE_REF_SHOP_ID = new HashMap<String, Long>();
        BS_VMI_CODE_REF_SHOP_ID.put(IT_VMI_CODE_TB, IT_BS_SHOP_ID);
        BS_VMI_CODE_REF_SHOP_ID.put(IT_VMI_CODE_BS, IT_BS_SHOP_ID);
        BS_VMI_CODE_REF_SHOP_ID.put(ESPRIT_TMALL, ESP_BS_SHOP_ID);
        BS_VMI_CODE_REF_SHOP_ID.put(ESPRIT_CN, ESP_BS_SHOP_ID);
        BS_VMI_CODE_REF_SHOP_ID.put(ESPRIT_VIM_CODE, ESP_BS_SHOP_ID);
    }

    // sku sales model (商品销售模式)
    public static final Integer SKU_SALES_MODEL_0 = 0; // 经销
    public static final Integer SKU_SALES_MODEL_1 = 1; // 代销
    public static final Integer SKU_SALES_MODEL_2 = 2; // 结算经销

    // sku life cycle status
    public static final Integer SKU_STATUS_ONSALES = 5;
    public static final Integer SKU_STATUS_OFFSALES = 1;
    public static final Integer SKU_STATUS_DISABLED = 0;

    public static final String IT_NATURAL_PROPERTY_CODE = "1243.03"; // 服装
    // product上默认物流方式
    public static final String SKU_LOGISTIC_TYPE_EXPRESS = "01"; // 尊宝快递（1～4天）

    // 顺丰 接口
    public static final String SF_WEB_SERVICE_INFO = "wmsSFServiceInfo";
    public static final String SF_WEB_SERVICE_INFO_CODE = "CLIENT_CODE";
    public static final String SF_WEB_SERVICE_INFO_CHECKWORD = "CHECKWORD";
    public static final String SF_WEB_SERVICE_INFO_BZ021003 = "BZ021003";
    public static final String SF_WEB_SERVICE_INFO_BZ021COD = "BZ021COD";
    public static final String SF_WEB_SERVICE_INFO_BZ021NOTCOD = "BZ021NOTCOD";


    public static final String ESPRIT_PO_TYPE_1 = "直送";
    public static final String ESPRIT_PO_TYPE_3 = "非直送";
    public static final String ESPRIT_PO_TYPE_5 = "进口";
    public static final String ESPRIT_PO_TYPE_7 = "特许证产品";
    public static final String ESPRIT_PO_TYPE_9 = "大连自提";

    public static final Map<String, Integer> ESPRIT_PO_TYPE;
    static {
        ESPRIT_PO_TYPE = new HashMap<String, Integer>();
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_1, 1);
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_3, 3);
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_5, 5);
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_7, 7);
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_9, 9);
    }

    // Nike web service
    public static final String UNION_ID_FOR_NIKE = "20101001_NIKE";
    public static final String NIKE_SHOP1_ID = "1NIKE中国官方商城"; // nike商城名称
    public static final String NIKE_SHOP2_ID = "1Nike官方旗舰店"; // nike官方旗舰店
    public static final String NIKE_SHOP3_ID = "5Nike-Global Swoosh 官方商城";// 5Nike-Global Swoosh
    // 官方商城
    public static final String NIKE_SHOP4_ID = "5Nike-Global Inline官方商城";// 1NIKE-GLOBLE官方旗舰店

    public static final String OP_TYPE_NIKE_SALES_OUT = "UD100"; // NIKE销售出库
    public static final String NIKE_RESOURCE = "NIKE";
    public static final String NIKE_ORDER_PRE = "NIKE_";
    public static final String NIKE_LOCATION_CODE = "3688";
    public static final String NIKE_TRANSACTION_TYPE_SALES_OUTBOUND = "UD100"; // 销售出库 - 销售出库
    public static final String NIKE_TRANSACTION_TYPE_RETURN_INBOUND = "UD011"; // 退货入库 - 退换货入库
    public static final String NIKE_TRANSACTION_TYPE_CHANGE_INBOUND = "UD015"; // 换货入库 - 退换货入库
    public static final String NIKE_TRANSACTION_TYPE_CHANGE_OUTBOUND = "UD014"; // 换货出库 - 退换货出库

    // PHILIPS
    public static final String PHILIPS_ORDER_PRE = "PHILIPS_";


    // Converse
    public static final String CONVERSE_TRANSFER_PREFIX = "CONVERSE";
    public static final String CONVERSE_LOCATION_CODE = "IDS";

    public static final String CONVERSE_RECEIVE_ST = "Stkreccon";
    public static final String CONVERSE_RECEIVE_TF = "tfx";
    public static final String CONVERSE_RECEIVE_CSV = "CSVOrders";
    public static final String CONVERSE_ADJUSTMENT_IA = "IA";
    public static final String CONVERSE_INVSTATUS_CHANGE = "bintransfer";
    public static final Long CONVERSE_SHOP_ID = 2306L;

    public static final String ETAM_CONFIG_GROUP_FTP = "ETAMFTP";
    public static final String ETAM_FTP_DOWNLOAD_FILE_START_STR = "etamFileStartStr";
    public static final String ETAM_VMI_CODE = "Etam2000";

    public static final String CONFIG_GROUP_GODIVA_SFTP = "GODIVASFTP";
    public static final String CONFIG_GROUP_ITOCHU_FTP = "ITOCHUFTP";
    public static final String CONFIG_GROUP_UA_FTP = "UAFTP";
    public static final String ITOCHU_FTP_DOWN_LOCALPATH_BACKUP = "itochuLocalDownPathBackup";
    public static final String ITOCHU_FTP_DOWN_LOCALPATH_BACKUP2 = "itochuLocalDownPathBackup2";
    public static final String ITOCHU_FTP_DOWNLOAD_FILE_START_STR = "itochuFileStartStr";

    public static final String VMI_FTP_DOWN_LOCALPATH_BACKUP = "vmiLocalDownPathBackup";
    public static final String TB_ORDER = "50";// 淘宝订单

    public static final String CHOOSEOPTION_CATEGORY_CODE_MSGOUTBOUNDORDER_STATUS = "msgOrderStatus";
    public static final String CHOOSEOPTION_CATEGORY_CODE_MSGINBOUNDORDER_STATUS = "msgRtnInboundStatus";

    public static final String MICROSOFTTEST_FTP_GROUP = "MICROSOFTTEST";
    public static final String MICROSOFTTEST_FTP_URL = "url";
    public static final String MICROSOFTTEST_FTP_PORT = "port";
    public static final String MICROSOFTTEST_FTP_USERNAME = "username";
    public static final String MICROSOFTTEST_FTP_PASSWORD = "password";
    public static final String MICROSOFTTEST_FTP_UPPATH = "uploadPath";
    public static final String MICROSOFTTEST_FTP_LOCAL_UPPATH = "localUpPath";
    public static final String MICROSOFTTEST_FTP_UPPATH_BACKUP = "localUpPathBackup";


    public static final String MICROSOFTTEST_SN_FTP_GROUP = "MICROSOFT_SN";
    public static final String MICROSOFTTEST_SN_FTP_URL = "url";
    public static final String MICROSOFTTEST_SN_FTP_PORT = "port";
    public static final String MICROSOFTTEST_SN_FTP_USERNAME = "username";
    public static final String MICROSOFTTEST_SN_FTP_PASSWORD = "password";
    public static final String MICROSOFTTEST_SN_FTP_UPPATH = "uploadPath";
    public static final String MICROSOFTTEST_SN_FTP_LOCAL_UPPATH = "localUpPath";
    public static final String MICROSOFTTEST_SN_FTP_UPPATH_BACKUP = "localUpPathBackup";



    public static final String CK_WH_SOURCE = "cksource";
    public static final String CK_GOOD_INV_STATUS_NAME = "良品";
    public static final String CK_DEFECTIVE_INV_STATUS_NAME = "残次品";
    public static final String CK_GOOD_NOT_SALE_NAME = "良品不可销售";

    public static final String CK_INV_FTP_CONFIG_GROUP = "CKINVFTP";
    public static final String CKFTP_CONFIG_GROUP = "CKFTP";
    public static final String CK_FTP_URL = "url";
    public static final String CK_FTP_PORT = "port";
    public static final String CK_FTP_USERNAME = "username";
    public static final String CK_FTP_PASSWORD = "password";
    public static final String CK_FTP_DOWNPATH = "remotedownPath";
    public static final String CK_FTP_DOWN_LOCALPATH = "localDownPath";
    public static final String CK_FTP_UP_LOCALPATH = "localUpPath";
    public static final String CK_FTP_UPPATH = "remoteUPPath";
    public static final String CK_FTP_TIMEOUT = "timeout";
    public static final String CK_BAK_DIR = "bak";
    public static final String CK_SHOP_INNER_CODE = "1charleskeith旗舰店";

    public static final String CK_BRANCH_CODE_1 = "100001"; // 主仓
    public static final String CK_BRANCH_CODE_2 = "100003"; // ECOM仓
    public static final String CK_BRANCH_CODE_3 = "100008"; // 预备仓
    public static final String CK_BRANCH_CODE_4 = "100006"; // 次品退货仓
    public static final String CK_BRANCH_CODE_5 = "100006"; // 正品退货仓

    // ----------------Coach ------------------
    public static final String COACHFTP_CONFIG_GROUP = "COACHFTP";
    public static final String COACH_FTP_URL = "url";
    public static final String COACH_FTP_PORT = "port";
    public static final String COACH_FTP_USERNAME = "username";
    public static final String COACH_FTP_PASSWORD = "password";
    public static final String COACH_FTP_LOCALPATH = "localPath";
    public static final String COACH_FTP_REMOTE = "remotePath";
    public static final String COACH_FTP_IN = "In";
    public static final String COACH_FTP_OUT = "Out";
    public static final String COACH_FTP_BAK = "BAK";
    public static final String COACH_VMI_CODE = "coach";
    // ----------------puma ------------------
    public static final String PUMA_VMI_CODE = "puma";
    // --------------speedo------------
    public static final String SPEEDO_VMI_CODE = "SPEEDO";

    public static final String COACH_OWNER1 = "1coach官方旗舰店";
    public static final String COACH_OWNER2 = "1Coach官方商城";


    public static final String BURBERRY_OWNER1 = "1博柏利官方旗舰店";
    // ----------------Mas ------------------
    public static final String MASFTP_CONFIG_GROUP = "MASFTP";
    public static final String MAS_FTP_URL = "url";
    public static final String MAS_FTP_PORT = "port";
    public static final String MAS_FTP_USERNAME = "username";
    public static final String MAS_FTP_PASSWORD = "password";
    public static final String MAS_FTP_LOCALPATH = "localPath";
    public static final String MAS_FTP_REMOTE = "remotePath";
    public static final String MAS_FTP_IN = "In";
    public static final String MAS_FTP_OUT = "Out";
    public static final String MAS_FTP_BAK = "BAK";
    public static final String MAS_VMI_CODE = "MAS";


    // wlb
    public static final String SHOPEX_LOG_SUCCESS = "SUCCESS";

    public static final String WLB_CREATE_SKU_TYPE = "【创建物流宝商品】";

    public static final String WLB_UPDATE_SKU_TYPE = "【更新物流宝商品】";

    public static final String WLB_CREATE_RETURN_ORDER_TYPE = "【创建退货单】";

    public static final String WLB_CREATE_SALES_ORDER_TYPE = "【创建订单】";

    public static final String WLB_CREATE_PURCHASE_ORDER_TYPE = "【创建采购入库单】";

    public static final String WLB_CANCEL_SALES_ORDERE = "【取消订单】";

    public static final String WLB_CHECK_SALES_ORDERE = "【检查商品是否已经创建到物流宝】";

    public static final String WLB_STORE_CODE = "FBI-0001";

    public static final String WLB_SENDER_INFO = "FBI-0001";

    public static final String BUSINESSES_SENDER_INFO = "201708^^^上海^^^上海市^^^青浦区^^^徐泾镇华徐公路589号E1库U2-U3仓^^^陈烨^^^13601693743^^^NA";

    public static final Integer WLB_PURCHARSE_ORDER = 1; // 创建了物流宝采购入库单

    public static final Integer WLB_CHUKU_ORDER = 2; // //创建了物流宝移库出库单
    public static final Long ZERO = 0L;
    public static final Long ONE = 1L;
    public static final String ILC_SHHDRDNNEW = "SHHDRDNNEW";// 伊藤忠-出库指示前缀
    public static final String ILC_SHDTLDNNEW = "SHDTLDNNEW";// 伊藤忠-出库指示前缀
    public static final String ILC_RCHDRRTNEW = "RCHDRRTNEW";
    public static final String ILC_RCDTLDNNEW = "RCDTLDNNEW";

    public static final String ILC_UA_EC = "UA-EC";// 伊藤忠-UA 品牌
    public static final String UA_OUTFILE_PREFIX = "SHIP";// UA出库通知
    public static final String UA_OUTFILE_SUFFIX = "shupl";
    public static final String UA_INFILE_PREFIX = "RecOnWay";// UA入库反馈
    public static final String UA_INTFILE_SUFFIX = "rcupl";
    public static final String UA_INRTNFILE_PREFIX = "RECV";// UA入库反馈
    public static final String UA_INRTNFILE_SUFFIX = "rcupl";
    public static final String UA_RETURN_PREFIX = "RETURN";// UA退仓反馈
    public static final String UA_RETURN_SUFFIX = "shupl";

    public static final String SHOP_ESPRIT = "S_ESPRIT";
    public static final String SHOP_MICROSOFT = "S_MICROSOFT";
    public static final String SHOP_CONVERS = "S_CONVERS";
    public static final String SHOP_CACHE = "S_CACHE";
    public static final String SHOP_NIKE = "S_NIKE";
    public static final String SHOP_NIKE_NEW = "S_NIKE_NEW";
    public static final String SHOP_JACKWOLFSKIN = "S_JACKWOLFSKIN";
    public static final String SHOP_BURBERRY = "S_BURBERRY";
    public static final String SHOP_TIGER = "S_TIGER";// 虎牌 bin.hu

    public static final String TEMPLATE_ESPRIT = "trunk_main_for_sprit_shop.jasper";
    public static final String TEMPLATE_MICROSOFT = "trunk_main_for_microsoft.jasper";
    public static final String TEMPLATE_CONVERS = "packing_list_convers_main1.jasper";
    public static final String TEMPLATE_CACHE = "trunk_main_for_cache_shop.jasper";
    public static final String TEMPLATE_NIKE = "packing_list_nike_main.jasper";
    public static final String TEMPLATE_NIKE_NEW = "packing_list_nike_main_new.jasper";
    public static final String TEMPLATE_JACKWOLFSKIN = "packing_list_jackwolfskin_main.jasper";
    public static final String TEMPLATE_BURBERRY = "packing_list_burberry_main.jasper";
    public static final String TEMPLATE_TIGER = "trunk_main_for_tiger_shop.jasper";// 虎牌 bin.hu

    /***** 特殊打印 模版 *******/
    public static final String SP_PRINT_COACH_MAIN = "coachTicketPrint.jasper";
    public static final String SP_PRINT_COACH_DETAIL = "coachTicketPrint_subreport1.jasper";
    public static final String SP_PRINT_BURBERRY_MAIN = "packing_list_burberry_main.jasper";
    public static final String SP_PRINT_BURBERRY_DETAIL = "packing_list_burberry_detail.jasper";
    public static final String SP_PRINT_BURBERRY_RETURN_MAIN = "return_burberry_main.jasper";
    public static final String SP_PRINT_BURBERRY_RETURN_DETAIL = "return_burberry_detail.jasper";

    public static final String NIKE_FILE_CODE_KEY = "_FILE_CODE";
    public static final String NIKE_FILE_WRITER_KEY = "_FILE_WRITER";
    public static final String NIKE_BUFFERED_WRITER_KEY = "_BUFFERED_WRITER";
    public static final String NIKE_IN_KEY = "_IN";
    public static final String NIKE_OUT_KEY = "_OUT";

    public static final String OMS3_SOURCE = "SYSTEM";

    public static final String PHILIPS_VMI_CODE = "philips";
    public static final Long MQ_SKU_ON_SALES_SHOP_ID = 441L;
    public static final String SHOP_SHARE_GROUP_GDV = "GDV";
    public static final String APP_KEY = "21325305";
    public static final String APP_SECRET = "44ddf917458123851b94a0b272ce2654";
    public static final String ZTO_INTERFACE_URL = "http://partner.zto.cn/partner/interface.php";

    // 物流推荐 规则数据缓存 时间
    public static final String TRANS_SUGGES_TIME_CATEGORY_CODE = "TRANS_SUGGES_TIME";
    public static final String TRANS_SUGGES_TIME_OPTION_KEY = "TRANS_ROLE_CACHE_TIME";// 单位为分钟

    // cas返回消息定义
    public static final String CAS_REGISTER_SUCCESS = "11";// 用户注册成功（注册请求返回）
    public static final String CAS_REGISTER_ERROR = "12";// 找到注册过的用户，且工号匹配（注册请求返回）
    public static final String CAS_REGISTER_ERROR1 = "13";// 找到注册过的用户，但工号不匹配（注册请求返回）
    public static final String CAS_PWDMODFIFY_SUCCESS = "21";// 更新密码成功（更新密码请求返回）
    public static final String CAS_PWDMODFIFY_ERROR = "22";// 更新密码失败，找不到用户（更新密码请求返回）
    public static final String CAS_PWDMODFIFY_ERROR1 = "23";// 更新密码失败，原密码错误（更新密码请求返回）
    public static final String CAS_SUPERMODIFY_SUCCESS = "41";// 超级用户更新密码成功（超级用户更新密码请求返回）
    public static final String CAS_SUPERMODIFY_ERROR = "42";// 超级用户更新密码失败，找不到用户（超级用户更新密码请求返回）
    public static final String CAS_SUPERMODIFY_ERROR1 = "43";// 超级用户更新密码失败，超级用户密码错误（超级用户更新密码请求返回）
    public static final String CAS_LOGIN_SUCCESS = "31";// 用户认证成功（用户请求返回）
    public static final String CAS_LOGIN_ERROR = "32";// 用户认证失败（用户请求返回）

    // oauth返回消息定义
    public static final String OAUTH_LOGIN_SUCCESS = "true";
    public static final String OAUTH_LOGIN_ERROR = "false";
    // 物流对账信息导出 常量
    public static final Long WH_PACKAGE_INFO_COUNT = 300000L;

    // 强生ftp相关信息
    public static final String CONFIG_GROUP_JNJFTP = "JNJFTP";
    public static final String JNJ_FTP_URL = "JNJFtpUrl";// 强生库存FTP服务器地址
    public static final String JNJ_FTP_PORT = "JNJFtpPort";// 端口
    public static final String JNJ_FTP_UPPATH = "JNJFtpUpPath";// 强生库存FTP上传目录
    public static final String JNJ_FTP_USERNAME = "JNJFtpUserName";// 用户名
    public static final String JNJ_FTP_PASSWORD = "JNJFtpPassword";// 密码
    public static final String JNJ_LOCAL_PATH = "JNJLocalPath";// 强生库存文件本地生成路径

    // lmis-ftp相关信息
    public static final String CONFIG_GROUP_LMISFTP = "LMISFTP";
    public static final String LMIS_FTP_URL = "LMISFtpUrl";// 库存FTP服务器地址
    public static final String LMIS_FTP_PORT = "LMISFtpPort";// 端口
    public static final String LMIS_FTP_UPPATH = "LMISFtpUpPath";// 库存FTP上传目录
    public static final String LMIS_FTP_USERNAME = "LMISFtpUserName";// 用户名
    public static final String LMIS_FTP_PASSWORD = "LMISFtpPassword";// 密码
    public static final String LMIS_LOCAL_PATH = "LMISLocalPath";// 库存文件本地生成路径

    public static final String EXPRESSWAYBILL_THREAD = "EXPRESSWAYBILL_THREAD";// 设置获取运单分页数据的线程数

    // Nike-HK装箱清单文案图片-ftp相关信息
    public static final String CONFIG_GROUP_NIKE_HK_SFTP = "nikeHKFTP";
    public static final String NIKE_HK_FTP_URL = "nikeHKFtpUrl";// FTP服务器地址
    public static final String NIKE_HK_FTP_PORT = "nikeHKFtpPort";// 端口
    public static final String NIKE_HK_FTP_DOWNPATH = "nikeHKRemotedownPath";// FTP下载目录
    public static final String NIKE_HK_FTP_DOWN_LOCALPATH = "nikeHKLocalDownPath";// 图片文件本地存放路径
    public static final String NIKE_HK_FTP_USERNAME = "nikeHKFtpUserName";// 用户名
    public static final String NIKE_HK_FTP_PASSWORD = "nikeHKFtpPassword";// 密码

    // --------------- 订单类型 ------------//
    // UA官网商城订单
    public static final Integer STORE_ONLINE_UA = 1;
    // UA官网员工订单
    public static final Integer STORE_ONLINE_EMPLOYEE_UA = 4;
    // UA台湾商城订单
    public static final Integer STORE_ONLINE_TW_UA = 2;
    // UA香港商城订单
    public static final Integer STORE_ONLINE_HK_UA = 3;
    // UA香港员工订单
    public static final Integer STORE_ONLINE_HK_EMPLOYEE_UA = 5;
    // UA台湾员工订单
    public static final Integer STORE_ONLINE_TW_EMPLOYEE_UA = 6;
    // 淘宝商城普通订单
    public static final Integer PLATFORM_ONLINE_TB = 50;

    // --------------- 支付方式 ------------//
    // 货到付款
    public static final Integer PAYMENT_COD = 1;
    // 银行电汇
    public static final Integer PAYMENT_BANK = 2;
    // 网银在线
    public static final Integer PAYMENT_PAY_ONLINE = 3;
    // 支付宝
    public static final Integer PAYMENT_ZFB = 6;
    // 贝宝
    public static final Integer PAYMENT_PAYPAL = 999;
    // 国际支付
    public static final Integer PAYMENT_INTERNATIONAL_PAY = 131;

    // --------------- 伊藤忠外部接口映射 ------------//
    // 官网商城COD订单
    public static final Integer ITCHU_STORE_COD = 1;
    // 官网商城普通订单
    public static final Integer ITCHU_STORE_NORMAL_ORDER = 2;
    // 台湾商城普通订单
    public static final Integer ITCHU_STORE_TW = 3;
    // 香港商城普通订单
    public static final Integer ITCHU_STORE_HKUA = 4;
    // 淘宝商城普通订单
    public static final Integer ITCHU_STORE_TB = 5;

    public static final String ITCHU_ORDER_TYPE = "itochuOrderType";

    // --------------- UA店铺 ------------//
    public static final String UA_CHANNEL = "1UA店铺";
    public static final String UA_HK_CHANNEL = "1UA香港店铺";
    public static final String UA_TW_CHANNEL = "1UA台湾店铺";
    public static final String UA_TB_CHANNEL = "1UA天猫店铺";

    // ------------------------MAIL_TEMPLATE-------------------------//
    public static final String MAIL_TEMPLATE_CODE_FALIED_TO_WAREHOUSE = "xxxxxx";
    public static final String MAIL_TEMPLATE_CODE_FALIED_TO_INBOUNDRTN2_EXCPTION = "vvvvvv";
    public static final String MAIL_TEMPLATE_CODE_FALIED_TO_TAOBAO_DELIVERY_EXCPTION = "dddddd";
    public static final String MAIL_TEMPLATE_CODE_FALIED_TO_BS_CREATE_ORDER_EXCPTION = "cccccc";

    // AF库存比对信息
    public static final String AF_VIM_CODE = "ANF";
    public static final String AF_VIM_SOURCE = "af";

    // 延迟到货短信通知
    public static final String WMS_DELIVER_SMS = "wmsDeliverSMS";
    public static final String DEFAULT_SMS_TEMPLATE = "defaultSmsTemplate";
    public static final String RECEIVER = "${receiver}";
    public static final String SHOP_NAME = "${shopName}";
    public static final String ORDER_CODE = "${orderCode}";
    public static final String EXPRESS = "${express}";
    public static final String TRANS_NO = "${transNo}";
    public static final String PLAN_ARRIVE_TIME = "${planArriveTime}";

    // 系统来源,区分所创的单据是由哪个系统指令创建的
    public static final String PACS = "pacs";// 集团pacs
    public static final String WMS = "wms";// wms
    public static final String TOMS = "toms";// 天猫OMS(tmall-oms)
    public static final String GOMS = "goms";// 大陆官网(goms)
    public static final String HKOMS = "hkoms";// 香港oms
    public static final String THREAD_OR_LIMIT_NUM = "system_thread";
    public static final String LF_PICI_NUM = "lf_pici_num";// 设置立峰批次数
    public static final String GUESS_OUTBOUNT_NUM = "guess_outbound_num";// guess出库通知批次数
    public static final String EMS_ISCOD_NUM = "ems_iscod_num";// ems_iscod_num
    public static final String EMS_ISNOTCOD_NUM = "ems_isnotcod_num";// ems_isnotcod_num
    public static final String ORDER_LIMIT = "ORDER_LIMIT";// 查询订单数
    public static final String ORDER_LIMIT_PAC = "ORDER_LIMIT_PAC";// 查询PAC订单数
    public static final String WAREHOUSE_THREAD = "WAREHOUSE_THREAD";// 分仓线程数
    public static final String WAREHOUSE_THREAD_PAC = "WAREHOUSE_THREAD_PAC";// PAC分仓线程数
    public static final String ORDER_THREAD = "ORDER_THREAD";// 设置优先级仓库线程数
    public static final String CREATE_STA_THREAD = "CREATE_STA_THREAD";// 创单线程数
    public static final String NO_CHECK_INV_THREAD = "NO_CHECK_INV_THREAD";// 不校验库存线程数
    public static final String CREATE_STA_THREAD_PAC = "CREATE_STA_THREAD_PAC";// 创单线程数
    public static final String SEND_AD_CHECK_THREAD = "SEND_AD_CHECK_THREAD";// AD确认线程数
    public static final String DO_WHILE_NUM = "DO_WHILE_NUM";// doWhile循环次数
    public static final String OWNER_THREAD = "OWNER_THREAD";// 分店铺线程数
    public static final String OUID_THREAD = "OUID_THREAD";// 分店铺线程数
    public static final String FLAGFORORDER = "FlagForOrder";// 直连计算库存线程数
    public static final String PREFIX_OF_INVOICE_BATCHCODE = "IBC_";
    public static final String SF_WEB_SERVICE_JCUSTID = "JCUST_ID";
    public static final String SF_WEB_SERVICE_CONFIRM_CHECKWORD = "CONFIRM_CHECKWORD";
    public static final String GYM_TMALL = "1200";
    public static final String GYM_SC = "1201";
    public static final String SYS_SOURCE_CODE = "SYS_SOURCE_CODE";
    public static final String INV_TO_OMS_EMAIL = "INV_TO_OMS_EMAIL";
    public static final String GYM_TMALL_EPID = "0C000000-0000-DB10-0000-0001DE1F5F11";
    public static final String GYM_TMALL_FMARKER = "TMALL01";
    public static final String M_RECORD = "RECORD";
    public static final String M_UPDATE = "UPDATE";
    public static final String MONGO_INV_FLAG = "MONGO_INV_FLAG";
    public static final String IS_SINGLE = "IS_SINGLE";
    public static final String MSG_UNLOCKED = "MSG_UNLOCKED";
    public static final String MSG_UNLOCKED_NUM = "MSG_UNLOCKED_NUM";
    public static final String RTN_MSG_NUM = "RTN_MSG_NUM";

    public static final String TEST_NUM = "TEST_NUM";
    // 云栈相关
    public static final String ALI_WAYBILL = "AliWaybill";
    public static final String ALI_WAYBILL_SWITCH_ON = "1";
    public static final String ALI_WAYBILL_SWITCH_OFF = "0";

    public static final String ORDER_PRIORITY_OMS = "priorityOrderOms";// 查询OMS优先级配置
    /************************************ 库位状态 start ***************************************/
    public static final String LOCATION_CODE_NOT_EXIST = "LOCATION_CODE_NOT_EXIST"; // 库位编码不存在

    public static final String LOCATION_LOCKED = "LOCATION_LOCKED"; // 库位被锁定

    public static final String LOCATION_STATUS_INVALID = "LOCATION_STATUS_INVALID"; // 库位状态无效
    /************************************ 库位状态 end *****************************************/


    /************************************ 菜鸟仓SN商品管理 start ***************************************/
    public static final String CAINIAO_SN_INBOUND = "入库"; // 菜鸟仓SN 入库

    public static final String CAINIAO_SN_OUTBOUND = "出库"; // 菜鸟仓SN 出库

    public static final Integer CAINIAO_DIRECTION_INBOUND = 1; // 菜鸟仓SN 事务方向:出库

    public static final Integer CAINIAO_DIRECTION_OUTBOUND = 2; // 菜鸟仓SN 事务方向:出库

    public static final String CHOOSEOPTION_CATEGORY_CODE_CAINIAO_SN = "sn_notice"; // 菜鸟仓出库SNcategoryCode

    public static final String MAIL_TEMPLATE_CODE_CAINIAO_SN = "sn_notice"; // 菜鸟仓出库SN邮件模板编码

    /************************************ 菜鸟仓SN商品管理 end ***************************************/

    /************************************ 菜鸟DB start ***************************************/
    public static final String CAINIAO_DB_SYSTEM_KEY = "cndb";// systemkey
    public static final String CAINIAO_DB_CUSTOMER_CODE = "CNDB";// 客户编码
    public static final String CAINIAO_COMMON_STORE_CFG = "comstorecfg";// 其他可使用菜鸟通用接口的仓库编码配置
    // 菜鸟下发库存类型
    public static final Integer SELLABLE_INVENTORY = 1;// 可销售库存(正品)
    public static final Integer DEFECTIVE_GOODS = 101;// 残次
    public static final Integer DAMAGE_TO_MACHINERY = 102;// 机损
    public static final Integer CASES_OF_LOSS = 103; // 箱损
    public static final Integer ON_ROAD_INVENTORY = 301; // 在途库存(采购入库单下发的库存类型是301)
    public static final Integer FREEZE_INVENTORY = 201;// 冻结库存

    /************************************ 菜鸟DB END ***************************************/

    /************************************ 韩国CJ start ***************************************/
    public static final String CJ_SYSTEM_KEY = "CJ";// systemkey
    public static final String CJ_CNJH = "CNJH";// 菜鸟集物流商编码
    public static final String CJ_FIRSTLOGISTICS = "firstLogistics";// 第1段物流承运商
    public static final String CJ_FIRSTWAYBILLNO = "first_waybillno";// 第1段物流运单号
    public static final String CJ_OLIVEYOUNG_RESOURCEID = "oliveyoungResourceId";// 物流资源ID
    public static final String CJ_OLIVEYOUNG_STORE_CODE = "oliveyoungStoreCode"; // 仓库编码
    public static final String CJ_SYSTEM_KEY_HUB = "CJ_SystemKey";// 调用hub接口key
    public static final String CJ_RESOURCE_ID = "CJ_ResourceId";// 菜鸟物流资源ID
    public static final String CJ_STORE_CODE = "CJ_StoreCode";// 菜鸟发货仓编码
    /************************************ 韩国CJ END ***************************************/

    /************************************ Gucci start ***************************************/
    public static final String GUCCI_BRAND_VMI_CODE = "gucci";// BrandVmiCode
    public static final String GUCCI_SYSTEM_KEY = "gucci";// systemkey
    // public static final String GUCCI_STATUS_GOOD = "GG";// gucci反馈良品代码
    public static final String GUCCI_DAMAGE = "DG";// gucci反馈残次品代码
    public static final String GUCCI_LOCATION = "location";// 库存快照库位
    public static final String JDA_WAREHOURSE_CODE = "jdaWarehouseCode";// 库存快照仓库编码
    public static final String GUCCI_BRAND_CODE = "brandCode";// 库存快照品牌编码
    public static final String GUCCI_INSTRUCTION_NO_SKU = "ASN_Gucci_No_Sku";// 入库指令中缺失商品
    public static final String GUCCI_DAMAGE_TO_LOCATION = "Gucci_Damage_To_Location";// gucci退残次的toLocation配置
    /************************************ Gucci END ***************************************/

    // 配货时校验所选店铺是够存在不同装箱清单模板的开关
    public static final String JOIN_PICKINGLIST = "joinPickList";
    public static final String IS_SAME_TEMPLATE = "template";

    // 触发器移除 trg_update_lpcode_is_null 开关
    public static final String UPDATE_DELIVERY_LPCODE = "updateLpcode";
    public static final String UPDATE_LPCODE_IS_OPEN = "isOpen";

    // 触发器移除TRI_T_WH_MSG_OUTBOUND 开关
    public static final String UPDATE_MSG_OUTBOUND_ORDERCODE = "updateMsgOutBoundOrderCode";
    public static final String MSG_OUTBOUND_ORDERCODE_IS_OPEN = "isOpen";

    // 库存状态调整-过期时间格式优化- 开关
    public static final String INV_STATUS_CHANGE_EXP_DATE = "invStatusChangeExpDate";
    public static final String DATE_ADJUST = "isOpen";

    // 通用退大仓合并模式配置
    public static final String VMI_RT_TO_HUB = "vmiRtToHub";
    public static final String VMI_RT_VMI_CODES = "vmiCodes";

    // wms数据导出线程数配置
    public static final String SN_VALIDDATESKU_SHOP_THREAD_NO = "snValidDateSkuShopThreadNo";// 设置拉取店铺数据的线程数
    public static final String Packaging_Sn_ExpDate_THREAD_NO = "packagingSnExpDateThreadNo";// 设置拉取sn和过期时间的线程数
    public static final String IMPORT_BRAND_UNFINISHED_INS = "importUnFinishedInsThreadNo";// 设置拉取品牌未完成指令的线程数
    // 文件生成标识 0未生成 1生成成功
    public static final String WMS_DATA_IMPORT_GROUP = "wmsDataImportGroup";
    public static final String WMS_DATA_IMPORT_SN_EXPDATE = "snAndExpDateFlow";
    public static final String WMS_DATA_BRAND_UNFINISHED_INS = "brandUnFinishedInstructions";
    public static final String WMS_SHARE_ROOT_DIR = "wmsShareRootDir";

    // wms3.0库存流水推送上位系统转移过期数据时间配置
    public static final String SKU_INV_FLOW = "skuInvFlow";
    public static final String INV_FLOW_EXP_DATE = "expDate";// 以天为单位 提前一天 -1 以此类推
    public static final String SKU_INV_FLOW_NUM = "skuInvFlowNum";// MQ批次推送数量
    public static final String SKU_OCCIED_AND_RELEASE = "skuOcciedAndRelease";
    public static final String SKU_OCCIED_AND_RELEASE_NUM = "skuOcciedAndReleaseNum";// MQ批次推送数量
    public static final String PUSH_IM_FAIID_COUNT = "pushToIMErrorCount";// 推送给im流水或占用失败的数据


    // 页面查询多线程优化
    public static final String MORE_THREAD_QUERY = "moreThreadQuery";
    public static final String MORE_THREAD_QUERY_INV_QTY = "moreThreadQueryInvQty";// 库存数量查询线程数
    public static final String MORE_THREAD_EXPORT_QTY = "moreThreadExportQty";// 库存数量查询线程数

    // 星巴克星享卡key
    public static final String STARBUCKS = "Starbucks";
    public static final String STARBUCKS_AES_KEY = "StarbucksAESKey";

    // 利丰外包仓source 预警邮件配置
    public static final String VIM_IDS_SEND_FAILED_SET = "idsSendFailedSources";// 需要发送预警邮件的source配置
    public static final String VIM_IDS_SEND_FAILED_TIMES_SET = "idsSendFailedTimes";// 推送失败预警时间配置
    public static final String VIM_IDS_SEND_FAILED_RECEIVER = "idsSendFailedReceiver";// 推送失败预警收件人配置

    // nike退大仓推送利丰外包仓source配置
    public static final String IDS_VMI_RTO_LF_SOURCES = "idsVmiRtoToLFSources";// 推送失败预警收件人配置

    
    public static final String AGV_SKU_TO_HUB = "agvSkuToHub";
    // 斯凯奇等走hub4.0店铺配置
    public static final String WMS_ADAPTER_OWNER_CONFIG = "wmsAdapterOwnerConfig";

    public static final String WMS_ADAPTER_SYSTEM_KEY_CONFIG = "wmsAdapterSystemKeyConfig";


}
