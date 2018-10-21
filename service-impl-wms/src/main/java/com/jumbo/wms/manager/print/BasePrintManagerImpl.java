package com.jumbo.wms.manager.print;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import loxia.support.jasperreport.ClasspathJasperPrinter;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;

public class BasePrintManagerImpl extends BaseManagerImpl {

    private static final long serialVersionUID = 8274841917525134968L;
    
    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);

    /**
     * 获取打印结果集
     * 
     * @param list 数据集
     * @param jasperFullPath 模板路径
     * @return
     */
    @SuppressWarnings("rawtypes")
    public JasperPrint getJasperPrint(List list, String jasperFullPath) {
        JRDataSource dataSource = new JRBeanCollectionDataSource(list);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasperFullPath);
        try {
            cjp.initializeReport(new HashMap<String, Object>(), dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("",e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (JasperPrintFailureException e) {
            log.error("",e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

}
