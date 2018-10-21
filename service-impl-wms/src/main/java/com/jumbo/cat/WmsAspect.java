package com.jumbo.cat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.baozun.scm.baseservice.cat.aop.CatServiceAspect;

@Aspect
public class WmsAspect extends CatServiceAspect
{
	@Around(" execution(* com.jumbo.pms.manager..*.*(..))" 
			+ " || execution(* com.jumbo.rmiservice..*.*(..))" 
			+ " || execution(* com.jumbo.service..*.*(..))"
			+ " || execution(* com.jumbo.sysMonitor..*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.af.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.archive.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.authorization.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.automaticEquipment.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.baseinfo.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.boc.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.channel.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.check.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.checklist.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.compensation.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.expressDelivery..*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.ftp.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.hub2wms.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.lf.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.listener.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.lmis.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.mongodb.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.mq.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.oms.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.omsService.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.operationcenter.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.outbound.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.outwh.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.pda.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.pickZone.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.print.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.report.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.sku.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.sn.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.system.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.trans.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.vmi.*.*(..))"
			+ " || execution(* com.jumbo.wms.manager.warehouse.*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable
	{
		return super.doAround(pjp);
	}
}
