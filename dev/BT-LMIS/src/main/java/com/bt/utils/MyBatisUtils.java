package com.bt.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

/**
 * @Title:MyBatisUtils
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月2日上午11:35:44
 */
public class MyBatisUtils {
	
	 /**
	 * 
	 * @Description: TODO(泛型方法)
	 * @param sqlSessionFactory
	 * @param mybatisSQLId
	 * @param commitCountEveryTime
	 * @param list
	 * @param logger
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月2日上午11:45:47
	 */
	public static <T> void batchCommit(SqlSessionFactory sqlSessionFactory, String mybatisSQLId, int commitCountEveryTime, List<T> list, Logger logger) {
		 SqlSession session= null;
		 try {
			 session= sqlSessionFactory.openSession(ExecutorType.BATCH, false);
			 int commitCount= (int)Math.ceil(list.size()/ (double)commitCountEveryTime);
			 List<T> tempList= new ArrayList<T>(commitCountEveryTime);
			 int start, stop;
			 Long startTime= System.currentTimeMillis();
			 for(int i= 0; i< commitCount; i++) {
				 tempList.clear();
				 start= i* commitCountEveryTime;
				 stop= Math.min(i* commitCountEveryTime+ commitCountEveryTime- 1, list.size()- 1);
				 for(int j = start; j <= stop; j++) {
					 tempList.add(list.get(j));
					 
				 }
				 session.insert(mybatisSQLId, tempList);
				 session.commit();
				 session.clearCache();
				 
			 }
			 Long endTime= System.currentTimeMillis();
			 logger.debug("batchCommit耗时："+ (endTime- startTime)+ "毫秒");
			 
		 } catch (Exception e) {
			 logger.error("batchCommit error!", e);
			 e.printStackTrace();
			 session.rollback();
			 
		 } finally {
			 if (session != null) {
				 session.close();
				 
			 }
			 
		 }
		 
	}
	
}
