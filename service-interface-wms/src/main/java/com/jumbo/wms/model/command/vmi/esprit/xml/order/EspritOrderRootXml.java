package com.jumbo.wms.model.command.vmi.esprit.xml.order;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}header"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}orders"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"header", "orders"})
@XmlRootElement(name = "espOrder")
public class EspritOrderRootXml implements Serializable {

    protected static final Logger log = LoggerFactory.getLogger(EspritOrderRootXml.class);

    private static final long serialVersionUID = 4786343339429618496L;
    @XmlElement(required = true)
    protected EspritHeaderXml header;
    @XmlElement(required = true)
    protected EspritOrdersXml orders;

    /**
     * Gets the value of the header property.
     * 
     * @return possible object is {@link EspritHeaderXml }
     * 
     */
    public EspritHeaderXml getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value allowed object is {@link EspritHeaderXml }
     * 
     */
    public void setHeader(EspritHeaderXml value) {
        this.header = value;
    }

    /**
     * Gets the value of the orders property.
     * 
     * @return possible object is {@link EspritOrdersXml }
     * 
     */
    public EspritOrdersXml getOrders() {
        return orders;
    }

    /**
     * Sets the value of the orders property.
     * 
     * @param value allowed object is {@link EspritOrdersXml }
     * 
     */
    public void setOrders(EspritOrdersXml value) {
        this.orders = value;
    }

