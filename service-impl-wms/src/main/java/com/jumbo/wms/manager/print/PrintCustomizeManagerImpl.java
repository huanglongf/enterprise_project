package com.jumbo.wms.manager.print;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.print.PrintCustomizeDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.model.command.PrintCustomizeCommand;
import com.jumbo.wms.model.print.PrintCustomize;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.PrintCustomizeType;
import com.jumbo.wms.model.warehouse.StockTransApplication;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import net.sf.jasperreports.engine.JasperCompileManager;

@Transactional
@Service("printCustomizeManager")
public class PrintCustomizeManagerImpl implements PrintCustomizeManager {

    @Autowired
    private PrintCustomizeDao printCustomizeDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private CartonDao cartonDao;
    @Value("${print.packingList.jrxml}")
    private String templetJrxmlUrl;// jrxml文件存放路径
    @Value("${print.packingList.jasper}")
    private String templetJasperUrl;// Jasper文件存放路径
    @Value("${print.packingList.picture}")
    private String pictureUrl;// 图片存放路径

    /**
     * 
     */
    private static final long serialVersionUID = -7958849580224098397L;

    /**
     * 根据店铺OWNER和打印类型获取数据
     */
    @Override
    public PrintCustomizeCommand getPrintCustomizeByOwnerAndType(String owner, Integer printType) {
        return printCustomizeDao.getPrintCustomizeByOwnerAndType(owner, printType, new BeanPropertyRowMapper<PrintCustomizeCommand>(PrintCustomizeCommand.class));
    }

