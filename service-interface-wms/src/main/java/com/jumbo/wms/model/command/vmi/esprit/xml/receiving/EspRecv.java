package com.jumbo.wms.model.command.vmi.esprit.xml.receiving;

import java.io.Serializable;
import java.lang.reflect.Field;
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
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}header"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}receivings"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"header", "receivings"})
@XmlRootElement(name = "espRecv")
public class EspRecv implements Serializable {

    protected static final Logger log = LoggerFactory.getLogger(EspRecv.class);

    private static final long serialVersionUID = 141923650675670034L;
    @XmlElement(required = true)
    protected Header header;
    @XmlElement(required = true)
    protected Receivings receivings;

    /**
     * Gets the value of the header property.
     * 
     * @return possible object is {@link Header }
     * 
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value allowed object is {@link Header }
     * 
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the receivings property.
     * 
     * @return possible object is {@link Receivings }
     * 
     */
    public Receivings getReceivings() {
        return receivings;
    }

    /**
     * Sets the value of the receivings property.
     * 
     * @param value allowed object is {@link Receivings }
     * 
     */
    public void setReceivings(Receivings value) {
        this.receivings = value;
    }


    public Map<String, List<String>> getSqlList() {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        List<String> headerList = new ArrayList<String>();
        String headSql = "insert into t_esprit_receiving(id,version #field ) values(s_t_esprit_receiving.nextval,sysdate #values )";
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
            for (Receiving org : this.receivings.getReceiving()) {
                StringBuilder receivingsFieldBr = new StringBuilder();
                StringBuilder receivingsValueBr = new StringBuilder();
                Field[] sfs = org.getClass().getFields();
                for (Field sf : sfs) {
                    if (sf.getType().toString().indexOf(Invoice.class.getName()) > -1 || sf.getType().toString().indexOf(Items.class.getName()) > -1) {
                        System.err.println(sf.getType().getName());
                        continue;
                    }
                    // key
                    receivingsFieldBr.append(",").append("receiving_").append(sf.getName());
                    // value
                    receivingsValueBr.append(",'").append(sf.get(org).toString().replaceAll("'", "''")).append("'");
                }
                receivingsFieldBr = receivingsFieldBr.insert(0, headFieldBr);
                receivingsValueBr = receivingsValueBr.insert(0, headValueBr);
                // invoice
                Field[] invfs = org.getInvoice().getClass().getFields();
                for (Field sf : invfs) {
                    // key
                    receivingsFieldBr.append(",").append("invoice_").append(sf.getName());
                    // value
                    receivingsValueBr.append(",'").append(sf.get(org.getInvoice()).toString().replaceAll("'", "''")).append("'");
                }
                // items
                for (Item item : org.getItems().getItem()) {
                    StringBuilder itemFieldBr = new StringBuilder();
                    StringBuilder itemValueBr = new StringBuilder();
                    Field[] itemfs = item.getClass().getFields();
                    for (Field sf : itemfs) {
                        // key
                        itemFieldBr.append(",").append("item_").append(sf.getName());
                        // value
                        itemValueBr.append(",'").append(sf.get(item).toString().replaceAll("'", "''")).append("'");
                    }
                    itemValueBr = itemValueBr.insert(0, receivingsFieldBr);
                    itemValueBr = itemValueBr.insert(0, receivingsValueBr);
                    String sql = headSql.replaceFirst("#field", receivingsFieldBr.toString());
                    sql = sql.replaceFirst("#values", receivingsValueBr.toString());
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
                log.error("getSqlList IllegalArgumentException！", e);
            }
        } catch (IllegalAccessException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("getSqlList IllegalAccessException！", e);
            }
        }
        return result;
    }

}
