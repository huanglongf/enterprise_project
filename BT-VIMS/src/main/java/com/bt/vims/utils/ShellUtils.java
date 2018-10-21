package com.bt.vims.utils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class ShellUtils {
	public static int executeShell(String scriptPath, String filePath,String printName) {
		int success = 0;
		String command = null;
		try {
		    //加上权限
			ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755",scriptPath);
			builder.start();
			command = scriptPath+" "+filePath+ " " + printName;
		    success = Runtime.getRuntime().exec(command).waitFor();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return success;
	}
}
