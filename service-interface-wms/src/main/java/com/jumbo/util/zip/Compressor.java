package com.jumbo.util.zip;


public interface Compressor {

    public byte[] compress(byte[] data) throws Exception;

    public byte[] decompress(byte[] data) throws Exception;

}
