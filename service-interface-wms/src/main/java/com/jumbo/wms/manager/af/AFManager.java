package com.jumbo.wms.manager.af;

public interface AFManager {

      void sendAfInventoryMessage();

    void sendAfInventoryMessage2();// 补偿

      /**
       * 发送AF库存对比Email
       */
       public void sendAfInvComReportMail(String vimCode,String vimSource,Long shopId);
       
       /**
        * 保存AF商品在利丰仓库的库存信息
        * @param message
        */
       public void saveAfLfInvInfo(String message);
       
       void insertAFLFInventoryInfoReportInfo(String vimCode,String vimSource,Long shopId);
}
