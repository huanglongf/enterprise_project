package com.bt.utils.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.bt.common.base.LoadingType;

public class ChildTest extends ParentTest implements Serializable {
	
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -5915910028402024427L;
	private String id;
	private Boolean flag;
	LoadingType loadingType;
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Boolean getFlag() {
		return flag;
	}


	public void setFlag(Boolean flag) {
		this.flag = flag;
	}


	public LoadingType getLoadingType() {
		return loadingType;
	}


	public void setLoadingType(LoadingType loadingType) {
		this.loadingType = loadingType;
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Serializable> T clone(T object) {
		T cloneObject = null;  
		try {  
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			ObjectOutputStream oos = new ObjectOutputStream(baos);  
			oos.writeObject(object);  
			oos.close();  
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());  
			ObjectInputStream ois = new ObjectInputStream(bais);  
			cloneObject = (T) ois.readObject();  
			ois.close();  
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return cloneObject;
		
	}
	
	public static void main(String[] args) {
		ChildTest test= new ChildTest();
		test.setId("1");
		test.setFlag(true);
		test.setLoadingType(LoadingType.MAIN);
		test.setUuid("1");
		ChildTest testclone= clone(test);
		System.out.println("id:"+testclone.getId() + ";flag:"+testclone.getFlag()+";loadingType:"+testclone.getLoadingType()+";uuid:"+testclone.getUuid()+";");
		
	}
	
}