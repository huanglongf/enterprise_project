package com.jumbo.web.manager.print;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import loxia.support.jasperreport.BasicJasperPrinter;
import loxia.support.jasperreport.JasperReportNotFoundException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * IO字节流加载Jasper文件，获取非项目内路径下的模板
 * @author jinggang.chen
 *
 */
public class ClasspathJasperPrinter extends BasicJasperPrinter {

    /**
     * 
     */
    private static final long serialVersionUID = 8715474238395858959L;

    private String reportName;

    public ClasspathJasperPrinter(String reportName) {
        this.reportName = reportName;
    }

    @Override
    protected JasperReport getJasperReport() throws JasperReportNotFoundException {
        InputStream is = null;
        try {
            File file = new File(reportName);
            is = new FileInputStream(file);
            return (JasperReport) JRLoader.loadObject(is);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JasperReportNotFoundException(e);
        } finally {
            if (is != null) try {
                is.close();
            } catch (Exception e) {}
        }
    }

}
