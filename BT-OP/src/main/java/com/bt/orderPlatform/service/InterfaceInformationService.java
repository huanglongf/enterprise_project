package com.bt.orderPlatform.service;

import com.bt.orderPlatform.model.InterfaceInformation;

public interface InterfaceInformationService<T>  {

	InterfaceInformation selectByCustid(String custid);


}
