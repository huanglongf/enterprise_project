package com.bt.quartz;

public interface QuartzService {
 //1.仓储费结算
	public void StorageChargeSettlement();
//2.操作费结算
	public void OperatingSummary();
//3.耗材费结算
	public void ConsumableCost();
//4.打包费结算
	public void PackingChargeSettlement();
//5.增值服务费结算
	public void VBASettlement();
//6.（快递/客户店铺使用快递）明细结算
	public void ExpressBalance();
 //7.客户/店铺结算汇总
	public void ConsumerSummary();
//8.快递结算汇总
	public void ExpressBalanceSummary();
//9.物流结算
	public void transSettleTask();
//10.物流结算汇总
	public void transPoolTask();
//11.客户运费结算汇总
	public void customerPoolTask();
//12.店铺运费结算汇总
	public void storePoolTask();
//13.结算-耗材费-实际使用量
	public void JkUseMaterialTask();
//14.结算-操作费接口
	public void JkOperatorTask();
//15.结算-耗材费-采购明细
	public void JkInvitationTask();
//16.结算-快递订单接口
	public void JkOrderDataTask();
//17.结算-仓储费
	public void JkStorageTask();
//18.客户运费结算
	public void customerSettle();
//19.顺丰路由查询接口
	public void sfRountQuery();
//20.下载SFTP文件
	public void SFTPDownLoad();
//21.数据初始化
	public void init_quoted();
//22.运单初始化
	public void init_order();
//23.地址匹配
	public void cultor_quoted();
//24.快递雷达转换
	public void erOrderPro();
//25.圆通路由查询接口
	public void ytoStart();
//26.ems路由查询接口
	public void emsStart();
//27.路由接口转换
	public void routeInfoExchange();
//28.计算节点时效时间
	public void CalDateStart();
//29.预警
	public void WarningCalFormula();
//30.预警匹配
	public void pushRouteTime();
//31.计算节点时效时间
	public void CalDateStartOther();
//32.生产计算主表
	public void praseCalFormula();
//33.事件预警升级
	public void EventWarningLevelUp();
//34.申通路由查询接口
	public void stRountQuery();
//35.生产计算表
	public void parseCalMaster();
//37.生成时效临时表
	public void parseReceiveTime_temp();
//38.调延时运单
	public void getRouteForInterance();
//39.超时预警
	public void AlarmRoute();
//40.超时预警
	public void AlarmRouteWith();
//41.数据核查
	public void jkDataCheck();
//42.工单生成
	public void wk_radar();
//43.工单升级
	public void wokOrderLevelUp();
//44.调延时运单SF
	public void getRouteForInteranceSF();
//45.调延时运单STO
	public void getRouteForInteranceSTO();
//46.调延时运单YTO
	public void getRouteForInteranceYTO();
//47.调延时运单EMS
	public void getRouteForInteranceEMSf();
//48.事件预警生成
	public void EventWarningGen();
//49.工单升级时效生成
	public void wokOrderCalDateDen();
//50.签收时间
	public void receivetimeParse();
//51.揽件时间
	public void embrancetimeParse();
//52.邮件推送
	public void JkWarningTask();
//53.结算邮件预警
	public void JkSettleWarningTask();
//54.索赔运单自动处理
	public void ClaimWorkOrderAutoProcess();
//55.结算退货入库
	public void JkReturnOrderTask();
}
