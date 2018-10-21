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
package com.jumbo.web.action.warehouse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 收货暂存区补货建议
 * 
 * @author sjk
 * 
 */
public class RecevingMoveSuggestAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -4285060542290211910L;

    @Autowired
    private ExcelExportManager excelExportManager;
    private StvLineCommand cmd;
    private String fileName;

    public String recevingMoveSugEnrty() {
        return SUCCESS;
    }

    /**
     * 导出补货建议
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    public String suggestMove() throws UnsupportedEncodingException {
        String fname = getMessage("excel.tplt_receving_move_suggest");
        setFileName(new String(fname.getBytes("GBK"), "ISO8859-1") + Constants.EXCEL_XLS);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelExportManager.recevingMoveSuggestExport(out, cmd, userDetails.getCurrentOu().getId());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        setInputStream(in);
        return SUCCESS;
    }

    public StvLineCommand getCmd() {
        return cmd;
    }

    public void setCmd(StvLineCommand cmd) {
        this.cmd = cmd;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
