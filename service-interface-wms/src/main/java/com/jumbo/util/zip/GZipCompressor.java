package com.jumbo.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GZipCompressor implements Compressor, Serializable {


    private static final long serialVersionUID = 7842075855070229247L;

    static int BUFFER_SIZE = 1024;

    private static final Logger logger = LoggerFactory.getLogger(ZLibCompressor.class);

    @Override
    public byte[] compress(byte[] data) throws IOException {
        if (null == data || data.length == 0) {
            logger.warn("## empty input when gzip compress.");
            return data;
        }
        InputStream input = null;
        ByteArrayOutputStream out = null;
        GZIPOutputStream zipOut = null;
        try {
            input = new ByteArrayInputStream(data);
            out = new ByteArrayOutputStream();
            zipOut = new GZIPOutputStream(out);
            byte[] buffer = new byte[BUFFER_SIZE];
            int i = 0;
            while ((i = input.read(buffer, 0, BUFFER_SIZE)) != -1) {
                zipOut.write(buffer, 0, i);
            }
            zipOut.finish();
            zipOut.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (input != null) {
                input.close();
            }
            if (out != null) {
                out.close();
            }
            if (zipOut != null) {
                zipOut.close();
            }
        }
    }

    @Override
    public byte[] decompress(byte[] data) throws Exception {
        if (null == data || data.length == 0) {
            logger.warn("## empty input when gzip compress.");
            return data;
        }
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        try {
            bais = new ByteArrayInputStream(data);
            baos = new ByteArrayOutputStream();
            GZIPInputStream gis = new GZIPInputStream(bais);
            int count;
            byte buffer[] = new byte[BUFFER_SIZE];
            while ((count = gis.read(buffer, 0, BUFFER_SIZE)) != -1) {
                baos.write(buffer, 0, count);
            }
            gis.close();
            return baos.toByteArray();
        } catch (Exception ex) {
            // ex.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("decompress Exception！", ex);
            }
            throw ex;
        } finally {
            if (bais != null) {
                bais.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }

}
