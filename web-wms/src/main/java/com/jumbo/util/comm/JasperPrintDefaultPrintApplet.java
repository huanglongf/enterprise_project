package com.jumbo.util.comm;

import java.applet.Applet;
import java.io.BufferedInputStream;
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

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.ContextClassLoaderObjectInputStream;
import net.sf.jasperreports.view.JasperViewer;

public class JasperPrintDefaultPrintApplet extends Applet {

    /**
     * 
     */
    private static final long serialVersionUID = -4084467775319294885L;

    // public static void main(String args[]) throws MalformedURLException, JRException {
    // File f = new File("d:/a.zip");
    // JasperPrintDefaultPrintApplet p = new JasperPrintDefaultPrintApplet();
    // p.printReprot(f.toURL().toString());
    // }

    private URL url = null;

    public void printReprot(String reportURL) {
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

    @SuppressWarnings("unchecked")
    public void printReport(String reportURL, final boolean showPrintDlg) {
        try {
            // if (reportURL.indexOf("://") > 0)
            url = new URL(reportURL);
            // else
            // url = new URL(getCodeBase(), reportURL);
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
                // print
                System.out.println("Print " + printList.size() + " files...");
                Thread thread = new PrintThread(printList, showPrintDlg);
                thread.start();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private class PrintThread extends Thread {

        private List<JasperPrint> printList;
        private boolean showPrintDlg;

        public PrintThread(List<JasperPrint> printList, boolean showPrintDlg) {
            this.printList = printList;
            this.showPrintDlg = showPrintDlg;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            AccessController.doPrivileged(new PrintPrivilegeAction(printList, showPrintDlg));
        }
    }

    /**
     * 打印反馈数据
     * 
     * @param p
     */
    private void printReport(JasperPrint p) {
        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
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

        public PrintPrivilegeAction(List<JasperPrint> printList, boolean showPrintDlg) {
            this.printList = printList;
        }

        public Object run() {
            System.out.println("Print Thread");
            for (JasperPrint jp : printList) {
                System.out.println("Printing... " + jp.getName());
                printReport(jp);
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
            System.out.println("====================================================");
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
}
