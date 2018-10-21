package com.bt.lmis.controller.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


/**
 * @ClassName: TestLucene
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Yuriy.Jiang
 * @date 2016年12月26日 下午7:04:37
 * 
 */
public class TestLucene {

	private static final Version VERSION = Version.LUCENE_47;// lucene版本  
	
	public static void create_index(String tableName,OpenMode openMode) throws Exception{
		//索引保存位置
		File indexDir = new File("c:\\lmis\\index\\");
//		File indexDir = new File("/usr/local/log/");
		Directory dir = FSDirectory.open(indexDir);
		//4.7自带分词工具
		Analyzer luceneAnalyzer = new StandardAnalyzer(VERSION);
		IndexWriterConfig iwc = new IndexWriterConfig(VERSION, luceneAnalyzer);
		iwc.setOpenMode(openMode);
		IndexWriter indexWriter = new IndexWriter(dir, iwc);
		/** Jdbc 连接 **/
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select express_number,warehouse,cost_center,store_name,epistatic_order,weight,province,order_amount,weight,volumn_weight,charged_weight,first_weight_price,added_weight_price,discount,standard_freight,afterdiscount_freight,insurance_fee,itemtype_name,(length*width*higth) from "+tableName;
		List<Map<String, Object>> list = jdbcUtils.findModeResult(sql, null);
		//开始时间
		long startTime = new Date().getTime();
		for (int j = 0; j < list.size(); j++) {
			Document document = new Document();
			Field FieldPath = new StringField("express_number", list.get(j).get("express_number").toString(), Field.Store.YES);
			Field FieldBody = new TextField("body",mapToJson(list.get(j)) , Field.Store.YES);
			document.add(FieldPath);
			document.add(FieldBody);
			indexWriter.addDocument(document);
		}
		indexWriter.commit();
		indexWriter.close();
		//结束时间
		long endTime = new Date().getTime();
//		System.out.println("花费了" + (endTime - startTime) + " 毫秒,创建索引!");
	
	}


    /** 
    * @Title: mapToJson 
    * @Description: TODO(map转换json) 
    * @param @param map 集合
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws 
    */
    public static String mapToJson(Map<String, Object> map) {
        Set<String> keys = map.keySet();
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            key = (String) it.next();
            value = null!=map.get(key) ? map.get(key).toString():"";
            jsonBuffer.append(key + ":" +"\""+ value+"\"");
            if (it.hasNext()) {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }
	
    
	public static String query(String express_number) throws IOException, ParseException {  
		String r_string = "";
		File indexDir = new File("c:\\lmis\\index\\");
//		File indexDir = new File("/usr/local/log/");
        IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDir));// 索引读取类  
        IndexSearcher search = new IndexSearcher(reader);// 搜索入口工具类  
        String queryStr = express_number;// 搜索关键字  
        QueryParser queryParser = new QueryParser(VERSION, "express_number", new StandardAnalyzer(VERSION));// 实例查询条件类  
        Query query = queryParser.parse(queryStr);  
        TopDocs topdocs = search.search(query, 1);// 查询前100条  
        System.out.println("查询结果总数---" + topdocs.totalHits);  
        ScoreDoc scores[] = topdocs.scoreDocs;// 得到所有结果集  
        for (int i = 0; i < scores.length; i++) {  
            int num = scores[i].doc;// 得到文档id  
            Document document = search.doc(num);// 拿到指定的文档  
            System.out.println("内容====" + document.get("body"));// 由于内容没有存储所以执行结果为null  
            r_string=document.get("body");
            return r_string;
        }
        return r_string;
    }  
	
	public static void main(String[] args) throws Exception {
//		TestLucene.create_index("tb_warehouse_express_data_settlement limit 0,50000",OpenMode.CREATE);
//		for (int i = 1; i < 100; i++) {
//			TestLucene.create_index("tb_warehouse_express_data_settlement limit "+(i*50000)+",50000",OpenMode.APPEND);
//			System.out.println(i);
//		}
		Date a = new Date();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from lmis_pe.tb_warehouse_express_data_settlement  limit 0,1000";
		List<Map<String, Object>> list = jdbcUtils.findModeResult(sql, null);
		int ia = 0;
		for (int i = 0; i < list.size(); i++) {
			if(null!=list.get(i).get("express_number")){
				ia=ia+1;
				System.out.println(+ia+"    ====="+query(list.get(i).get("express_number").toString()));
			}
		}
		Date b = new Date();
		System.out.println(b.getTime()-a.getTime()+"ms,");
	}
	
	/** 
	* @Title: setSFMap 
	* @Description: TODO(SFTemplate和Settlement组合导出数据) 
	* @param @param sftemp
	* @param @param query
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public static List<Map<String, Object>> setSFMap(List<Map<String, Object>> sftemp){
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < sftemp.size(); i++) {
			String express_number = null!=sftemp.get(i).get("express_number") ? sftemp.get(i).get("express_number").toString():"";
			if(!express_number.equals("")){
				try {
					String query = TestLucene.query(express_number.replace("\"", ""));
					System.out.println(query);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
