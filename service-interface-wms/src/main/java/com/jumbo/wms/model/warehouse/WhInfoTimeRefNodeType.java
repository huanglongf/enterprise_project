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

package com.jumbo.wms.model.warehouse;


/**
 * 订单触发时间 节点类型
 * 
 * @author xiaolong.fei
 * 
 */
public enum WhInfoTimeRefNodeType {
    CREATE(1), // 新建
    OCCUPIED(2), // 配货
    CHECKED(3), // 核对
    INTRANSIT(4), // 出库（已转出）
    FINISHED(5), // 已完成
    CANCEL_UNDO(6), // 取消未处理
    CANCELED(7), // 取消已处理
    PRINT_PICKING_LIST(8), // 首次打印装箱清单
    PRINT_TRANK(9), // 打印物流面单
    PRING_PICKING1(10), // 打印拣货单1
    PRING_PICKING2(11), // 打印拣货单2
    BEGIN_PRING(12), // 未开始拣货
    END_PRING(13), // 正在拣货
    PRINGING(14), // 拣货完成
    PARTLY_RETURNED(15), // 部分转入
    PRING_PICKING3(16), // 打印拣货单3
    FAILED(20), // 配货失败

    RECEIVE(30), // 收货数据导入
    CONFIRM_RECEIVE(31), // 确认收货
    CHECK_RECEIVE(33), // 收货审核（上架前审核）
    SHELVES(34), // 上架
    CLOSE(35); // 关闭（针对部分出/入）
    private int value;

    private WhInfoTimeRefNodeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WhInfoTimeRefNodeType valueOf(int value) {
        switch (value) {
            case 1:
                return CREATE;
            case 2:
                return OCCUPIED;
            case 3:
                return CHECKED;
            case 4:
                return INTRANSIT;
            case 5:
                return FINISHED;
            case 6:
                return CANCEL_UNDO;
            case 7:
                return CANCELED;
            case 8:
                return PRINT_PICKING_LIST;
            case 9:
                return PRINT_TRANK;
            case 10:
                return PRING_PICKING1;
            case 11:
                return PRING_PICKING2;
            case 12:
                return BEGIN_PRING;
            case 13:
                return END_PRING;
            case 14:
                return PRINGING;
            case 15:
                return PARTLY_RETURNED;
            case 16:
                return PRING_PICKING3;
            case 20:
                return FAILED;
            case 30:
                return RECEIVE;
            case 31:
                return CONFIRM_RECEIVE;
            case 33:
                return CHECK_RECEIVE;
            case 34:
                return SHELVES;
            case 35:
                return CLOSE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
