package com.jumbo.wms.manager.print;

import java.io.File;
import java.io.IOException;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.PrintCustomizeCommand;
import com.jumbo.wms.model.print.PrintCustomize;

public interface PrintCustomizeManager extends BaseManager {

    PrintCustomizeCommand getPrintCustomizeByOwnerAndType(String owner, Integer printType);

    /**
     * 根据staid获取
     * 
     * @param staId
     * @param printType
     * @return
     */
    public PrintCustomizeCommand findPrintCustomizeByStaIdAndType(Long staId, Integer printType);


    /**
     * 根据箱ID获取
     * 
     * @param cartonId
     * @param printType
     * @return
     */
    public PrintCustomizeCommand findPrintCustomizeByCartonIdAndType(Long cartonId, Integer printType);

    /**
     * @param templetCode 模板编码
     * @param memo 备注
     * @param mianJrxml 主模板
     * @param subJrxml 子模板
     * @param picture 图片
     * @param mianJrxmlName 主模板文件名
     * @param subJrxmlName 子模板文件名
     * @param pictureName 图片名
     * @throws IOException
     */
    public void savePrintTemplet(String templetCode, String memo, File mianJrxml, File subJrxml, File picture, String mianJrxmlName, String subJrxmlName, String pictureName, Long id) throws Exception;

    /**
     * 根据店铺获取出库装箱单配置信息
     * 
     * @param storeCode
     * @return
     */
    public Pagination<PrintCustomize> findPzByStoreCode(int start, int size, Sort sort[], String storeCode);

    /**
     * 根据id获取主模版,子模板的下载路径
     * 
     * @param id
     * @return
     */
    public List<String> findPathById(Long id);

    /**
     * 获取所有打印编码code
     */
    public List<PrintCustomize> getAllPrintCode();

    /**
     * 根据code获取打印配置
     */
    public PrintCustomize findPrintCustomizeByPrintCode(String code);

    /**
     * 根据路径删除指定文件
     * 
     * @param url
     */
    public void deleteFile(String fileName, int type);
}
