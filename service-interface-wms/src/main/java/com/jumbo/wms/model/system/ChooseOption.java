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

package com.jumbo.wms.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 系统常量表，用于记录常量列表或者系统常量设置，举例如下： 对于常量列表 布尔值， 可以如下设置： 常量集编码 常量集名称 常量键 常量值 bool 布尔值 true 真 bool 布尔值
 * false 假
 * 
 * 对于系统常量，如每页数据记录数，可以如下设置 常量集编码 常量集名称 常量键 常量值 system 系统常量 ITEM_PER_PAGE 50 其中
 * 系统常量[system]是系统预定义的常量集编码和名称
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_SYS_CHOOSE_OPTION")
public class ChooseOption extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8939064652943346455L;

    public static final String CATEGORY_CODE_SYSTEM = "system";
    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_TEST_OUTBOUND = "test_outbound";

    public static final String CATEGORY_CODE_PDA_MACHINE_CODE = "pdaMachineCode";

    public static final String MQ_SEND_MSG_SIZE = "MQ_SEND_MSG_SIZE";// MQ消息每条消息最大传送数

    public static final String CATEGORY_CODE_SYSTEM_THREAD = "system_thread";// 系统线程配置
    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_SF_TRANS_NO_WH = "pool_qty_get_sf_trans_no_wh";// 顺丰获取运单号单仓线程数
    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_PAC = "pool_qty_outbound_notice_pac";
    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_OMS = "pool_qty_outbound_notice_oms";
    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_FINISH_OMS = "pool_qty_outbound_notice_finish_oms";// 直连订单创单线程数
    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_RTN_MQ = "pool_qty_outbound_rtn_mq";// 外包仓出库反馈执行失败或丢mq失败补偿线程数

    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_PACS_TOMS_OUTBOUND = "pool_qty_pacs_toms_outbound";// pacs直连转toms出库

    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_ZHAN_YONG = "pool_qty_zhan_yong";// 占用丢MQ

    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_PACS_MQ_5 = "pool_qty_pacs_5";// pacs5



    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_COMMON_ERROR_MQ = "pool_qty_common_error_mq";// 通用补偿线程数


    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_WCS = "pool_qty_wcs"; // 自动化线程数
    public static final String CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_EXPRESS_TRANS_NO = "pool_qty_get_express_trans_no"; // 普通快递获取运单号线程数

    // 库存初始化非空校验
    public static final String KEY_INITIALIZE_INVENTORY_NULL_CHECK = "INITIALIZE_INVENTORY_NULL_CHECK";
    public static final String KEY_INITIALIZE_INVENTORY_NULL_CHECK_TRUE = "1";
    public static final String KEY_INITIALIZE_INVENTORY_NULL_CHECK_FALSE = "0";

    public static final String KEY_SMS_START_TIME = "SMS_START_TIME";
    public static final String KEY_SMS_END_TIME = "SMS_END_TIME";

    // 盘点导入处理差异行模式切换，excel数据行大小
    public static final String KEY_INVENTORY_CHECK_MARGEN_MODE_DATA_SIZE = "INVENTORY_CHECK_MARGEN_MODE_DATA_SIZE";

    // 订单核算运费计算模式
    public static final String CATEGORY_CODE_SO_COUNT_TRANS_FEE_TYPE = "soCountTransFeeType";
    // 订单核算操作费计算模式
    public static final String CATEGORY_CODE_SO_COUNT_OPERATING_FEE_TYPE = "soCountOperatingFeeType";
    // 订单核算包装费计算模式
    public static final String CATEGORY_CODE_SO_COUNT_PACKING_FEE_TYPE = "soCountPackingFeeType";
    // 订单核算仓储费计算模式
    public static final String CATEGORY_CODE_SO_COUNT_WAREHOUSE_FEE_TYPE = "soCountWarehouseFeeType";
    // 订单核算店铺人员费计算模式
    public static final String CATEGORY_CODE_SO_COUNT_PERSONNEL_FEE_TYPE = "soCountPersonnelFeeType";
    // coach需要保修卡的商品department列表
    public static final String CATEGORY_CODE_COACH_NEEDED_WARRANTY_DEPARTMENT = "coachProductWarrantyDept";

    public static final String CATEGORY_CODE_ADMIN_PASSWORD = "adminPassWord";

    public static final String WX_OUT_CODE_TYPE = "wxOutoutBoundCodeType";
    // 外包仓商品对接配置
    public static final String THREEPL_SKU_CONFIG = "threePlSkuConfig";
    // 二级批次
    public static final String LOCATION_SORT_BATCHNUM = "batchNum";

    /**
     * PK
     */
    private Long id;

    /**
     * 常量集编码
     */
    private String categoryCode;

    /**
     * 常量集名称
     */
    private String categoryName;

    /**
     * 所属组名，用于区分此常量是否可以通过系统功能维护，以及维护时的分组情况 空值代表此常量是系统初始化的常量，不能通过系统功能维护
     */
    private String packageName;

    /**
     * 排序号
     */
    private int sortNo;

    /**
     * 常量键，如果是用作常量列表，则里面存储的是常量的真实值；如果是用作系统常量，则里面存储的是系统常量的键名称
     */
    private String optionKey;

    /**
     * 常量值，如果是用作常量列表，则里面存储的是常量的显示值；如果是用作系统常量，则里面存储的是系统常量的值
     */
    private String optionValue;

    /**
     * 是否可用
     */
    private Boolean isAvailable = true;

    /**
     * 选项组可用性
     */
    private Boolean categoryAvailable;

    /**
     * 选项组描述
     */
    private String optionDescription;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SCC", sequenceName = "S_T_SYS_CHOOSE_OPTION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCC")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CATEGORY_CODE", length = 50)
    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Column(name = "CATEGORY_NAME", length = 50)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Column(name = "PACKAGE_NAME", length = 50)
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Column(name = "SORT_NO")
    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    @Column(name = "OPTION_KEY", length = 100)
    public String getOptionKey() {
        return optionKey;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    @Column(name = "OPTION_VALUE", length = 255)
    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    @Column(name = "IS_AVAILABLE")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Column(name = "CATEGORY_AVAILABLE")
    public Boolean getCategoryAvailable() {
        return categoryAvailable;
    }

    public void setCategoryAvailable(Boolean categoryAvailable) {
        this.categoryAvailable = categoryAvailable;
    }

    @Column(name = "OPTION_DESCRIPTION", length = 500)
    public String getOptionDescription() {
        return optionDescription;
    }

    public void setOptionDescription(String optionDescription) {
        this.optionDescription = optionDescription;
    }


}
