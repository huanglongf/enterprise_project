package com.jumbo.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.SyncErrorCode;
import com.jumbo.wms.exception.SyncException;

public class ZipUtil implements Serializable {


    private static final long serialVersionUID = 717922275940626220L;
    protected static final Logger log = LoggerFactory.getLogger(ZipUtil.class);
    private static final String DEFAULT_CODING = "UTF-8";

    public static final String GZIP = "gz";
    public static final String ZLIB = "zlib";

    public static final Set<String> zipTypes = new HashSet<String>();

    static {
        zipTypes.add(GZIP);
        zipTypes.add(ZLIB);
    }

    private static Compressor zlibCompressor = new ZLibCompressor();
    private static Compressor gzipCompressor = new GZipCompressor();

    public static String decompress(String s, String format) {
        return decompress(s, format, DEFAULT_CODING);
    }

    public static String decompress(String s) {
        return decompress(s, GZIP, DEFAULT_CODING);
    }

    public static String decompress(String s, String format, String encoding) {
        try {
            byte[] input = Base64.decodeBase64(s);
            Compressor compressor = getCompressorByStyle(format);
            byte[] output = compressor.decompress(input);
            return new String(output, encoding);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new SyncException("## failt to get byte[] from compressed string.", e, SyncErrorCode.UNSUPPORTED_ZIP_FORMAT);
        } catch (Exception e) {
            log.error("", e);
            throw new SyncException("## failt to decompress input byte[].", e, SyncErrorCode.UNSUPPORTED_ZIP_FORMAT);
        }
    }

    public static String compress(String s, String format) {
        return compress(s, format, DEFAULT_CODING);
    }

    public static String compress(String s) {
        return compress(s, GZIP, DEFAULT_CODING);
    }

    public static String compress(String s, String format, String encoding) {
        try {
            byte[] input = s.getBytes(encoding);
            Compressor compressor = getCompressorByStyle(format);
            byte[] output = compressor.compress(input);
            return Base64.encodeBase64String(output);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new SyncException("## failt to get byte[] from input string.", e, SyncErrorCode.UNSUPPORTED_ZIP_FORMAT);
        } catch (Exception e) {
            log.error("", e);
            throw new SyncException("## failt to compress input byte[].", e, SyncErrorCode.UNSUPPORTED_ZIP_FORMAT);
        }
    }

    private static Compressor getCompressorByStyle(String style) {
        if (null == style) {
            return gzipCompressor; // default : gzip
        }
        if (ZLIB.equalsIgnoreCase(style)) {
            return zlibCompressor;
        } else if (GZIP.equalsIgnoreCase(style)) {
            return gzipCompressor;
        }
        throw new SyncException(SyncErrorCode.UNSUPPORTED_ZIP_FORMAT, "zip format unsupported : " + style);
    }

    // public static FileInputStream zipFileDownloadForExcel(String fileName, ByteArrayOutputStream
    // out) throws IOException {
    // String fileNameZip = fileName + Constants.ZIP;
    // if (!FileUtil.isFileExists(Constants.FILE_PATH_EXPRESS_BILL)) {
    // File bp = new File(Constants.FILE_PATH_EXPRESS_BILL);
    // bp.mkdirs();
    // }
    // ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new
    // FileOutputStream(Constants.FILE_PATH_EXPRESS_BILL + "/" + fileNameZip), 1024));
    // String zipName = fileName + Constants.EXCEL_XLS;
    // zos.putNextEntry(new ZipEntry(zipName));
    // zos.write(out.toByteArray());
    // zos.closeEntry();
    // zos.close();
    //
    // File f = new File(Constants.FILE_PATH_EXPRESS_BILL + "/" + fileNameZip);
    // FileInputStream input = new FileInputStream(f);
    // return input;
    // }
    public static ByteArrayInputStream zipFileDownloadForExcel(String fileName, ByteArrayOutputStream out) throws IOException {
        ByteArrayOutputStream zipos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(zipos);
        String zipName = fileName + Constants.EXCEL_XLS;
        zos.putNextEntry(new ZipEntry(zipName));
        zos.write(out.toByteArray());
        zos.closeEntry();
        zos.close();
        ByteArrayInputStream input = new ByteArrayInputStream(zipos.toByteArray());
        return input;
    }

    public static ByteArrayInputStream zipFileDownloadForExcel1(String fileName, List<ByteArrayOutputStream> out) throws IOException {
        byte[] buf = new byte[1024];
        ByteArrayOutputStream zipos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(zipos);
        for (int i = 0; i <= out.size() - 1; i++) {
            String zipName = fileName + i + Constants.EXCEL_XLS;
            ByteArrayInputStream in = new ByteArrayInputStream(out.get(i).toByteArray());
            zos.putNextEntry(new ZipEntry(zipName));
            int len;
            while ((len = in.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        }
        zipos.close();
        zos.close();
        ByteArrayInputStream input = new ByteArrayInputStream(zipos.toByteArray());
        return input;
    }
}
