package com.bt.orderPlatform.ftpupload;

import com.bt.common.util.FTPUtil;
import com.bt.orderPlatform.model.*;
import com.bt.orderPlatform.service.*;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * @Description: 
 * @author  Hanery:
 * @date 2017年7月21日 上午9:28:03  
 */
@Service
public class FTPFileUpload {

    @Resource(name = "ftpFileNameServiceImpl")
    private FtpFileNameService<FtpFileName> ftpFileNameService;
    @Resource(name = "waybillMasterServiceImpl")
    private WaybillMasterService<WaybillMaster> waybillMasterServiceImpl;
    @Resource(name = "waybilMasterDetailServiceImpl")
    private WaybilMasterDetailService<WaybilMasterDetail> waybilMasterDetailService;
    @Resource(name = "organizationInformationServiceImpl")
    private OrganizationInformationService<OrganizationInformation> organizationInformationService;
    @Resource(name = "waybillMasterBackupsServiceImpl")
    private WaybillMasterBackupsService<WaybillMasterBackups> waybillMasterBackupsService;
    @Resource(name = "waybilDetailServiceImpl")
    private WaybilDetailService<WaybilDetail> waybilDetailService;
    @Resource(name = "waybilDetailBackupsServiceImpl")
    private WaybilDetailBackupsService<WaybilDetail> waybilDetailBackupsService;

    public static final Map<String,Object> LOCK_MAP = new ConcurrentHashMap<String,Object>(8);


