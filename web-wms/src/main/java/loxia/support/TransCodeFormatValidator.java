package loxia.support;

public interface TransCodeFormatValidator {
    public boolean validate(String transCode);

    /**
     * 单张物流面单打印模板
     * 
     * @return
     */
    public String getSinglePrintTemplate();

    /**
     * 多张物流面单打印模板
     * 
     * @return
     */
    public String getPrintTemplate();
}