    /**
     * 根据staid获取
     * 
     * @param staId
     * @param printType
     * @return
     */
    public PrintCustomizeCommand findPrintCustomizeByStaIdAndType(Long staId, Integer printType) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        return getPrintCustomizeByOwnerAndType(sta.getOwner(), printType);
    }

    /**
     * 根据箱ID获取
     * 
     * @param cartonId
     * @param printType
     * @return
     */
    public PrintCustomizeCommand findPrintCustomizeByCartonIdAndType(Long cartonId, Integer printType) {
        Carton c = cartonDao.getByPrimaryKey(cartonId);
        return findPrintCustomizeByStaIdAndType(c.getSta().getId(), printType);
    }

    @Override
    public void savePrintTemplet(String templetCode, String memo, File mianJrxml, File subJrxml, File picture, String mianJrxmlName, String subJrxmlName, String pictureName, Long id) throws java.lang.Exception {
        // 验证模板编码是否已存在,若存在，抛异常
        PrintCustomize pz = printCustomizeDao.findPcBytempletCode(templetCode);
        if (pz != null && id == null) {
            throw new Exception("模板编码已存在:" + templetCode);
        }
        if (id == null) {
            // 保存主模板到服务器共享目录
            saveFile(mianJrxml, templetJrxmlUrl, mianJrxmlName, id, 1);
            // 编译主模板
            String mainJasperName = mianJrxmlName.replace(".jrxml", ".jasper");
            try {
                JasperCompileManager.compileReportToFile(templetJrxmlUrl + mianJrxmlName, templetJasperUrl + mainJasperName);
            } catch (Exception e) {
                throw new Exception("主模板编译失败:" + e.getMessage());
            }
            if (subJrxml != null) {
                // 保存子模板
                saveFile(subJrxml, templetJrxmlUrl, subJrxmlName, id, 2);
                // 编译子模板
                String subJasperName = subJrxmlName.replace(".jrxml", ".jasper");
                try {
                    JasperCompileManager.compileReportToFile(templetJrxmlUrl + subJrxmlName, templetJasperUrl + subJasperName);
                } catch (Exception e) {
                    throw new Exception("子模板编译失败:" + e.getMessage());
                }
            }
            if (picture != null) {
                // 保存图片
                saveFile(picture, pictureUrl, pictureName, id, 3);
            }
            // 生成对应打印策略
            PrintCustomize pc = new PrintCustomize();
            pc.setId(id);
            pc.setTempletCode(templetCode);
            pc.setMasterTemplet(mianJrxmlName.replace(".jrxml", ".jasper"));
            pc.setSubTemplet(subJrxmlName.replace(".jrxml", ".jasper"));
            pc.setMemo(memo);
            pc.setPrintType(PrintCustomizeType.OUTBOUND_PACKING_LIST);
            printCustomizeDao.save(pc);
        } else {
            pz = printCustomizeDao.getByPrimaryKey(id);
            // 保存主模板到服务器共享目录
            saveFile(mianJrxml, templetJrxmlUrl, mianJrxmlName, id, 1);
            // 编译主模板
            String mainJasperName = pz.getMasterTemplet();
            try {
                JasperCompileManager.compileReportToFile(templetJrxmlUrl + mainJasperName.replace(".jasper", ".jrxml"), templetJasperUrl + mainJasperName);
            } catch (Exception e) {
                throw new Exception("主模板编译失败:" + e.getMessage());
            }
            if (subJrxml != null) {
                // 保存子模板
                saveFile(subJrxml, templetJrxmlUrl, subJrxmlName, id, 2);
                // 编译子模板
                String subJasperName = pz.getSubTemplet();
                try {
                    JasperCompileManager.compileReportToFile(templetJrxmlUrl + subJasperName.replace(".jasper", ".jrxml"), templetJasperUrl + subJasperName);
                } catch (Exception e) {
                    throw new Exception("子模板编译失败:" + e.getMessage());
                }
            }
            if (picture != null) {
                // 保存图片
                saveFile(picture, pictureUrl, pictureName, id, 3);
            }
            pz.setMemo(memo);
            printCustomizeDao.save(pz);
        }
    }

    /**
     * 
     * @param file 上传的文件
     * @param dir 路径
     * @param fileName 文件名
     * @param type 类型 1.主模板，2.子模板，3.图片
     * @throws IOException
     * @throws Exception
     */

    private void saveFile(File file, String dir, String fileName, Long id, int type) throws IOException, Exception {
        File files = new File(dir + fileName);
        // 新增模板时，文件名重复，抛异常。
        if (files.exists() && id == null) {
            throw new Exception("文件名已存在:" + fileName);
        } else if (id != null) {// 修改模板时，文件名重复或文件名与就模板文件名不一致，抛异常
            PrintCustomize pz = printCustomizeDao.getByPrimaryKey(id);
            if (type == 1) {
                fileName = pz.getMasterTemplet().replace(".jasper", ".jrxml");
            } else if (type == 2) {
                fileName = pz.getSubTemplet().replace(".jasper", ".jrxml");
            }
        }
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(new File(dir + fileName));
        byte[] read = new byte[2048];
        int len = 0;
        while ((len = fis.read(read)) != -1) {
            fos.write(read, 0, len);
        }
        fos.flush();
        fis.close();
        fos.close();
    }

    @Override
    public Pagination<PrintCustomize> findPzByStoreCode(int start, int size, Sort sort[], String storeCode) {
        String owner = null;
        if (!StringUtil.isEmpty(storeCode)) {
            owner = "%" + storeCode + "%";
        }
        return printCustomizeDao.findPrintCustomizeByOwner(start, size, sort, owner, new BeanPropertyRowMapper<PrintCustomize>(PrintCustomize.class));
    }

    @Override
    public List<String> findPathById(Long id) {
        PrintCustomize pz = printCustomizeDao.getByPrimaryKey(id);
        List<String> pathList = null;
        if (pz != null) {
            pathList = new ArrayList<String>();
            String mainTemplet = templetJrxmlUrl + pz.getMasterTemplet().replace(".jasper", ".jrxml");
            pathList.add(mainTemplet);
            if (pz.getSubTemplet() != null) {
                String subTemplet = templetJrxmlUrl + pz.getSubTemplet().replace(".jasper", ".jrxml");
                pathList.add(subTemplet);
            }
        }
        return pathList;
    }

    @Override
    public List<PrintCustomize> getAllPrintCode() {
        return printCustomizeDao.findPcCode(new BeanPropertyRowMapper<PrintCustomize>(PrintCustomize.class));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PrintCustomize findPrintCustomizeByPrintCode(String code) {
        PrintCustomize pc = printCustomizeDao.findPcBytempletCode(code);
        if (pc != null) {
            pc.setMasterTemplet(templetJasperUrl + pc.getMasterTemplet());
            pc.setSubTemplet(templetJasperUrl + pc.getSubTemplet());
        }
        return pc;
    }

    @Override
    public void deleteFile(String fileName, int type) {
        File file = null;
        if (type == 1) {
            file = new File(templetJrxmlUrl + fileName);
        } else if (type == 2) {
            file = new File(templetJasperUrl + fileName);
        } else if (type == 3) {
            file = new File(pictureUrl + fileName);
        }
        if (file.exists()) {
            file.delete();
        }
    }

}
