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
package com.jumbo.web.action.baseinfo;

import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.warehouse.ProductThreeDimensionalCommand;

/**
 * 接口/类说明：商品三维数据采集
 * 
 * @ClassName: ProductThreeDimensionalCollectAction
 * @author LuYingMing
 * @date 2016年6月7日 下午5:48:34
 */
public class ProductThreeDimensionalCollectAction extends
		BaseJQGridProfileAction {

	private static final long serialVersionUID = 6726421432036145427L;


    @Autowired
    private WareHouseManager wareHouseManager;

    private ProductThreeDimensionalCommand product;

    private String ids;

	/**
	 * 方法说明：跳转至数据三维采集页面
	 * 
	 * @author LuYingMing
	 * @date 2016年6月7日 下午5:51:37
	 * @return
	 */
	public String execute() {
        return SUCCESS;
	}


    /**
     * 方法说明：根据条码查询
     * 
     * @author LuYingMing
     * @date 2016年6月17日 下午3:41:54
     * @return
     * @throws JSONException
     */
    public String findThreeDimensionalData() throws JSONException {
        List<ProductThreeDimensionalCommand> threeDimensionalList = null;
        JSONObject result = new JSONObject();
        try {
            if (StringUtil.isEmpty(product.getBarCode()) && StringUtil.isEmpty(product.getIsSupplierCode())) {
                result.put("msg", ERROR);
            }
            threeDimensionalList = wareHouseManager.findProductByBarCodeWithCondition(product.getBarCode(), product.getIsSupplierCode());
            if (null != threeDimensionalList && !threeDimensionalList.isEmpty()) {
                if (threeDimensionalList.size() == 1) {
                    // 如果只有一条数据,就直接更新保存; 不然显示到页面让客户选择操作
                    Long skuId = threeDimensionalList.get(0).getId();
                    User user = userDetails.getUser();
                    if (null != product.getGrossWeight() && null != product.getLength() && null != product.getWidth() && null != product.getHeight()) {
                        if (null != skuId && null != user) {
                            wareHouseManager.updateThreeDimensionalData(skuId, product, user);
                            result.put("msg", SUCCESS);
                        } else {
                            result.put("msg", ERROR);
                        }
                    } else {
                        result.put("msg", ERROR);
                    }
                } else {
                    result.put("msg", "multiple");
                }
            } else {
                log.info("查询结果为空~~~~~~~~~~~~~~~~~~");
                result.put("msg", NONE);
            }
        } catch (Exception e) {
            log.error(e.getMessage() + "~~~~~~~~~~~~~" + e.getCause());
            result.put("msg", ERROR);

        }

        request.put(JSON, result);
        return JSON;
    }



    /**
     * 方法说明：封装表格数据
     * 
     * @author LuYingMing
     * @date 2016年6月17日 下午3:41:44
     * @return
     */
    public String findTableByData() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findProductForThreeDimensionalData(tableConfig.getStart(), tableConfig.getPageSize(), product, tableConfig.getSorts())));
        return JSON;
    }


    /**
     * 方法说明：更新三维数据
     * 
     * @author LuYingMing
     * @date 2016年6月23日 下午1:18:08
     * @return
     * @throws JSONException
     */
    public String refreshData() throws JSONException {
        String[] idArray = ids.split(",");
        JSONObject result = new JSONObject();
        if (null != idArray && idArray.length > 0) {
            User user = userDetails.getUser();
            if (null != user) {
                if (null != product.getGrossWeight() && null != product.getLength() && null != product.getWidth() && null != product.getHeight()) {
                    for (int i = 0; i < idArray.length; i++) {
                        Long skuId = Long.parseLong(idArray[i]);
                        wareHouseManager.updateThreeDimensionalData(skuId, product, user);
                    }
                    result.put("msg", SUCCESS);
                } else {
                    result.put("msg", ERROR);
                }
            } else {
                result.put("msg", ERROR);
            }
        } else {
            result.put("msg", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public ProductThreeDimensionalCommand getProduct() {
        return product;
    }


    public void setProduct(ProductThreeDimensionalCommand product) {
        this.product = product;
    }


    public String getIds() {
        return ids;
    }


    public void setIds(String ids) {
        this.ids = ids;
    }


}
