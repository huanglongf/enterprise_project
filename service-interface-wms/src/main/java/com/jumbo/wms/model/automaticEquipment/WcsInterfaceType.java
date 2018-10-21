package com.jumbo.wms.model.automaticEquipment;

/**
 * @author jinlong.ke
 * @date 2016年6月7日上午10:39:34
 * 
 */
public enum WcsInterfaceType {
    SShouRongQi(1), // 收货入库货箱流向
    SQuxiaoRongQi(2), // 货箱流向取消接口
    SBoZhong(3), // 播种接口
    OShouRongQi(4), // 出库货箱流向
    OQuxiaoRongQi(5), // 货箱流向取消
    SjiHuo(6), // 快递集货
    OQuxiaoBoZhong(7), // 播种取消
    HXQX(11), // 货箱流向取消回复结果
    HXDDRZ(12), // 货箱到达信息日志通知
    HXDDQR(13), // 箱体到达指定位置确认
    BZQXQR(14), // 播种任务取消结果确认
    BZZXQR(15), // 播种任务执行结果确认
    DDPH(16), // 订单铺货货位匹配关系
    BZZXMX(17), // 播种任务执行明细
    SBZTHC(18), // 设备状态回传
    JHRZHC(19);// 集货日志回传
    private int value;

    private WcsInterfaceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WcsInterfaceType valueOf(int value) {
        switch (value) {
            case 1:
                return SShouRongQi;
            case 2:
                return SQuxiaoRongQi;
            case 3:
                return SBoZhong;
            case 4:
                return OShouRongQi;
            case 5:
                return OQuxiaoRongQi;
            case 6:
                return SjiHuo;
            case 7:
                return OQuxiaoBoZhong;
            case 11:
                return HXQX;
            case 12:
                return HXDDRZ;
            case 13:
                return HXDDQR;
            case 14:
                return BZQXQR;
            case 15:
                return BZZXQR;
            case 16:
                return DDPH;
            case 17:
                return BZZXMX;
            case 18:
                return SBZTHC;
            case 19:
                return JHRZHC;
            default:
                return null;
        }
    }
}
