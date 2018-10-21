//package com.bt.lmis.report;
//
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import javax.servlet.http.HttpSession;
//
//import com.bt.lmis.utils.Constant;
//import com.bt.lmis.utils.Property;
//import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
//import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
//
//
//public class JRC_ResultSet_DataSource{
//
//	private String REPORT_NAME;
//	
//	public JRC_ResultSet_DataSource(String name){
//		System.out.println("================");
//		REPORT_NAME=name;
//	}
//	
//   private static ResultSet getResultSetFromQuery(String query, int scrollType)throws SQLException, ClassNotFoundException{
//       Class.forName(new Property(Constant.jdbc_property).getValue("driverClassName"));
//       final String DBUSERNAME = new Property(Constant.jdbc_property).getValue("jdbc_username");
//       final String DBPASSWORD = new Property(Constant.jdbc_property).getValue("jdbc_password");
//       final String CONNECTION_URL = new Property(Constant.jdbc_property).getValue("jdbc_url");
//        java.sql.Connection connection = DriverManager.getConnection(CONNECTION_URL, DBUSERNAME, DBPASSWORD); 
//        Statement statement = connection.createStatement(scrollType, ResultSet.CONCUR_READ_ONLY);
//       return statement.executeQuery(query);
//    }
//	
//   public boolean isReportSourceInSession(String session_name,HttpSession session) throws ReportSDKException, SQLException, ClassNotFoundException{
//       boolean flag=false;
//        try{
//            ReportClientDocument reportClientDoc = new ReportClientDocument();
//            reportClientDoc.open(REPORT_NAME, 0);
//            ResultSet resultSet = this.getResultSetFromQuery(SqlString.REPORT_CONTRACT,ResultSet.TYPE_SCROLL_INSENSITIVE);
//            String tableAlias = reportClientDoc.getDatabaseController().getDatabase().getTables().getTable(0).getAlias();
//            reportClientDoc.getDatabaseController().setDataSource(resultSet,tableAlias, "resultsetTable");
//            session.setAttribute(session_name, reportClientDoc.getReportSource());
//            flag=true;
//           return flag;
//        } catch (Exception e){
//           // TODO: handle exception
//            e.printStackTrace();
//           return flag;
//        }        
//   }
//}
