package com.jumbo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Copy files, using two techniques, FileChannels and streams. Using FileChannels is usually faster
 * than using streams.
 */
public final class FileUtils {

    /* Change these settings before running this class. */

    /** The file to be copied. */
    public static final String INPUT_FILE = "C:\\TEMP\\cottage.jpg";

    /**
     * The name of the copy to be created by this class. If this file doesn't exist, it will be
     * created, along with any needed parent directories.
     */
    public static final String COPY_FILE_TO = "C:\\TEMP10\\cottage_2.jpg";

    /** Run the example. */
    public static void main(String... aArgs) throws IOException {
        File source = new File(INPUT_FILE);
        File target = new File(COPY_FILE_TO);
        FileUtils test = new FileUtils();
        test.copyWithChannels(source, target, false);
        // test.copyWithStreams(source, target, false);
        log("Done.");
    }

    /** This may fail for VERY large files. */
    public void copyWithChannels(File aSourceFile, File aTargetFile, boolean aAppend) {
        log("Copying files with channels.");
        ensureTargetDirectoryExists(aTargetFile.getParentFile());
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            try {
                inStream = new FileInputStream(aSourceFile);
                inChannel = inStream.getChannel();
                outStream = new FileOutputStream(aTargetFile, aAppend);
                outChannel = outStream.getChannel();
                long bytesTransferred = 0;
                // defensive loop - there's usually only a single iteration :
                while (bytesTransferred < inChannel.size()) {
                    bytesTransferred += inChannel.transferTo(0, inChannel.size(), outChannel);
                }
            } finally {
                // being defensive about closing all channels and streams
                if (inChannel != null) inChannel.close();
                if (outChannel != null) outChannel.close();
                if (inStream != null) inStream.close();
                if (outStream != null) outStream.close();
            }
        } catch (FileNotFoundException ex) {
            log("File not found: " + ex);
        } catch (IOException ex) {
            log(ex);
        }
    }

    private void copyWithStreams(File aSourceFile, File aTargetFile, boolean aAppend) {
        log("Copying files with streams.");
        ensureTargetDirectoryExists(aTargetFile.getParentFile());
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            try {
                byte[] bucket = new byte[32 * 1024];
                inStream = new BufferedInputStream(new FileInputStream(aSourceFile));
                outStream = new BufferedOutputStream(new FileOutputStream(aTargetFile, aAppend));
                int bytesRead = 0;
                while (bytesRead != -1) {
                    bytesRead = inStream.read(bucket); // -1, 0, or more
                    if (bytesRead > 0) {
                        outStream.write(bucket, 0, bytesRead);
                    }
                }
            } finally {
                if (inStream != null) inStream.close();
                if (outStream != null) outStream.close();
            }
        } catch (FileNotFoundException ex) {
            log("File not found: " + ex);
        } catch (IOException ex) {
            log(ex);
        }
    }

    private void ensureTargetDirectoryExists(File aTargetDir) {
        if (!aTargetDir.exists()) {
            aTargetDir.mkdirs();
        }
    }

    private static void log(Object aThing) {
        System.out.println(String.valueOf(aThing));
    }
}
