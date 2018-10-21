package com.bt.utils;

import java.io.*;
import java.util.*;

public class Property {
	Properties props = new Properties();
	InputStream in = null;
	
	public Property(String file){
		try{
			in = getClass().getClassLoader().getResourceAsStream(file);
			props.load(in);
		}catch(Exception e){}
	}
	public String getValue(String name){
		return props.getProperty(name);
	}
}
