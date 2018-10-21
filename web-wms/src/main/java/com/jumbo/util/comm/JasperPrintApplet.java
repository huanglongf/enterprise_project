package com.jumbo.util.comm;

import java.applet.Applet;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.ContextClassLoaderObjectInputStream;
import net.sf.jasperreports.view.JasperViewer;

public class JasperPrintApplet extends Applet {

    /**
     * 
     */
    private static final long serialVersionUID = -4084467775319294885L;

    private URL url = null;

    public void printReprot(String reportURL) throws IOException {
        printReport(reportURL, false);
    }

    @SuppressWarnings("unchecked")
    public void previewReport(String reportURL) {
        try {
            if (reportURL.indexOf("://") > 0)
                url = new URL(reportURL);
            else
                url = new URL(getCodeBase(), reportURL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // URL existence
        if (url == null) {
            JOptionPane.showMessageDialog(this, "No printing needs correctly.");
            return;
        }

        try {
            System.out.println("Load print objects[" + url + "]");

            List<JasperPrint> printList = (List<JasperPrint>) loadObject(url);
            System.out.println("Print objects loading finished.");
            if (printList.size() > 0) {
                // preview first one
                final JasperPrint jasperPrint = printList.get(0);
                new Thread(new Runnable() {
                    @SuppressWarnings("rawtypes")
                    public void run() {
                        try {
                            AccessController.doPrivileged(new PrivilegedExceptionAction() {
                                public Object run() {
                                    JasperViewer.viewReport(jasperPrint, false);
                                    return null;
                                }
                            });
                        } catch (PrivilegedActionException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public String printReport(String reportURL, final boolean showPrintDlg, final String printerName) throws IOException {
        BufferedWriter br = null;
        String flag = "SUCCESS";
        try {
            // OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(new
            // File("C:\\outbound.txt"), true), "UTF-8");
            // br = new BufferedWriter(write);
            // br.write(reportURL);
            // br.newLine();
            if (reportURL.indexOf("://") > 0)
                url = new URL(reportURL);
            else
                url = new URL(getCodeBase(), reportURL);

            // URL existence
            if (url == null) {
                JOptionPane.showMessageDialog(this, "No printing needs correctly.");
                // br.write("No printing needs correctly.");
                // br.newLine();
                flag = "ERROR";
            }
            // br.write("Load print objects[" + url + "]");
            // br.newLine();
            // System.out.println("Load print objects[" + url + "]");
            @SuppressWarnings("unchecked")
            List<JasperPrint> printList = (List<JasperPrint>) loadObject(url);
            // br.write("Print objects loading finished.");
            // br.newLine();
            // System.out.println("Print objects loading finished.");
            if (printList.size() > 0) {
                // print
                // br.write("Print " + printList.size() + " files...");
                // br.newLine();
                // System.out.println("Print " + printList.size() + " files...");
                Thread thread = new PrintThread(printList, showPrintDlg, printerName);
                thread.start();
                flag = "SUCCESS";
            } else {
                // br.write("没有能打印的对象，这个地方可能有问题！");
                // br.newLine();
                flag = "ERROR";
            }
            return flag;
        } catch (Throwable e) {
            e.printStackTrace();
            // br.write("打印报出异常");
            // br.newLine();
            // br.write(e.getMessage());
            // br.newLine();
            flag = "ERROR";
            return flag;
        } finally {
            // try {
            // if (br != null) {
            // br.close();
            // }
            // br = null;
            // } catch (IOException e) {}
        }
    }

    public String printReport(String reportURL, final boolean showPrintDlg) throws IOException {
        return printReport(reportURL, showPrintDlg, null);
    }

    private class PrintThread extends Thread {

        private List<JasperPrint> printList;
        private boolean showPrintDlg;
        private String printerName;

        public PrintThread(List<JasperPrint> printList, boolean showPrintDlg, String printerName) {
            this.printList = printList;
            this.showPrintDlg = showPrintDlg;
            this.printerName = printerName;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            AccessController.doPrivileged(new PrintPrivilegeAction(printList, showPrintDlg, printerName));
        }
    }

    public static void main(String[] args) {
        PrintService[] pss = PrinterJob.lookupPrintServices();
        for (PrintService ps : pss) {
            System.out.println(ps.getName());
        }

        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println(ps.getName());
    }

    /**
     * 打印反馈数据,如果打印机名称没有设置则选择默认打印机
     * 
     * @param p
     * @param printName 打印机名称
     */
    private void printJapserData(JasperPrint p, String printName) {
        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
        if (printName != null && !"".equals(printName)) {
            for (PrintService tmp : PrinterJob.lookupPrintServices()) {
                if (printName.equals(tmp.getName())) {
                    ps = tmp;
                }
            }
        }
        System.out.println("printer name is : " + ps.getName());
        JRAbstractExporter je = new JRPrintServiceExporter();
        je.setParameter(JRExporterParameter.JASPER_PRINT, p);
        // 设置指定打印机
        je.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, ps);
        je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, false);
        je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, false);
        // 打印
        try {
            je.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    private class PrintPrivilegeAction implements PrivilegedAction {

        private List<JasperPrint> printList;
        private boolean showPrintDlg;
        private String printerName;

        public PrintPrivilegeAction(List<JasperPrint> printList, boolean showPrintDlg, String printerName) {
            this.printList = printList;
            this.showPrintDlg = showPrintDlg;
            this.printerName = printerName;
        }

        public Object run() {
            System.out.println("Print Thread");
            try {
                for (JasperPrint jp : printList) {
                    System.out.println("showPrintDlg... " + showPrintDlg);
                    if (showPrintDlg) {
                        JasperPrintManager.printReport(jp, showPrintDlg);
                    } else {
                        if (printerName == null) {
                            printJapserData(jp, null);
                        } else {
                            printJapserData(jp, printerName);
                        }

                    }

                }
            } catch (JRException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
    *
    */
    public static Object loadObject(URL url) throws JRException {
        Object obj = null;
        InputStream is = null;
        ObjectInputStream ois = null;
        try {
            is = url.openStream();
            ZipInputStream zis = new ZipInputStream(is);
            BufferedInputStream stream = new BufferedInputStream(zis);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            ZipEntry entry = zis.getNextEntry();
            if (entry == null) {
                return null;
            }
            try {
                int len = 0;
                while ((len = stream.read(buffer)) > 0) {
                    output.write(buffer, 0, len);
                }
            } finally {
                if (output != null) {
                    output.close();
                }
            }
            ois = new ContextClassLoaderObjectInputStream(new ByteArrayInputStream(output.toByteArray()));
            obj = ois.readObject();
        } catch (IOException e) {
            throw new JRException("Error loading object from URL : " + url, e);
        } catch (ClassNotFoundException e) {
            throw new JRException("Class not found when loading object from URL : " + url, e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {}
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {}
            }
        }
        return obj;
    }

    /**
     * 获取可服务的打印机列表
     */
    public static String getAllPrinterNames() {
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        // 查找所有的可用的打印服务
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, pras);
        StringBuffer br = new StringBuffer();
        for (int i = 0; i < printService.length; i++) {
            br.append(printService[i].getName() + ",");
            // System.out.println(printService[i].getName());
        }
        if (br.toString() != null) {
            return br.toString().substring(0, br.lastIndexOf(","));
        } else {
            return "";
        }
    }

}
