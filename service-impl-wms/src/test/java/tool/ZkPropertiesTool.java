package tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;



import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.jfree.util.Log;

public class ZkPropertiesTool implements Watcher{
	
	static String root="/sysconfig/wms/service/";
	
	static String zkHost="10.8.4.55";
	
	static String confForlder="zkconfig";
	
	static ZooKeeper zk = null;
	
	public  void runAllPro(String root,String forlder) throws KeeperException, InterruptedException{
		
		
		String path=ZkPropertiesTool.class.getResource("/"+forlder).getPath();
		
		File dir=new File(path);
		
		for(File cd:dir.listFiles()){
			int index2=cd.getName().lastIndexOf(".");
			String fileName=cd.getName().substring(0,index2);
			run(root+fileName,forlder+"/"+cd.getName());
		}
		
	
	}
	
	
	
	public static Properties findCommonPro(String source){
		
		String path=source;
		
		Properties pro=new Properties();
	
		
			InputStream is = getResourceAsStream(
					path, ZkPropertiesTool.class);
			
			 pro=new Properties();
			try {
				pro.load(is);
				
			} catch (IOException e) {
				Log.error("",e);
			}
	
		
		return pro;
	}
	
	public static URL getResource(String resourceName, Class<?> callingClass) {
        URL url = null;
        url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) {
            url = ZkPropertiesTool.class.getClassLoader().getResource(resourceName);
        }
        if (url == null && callingClass != null) {
            url = callingClass.getClassLoader().getResource(resourceName);
        }
        return url;
    }
	
	public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
        URL url = getResource(resourceName, callingClass);
        try {
            return (url != null) ? url.openStream() : null;
        } catch (IOException e) {
            return null;
        }
    }

	public  void run(String root,String configPath) throws KeeperException, InterruptedException{
		
		
		
		Properties prop=findCommonPro(configPath);
		
		String[] strs=root.split("/");
		
		String lastNode="";
		for(int i=1;i<strs.length;i++){
			String str=strs[i];
			lastNode=lastNode+"/"+str;
			Stat stat=zk.exists(lastNode, false);
			
			if(stat==null){
				zk.create(lastNode,"config".getBytes() , Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		}
		
		
		for(Map.Entry<Object, Object> entry:prop.entrySet()){
			
			System.out.println(entry.getKey()+":"+entry.getValue());
			
			zk.create(root+"/"+entry.getKey(), entry.getValue().toString().getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}
	
	public void init(){
		
		try {
			zk = new ZooKeeper(zkHost, 30000, this);
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) throws KeeperException, InterruptedException {

	
		
		ZkPropertiesTool zt=new ZkPropertiesTool();
		
		zt.init();
		
		
		zt.runAllPro(root,confForlder);
		

		
		
		
	}

	@Override
	public void process(WatchedEvent event) {
		
	}

}
