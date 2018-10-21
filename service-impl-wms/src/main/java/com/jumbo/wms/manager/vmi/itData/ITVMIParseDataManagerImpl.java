package com.jumbo.wms.manager.vmi.itData;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.itData.DimensionDao;
import com.jumbo.dao.vmi.itData.ITBrandDao;
import com.jumbo.dao.vmi.itData.ITSkuDao;
import com.jumbo.dao.vmi.itData.PluDao;
import com.jumbo.dao.vmi.itData.PluPriceDao;
import com.jumbo.dao.vmi.itData.SeasonDao;
import com.jumbo.dao.vmi.itData.SkuPriceDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.UnicodeReader;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.model.vmi.itData.Dimension;
import com.jumbo.wms.model.vmi.itData.ITBrand;
import com.jumbo.wms.model.vmi.itData.ITSku;
import com.jumbo.wms.model.vmi.itData.Plu;
import com.jumbo.wms.model.vmi.itData.PluPrice;
import com.jumbo.wms.model.vmi.itData.Season;
import com.jumbo.wms.model.vmi.itData.SkuPrice;

import cpdetector.io.CodepageDetectorProxy;
import cpdetector.io.JChardetFacade;

@Transactional
@Service("iTVMIParseDataManager")
public class ITVMIParseDataManagerImpl extends BaseManagerImpl implements ITVMIParseDataManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7921429239218847187L;

    @Autowired
    private ITBrandDao brandDao;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private DimensionDao dimensionDao;
    @Autowired
    private SeasonDao seasonDao;
    @Autowired
    private PluDao pluDao;
    @Autowired
    private ITSkuDao itSkuDao;
    @Autowired
    private PluPriceDao pluPriceDao;
    @Autowired
    private SkuPriceDao skuPriceDao;

    public String getITDATA(String name) {
        Map<String, String> config = configManager.getVMIFTPConfig();
        String value = "";
        if (config != null) {
            value = config.get(name);
        }

        return value;
    }

    // public String getFinishGoalDir(File file){
    // String dir = getITDATA(Constants.VMI_FTP_DOWN_LOCALPATH);
    // if(dir != null && !dir.equals("")){
    // dir += "/" + Constants.IT_FTP_FINISH_DIR + "/" + getDate() +"/"+
    // file.getParentFile().getName();
    // }
    // return dir;
    // }

    private String toHexString(byte b) {
        String temp = Integer.toHexString(b);
        if (temp.length() >= 6) {
            return temp.substring(6).toUpperCase();
        }
        return "";

    }

    public boolean isBom(File file) {
        boolean flag = false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String line = br.readLine();
            if (line != null) {
                byte[] allbytes = line.getBytes("utf-8");
                if (allbytes.length > 3) {
                    if (toHexString(allbytes[0]).equals("EF") && toHexString(allbytes[1]).equals("BB") && toHexString(allbytes[2]).equals("BF")) {
                        flag = true;
                    }
                }
            }
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }

        return flag;
    }

    /**
     * 
     * getFileCharacterEnding:获取文件的编码
     * 
     * @param @param file
     * @param @return 设定文件
     * @return String DOM对象
     * @throws
     * @since CodingExample　Ver 1.1
     */
    public String getFileCharacterEnding(File file) {

        String fileCharacterEnding = "UTF-8";
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(JChardetFacade.getInstance());
        Charset charset = null;
        try {
            charset = detector.detectCodepage(file.toURI().toURL());
        } catch (Exception e) {
            log.error("", e);
        }
        if (charset != null) {
            fileCharacterEnding = charset.name();
        }

        return fileCharacterEnding;
    }

    /**
     * 获取前一天时间
     * 
     * @return
     */
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar c = Calendar.getInstance();
        Date today = new Date();
        c.setTime(today);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        Date yesterday = c.getTime();
        return sdf.format(yesterday);
    }

    public boolean removeFile(File file) {
        return FileUtil.deleteFile(file.getAbsolutePath());
    }


    public int importBrand(File brandFile, String batch, String bakDir) {
        int lenth = 0;
        if (!brandFile.exists()) {
            log.error("brand file is not exist");
            throw new BusinessException(0, "brand file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + brandFile.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(brandFile.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("brand file copy error");
        }
        if (flag) {
            String fileName = brandFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            boolean isbom = isBom(brandFile);
            String character = getFileCharacterEnding(brandFile);
            BufferedReader br = null;
            try {
                if (isbom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                }
                String line = null;
                ITBrand brand = null;
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split(",");
                    if (items.length != 4) {
                        log.debug(getDate() + "BrandFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    brand = new ITBrand();
                    brand.setCode(items[0].replace("\"", "").trim());
                    brand.setName(items[1].replace("\"", "").trim());
                    String md = items[2].replace("\"", "".trim());
                    if (md == null || md.equals("")) {
                        md = "0";
                    }
                    brand.setMaxdisc(new BigDecimal(md));
                    brand.setUpdFlag(items[3].replace("\"", "").trim());
                    brand.setFlag(0);
                    brand.setBatchNum(batch);
                    brandDao.save(brand);
                    lenth++;
                }

                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    removeFile(brandFile);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }

            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT importBrand Exception:", e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return lenth;
    }

    public int importDimension(File file, String batch, String bakDir) {
        int lenth = 0;
        if (!file.exists()) {
            log.error("Dimension file is not exist！");
            throw new BusinessException(0, "Dimension file is not exist！");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + file.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(file.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("Dimension file copy error");
        }
        if (flag) {
            boolean isbom = isBom(file);
            String character = getFileCharacterEnding(file);
            BufferedReader br = null;
            String fileName = file.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                if (isbom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                }
                String line = null;
                Dimension ds = null;
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;

                    String items[] = line.split(",");
                    if (items.length != 6) {
                        log.debug(getDate() + "DimensionFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    ds = new Dimension();
                    String dp = items[0].replace("\"", "").trim();
                    if (dp != null && dp != "") {

                        Integer temp = Integer.valueOf(dp);
                        ds.setDimPos(temp);
                    }

                    ds.setDimgrpCode(items[1].replace("\"", "").trim());
                    ds.setDimCode(items[2].replace("\"", "").trim());
                    ds.setDimName(items[3].replace("\"", "").trim());
                    String showorder = items[4].replace("\"", "").trim();
                    if (showorder != null && !showorder.equals("")) {
                        Integer temp = Double.valueOf(showorder).intValue();
                        ds.setShowOrder(temp);
                    }
                    ds.setUpdFlag(items[5].replace("\"", "").trim());
                    ds.setFlag(0);
                    ds.setBatchNum(batch);
                    dimensionDao.save(ds);
                    lenth++;

                }

                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    removeFile(file);
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                // 处理完的文件转移到另一个文件夹
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT importDimension Exception:", e2);
                    }
                }

            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return lenth;
    }

    public int importSeason(File file, String batch, String bakDir) {
        int lenth = 0;
        if (!file.exists()) {
            log.error("season file is not exist");
            throw new BusinessException(0, "season file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + file.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(file.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("Season file copy error！");
        }
        if (flag) {
            boolean isbom = isBom(file);
            String character = getFileCharacterEnding(file);
            BufferedReader br = null;
            String fileName = file.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                if (isbom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                }
                String line = null;
                Season season = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                int lineNum = 0;

                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split(",");
                    if (items.length != 4) {
                        log.debug(getDate() + "SeasonFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    season = new Season();
                    season.setCode(items[0].replace("\"", "").trim());
                    season.setShortCode(items[1].replace("\"", "").trim());
                    season.setUpdFlag(items[2].replace("\"", "").trim());
                    String expdate = items[3].replace("\"", "").trim();
                    if (expdate != null && !expdate.equals("")) {
                        Date date = null;
                        try {
                            date = sdf.parse(expdate);
                        } catch (ParseException e) {
                            log.error("", e);
                        }
                        if (date != null) {
                            season.setExpDate(date);
                        }
                    }
                    season.setFlag(0);
                    season.setBatchNum(batch);
                    seasonDao.save(season);
                    lenth++;
                }

                try {
                    br.close();
                    removeFile(file);
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT importSeason Exception:", e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return lenth;
    }


    public int importPlu(File file, String batch, String bakDir) {
        int lenth = 0;
        if (!file.exists()) {
            log.error("plu file is not exist");
            throw new BusinessException(0, "plu file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + file.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(file.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("Plu File copy error！");
        }
        if (flag) {
            String character = getFileCharacterEnding(file);
            boolean isBom = isBom(file);
            BufferedReader br = null;
            String fileName = file.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                if (isBom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    if (character.toUpperCase().equals("BIG5")) {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile)));
                    } else {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                    }
                }
                String line = null;
                Plu plu = null;
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split(",");
                    String pluNo = items[0];
                    if (pluNo != null) {
                        String last = line.substring(pluNo.length() + 1);
                        String items2[] = last.split("\",");
                        if (items2.length != 28) {
                            log.debug(getDate() + "PluFile line (" + lineNum + ") read error ");
                            continue;
                        }
                        plu = new Plu();
                        //
                        if (pluNo != null && !pluNo.equals("")) {
                            plu.setPLUNo(new Long(pluNo));
                        }
                        plu.setPLUKO(items2[0].replace("\"", "").trim());
                        plu.setName(items2[1].replace("\"", "").trim());
                        plu.setSuppCode(items2[2].replace("\"", "").trim());
                        plu.setBrand(items2[3].replace("\"", "").trim());

                        plu.setSeason(items2[4].replace("\"", "").trim());
                        plu.setDimGrpCode1(items2[5].replace("\"", "").trim());
                        plu.setDimGrpCode2(items2[6].replace("\"", "").trim());
                        plu.setDimGrpCode3(items2[7].replace("\"", "").trim());
                        plu.setAttrCode1(items2[8].replace("\"", "").trim());
                        plu.setAttrCode2(items2[9].replace("\"", "").trim());
                        plu.setAttrCode3(items2[10].replace("\"", "").trim());
                        plu.setAttrCode4(items2[11].replace("\"", "").trim());
                        plu.setAttrCode5(items2[12].replace("\"", "").trim());
                        plu.setAttrCode6(items2[13].replace("\"", "").trim());
                        plu.setAttrCode7(items2[14].replace("\"", "").trim());
                        plu.setAttrCode8(items2[15].replace("\"", "").trim());
                        plu.setAttrCode9(items2[16].replace("\"", "").trim());
                        plu.setAttrCode10(items2[17].replace("\"", "").trim());
                        plu.setSuppRef(items2[18].replace("\"", "").trim());
                        String item19 = items2[19];
                        String items3[] = item19.split(",");
                        if (items3.length != 3) {
                            log.debug(getDate() + "PluFile line (" + lineNum + ") read error ");
                            continue;
                        }
                        String maxdisc = items3[0].replace("\"", "").trim();
                        if (maxdisc != null && !maxdisc.equals("")) {
                            plu.setMaxDisc(new BigDecimal(maxdisc));
                        }
                        String isgift = items3[1].replace("\"", "").trim();
                        if (isgift != null && !isgift.equals("")) {
                            Integer ig = Double.valueOf(isgift).intValue();
                            plu.setIsGift(ig);
                        }
                        plu.setType(items3[2].replace("\"", "").trim());
                        String item20 = items2[20];
                        String items4[] = item20.split(",");
                        if (items4.length != 4) {
                            log.debug(getDate() + "PluFile line (" + lineNum + ") read error ");
                            continue;
                        }
                        String status = items4[0].replace("\"", "").trim();
                        if (status != null && !status.equals("")) {
                            Integer sta = Double.valueOf(status).intValue();
                            plu.setStatus(sta);
                        }
                        String running = items4[1].replace("\"", "").trim();
                        if (running != null && !running.equals("")) {
                            Integer run = Double.valueOf(running).intValue();
                            plu.setRunning(run);
                        }
                        String wc = items4[2].replace("\"", "").trim();
                        if (wc != null && !wc.equals("")) {
                            Integer temp = Double.valueOf(wc).intValue();
                            plu.setWebCollection(temp);
                        }
                        plu.setUpdFlag(items4[3].replace("\"", "").trim());
                        plu.setAttrCode11(items2[21].replace("\"", "").trim());
                        plu.setAttrCode12(items2[22].replace("\"", "").trim());
                        plu.setAttrCode13(items2[23].replace("\"", "").trim());
                        plu.setAttrCode14(items2[24].replace("\"", "").trim());
                        plu.setAttrCode15(items2[25].replace("\"", "").trim());
                        String item26 = items2[26];
                        String items5[] = item26.split(",");
                        if (items5.length != 2) {
                            log.debug(getDate() + "PluFile line (" + lineNum + ") read error ");
                            continue;
                        }
                        String CarryFwdr = items5[0].replace("\"", "").trim();
                        if (CarryFwdr != null && !CarryFwdr.equals("")) {
                            Integer temp = Double.valueOf(CarryFwdr).intValue();
                            plu.setCarryFwdr(temp);
                        }
                        plu.setCfSeason(items5[1].replace("\"", "").trim());
                        String item27 = items2[27];
                        String items6[] = item27.split(",");

                        String linkType = items6[0].replace("\"", "").trim();
                        if (linkType != null && !linkType.equals("")) {
                            Integer temp = Double.valueOf(linkType).intValue();
                            plu.setLinkType(temp);
                        }
                        if (items6.length == 2) {
                            plu.setProdName(items6[1].replace("\"", "").trim());
                        }
                        plu.setFlag(0);
                        plu.setBatchNum(batch);
                        pluDao.save(plu);
                        lenth++;
                    }
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT importPlu Exception:" + batch, e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            try {
                removeFile(file);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return lenth;
    }

    public int importSKU(File file, String batch, String bakDir) {
        int lenth = 0;
        if (!file.exists()) {
            log.error("sku file is not exist");
            throw new BusinessException(0, "sku file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + file.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(file.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("SKU file copy error！");
        }
        if (flag) {
            boolean isbom = isBom(file);
            String character = getFileCharacterEnding(file);
            String fileName = file.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            BufferedReader br = null;
            try {
                if (isbom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                }
                String line = null;
                ITSku sku = null;
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split(",");
                    if (items.length != 4) {
                        log.debug(getDate() + "SKUFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    sku = new ITSku();
                    String skno = items[0].replace("\"", "").trim();
                    if (skno != null && !skno.equals("")) {
                        sku.setSKNO(new Long(skno));
                    }
                    sku.setSkuKo(items[1].replace("\"", "").trim());
                    String pluNo = items[2].replace("\"", "").trim();
                    if (pluNo != null && !pluNo.equals("")) {
                        Long pn = new Long(pluNo);
                        sku.setPluNo(pn);
                    }

                    sku.setUpdFlag(items[3].replace("\"", "").trim());

                    sku.setFlag(0);
                    sku.setBatchNum(batch);
                    itSkuDao.save(sku);
                    lenth++;
                }
                try {
                    br.close();
                    removeFile(file);
                } catch (Exception e) {
                    log.error("", e);
                }

            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT importSKU Exception:" + batch, e2);
                    }
                }

            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return lenth;
    }

    public int importPLUPrice(File file, String batch, String bakDir) {
        int lenth = 0;
        if (!file.exists()) {
            log.error("pluprice file is not exist");
            throw new BusinessException(0, "pluprice file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + file.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(file.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("PLUPrice File copy error！");
        }
        if (flag) {
            boolean isbom = isBom(file);
            String character = getFileCharacterEnding(file);
            String fileName = file.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            BufferedReader br = null;
            try {
                if (isbom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                }
                String line = null;
                PluPrice pluPrice = null;
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split(",");
                    if (items.length != 6) {
                        log.debug(getDate() + "PLUPriceFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    pluPrice = new PluPrice();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String effDate = items[0].replace("\"", "").trim();
                    if (effDate != null && !effDate.equals("")) {
                        Date date = null;
                        try {
                            date = sdf.parse(effDate);
                        } catch (ParseException e) {
                            log.error("", e);
                        }
                        if (date != null) {
                            pluPrice.setEffDate(date);
                        }
                    }
                    pluPrice.setPLUKo(items[1].replace("\"", "").trim());
                    String retailPrice = items[2].replace("\"", "").trim();
                    if (retailPrice != null && !retailPrice.equals("")) {
                        BigDecimal rp = null;
                        try {
                            rp = new BigDecimal(retailPrice);
                        } catch (Exception e) {

                        }
                        if (rp != null) {
                            pluPrice.setRetailPrice(rp);
                        }

                    }
                    String proPrice = items[3].replace("\"", "").trim();
                    if (proPrice != null && !proPrice.equals("")) {
                        pluPrice.setProPrice(new BigDecimal(proPrice));
                    }
                    String prePrice = items[4].replace("\"", "").trim();
                    if (prePrice != null && !prePrice.equals("")) {
                        pluPrice.setPrePrice(new BigDecimal(prePrice));
                    }
                    String mode = items[5].replace("\"", "").trim();
                    if (mode != null && !mode.equals("")) {
                        Integer mo = Double.valueOf(mode).intValue();
                        pluPrice.setMode(mo);
                    }
                    pluPrice.setFlag(0);
                    pluPrice.setBatchNum(batch);
                    pluPriceDao.save(pluPrice);
                    lenth++;
                }
                try {
                    br.close();
                    removeFile(file);
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT importPLUPrice Exception:" + batch, e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return lenth;
    }

    public int importSkuPrice(File file, String batch, String bakDir) {
        int lenth = 0;
        if (!file.exists()) {
            log.error("skuprice file is not exist");
            throw new BusinessException(0, "skuprice file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + file.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(file.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("SkuPrice file copy error");
        }
        if (flag) {
            boolean isbom = isBom(file);
            String character = getFileCharacterEnding(file);
            String fileName = file.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            BufferedReader br = null;
            try {
                if (isbom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                }
                String line = null;
                SkuPrice skuPrice = null;
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split(",");
                    if (items.length != 6) {
                        log.debug(getDate() + "SkuPriceFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    skuPrice = new SkuPrice();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String effDate = items[0].replace("\"", "").trim();
                    if (effDate != null && !effDate.equals("")) {
                        Date date = null;
                        try {
                            date = sdf.parse(effDate);
                        } catch (ParseException e) {
                            log.error("", e);
                        }
                        if (date != null) {
                            skuPrice.setEffDate(date);
                        }
                    }
                    skuPrice.setSkuKO(items[1].replace("\"", "").trim());
                    String retailPrice = items[2].replace("\"", "").trim();
                    if (retailPrice != null && !retailPrice.equals("")) {
                        skuPrice.setRetailPrice(new BigDecimal(retailPrice));
                    }
                    String proPrice = items[3].replace("\"", "").trim();
                    if (proPrice != null && !proPrice.equals("")) {
                        skuPrice.setProPrice(new BigDecimal(proPrice));
                    }
                    String prePrice = items[4].replace("\"", "").trim();
                    if (prePrice != null && !prePrice.equals("")) {
                        skuPrice.setPrePrice(new BigDecimal(prePrice));
                    }
                    String mode = items[5].replace("\"", "").trim();
                    if (mode != null && !mode.equals("")) {
                        skuPrice.setMode(new Integer(mode));
                    }
                    skuPrice.setFlag(0);
                    skuPrice.setBatchNum(batch);
                    skuPriceDao.save(skuPrice);
                    lenth++;
                }
                try {
                    br.close();
                    removeFile(file);
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (IOException e) {
                log.error("", e);
                try {
                    removeFile(bakFile);
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT importSkuPrice Exception:" + batch, e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return lenth;
    }

    public void importByFileName(File file, String batch, String bakDir) {
        String fileName = file.getName();
        if (fileName.equals("nwbrand.del")) {
            importBrand(file, batch, bakDir);
        } else if (fileName.equals("nwdimtbl.del")) {
            importDimension(file, batch, bakDir);
        } else if (fileName.equals("nwseason.del")) {
            importSeason(file, batch, bakDir);
        } else if (fileName.equals("nwplu.del")) {
            importPlu(file, batch, bakDir);
        } else if (fileName.equals("nwsku.del")) {
            importSKU(file, batch, bakDir);
        } else if (fileName.equals("nwprice.del")) {
            importPLUPrice(file, batch, bakDir);
        } else if (fileName.equals("nwskuprice.del")) {
            importSkuPrice(file, batch, bakDir);
        }
    }

    // 调用存储过程，将生成的销售数据转变成OMS的销售数据

    public void callProcedure(String batch) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("batch", batch);
        SqlParameter[] sqlParameters = {new SqlParameter("batch", Types.VARCHAR)};
        brandDao.executeSp("sp_sychronization_itData", sqlParameters, params);
    }

    public void importProductInfo(String downDir, String bakDir, String batch) {
        if (downDir == null || downDir.equals("")) {
            return;
        }
        File dir = new File(downDir);
        File dirs[] = dir.listFiles();
        if (dirs == null || dirs.length == 0) {
            log.debug("Import Stock File Dir is not exist!");
            return;
        }
        for (File childDir : dirs) {
            if (childDir.isDirectory()) {
                List<File> fileList = FileUtil.traveFile(childDir.getAbsolutePath());
                for (File file : fileList) {
                    try {
                        // 读文件到数据库
                        importByFileName(file, batch, bakDir);

                    } catch (Exception e) {
                        log.error("", e);
                    }

                }
                brandDao.flush();
                List<File> test = FileUtil.traveFile(childDir.getAbsolutePath());
                if (test.size() == 0) {
                    com.jumbo.util.FileUtil.deleteFile(childDir.getAbsolutePath());
                }
                callProcedure(batch);
            }
        }
        File dirs2[] = dir.listFiles();
        if (dirs2.length == 0) {
            com.jumbo.util.FileUtil.deleteFile(downDir);
        }
    }


}
