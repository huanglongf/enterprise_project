package com.jumbo.util.comm;

import java.io.IOException;
import java.io.InputStream;

public class HttpBodyUtil {

	/**
	 * before this method,you need do these:
	 *    InputStream is = request.getInputStream();
	 *    request.setCharacterEncoding("UTF-8");
	 *    int size = request.getContentLength();
	 * @param is
	 * @param size
	 */
	public static String processRequest(InputStream is,int size) {
		
		try {


			byte[] reqBodyBytes = readBytes(is, size);

			String res = new String(reqBodyBytes);

			
			return res;

		} catch (Exception e) {
		}
		
		return null;
	}

	public static final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;

			int readLengthThisTime = 0;

			byte[] message = new byte[contentLen];

			try {

				while (readLen != contentLen) {

					readLengthThisTime = is.read(message, readLen, contentLen
							- readLen);

					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}

					readLen += readLengthThisTime;
				}

				return message;
			} catch (IOException e) {
				// Ignore
				// e.printStackTrace();
			}
		}

		return new byte[] {};
	}
}
