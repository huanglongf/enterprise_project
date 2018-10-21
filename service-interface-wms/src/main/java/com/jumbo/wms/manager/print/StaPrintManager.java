package com.jumbo.wms.manager.print;



public interface StaPrintManager extends BasePrintManager {
    /**
     * 按配货清单连打面单,有LPCODE
     * 
     * @param pickingListId
     * @return
     */
//    JasperPrint printExpressBillByPickingListLpCode(Long pickingListId, Boolean isOline, Long userId);

    /**
     * 按配货清单连打面单,混配，无LPCODE
     * 
     * @param pickingListId
     * @return
     */
//    List<JasperPrint> printExpressBillByPickingList(Long pickingListId, Boolean isOline,Long userId);

    /**
     * 按作业单打面单
     * 
     * @param staId
     * @return
     */
//    public JasperPrint printExpressBillBySta(Long staId, Boolean isOline,Long userId);

    /**
     * 按作业单打面单
     * 
     * @param staId
     * @return
     */
//    public JasperPrint printExpressBillByTrankNo(Long staId, Boolean isOline, String trankNo,Long userId);

//    JasperPrint printImportInfo();
}
