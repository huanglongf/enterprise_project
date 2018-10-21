package com.jumbo.util.zip;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZLibCompressor implements Compressor, Serializable {


    private static final long serialVersionUID = 6374049565884580357L;

    static int BUFFER_SIZE = 1024;

    private static final Logger logger = LoggerFactory.getLogger(ZLibCompressor.class);

    @Override
    public byte[] compress(byte[] data) throws Exception {
        if (null == data || data.length == 0) {
            logger.warn("## empty input when zlib compress.");
            return data;
        }
        ByteArrayOutputStream bos = null;
        try {
            byte[] output = null;
            Deflater compresser = new Deflater();
            compresser.reset();
            compresser.setInput(data);
            compresser.finish();
            bos = new ByteArrayOutputStream(data.length);
            byte[] buf = new byte[BUFFER_SIZE];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
            compresser.end();
            return output;
        } catch (Exception ex) {
            // ex.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("compress Exception！", ex);
            }
            throw ex;
        } finally {
            if (bos != null) {
                bos.close();
            }
        }

    }

    @Override
    public byte[] decompress(byte[] data) throws Exception {
        if (null == data || data.length == 0) {
            logger.warn("## empty input when zlib decompress.");
            return data;
        }
        byte[] output = null;

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[BUFFER_SIZE];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
            decompresser.end();
            return output;
        } catch (Exception e) {
            throw e;
        } finally {
            if (o != null) {
                o.close();
            }
        }
    }

}
