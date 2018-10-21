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
package com.jumbo.wms.manager.warehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.ecp.ip.util.XmlUtil;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.hub4.WmsOrderServiceToHub4Manager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.wmsInterface.WmsOutBound;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundDeliveryInfo;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundLine;

import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * 
 * @author hui.li
 * 
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class WmsOrderServiceToHub4ManagerTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private WmsOrderServiceToHub4Manager wmsOrderServiceToHub4Manager;

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;

    /**
     * 部分出库 单元测试 （商品A 库存数量0）
     * 
     * @throws Exception
     */
    @Test
    public void createOrderOutboundTest01() throws Exception {
        // 初始化库存 skuId=7017751
        Inventory inv = inventoryDao.getByPrimaryKey(13134705L);
        Inventory i = new Inventory();
        BeanUtils.copyProperties(inv, i);
        i.setId(null);
        i.setIsOccupied(false);
        i.setOccupationCode(null);
        i.setOwner("调拨测试店铺B");
        i.setQuantity(0L);
        OperationUnit ou=new OperationUnit();
        ou.setId(121L);
        i.setOu(ou);
        WarehouseLocation wl = new WarehouseLocation();
        wl.setId(818926L);
        i.setLocation(wl);
        i.setDistrict(new WarehouseDistrict(3653L));
        inventoryDao.save(i);

        //设置店铺是否允许部分占用
        BiChannel bc=biChannelDao.getByCode("调拨测试店铺B");
        bc.setIsPartOutbound(true);
        biChannelDao.save(bc);
        
        inventoryDao.flush();
        
        // 生成单据并占有
        String msg = "<OutboundOrders>\r\n" + "    <sourceMarkCode>routeWms</sourceMarkCode>\r\n" + "    <OutboundOrder>\r\n" + "        <OutboundNo>TF330000000100112_1</OutboundNo>\r\n" + "        <PlatformCode>TF330000000100112_1</PlatformCode>\r\n"
                + "        <CustomerCode>调拨测试店铺B</CustomerCode>\r\n" + "        <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "        <OrderType>1</OrderType>\r\n" + "        <DataSource>inner-flow</DataSource>\r\n" + "        <WhCode>SHWH10</WhCode>\r\n"
                + "        <Receiver>\r\n" + "            <ReceiverName>JESSICA</ReceiverName>\r\n" + "            <ReceiverPhone>82131234</ReceiverPhone>\r\n" + "            <ReceiverMobile>18616362606</ReceiverMobile>\r\n"
                + "            <Postcode>200000</Postcode>\r\n" + "            <Country>中国</Country>\r\n" + "            <Province>上海</Province>\r\n" + "            <City>上海市</City>\r\n" + "            <District>静安区</District>\r\n"
                + "            <Street>彭浦</Street>\r\n" + "            <Address>1188号</Address>\r\n" + "            <Lpcode>EMS</Lpcode>\r\n" + "        </Receiver>\r\n" + "        <OutboundLines>\r\n" + "            <OutboundLine>\r\n"
                + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241343464</Upc>\r\n" + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n"
                + "            </OutboundLine>\r\n" + "        </OutboundLines>\r\n" + "    </OutboundOrder>\r\n" + "</OutboundOrders>";
        List<WmsOutBound> outBounds = outBounds(msg);
        // 调用wms3.0出库单接口
        WmsResponse wr = wmsOrderServiceToHub4Manager.createOrderOutbound(outBounds);

        // 校验数据是否正确
        assertEquals(WmsResponse.STATUS_ERROR, wr.getStatus());
        
        // 清除库存数据，设置店铺配置
        List<Inventory> invList = inventoryDao.findSkuLocationOuIdOwner(881L, 8842028L, 7017751L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
        inventoryDao.deleteAll(invList);
        
        bc.setIsPartOutbound(false);
        biChannelDao.save(bc);
    }

    /**
     * 部分出库 单元测试 （商品A 库存数量少）
     * 
     * @throws Exception
     */
    @Test
    public void createOrderOutboundTest02() throws Exception {
        // 初始化库存 skuId=7017751
        Inventory inv = inventoryDao.getByPrimaryKey(13134705L);
        Inventory i = new Inventory();
        BeanUtils.copyProperties(inv, i);
        i.setId(null);
        i.setIsOccupied(false);
        i.setOccupationCode(null);
        i.setOwner("调拨测试店铺B");
        i.setQuantity(10L);
        OperationUnit ou = new OperationUnit();
        ou.setId(121L);
        i.setOu(ou);
        WarehouseLocation wl = new WarehouseLocation();
        wl.setId(818926L);
        i.setLocation(wl);
        i.setDistrict(new WarehouseDistrict(3653L));
        inventoryDao.save(i);

        // 设置店铺是否允许部分占用
        BiChannel bc = biChannelDao.getByCode("调拨测试店铺B");
        bc.setIsPartOutbound(true);
        biChannelDao.save(bc);

        inventoryDao.flush();

        // 生成单据并占有
        String msg = "<OutboundOrders>\r\n" + "    <sourceMarkCode>routeWms</sourceMarkCode>\r\n" + "    <OutboundOrder>\r\n" + "        <OutboundNo>TF330000000100112_2</OutboundNo>\r\n" + "        <PlatformCode>TF330000000100112_2</PlatformCode>\r\n"
                + "        <CustomerCode>调拨测试店铺B</CustomerCode>\r\n" + "        <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "        <OrderType>1</OrderType>\r\n" + "        <DataSource>inner-flow</DataSource>\r\n" + "        <WhCode>SHWH10</WhCode>\r\n"
                + "        <Receiver>\r\n" + "            <ReceiverName>JESSICA</ReceiverName>\r\n" + "            <ReceiverPhone>82131234</ReceiverPhone>\r\n" + "            <ReceiverMobile>18616362606</ReceiverMobile>\r\n"
                + "            <Postcode>200000</Postcode>\r\n" + "            <Country>中国</Country>\r\n" + "            <Province>上海</Province>\r\n" + "            <City>上海市</City>\r\n" + "            <District>静安区</District>\r\n"
                + "            <Street>彭浦</Street>\r\n" + "            <Address>1188号</Address>\r\n" + "            <Lpcode>EMS</Lpcode>\r\n" + "        </Receiver>\r\n" + "        <OutboundLines>\r\n" + "            <OutboundLine>\r\n"
                + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241343464</Upc>\r\n" + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n"
                + "            </OutboundLine>\r\n" + "        </OutboundLines>\r\n" + "    </OutboundOrder>\r\n" + "</OutboundOrders>";
        List<WmsOutBound> outBounds = outBounds(msg);
        // 调用wms3.0出库单接口
        WmsResponse wr = wmsOrderServiceToHub4Manager.createOrderOutbound(outBounds);

        // 校验数据是否正确
        assertEquals(WmsResponse.STATUS_SUCCESS, wr.getStatus());
        List<StockTransApplication> staList = staDao.findBySlipCode("TF330000000100112_2");
        assertNotNull(staList);
        assertNotNull(staList.get(0));
        List<Inventory> ocpinvList = inventoryDao.findByOccupiedCode(staList.get(0).getCode());
        assertNotNull(ocpinvList);
        Map<Long, Long> ocpQty = mergerOcpInvQty(ocpinvList);

        assertEquals(ocpQty.get(7017751L).longValue(), 10L);
        StockTransVoucher stv = stvDao.findStvByStaId(staList.get(0).getId());
        assertNotNull(stv);
        List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId());
        assertNotNull(stvLines);
        Map<Long, Long> skuQty = mergerStvQty(stvLines);
        assertEquals(skuQty.get(7017751L).longValue(), ocpQty.get(7017751L).longValue());

        // 清除库存数据，设置店铺配置
        List<Inventory> invList = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017751L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
        inventoryDao.deleteAll(invList);

        bc.setIsPartOutbound(false);
        biChannelDao.save(bc);
    }

    /**
     * 部分出库 单元测试 （商品A 库存数量0，商品B 库存满足）
     * 
     * @throws Exception
     */
    @Test
    public void createOrderOutboundTest03() throws Exception {
        // 设置店铺是否允许部分占用
        BiChannel bc = biChannelDao.getByCode("调拨测试店铺B");
        bc.setIsPartOutbound(true);
        biChannelDao.save(bc);
        try {

            // 初始化库存 skuId=7017751，skuId=7017756
            Inventory inv = inventoryDao.getByPrimaryKey(13134705L);
            Inventory i = new Inventory();
            BeanUtils.copyProperties(inv, i);
            i.setId(null);
            i.setIsOccupied(false);
            i.setOccupationCode(null);
            i.setOwner("调拨测试店铺B");
            i.setQuantity(0L);
            OperationUnit ou = new OperationUnit();
            ou.setId(121L);
            i.setOu(ou);
            WarehouseLocation wl = new WarehouseLocation();
            wl.setId(818926L);
            i.setLocation(wl);
            i.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i);

            Inventory inv2 = inventoryDao.getByPrimaryKey(13134747L);
            Inventory i2 = new Inventory();
            BeanUtils.copyProperties(inv2, i2);
            i2.setId(null);
            i2.setIsOccupied(false);
            i2.setOccupationCode(null);
            i2.setOwner("调拨测试店铺B");
            i2.setQuantity(50L);
            i2.setOu(ou);
            i2.setLocation(wl);
            i2.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i2);


            inventoryDao.flush();

            // 生成单据并占有
            String msg = "<OutboundOrders>\r\n" + "    <sourceMarkCode>routeWms</sourceMarkCode>\r\n" + "    <OutboundOrder>\r\n" + "        <OutboundNo>TF330000000100112_3_3</OutboundNo>\r\n"
                    + "        <PlatformCode>TF330000000100112_3_3</PlatformCode>\r\n" + "        <CustomerCode>调拨测试店铺B</CustomerCode>\r\n" + "        <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "        <OrderType>1</OrderType>\r\n"
                    + "        <DataSource>inner-flow</DataSource>\r\n" + "        <WhCode>SHWH10</WhCode>\r\n" + "        <Receiver>\r\n" + "            <ReceiverName>JESSICA</ReceiverName>\r\n" + "            <ReceiverPhone>82131234</ReceiverPhone>\r\n"
                    + "            <ReceiverMobile>18616362606</ReceiverMobile>\r\n" + "            <Postcode>200000</Postcode>\r\n" + "            <Country>中国</Country>\r\n" + "            <Province>上海</Province>\r\n" + "            <City>上海市</City>\r\n"
                    + "            <District>静安区</District>\r\n" + "            <Street>彭浦</Street>\r\n" + "            <Address>1188号</Address>\r\n" + "            <Lpcode>EMS</Lpcode>\r\n" + "        </Receiver>\r\n" + "        <OutboundLines>\r\n"
                    + "            <OutboundLine>\r\n" + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241343464</Upc>\r\n" + "                <Qty>50</Qty>\r\n"
                    + "                <InvStatus>5</InvStatus>\r\n" + "            </OutboundLine>\r\n" + "            <OutboundLine>\r\n" + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n"
                    + "                <Upc>241221820</Upc>\r\n" + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n" + "            </OutboundLine>\r\n" + "        </OutboundLines>\r\n" + "    </OutboundOrder>\r\n"
                    + "</OutboundOrders>";
            List<WmsOutBound> outBounds = outBounds(msg);
            // 调用wms3.0出库单接口
            WmsResponse wr = wmsOrderServiceToHub4Manager.createOrderOutbound(outBounds);

            // 校验数据是否正确
            assertEquals(WmsResponse.STATUS_SUCCESS, wr.getStatus());
            List<StockTransApplication> staList = staDao.findBySlipCode("TF330000000100112_3_3");
            assertNotNull(staList);
            assertNotNull(staList.get(0));
            List<Inventory> ocpinvList = inventoryDao.findByOccupiedCode(staList.get(0).getCode());
            assertNotNull(ocpinvList);
            Map<Long, Long> ocpQty = mergerOcpInvQty(ocpinvList);

            assertNull(ocpQty.get(7017751L));
            assertEquals(ocpQty.get(7017756L).longValue(), 50L);
            StockTransVoucher stv = stvDao.findStvByStaId(staList.get(0).getId());
            assertNotNull(stv);
            List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId());
            assertNotNull(stvLines);
            Map<Long, Long> skuQty = mergerStvQty(stvLines);
            assertNull(skuQty.get(7017751L));
            assertEquals(skuQty.get(7017756L).longValue(), ocpQty.get(7017756L).longValue());
        } finally {

            // 清除库存数据，设置店铺配置
            List<Inventory> invList = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017751L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            List<Inventory> invList2 = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017756L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            invList.addAll(invList2);
            inventoryDao.deleteAll(invList);

            bc.setIsPartOutbound(false);
            // biChannelDao.save(bc);
        }
    }

    /**
     * 部分出库 单元测试 （商品A 库存数量少，商品B 库存满足）
     * 
     * @throws Exception
     */
    @Test
    public void createOrderOutboundTest04() throws Exception {
        // 设置店铺是否允许部分占用
        BiChannel bc = biChannelDao.getByCode("调拨测试店铺B");
        bc.setIsPartOutbound(true);
        biChannelDao.save(bc);
        try {

            // 初始化库存 skuId=7017751，skuId=7017756
            Inventory inv = inventoryDao.getByPrimaryKey(13134705L);
            Inventory i = new Inventory();
            BeanUtils.copyProperties(inv, i);
            i.setId(null);
            i.setIsOccupied(false);
            i.setOccupationCode(null);
            i.setOwner("调拨测试店铺B");
            i.setQuantity(10L);
            OperationUnit ou = new OperationUnit();
            ou.setId(121L);
            i.setOu(ou);
            WarehouseLocation wl = new WarehouseLocation();
            wl.setId(818926L);
            i.setLocation(wl);
            i.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i);

            Inventory inv2 = inventoryDao.getByPrimaryKey(13134747L);
            Inventory i2 = new Inventory();
            BeanUtils.copyProperties(inv2, i2);
            i2.setId(null);
            i2.setIsOccupied(false);
            i2.setOccupationCode(null);
            i2.setOwner("调拨测试店铺B");
            i2.setQuantity(50L);
            i2.setOu(ou);
            i2.setLocation(wl);
            i2.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i2);


            inventoryDao.flush();

            // 生成单据并占有
            String msg = "<OutboundOrders>\r\n" + "    <sourceMarkCode>routeWms</sourceMarkCode>\r\n" + "    <OutboundOrder>\r\n" + "        <OutboundNo>TF330000000100112_4</OutboundNo>\r\n" + "        <PlatformCode>TF330000000100112_4</PlatformCode>\r\n"
                    + "        <CustomerCode>调拨测试店铺B</CustomerCode>\r\n" + "        <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "        <OrderType>1</OrderType>\r\n"
                    + "        <DataSource>inner-flow</DataSource>\r\n" + "        <WhCode>SHWH10</WhCode>\r\n" + "        <Receiver>\r\n" + "            <ReceiverName>JESSICA</ReceiverName>\r\n" + "            <ReceiverPhone>82131234</ReceiverPhone>\r\n"
                    + "            <ReceiverMobile>18616362606</ReceiverMobile>\r\n" + "            <Postcode>200000</Postcode>\r\n" + "            <Country>中国</Country>\r\n" + "            <Province>上海</Province>\r\n" + "            <City>上海市</City>\r\n"
                    + "            <District>静安区</District>\r\n" + "            <Street>彭浦</Street>\r\n" + "            <Address>1188号</Address>\r\n" + "            <Lpcode>EMS</Lpcode>\r\n" + "        </Receiver>\r\n" + "        <OutboundLines>\r\n"
                    + "            <OutboundLine>\r\n" + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241343464</Upc>\r\n" + "                <Qty>50</Qty>\r\n"
                    + "                <InvStatus>5</InvStatus>\r\n" + "            </OutboundLine>\r\n" + "            <OutboundLine>\r\n" + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n"
                    + "                <Upc>241221820</Upc>\r\n" + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n" + "            </OutboundLine>\r\n" + "        </OutboundLines>\r\n" + "    </OutboundOrder>\r\n"
                    + "</OutboundOrders>";
            List<WmsOutBound> outBounds = outBounds(msg);
            // 调用wms3.0出库单接口
            WmsResponse wr = wmsOrderServiceToHub4Manager.createOrderOutbound(outBounds);

            // 校验数据是否正确
            assertEquals(WmsResponse.STATUS_SUCCESS, wr.getStatus());
            List<StockTransApplication> staList = staDao.findBySlipCode("TF330000000100112_4");
            assertNotNull(staList);
            assertNotNull(staList.get(0));
            List<Inventory> ocpinvList = inventoryDao.findByOccupiedCode(staList.get(0).getCode());
            assertNotNull(ocpinvList);
            Map<Long, Long> ocpQty = mergerOcpInvQty(ocpinvList);

            assertEquals(ocpQty.get(7017751L).longValue(), 10L);
            assertEquals(ocpQty.get(7017756L).longValue(), 50L);
            StockTransVoucher stv = stvDao.findStvByStaId(staList.get(0).getId());
            assertNotNull(stv);
            List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId());
            assertNotNull(stvLines);
            Map<Long, Long> skuQty = mergerStvQty(stvLines);
            assertEquals(skuQty.get(7017751L).longValue(), ocpQty.get(7017751L).longValue());
            assertEquals(skuQty.get(7017756L).longValue(), ocpQty.get(7017756L).longValue());
        } finally {

            // 清除库存数据，设置店铺配置
            List<Inventory> invList = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017751L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            List<Inventory> invList2 = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017756L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            invList.addAll(invList2);
            inventoryDao.deleteAll(invList);

            bc.setIsPartOutbound(false);
            // biChannelDao.save(bc);
        }
    }

    /**
     * 部分出库 单元测试 （商品A 库存数量少，商品B 库存少）
     * 
     * @throws Exception
     */
    @Test
    public void createOrderOutboundTest05() throws Exception {
        // 设置店铺是否允许部分占用
        BiChannel bc = biChannelDao.getByCode("调拨测试店铺B");
        bc.setIsPartOutbound(true);
        biChannelDao.save(bc);
        try {

            // 初始化库存 skuId=7017751，skuId=7017756
            Inventory inv = inventoryDao.getByPrimaryKey(13134705L);
            Inventory i = new Inventory();
            BeanUtils.copyProperties(inv, i);
            i.setId(null);
            i.setIsOccupied(false);
            i.setOccupationCode(null);
            i.setOwner("调拨测试店铺B");
            i.setQuantity(10L);
            OperationUnit ou = new OperationUnit();
            ou.setId(121L);
            i.setOu(ou);
            WarehouseLocation wl = new WarehouseLocation();
            wl.setId(818926L);
            i.setLocation(wl);
            i.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i);

            Inventory inv2 = inventoryDao.getByPrimaryKey(13134747L);
            Inventory i2 = new Inventory();
            BeanUtils.copyProperties(inv2, i2);
            i2.setId(null);
            i2.setIsOccupied(false);
            i2.setOccupationCode(null);
            i2.setOwner("调拨测试店铺B");
            i2.setQuantity(10L);
            i2.setOu(ou);
            i2.setLocation(wl);
            i2.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i2);


            inventoryDao.flush();

            // 生成单据并占有
            String msg = "<OutboundOrders>\r\n" + "    <sourceMarkCode>routeWms</sourceMarkCode>\r\n" + "    <OutboundOrder>\r\n" + "        <OutboundNo>TF330000000100112_5</OutboundNo>\r\n" + "        <PlatformCode>TF330000000100112_5</PlatformCode>\r\n"
                    + "        <CustomerCode>调拨测试店铺B</CustomerCode>\r\n" + "        <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "        <OrderType>1</OrderType>\r\n" + "        <DataSource>inner-flow</DataSource>\r\n" + "        <WhCode>SHWH10</WhCode>\r\n"
                    + "        <Receiver>\r\n" + "            <ReceiverName>JESSICA</ReceiverName>\r\n" + "            <ReceiverPhone>82131234</ReceiverPhone>\r\n" + "            <ReceiverMobile>18616362606</ReceiverMobile>\r\n"
                    + "            <Postcode>200000</Postcode>\r\n" + "            <Country>中国</Country>\r\n" + "            <Province>上海</Province>\r\n" + "            <City>上海市</City>\r\n" + "            <District>静安区</District>\r\n"
                    + "            <Street>彭浦</Street>\r\n" + "            <Address>1188号</Address>\r\n" + "            <Lpcode>EMS</Lpcode>\r\n" + "        </Receiver>\r\n" + "        <OutboundLines>\r\n" + "            <OutboundLine>\r\n"
                    + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241343464</Upc>\r\n" + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n"
                    + "            </OutboundLine>\r\n" + "            <OutboundLine>\r\n" + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241221820</Upc>\r\n"
                    + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n" + "            </OutboundLine>\r\n" + "        </OutboundLines>\r\n" + "    </OutboundOrder>\r\n" + "</OutboundOrders>";
            List<WmsOutBound> outBounds = outBounds(msg);
            // 调用wms3.0出库单接口
            WmsResponse wr = wmsOrderServiceToHub4Manager.createOrderOutbound(outBounds);

            // 校验数据是否正确
            assertEquals(WmsResponse.STATUS_SUCCESS, wr.getStatus());
            List<StockTransApplication> staList = staDao.findBySlipCode("TF330000000100112_5");
            assertNotNull(staList);
            assertNotNull(staList.get(0));
            List<Inventory> ocpinvList = inventoryDao.findByOccupiedCode(staList.get(0).getCode());
            assertNotNull(ocpinvList);
            Map<Long, Long> ocpQty = mergerOcpInvQty(ocpinvList);

            assertEquals(ocpQty.get(7017751L).longValue(), 10L);
            assertEquals(ocpQty.get(7017756L).longValue(), 10L);
            StockTransVoucher stv = stvDao.findStvByStaId(staList.get(0).getId());
            assertNotNull(stv);
            List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId());
            assertNotNull(stvLines);
            Map<Long, Long> skuQty = mergerStvQty(stvLines);
            assertEquals(skuQty.get(7017751L).longValue(), ocpQty.get(7017751L).longValue());
            assertEquals(skuQty.get(7017756L).longValue(), ocpQty.get(7017756L).longValue());
        } finally {

            // 清除库存数据，设置店铺配置
            List<Inventory> invList = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017751L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            List<Inventory> invList2 = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017756L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            invList.addAll(invList2);
            inventoryDao.deleteAll(invList);

            bc.setIsPartOutbound(false);
            // biChannelDao.save(bc);
        }
    }

    /**
     * 部分出库 单元测试 （商品A 库存数量0，商品B 库存0）
     * 
     * @throws Exception
     */
    @Test
    public void createOrderOutboundTest06() throws Exception {
        // 设置店铺是否允许部分占用
        BiChannel bc = biChannelDao.getByCode("调拨测试店铺B");
        bc.setIsPartOutbound(true);
        biChannelDao.save(bc);
        try {

            // 初始化库存 skuId=7017751，skuId=7017756
            Inventory inv = inventoryDao.getByPrimaryKey(13134705L);
            Inventory i = new Inventory();
            BeanUtils.copyProperties(inv, i);
            i.setId(null);
            i.setIsOccupied(false);
            i.setOccupationCode(null);
            i.setOwner("调拨测试店铺B");
            i.setQuantity(0L);
            OperationUnit ou = new OperationUnit();
            ou.setId(121L);
            i.setOu(ou);
            WarehouseLocation wl = new WarehouseLocation();
            wl.setId(818926L);
            i.setLocation(wl);
            i.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i);

            Inventory inv2 = inventoryDao.getByPrimaryKey(13134747L);
            Inventory i2 = new Inventory();
            BeanUtils.copyProperties(inv2, i2);
            i2.setId(null);
            i2.setIsOccupied(false);
            i2.setOccupationCode(null);
            i2.setOwner("调拨测试店铺B");
            i2.setQuantity(0L);
            i2.setOu(ou);
            i2.setLocation(wl);
            i2.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i2);


            inventoryDao.flush();

            // 生成单据并占有
            String msg = "<OutboundOrders>\r\n" + "    <sourceMarkCode>routeWms</sourceMarkCode>\r\n" + "    <OutboundOrder>\r\n" + "        <OutboundNo>TF330000000100112_6</OutboundNo>\r\n" + "        <PlatformCode>TF330000000100112_6</PlatformCode>\r\n"
                    + "        <CustomerCode>调拨测试店铺B</CustomerCode>\r\n" + "        <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "        <OrderType>1</OrderType>\r\n" + "        <DataSource>inner-flow</DataSource>\r\n" + "        <WhCode>SHWH10</WhCode>\r\n"
                    + "        <Receiver>\r\n" + "            <ReceiverName>JESSICA</ReceiverName>\r\n" + "            <ReceiverPhone>82131234</ReceiverPhone>\r\n" + "            <ReceiverMobile>18616362606</ReceiverMobile>\r\n"
                    + "            <Postcode>200000</Postcode>\r\n" + "            <Country>中国</Country>\r\n" + "            <Province>上海</Province>\r\n" + "            <City>上海市</City>\r\n" + "            <District>静安区</District>\r\n"
                    + "            <Street>彭浦</Street>\r\n" + "            <Address>1188号</Address>\r\n" + "            <Lpcode>EMS</Lpcode>\r\n" + "        </Receiver>\r\n" + "        <OutboundLines>\r\n" + "            <OutboundLine>\r\n"
                    + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241343464</Upc>\r\n" + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n"
                    + "            </OutboundLine>\r\n" + "            <OutboundLine>\r\n" + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241221820</Upc>\r\n"
                    + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n" + "            </OutboundLine>\r\n" + "        </OutboundLines>\r\n" + "    </OutboundOrder>\r\n" + "</OutboundOrders>";
            List<WmsOutBound> outBounds = outBounds(msg);
            // 调用wms3.0出库单接口
            WmsResponse wr = wmsOrderServiceToHub4Manager.createOrderOutbound(outBounds);

            // 校验数据是否正确
            assertEquals(WmsResponse.STATUS_ERROR, wr.getStatus());

        } finally {

            // 清除库存数据，设置店铺配置
            List<Inventory> invList = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017751L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            List<Inventory> invList2 = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017756L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            invList.addAll(invList2);
            inventoryDao.deleteAll(invList);

            bc.setIsPartOutbound(false);
            // biChannelDao.save(bc);
        }
    }

    /**
     * 部分出库 单元测试 （商品A 库存数量满足，商品B 库存满足）
     * 
     * @throws Exception
     */
    @Test
    public void createOrderOutboundTest07() throws Exception {
        // 设置店铺是否允许部分占用
        BiChannel bc = biChannelDao.getByCode("调拨测试店铺B");
        bc.setIsPartOutbound(true);
        biChannelDao.save(bc);
        try {

            // 初始化库存 skuId=7017751，skuId=7017756
            Inventory inv = inventoryDao.getByPrimaryKey(13134705L);
            Inventory i = new Inventory();
            BeanUtils.copyProperties(inv, i);
            i.setId(null);
            i.setIsOccupied(false);
            i.setOccupationCode(null);
            i.setOwner("调拨测试店铺B");
            i.setQuantity(110L);
            OperationUnit ou = new OperationUnit();
            ou.setId(121L);
            i.setOu(ou);
            WarehouseLocation wl = new WarehouseLocation();
            wl.setId(818926L);
            i.setLocation(wl);
            i.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i);

            Inventory inv2 = inventoryDao.getByPrimaryKey(13134747L);
            Inventory i2 = new Inventory();
            BeanUtils.copyProperties(inv2, i2);
            i2.setId(null);
            i2.setIsOccupied(false);
            i2.setOccupationCode(null);
            i2.setOwner("调拨测试店铺B");
            i2.setQuantity(110L);
            i2.setOu(ou);
            i2.setLocation(wl);
            i2.setDistrict(new WarehouseDistrict(3653L));
            inventoryDao.save(i2);


            inventoryDao.flush();

            // 生成单据并占有
            String msg = "<OutboundOrders>\r\n" + "    <sourceMarkCode>routeWms</sourceMarkCode>\r\n" + "    <OutboundOrder>\r\n" + "        <OutboundNo>TF330000000100112_7_1</OutboundNo>\r\n"
                    + "        <PlatformCode>TF330000000100112_7_1</PlatformCode>\r\n"
                    + "        <CustomerCode>调拨测试店铺B</CustomerCode>\r\n" + "        <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "        <OrderType>1</OrderType>\r\n" + "        <DataSource>inner-flow</DataSource>\r\n" + "        <WhCode>SHWH10</WhCode>\r\n"
                    + "        <Receiver>\r\n" + "            <ReceiverName>JESSICA</ReceiverName>\r\n" + "            <ReceiverPhone>82131234</ReceiverPhone>\r\n" + "            <ReceiverMobile>18616362606</ReceiverMobile>\r\n"
                    + "            <Postcode>200000</Postcode>\r\n" + "            <Country>中国</Country>\r\n" + "            <Province>上海</Province>\r\n" + "            <City>上海市</City>\r\n" + "            <District>静安区</District>\r\n"
                    + "            <Street>彭浦</Street>\r\n" + "            <Address>1188号</Address>\r\n" + "            <Lpcode>EMS</Lpcode>\r\n" + "        </Receiver>\r\n" + "        <OutboundLines>\r\n" + "            <OutboundLine>\r\n"
                    + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241343464</Upc>\r\n" + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n"
                    + "            </OutboundLine>\r\n" + "            <OutboundLine>\r\n" + "                <StoreCode>调拨测试店铺B</StoreCode>\r\n" + "                <Owner>调拨测试店铺B</Owner>\r\n" + "                <Upc>241221820</Upc>\r\n"
                    + "                <Qty>50</Qty>\r\n" + "                <InvStatus>5</InvStatus>\r\n" + "            </OutboundLine>\r\n" + "        </OutboundLines>\r\n" + "    </OutboundOrder>\r\n" + "</OutboundOrders>";
            List<WmsOutBound> outBounds = outBounds(msg);
            // 调用wms3.0出库单接口
            WmsResponse wr = wmsOrderServiceToHub4Manager.createOrderOutbound(outBounds);

            // 校验数据是否正确
            assertEquals(WmsResponse.STATUS_SUCCESS, wr.getStatus());
            List<StockTransApplication> staList = staDao.findBySlipCode("TF330000000100112_7_1");
            assertNotNull(staList);
            assertNotNull(staList.get(0));
            List<Inventory> ocpinvList = inventoryDao.findByOccupiedCode(staList.get(0).getCode());
            assertNotNull(ocpinvList);
            Map<Long, Long> ocpQty = mergerOcpInvQty(ocpinvList);

            assertEquals(ocpQty.get(7017751L).longValue(), 50L);
            assertEquals(ocpQty.get(7017756L).longValue(), 50L);
            StockTransVoucher stv = stvDao.findStvByStaId(staList.get(0).getId());
            assertNotNull(stv);
            List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId());
            assertNotNull(stvLines);
            Map<Long, Long> skuQty = mergerStvQty(stvLines);
            assertEquals(skuQty.get(7017751L).longValue(), ocpQty.get(7017751L).longValue());
            assertEquals(skuQty.get(7017756L).longValue(), ocpQty.get(7017756L).longValue());
        } finally {

            // 清除库存数据，设置店铺配置
            List<Inventory> invList = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017751L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            List<Inventory> invList2 = inventoryDao.findSkuLocationOuIdOwner(121L, 818926L, 7017756L, new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
            invList.addAll(invList2);
            inventoryDao.deleteAll(invList);

            bc.setIsPartOutbound(false);
            // biChannelDao.save(bc);
        }
    }

    /**
     * 转换
     * 
     * @param outboundStr
     * @return
     * @throws Exception
     */
    public List<WmsOutBound> outBounds(String outboundStr) throws Exception {
        com.baozun.ecp.ip.command.outboundOrders.OutboundOrders outboundOrder = null;
        outboundOrder = XmlUtil.buildJaxb(com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.class, outboundStr);
        List<com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder> list = outboundOrder.getOutboundOrder();
        List<WmsOutBound> outBounds = new ArrayList<WmsOutBound>();
        for (com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder out : list) {
            WmsOutBound outbound = outboundOrderNotifyTranfer(out);
            outBounds.add(outbound);
        }
        return outBounds;
    }


    /**
     * 出库单通知模型转换
     * 
     * @throws Exception
     */
    public WmsOutBound outboundOrderNotifyTranfer(com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder outboundOrder) throws Exception {
        // 封装入库单头
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WmsOutBound out = new WmsOutBound();
        out.setExtCode(outboundOrder.getOutboundNo());
        out.setEcOrderCode(outboundOrder.getPlatformCode());
        out.setOwner(outboundOrder.getStoreCode());
        out.setStoreCode(outboundOrder.getStoreCode());
        out.setExtType(outboundOrder.getOrderType());
        // wms单据类型=VMI退大仓
        out.setWmsType(101);
        out.setWhCode(outboundOrder.getWhCode());
        if (!StringUtil.isEmpty(outboundOrder.getOutTime())) {
            out.setPlanOutboundTime(formatter.parse(outboundOrder.getOutTime()));
        }
        out.setMemo(outboundOrder.getExtMemo());
        out.setDataSource(outboundOrder.getDataSource());
        List<com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder.OutboundLines.OutboundLine> lines = outboundOrder.getOutboundLines().getOutboundLine();
        List<WmsOutBoundLine> wmsOutBoundLines = new ArrayList<WmsOutBoundLine>();
        for (com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder.OutboundLines.OutboundLine line : lines) {
            WmsOutBoundLine l = new WmsOutBoundLine();
            l.setUpc(line.getUpc());
            l.setLineNo(line.getLineNo());
            l.setStoreCode(line.getStoreCode());
            l.setInvStatus(line.getInvStatus());
            l.setWhCode(outboundOrder.getWhCode());
            l.setQty(line.getQty().longValue());
            wmsOutBoundLines.add(l);
        }
        out.setWmsOutBoundLines(wmsOutBoundLines);
        // 物流信息
        com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder.Receiver receiver = outboundOrder.getReceiver();
        WmsOutBoundDeliveryInfo deliveryInfo = new WmsOutBoundDeliveryInfo();
        if (receiver == null) {
            receiver = new com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder.Receiver();
        }
        deliveryInfo.setCountry(receiver.getCountry());
        deliveryInfo.setProvince(receiver.getProvince());
        deliveryInfo.setCity(receiver.getCity());
        deliveryInfo.setDistrict(receiver.getDistrict());
        deliveryInfo.setAddress(receiver.getAddress());
        deliveryInfo.setReceiver(receiver.getReceiverName());
        deliveryInfo.setTelephone(receiver.getReceiverPhone());
        deliveryInfo.setMoblie(receiver.getReceiverMobile());
        deliveryInfo.setZipcode(receiver.getPostcode());
        out.setWmsOutBoundDeliveryInfo(deliveryInfo);
        return out;
    }

    /**
     * 合并STVLINE数量
     * 
     * @param stvLines
     * @return
     */
    private Map<Long, Long> mergerStvQty(List<StvLine> stvLines) {
        Map<Long, Long> skuQty = new HashMap<Long, Long>();
        for (StvLine stvLine : stvLines) {
            if (skuQty.get(stvLine.getSku().getId()) == null) {
                skuQty.put(stvLine.getSku().getId(), 0L);
            }
            skuQty.put(stvLine.getSku().getId(), skuQty.get(stvLine.getSku().getId()) + stvLine.getQuantity());
        }

        return skuQty;
    }

    /**
     * 合并Inventory数量
     * 
     * @param stvLines
     * @return
     */
    private Map<Long, Long> mergerOcpInvQty(List<Inventory> ocpinvList) {
        Map<Long, Long> skuQty = new HashMap<Long, Long>();
        for (Inventory inv : ocpinvList) {
            if (skuQty.get(inv.getSku().getId()) == null) {
                skuQty.put(inv.getSku().getId(), 0L);
            }
            skuQty.put(inv.getSku().getId(), skuQty.get(inv.getSku().getId()) + inv.getQuantity());
        }

        return skuQty;
    }

}
