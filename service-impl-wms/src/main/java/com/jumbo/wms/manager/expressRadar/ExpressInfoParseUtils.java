package com.jumbo.wms.manager.expressRadar;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.baozun.bh.util.DateUtil;

import com.jumbo.util.StringUtil;
import com.jumbo.wms.model.expressRadar.RadarTransNo;
import com.jumbo.wms.model.expressRadar.TransRoute;

public class ExpressInfoParseUtils {

    static Logger log = LoggerFactory.getLogger(ExpressInfoParseUtils.class);
	/**
	 * 解析EMS的路由信息
	 * @param expressInfo
	 * @param radarTransNo
	 * @return
	 */
	public static List<TransRoute> getTransRouteByEMS(String expressInfo,RadarTransNo radarTransNo ) {
		
		if(expressInfo!=null&&radarTransNo!=null){
			try {
				List<TransRoute> expressRadarDetailList = new ArrayList<TransRoute>();
				JSONObject  dataJson=new JSONObject(expressInfo);
				JSONArray ja=dataJson.getJSONArray("traces");
				for(int i=0;i<ja.length();i++){
					TransRoute transRoute = new TransRoute();
					String info=ja.getJSONObject(i).getString("remark");
					String status=null;
					if (!StringUtil.isEmpty(info)) {
						// 解析remark,得到状态
						if (info.indexOf("收寄") > -1) {
                            status = "揽收";
						} else if (info.indexOf("揽收") > -1 || info.indexOf("已收件") > -1) {
							status = "揽收";
                        } else if (info.indexOf("到达") > -1) {
							status ="到件";// 到件
						} else if (info.indexOf("海关放行") > -1) {
							// 国内的不需要该状态
						} else if (info.indexOf("离开") > -1 && info.indexOf("发往") > -1) {
							status ="发件";
						} else if (info.indexOf("安排投递") > -1) {
							status = "投递";
                        } else if (info.indexOf("未妥投") > -1) {
                            status = "未妥投";
                        } else if (info.indexOf("签收人") > -1 || info.indexOf("妥投") > -1) {
                            status = "已签收";
                        }
						
					}
					transRoute.setExpressCode(radarTransNo.getExpressCode());
					transRoute.setAddress(ja.getJSONObject(i).getString("acceptAddress"));
                    transRoute.setMessage(info);
					transRoute.setOpcode(status);
					transRoute.setOperateTime(DateUtil.parse(ja.getJSONObject(i).getString("acceptTime")));
					transRoute.setRadarTransNo(radarTransNo);
                    transRoute.setLastModifyTime(new Date());
					expressRadarDetailList.add(transRoute);
					
				}
				return expressRadarDetailList;
			} catch (Exception e) {
                log.debug("单号为" + radarTransNo.getExpressCode() + "的EMS快递信息解析失败！");
			}
		}
		return null;
	}
	
