package com.jumbo.web.action.warehouse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.print.PrintCustomizeManager;
import com.jumbo.wms.model.print.PrintCustomize;

/**
 * 
 * @author jinggang.chen 销售出库装箱清单上传
 */
public class PrintTempletUploadAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 5260153576278969050L;

    private Long id;// 主键

    private File mainJrxmlFile;// 主模板

    private File subJrxmlFile;// 子模板

    private File picture;// 图片

    private String storeCode;// 店铺

    private String templetCode;// 模板编码

    private String memo;// 备注

    private String mainJrxmlName;// 主模板文件名

    private String subJrxmlName;// 子模板文件名

    private String pictureName;// 图片名

    private int type;// 模板类型

    @Autowired
    private PrintCustomizeManager printCustomizeManager;

    public String uploadPrintTemplet() {
        return SUCCESS;
    }

    public String getPrintCustomizeList() {
        setTableConfig();
        Pagination<PrintCustomize> list = printCustomizeManager.findPzByStoreCode(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), storeCode);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 导入模板
     * 
     * @return
     */
    public String importPrintTemplet() {
        try {
            if ((mainJrxmlFile != null && subJrxmlFile != null)||id!=null) {
                printCustomizeManager.savePrintTemplet(templetCode, memo, mainJrxmlFile, subJrxmlFile, picture, mainJrxmlName, subJrxmlName, pictureName, id);
                request.put("msg", "保存成功");
            }
        } catch (Exception e) {
        	if(id==null) {
	            // 删除已经保存的文件
	            printCustomizeManager.deleteFile(mainJrxmlName, 1);
	            printCustomizeManager.deleteFile(subJrxmlName, 2);
	            printCustomizeManager.deleteFile(pictureName, 3);
        	}
            request.put("msg", e.getMessage());
        }
        return SUCCESS;
    }

    /**
     * 下载模板文件
     * 
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public String downLoadFile() throws JSONException, IOException {
        if (id != null) {
            List<String> pathList = printCustomizeManager.findPathById(id);
            try {
                File file = new File(pathList.get(type));
                String filename = file.getName();// 获取文件名称
                InputStream fis = new BufferedInputStream(new FileInputStream(pathList.get(type)));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
                response.addHeader("Content-Length", "" + file.length());
                OutputStream os = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                os.write(buffer);// 输出文件
                os.flush();
                os.close();
            } catch (Exception e) {
                log.error("模板下载异常", e);
            }
        }
        return null;
    }

    public String downPrintTemplet() {

        return SUCCESS;
    }

    public File getMainJrxmlFile() {
        return mainJrxmlFile;
    }


    public void setMainJrxmlFile(File mainJrxmlFile) {
        this.mainJrxmlFile = mainJrxmlFile;
    }


    public File getSubJrxmlFile() {
        return subJrxmlFile;
    }


    public void setSubJrxmlFile(File subJrxmlFile) {
        this.subJrxmlFile = subJrxmlFile;
    }


    public File getPicture() {
        return picture;
    }


    public void setPicture(File picture) {
        this.picture = picture;
    }


    public String getStoreCode() {
        return storeCode;
    }


    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }


    public String getTempletCode() {
        return templetCode;
    }


    public void setTempletCode(String templetCode) {
        this.templetCode = templetCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMainJrxmlName() {
        return mainJrxmlName;
    }

    public void setMainJrxmlName(String mainJrxmlName) {
        this.mainJrxmlName = mainJrxmlName;
    }

    public String getSubJrxmlName() {
        return subJrxmlName;
    }

    public void setSubJrxmlName(String subJrxmlName) {
        this.subJrxmlName = subJrxmlName;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