    public void  FTPFile() throws Exception{
        try {
            FtpFileName f = new FtpFileName();
            /*String hostname = "10.7.46.46";
            int port = 21;
            String username = "op_user01";
            String password = "baozun123";
            String pathname = "file";
            String localpath = "d:\\ftp";*/
            String hostname = "167.64.85.193"; 
            int port = 21;
            String username = "custprog";
            String password = "Fd26e3jm";
            String pathname = "/users/CUSTPROG/custshoe_v/china";
            String localpath = "/home/op_files/ftp/";
//                      String localpath = "D:/";
            boolean isdelete = false;//校验是否删除数据库标识
            Date date = new Date();
            OrganizationInformation organizationInformation = organizationInformationService.selectById("5");
            //获取路径下所有文件名


            //          List<String> fileList = FTPUtil.getFileList(hostname, port, username, password, pathname);
            //          List<String> fileList = downloadFiles(hostname, port, username, password, pathname,localpath);
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.setControlEncoding("GBK");
                //连接FTP服务器
                ftpClient.connect(hostname, port);
                //登录FTP服务器
                ftpClient.login(username, password);
                //验证FTP服务器是否登录成功
                int replyCode = ftpClient.getReplyCode();
                if(!FTPReply.isPositiveCompletion(replyCode)){
                    System.out.println("ftpupload------debug："+"登陆");
                    return;
                }
                List<Map> checkSign = ftpFileNameService.checkSign();//判断文件是否存在 表示是否有其他线程  正在执行该操作
                System.out.println("ftpupload------debug："+"校验是否有任务在跑该文件"+checkSign.size());
                if (checkSign.size()>0) {//文件存在则不继续操作
                    return;
                }else{
                    isdelete = true;
                    //切换FTP目录
                    ftpClient.changeWorkingDirectory(pathname);
                    ftpClient.enterLocalPassiveMode();
                    ftpFileNameService.insertSign();
                } 


                FTPFile[] ftpFiles = ftpClient.listFiles();
                for(FTPFile file : ftpFiles){
                    if(!file.getName().equals(".")&&!file.getName().equals("..")&&!file.getName().endsWith(".load")){
                        String uuid = UUID.randomUUID().toString();
                        f.setFile_name(file.getName());
                        //匹配文件是否已经读取
                        //                        List<FtpFileName> list = ftpFileNameService.selectByfile_Name(f);
                        //                        if(list.size()==0){
                        List<WaybilMasterDetail> listwaybilMasterDetail = new ArrayList<WaybilMasterDetail>();
                        //就进行下载
                        //                       //下载
                        System.out.println("ftpupload------debug："+"准备下载"+file.getName());
                        if(file.getName().equalsIgnoreCase(file.getName())){
                            File localFile = new File(localpath + "/" + file.getName());
                            FileOutputStream os = new FileOutputStream(localFile);
                            ftpClient.retrieveFile(file.getName(), os);
                            os.close();
                        }


                        //复制到AlreadyRead文件夹下
                        ByteArrayInputStream in = null;
                        ByteArrayOutputStream fos = new ByteArrayOutputStream();  
                        ftpClient.retrieveFile(file.getName(),fos);
                        in = new ByteArrayInputStream(fos.toByteArray()); 
                        if (in != null) {  
                            ftpClient.changeWorkingDirectory("AlreadyRead.load");
                            ftpClient.storeFile(file.getName()+".load", in);  
                        }
                        if (in != null) {  
                            in.close();  
                        }  
                        if (fos != null) {  
                            fos.close();  
                        }  

                        ftpClient.changeWorkingDirectory(pathname);


                        System.out.println("ftpupload------debug："+"下载结束"+file.getName());
                        //下载到本地读取该文件
                        List<String[]> readFTPFile = ReadFTPFile(localpath, file.getName());
                        for (String[] strings : readFTPFile) {
                            String[] ftpFile = strings;
                            if(ftpFile.length>0){
                                //将文件保存到临时表
                                WaybilMasterDetail waybilMasterDetail = FTPFileToWaybilMasterDetail(ftpFile,uuid,organizationInformation);
                                waybilMasterDetail.setSku_name("Vans Custom Shoes");//这里不取直接写死
                                if(waybilMasterDetail!=null){
                                    listwaybilMasterDetail.add(waybilMasterDetail);
                                }
                                //
                            }
                        }
                        waybillMasterServiceImpl.insertWayBilMasterDetail(listwaybilMasterDetail);
                        System.out.println("ftpupload------debug："+"备份");
                        ftpClient.deleteFile(file.getName());//删除原文件                    
                        List<WaybilMasterDetail> selectByBatIdAndCustomer = waybilMasterDetailService.selectByBatIdAndCustomer(uuid);
                        for (WaybilMasterDetail waybilMasterDetail1 : selectByBatIdAndCustomer) {
                            WaybillMaster queryParam = new WaybillMaster();
                            queryParam.setFrom_orgnization(waybilMasterDetail1.getFrom_orgnization());
                            queryParam.setCustomer_number(waybilMasterDetail1.getCustomer_number());
                            List<WaybillMaster> selectByQuery = waybillMasterServiceImpl.selectByWaybillMaster(queryParam);
                            if(selectByQuery.size()!=0){
                                for (WaybillMaster waybillMaster : selectByQuery) {
                                    if(waybillMaster.getStatus().equals("1")||waybillMaster.getStatus().equals("0")){
                                        waybillMasterBackupsService.insert(waybillMaster);
                                        List<WaybilDetail> queryOrderByOrderId = waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id());
                                        waybilDetailBackupsService.insert(queryOrderByOrderId);
                                        waybilDetailService.deletByOrder_Id(waybillMaster.getOrder_id());
                                        waybillMasterServiceImpl.delete(waybillMaster.getId());
                                        waybillMasterServiceImpl.insetWaybilMasterDetailByCustomer(waybilMasterDetail1);
                                    }else{
                                        waybillMasterBackupsService.insetWaybilMasterDetailByCustomer(waybilMasterDetail1);
                                    }
                                }
                            }else{
                                waybillMasterServiceImpl.insetWaybilMasterDetailByCustomer(waybilMasterDetail1);
                            }

                        }
                        f.setCreate_time(date);
                        f.setUpdate_time(date);
                        f.setCreate_user("system");
                        f.setUpdate_user("system");
                        ftpFileNameService.insert(f);
                    }

                    //                    }
                }
            }catch (Exception e) {

                e.printStackTrace();
            } finally{
                if(ftpClient.isConnected()){
                    try {
                        if(isdelete){
                            ftpFileNameService.deleteSign();   
                        }
                       
                        ftpClient.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String[]>  ReadFTPFile(String path,String file_name) throws Exception{
        List<String[]> list = new ArrayList<String[]>();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = "";
            fis = new FileInputStream(path+"/" +file_name);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new
            // InputStreamReader的对象
            while ((str = br.readLine()) != null) {
                //截取得到的一行数据
                String[] parms = str.split("\t");
                list.add(parms);
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
            return null;
        } catch (IOException e) {
            System.out.println("读取文件失败");
            return null;
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public WaybilMasterDetail  FTPFileToWaybilMasterDetail(String[] parms,String uuid,OrganizationInformation organizationInformation) throws Exception{
        WaybilMasterDetail waybilMasterDetail  = new WaybilMasterDetail();
        Date date = new Date();

        for (String string : parms) {
            String[] master = string.split("\",\"");
            if(master.length>30){
                UUID id = UUID.randomUUID();
                waybilMasterDetail.setId(id.toString());
                waybilMasterDetail.setBat_id(uuid);
                waybilMasterDetail.setCreate_time(date);
                waybilMasterDetail.setUpdate_time(date);
                waybilMasterDetail.setCreate_user(organizationInformation.getOrg_contacts());
                waybilMasterDetail.setFrom_contacts(organizationInformation.getOrg_contacts());
                waybilMasterDetail.setUpdate_user(organizationInformation.getOrg_contacts());
                waybilMasterDetail.setFrom_address(organizationInformation.getOrg_address());
                waybilMasterDetail.setFrom_city(organizationInformation.getOrg_city());
                waybilMasterDetail.setFrom_city_code(organizationInformation.getOrg_city_code());
                waybilMasterDetail.setFrom_orgnization_code(organizationInformation.getId());
                waybilMasterDetail.setFrom_orgnization(organizationInformation.getOrg_name());
                waybilMasterDetail.setFrom_phone(organizationInformation.getOrg_phone());
                waybilMasterDetail.setFrom_province(organizationInformation.getOrg_province());
                waybilMasterDetail.setFrom_province_code(organizationInformation.getOrg_province_code());
                waybilMasterDetail.setFrom_state(organizationInformation.getOrg_state());
                waybilMasterDetail.setFrom_state_code(organizationInformation.getOrg_state_code());
                waybilMasterDetail.setFrom_street(organizationInformation.getOrg_street());
                waybilMasterDetail.setStore_code(organizationInformation.getStore_code());
                waybilMasterDetail.setProducttype_code("1");
                waybilMasterDetail.setProducttype_name("顺丰标准");
                waybilMasterDetail.setExpress_name("顺丰");
                waybilMasterDetail.setExpress_code("SF");
                waybilMasterDetail.setPayment("月付");
                waybilMasterDetail.setPay_path("3");
                waybilMasterDetail.setNew_source(1);
                waybilMasterDetail.setStatus("1");
                waybilMasterDetail.setCustid(organizationInformation.getCustid());
                waybilMasterDetail.setTotal_weight(new BigDecimal(1));


                //waybilMasterDetail.setMemo(master[0].replaceAll("\"", "").trim());
                waybilMasterDetail.setTo_contacts(master[1].trim());
                waybilMasterDetail.setTo_address(master[2].trim()+master[3].trim()+master[4].trim());
                waybilMasterDetail.setTo_province(master[6].trim());
                waybilMasterDetail.setTo_city(master[5].trim());
                waybilMasterDetail.setTo_phone(master[9].trim());
                if(!master[12].trim().equals("")){
                    waybilMasterDetail.setTotal_weight(new BigDecimal(master[12].trim()));
                }
                //waybilMasterDetail.setSku_code(master[16].trim());
                waybilMasterDetail.setDescribes(master[16].trim()+","+master[0].replaceAll("\"", "").trim());
                waybilMasterDetail.setCustomer_number(master[15].trim());
                if(!master[18].trim().equals("")){
                    waybilMasterDetail.setTotal_qty((int)Float.parseFloat(master[18].trim().toString()));
                }
                if(!master[18].trim().equals("")){
                    waybilMasterDetail.setQty((int)Float.parseFloat(master[18].trim().toString()));
                }
                waybilMasterDetail.setSku_name(master[19].trim().replaceAll(" ","").replaceAll(",", "").replaceAll("%", ""));
                waybilMasterDetail.setSku_code(master[27].trim().replaceAll(" ","").replaceAll(",", "").replaceAll("%", ""));

                if(!master[28].trim().equals("")){
                    waybilMasterDetail.setWeight(new BigDecimal(master[28].trim()));
                }

                if(!master[29].trim().equals("")){
                    waybilMasterDetail.setAmount(new BigDecimal(master[29].trim()));
                }
            }else{
                return null;
            }
        }

        return waybilMasterDetail;
    }

    /**
     * 删除标识
     */
    @PostConstruct
    public void execute(){
        ftpFileNameService.deleteSign();
    }

}