	/**
	 * 解析顺丰的路由信息
	 * @param expressInfo
	 * @param radarTransNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
    public static List<TransRoute> getTransRouteBySF(String expressInfo,RadarTransNo radarTransNo) {
		if(expressInfo!=null&&radarTransNo!=null){
			
            try {


                Document document = DocumentHelper.parseText(expressInfo);
                Element rootElm = document.getRootElement();
                Element memberElm = rootElm.element("Body").element("RouteResponse");
                List nodes = memberElm.elements("Route");
                List<TransRoute> expressRadarDetailList = new ArrayList<TransRoute>();
                for (Iterator it = nodes.iterator(); it.hasNext();) {
                    Element elm = (Element) it.next();
                    TransRoute expressRadarDetail = new TransRoute();
                    expressRadarDetail.setExpressCode(radarTransNo.getExpressCode());
                    expressRadarDetail.setMessage(elm.attributeValue("remark"));
                    expressRadarDetail.setAddress(elm.attributeValue("accept_address"));
                    expressRadarDetail.setOperateTime(DateUtil.parse(elm.attributeValue("accept_time")));
                    expressRadarDetail.setOpcode(elm.attributeValue("opcode"));
                    expressRadarDetail.setLastModifyTime(new Date());
                    expressRadarDetail.setRadarTransNo(radarTransNo);
                    expressRadarDetailList.add(expressRadarDetail);
                }
                return expressRadarDetailList;
			} catch (Exception e) {
                log.debug("单号为" + radarTransNo.getExpressCode() + "的顺丰快递信息解析失败！");
			}
		}
		return null;
	}
	
	/**
	 * 解析申通的路由信息
	 * @param expressInfo
	 * @param radarTransNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
    public static List<TransRoute> getTransRouteBySTO(String expressInfo,RadarTransNo radarTransNo) {
		if(expressInfo!=null&&radarTransNo!=null){
			
            try {

                Document document = DocumentHelper.parseText(expressInfo);
                Element rootElm = document.getRootElement();
                Element memberElm = rootElm.element("track");
                List nodes = memberElm.elements("detail");
                List<TransRoute> expressRadarDetailList = new ArrayList<TransRoute>();
                String address = "";
                for (Iterator it = nodes.iterator(); it.hasNext();) {
                    Element elm = (Element) it.next();
                    TransRoute expressRadarDetail = new TransRoute();
                    // expressRadarDetail.setExpressCode(expressInfo);

                    expressRadarDetail.setMessage(elm.element("memo").getText());
                    expressRadarDetail.setOperateTime(DateUtil.parse(elm.element("time").getText().replace("/", "-")));
                    expressRadarDetail.setOpcode(elm.element("scantype").getText());
                    expressRadarDetail.setLastModifyTime(new Date());
                    expressRadarDetail.setRadarTransNo(radarTransNo);
                    expressRadarDetail.setExpressCode(radarTransNo.getExpressCode());
                    expressRadarDetail.setLastModifyTime(new Date());
                    // 用正则获取地址
                    Pattern pattern = Pattern.compile("【(.*?)】");
                    Matcher matcher = pattern.matcher(elm.element("memo").getText());
                    if (matcher.find()) {
                        if (!"草签".equals(matcher.group(1))) {
                            expressRadarDetail.setAddress(matcher.group(1));
                            address = matcher.group(1);
                        } else {
                            expressRadarDetail.setAddress(address);
                        }
                    }

                    expressRadarDetailList.add(expressRadarDetail);
                }
                return expressRadarDetailList;
            } catch (Exception e) {
                log.debug("单号为" + radarTransNo.getExpressCode() + "的申通快递信息解析失败！");
            }
		}
		return null;
	}
	
	/*public static void main(String[] args) {
		StringBuilder expressInfo=new StringBuilder();
		expressInfo.append("{'traces':[																															");
		expressInfo.append("       	{'acceptTime':'2015-03-02  18:24:00','acceptAddress':'西安速递高新揽投部','remark':'收寄'},                                 ");
		expressInfo.append("       	{'acceptTime':'2015-03-02  18:24:43','acceptAddress':'西安速递高新揽投部','remark':'揽收'},                                 ");
		expressInfo.append("       	{'acceptTime':'2015-03-02  18:31:37','acceptAddress':'西安速递高新揽投部','remark':'离开处理中心,发往西安邮件处理中心'},    ");
		expressInfo.append("       	{'acceptTime':'2015-03-03  02:07:43','acceptAddress':'西安邮件处理中心','remark':'到达处理中心,来自西安速递高新揽投部'},    ");
		expressInfo.append("       	{'acceptTime':'2015-03-03  04:49:49','acceptAddress':'西安邮件处理中心','remark':'离开处理中心,发往乌鲁木齐市'},            ");
		expressInfo.append("       	{'acceptTime':'2015-03-06  18:09:10','acceptAddress':'乌鲁木齐市','remark':'到达处理中心,来自西安邮件处理中心'},            ");
		expressInfo.append("       	{'acceptTime':'2015-03-06  18:55:12','acceptAddress':'乌鲁木齐市','remark':'离开处理中心,发往乌鲁木齐分公司沙依巴克区揽投部'},");
		expressInfo.append("       	{'acceptTime':'2015-03-07  09:00:33','acceptAddress':'乌鲁木齐分公司沙依巴克区揽投部','remark':'到达处理中心,来自乌鲁木齐市'},");
		expressInfo.append("       	{'acceptTime':'2015-03-07  09:30:00','acceptAddress':'乌鲁木齐分公司沙依巴克区揽投部','remark':'安排投递'},                 ");
		expressInfo.append("       	{'acceptTime':'2015-03-07  13:46:56','acceptAddress':'乌鲁木齐分公司沙依巴克区揽投部','remark':'妥投'}                      ");
		expressInfo.append(" ]}                                                                                                                                 ");
		//List<TransRoute> expressRadarDetailList=ExpressInfoParseUtils.getTransRouteByEMS(expressInfo.toString(), null);
		//System.out.println(expressRadarDetailList.toString());
		StringBuilder expressInfo2=new StringBuilder();
		expressInfo2.append("<?xml version='1.0' encoding='UTF-8'?><root>                                                                                                                                         ");
		expressInfo2.append("	<track>                                                                                                                                     ");
		expressInfo2.append("		<billcode>220077149760</billcode>                                                                                                       ");
		expressInfo2.append("		<detail><time>2014-10-18 16:45:55</time><scantype>收件</scantype><memo>【江苏南通公司】的收件员【陈光军】已收件</memo></detail>         ");
		expressInfo2.append("		<detail><time>2014-10-18 18:32:46</time><scantype>收件</scantype><memo>【江苏南通公司】的收件员【江苏南通公司十三区】已收件</memo></detail>");
		expressInfo2.append("		<detail><time>2014-10-18 18:34:31</time><scantype>发件</scantype><memo>由【江苏南通公司】发往【江苏南通中转部】</memo></detail>         ");
		expressInfo2.append("		<detail><time>2014-10-18 18:34:31</time><scantype>装袋</scantype><memo>【江苏南通公司】正在进行【装袋】扫描</memo></detail>             ");
		expressInfo2.append("		<detail><time>2014-10-19 01:57:49</time><scantype>发件</scantype><memo>由【浙江嘉兴中转部】发往【浙江桐乡公司】</memo></detail>         ");
		expressInfo2.append("		<detail><time>2014-10-19 08:06:46</time><scantype>到件</scantype><memo>快件已到达【浙江桐乡公司】</memo></detail>                       ");
		expressInfo2.append("		<detail><time>2014-10-19 10:01:54</time><scantype>派件</scantype><memo>【浙江桐乡公司】的派件员【濮院杨仁义0219】正在派件</memo></detail>");
		expressInfo2.append("		<detail><time>2014-10-19 11:31:24</time><scantype>签收</scantype><memo>已签收,签收人是:【草签】</memo></detail>                         ");
		expressInfo2.append("	</track>                                                                                                                                    ");
		expressInfo2.append("</root>                                                                                                                                        ");
		
		expressInfo2.append("<?xml version='1.0' encoding='UTF-8'?><root><track><billcode>220077149760</billcode><detail><time>2014-10-18 16:45:55</time><scantype>收件</scantype><memo>【江苏南通公司】的收件员【陈光军】已收件</memo></detail><detail><time>2014-10-18 18:32:46</time><scantype>收件</scantype><memo>【江苏南通公司】的收件员【江苏南通公司十三区】已收件</memo></detail><detail><time>2014-10-18 18:34:31</time><scantype>发件</scantype><memo>由【江苏南通公司】发往【江苏南通中转部】</memo></detail><detail><time>2014-10-18 18:34:31</time><scantype>装袋</scantype><memo>【江苏南通公司】正在进行【装袋】扫描</memo></detail><detail><time>2014-10-19 01:57:49</time><scantype>发件</scantype><memo>由【浙江嘉兴中转部】发往【浙江桐乡公司】</memo></detail><detail><time>2014-10-19 08:06:46</time><scantype>到件</scantype><memo>快件已到达【浙江桐乡公司】</memo></detail><detail><time>2014-10-19 10:01:54</time><scantype>派件</scantype><memo>【浙江桐乡公司】的派件员【濮院杨仁义0219】正在派件</memo></detail><detail><time>2014-10-19 11:31:24</time><scantype>签收</scantype><memo>已签收,签收人是:【草签】</memo></detail></track></root>");
		List<TransRoute> expressRadarDetailList2=ExpressInfoParseUtils.getTransRouteBySTO(expressInfo2.toString(), null);
		System.out.println(expressRadarDetailList2.toString());
	}*/
}