    public Map<String, List<String>> getSqlList(BigDecimal batch, int start, int end) {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        List<String> headerList = new ArrayList<String>();
        int count = 0;
        List<String> updateStatusSql = new ArrayList<String>();
        String headSql = "insert into t_esprit_order(id,version,status,batch_id #field ) values(s_t_esprit_order.nextval,sysdate,1," + batch + " #values )";
        try {
            // head
            StringBuilder headFieldBr = new StringBuilder();
            StringBuilder headValueBr = new StringBuilder();
            Field[] fs = this.header.getClass().getFields();
            for (Field org : fs) {
                // key
                headFieldBr.append(",").append("header_").append(org.getName());
                // value
                headValueBr.append(",'").append(org.get(this.header).toString().replaceAll("'", "''")).append("'");
            }
            boolean isBreak = false;
            if (this.orders == null || this.orders.order == null) {
                return result;
            }
            // style
            for (EspritOrderXml org : this.orders.order) {
                if (isBreak) {
                    break;
                }
                boolean updateCancel = false;
                StringBuilder orderFieldBr = new StringBuilder();
                StringBuilder orderValueBr = new StringBuilder();
                Field[] sfs = org.getClass().getFields();
                String po = "";
                for (Field sf : sfs) {
                    if (sf.getType().toString().indexOf(EspritOrderDetailsXml.class.getName()) > -1) {
                        System.err.println(sf.getType().getName());
                        continue;
                    }
                    if ("Cancel".equals(org.getStatusInEDIFile())) {
                        updateCancel = true;
                        po = org.getPo();
                    }
                    // key
                    orderFieldBr.append(",").append("od_").append(sf.getName());
                    // value
                    orderValueBr.append(",'").append(sf.get(org).toString().replaceAll("'", "''")).append("'");
                }
                if (updateCancel) {
                    updateStatusSql.add("update t_esprit_order set status = 99 where od_po = '" + po + "'");
                }
                orderFieldBr = orderFieldBr.insert(0, headFieldBr);
                orderValueBr = orderValueBr.insert(0, headValueBr);
                // OrderDetail
                if (org.getOrderDetails().getItem() == null || org.getOrderDetails().getItem().size() < 1) {
                    continue;
                }
                for (EspritItemXml od : org.getOrderDetails().getItem()) {
                    count++;
                    if (count > start && count <= end) {
                        StringBuilder odFieldBr = new StringBuilder();
                        StringBuilder odValueBr = new StringBuilder();
                        Field[] skfs = od.getClass().getFields();
                        for (Field sf : skfs) {
                            // key
                            odFieldBr.append(",").append("od_").append(sf.getName());
                            // value
                            odValueBr.append(",'").append(sf.get(od).toString().replaceAll("'", "''")).append("'");
                        }
                        odFieldBr = odFieldBr.insert(0, orderFieldBr);
                        odValueBr = odValueBr.insert(0, orderValueBr);
                        String sql = headSql.replaceFirst("#field", odFieldBr.toString());
                        sql = sql.replaceFirst("#values", odValueBr.toString());
                        System.out.println(sql);
                        headerList.add(sql);
                    } else if (count > end) {
                        isBreak = true;
                        break;
                    }
                }
            }
            if (count < start) {
                return result;
            }
            int i = 0;
            List<String> sqlList = new ArrayList<String>();
            int max = 2000;
            int maxi = headerList.size();
            if (headerList.size() > max) {
                maxi = headerList.size() / max;
            }
            for (String sql : headerList) {
                i++;
                sqlList.add(sql);
                if (i > maxi * max && i == headerList.size()) {
                    result.put(i + "" + System.currentTimeMillis(), new ArrayList<String>(sqlList));
                }
                if (i < max && i == headerList.size()) {
                    result.put(i + "" + System.currentTimeMillis(), new ArrayList<String>(sqlList));
                }
                if (i % max == 0) {
                    result.put(i + "" + System.currentTimeMillis(), new ArrayList<String>(sqlList));
                    sqlList.clear();
                }
            }
        } catch (IllegalArgumentException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("getSqlList IllegalArgumentException！" + batch, e);
            }
        } catch (IllegalAccessException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("getSqlList IllegalAccessException！" + batch, e);
            }
        }
        headerList.addAll(updateStatusSql);
        return result;
    }

    public Map<String, List<String>> getSqlList(BigDecimal batch) {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        List<String> headerList = new ArrayList<String>();
        List<String> updateStatusSql = new ArrayList<String>();
        String headSql = "insert into t_esprit_order(id,version,status,batch_id #field ) values(s_t_esprit_order.nextval,sysdate,1," + batch + " #values )";
        try {
            // head
            StringBuilder headFieldBr = new StringBuilder();
            StringBuilder headValueBr = new StringBuilder();
            Field[] fs = this.header.getClass().getFields();
            for (Field org : fs) {
                // key
                headFieldBr.append(",").append("header_").append(org.getName());
                // value
                headValueBr.append(",'").append(org.get(this.header).toString().replaceAll("'", "''")).append("'");
            }
            // style
            for (EspritOrderXml org : this.orders.order) {
                boolean updateCancel = false;
                StringBuilder orderFieldBr = new StringBuilder();
                StringBuilder orderValueBr = new StringBuilder();
                Field[] sfs = org.getClass().getFields();
                String po = "";
                for (Field sf : sfs) {
                    if (sf.getType().toString().indexOf(EspritOrderDetailsXml.class.getName()) > -1) {
                        System.err.println(sf.getType().getName());
                        continue;
                    }
                    if ("Cancel".equals(org.getStatusInEDIFile())) {
                        updateCancel = true;
                        po = org.getPo();
                    }
                    // key
                    orderFieldBr.append(",").append("od_").append(sf.getName());
                    // value
                    orderValueBr.append(",'").append(sf.get(org).toString().replaceAll("'", "''")).append("'");
                }
                if (updateCancel) {
                    updateStatusSql.add("update t_esprit_order set status = 99 where od_po = '" + po + "'");
                }
                orderFieldBr = orderFieldBr.insert(0, headFieldBr);
                orderValueBr = orderValueBr.insert(0, headValueBr);
                // OrderDetail
                if (org.getOrderDetails().getItem() == null || org.getOrderDetails().getItem().size() < 1) {
                    String sql = headSql.replaceFirst("#field", orderFieldBr.toString());
                    sql = sql.replaceFirst("#values", orderValueBr.toString());
                    System.out.println(sql);
                    headerList.add(sql);
                    continue;
                }
                for (EspritItemXml od : org.getOrderDetails().getItem()) {
                    StringBuilder odFieldBr = new StringBuilder();
                    StringBuilder odValueBr = new StringBuilder();
                    Field[] skfs = od.getClass().getFields();
                    for (Field sf : skfs) {
                        // key
                        odFieldBr.append(",").append("od_").append(sf.getName());
                        // value
                        odValueBr.append(",'").append(sf.get(od).toString().replaceAll("'", "''")).append("'");
                    }
                    odFieldBr = odFieldBr.insert(0, orderFieldBr);
                    odValueBr = odValueBr.insert(0, orderValueBr);
                    String sql = headSql.replaceFirst("#field", odFieldBr.toString());
                    sql = sql.replaceFirst("#values", odValueBr.toString());
                    System.out.println(sql);
                    headerList.add(sql);
                }
            }
            int i = 0;
            List<String> sqlList = new ArrayList<String>();
            int max = 2000;
            int maxi = headerList.size();
            if (headerList.size() > max) {
                maxi = headerList.size() / max;
            }
            for (String sql : headerList) {
                i++;
                sqlList.add(sql);
                if (i > maxi * max && i == headerList.size()) {
                    result.put(i + "", new ArrayList<String>(sqlList));
                }
                if (i < max && i == headerList.size()) {
                    result.put(i + "", new ArrayList<String>(sqlList));
                }
                if (i % max == 0) {
                    result.put(i + "", new ArrayList<String>(sqlList));
                    sqlList.clear();
                }
            }
        } catch (IllegalArgumentException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("getSqlList IllegalArgumentException！" + batch, e);
            }
        } catch (IllegalAccessException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("getSqlList IllegalAccessException！" + batch, e);
            }
        }
        headerList.addAll(updateStatusSql);
        return result;
    }

}
