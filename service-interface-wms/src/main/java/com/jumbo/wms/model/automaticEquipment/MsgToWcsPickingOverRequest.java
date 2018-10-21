package com.jumbo.wms.model.automaticEquipment;



public class MsgToWcsPickingOverRequest {
    /**
     * 容器号
     */
    private String ContainerNO;
    /**
     * 目的位置
     */
    private String DestinationNO;
    /**
     * 波次号
     */
    private String WaveOrderr;
    /**
     * 播种模式
     */
    private String CategoryMode;
    /**
     * 完结标识
     */
    private String EndingTag;

    /**
     * 货箱数量
     */
    private String Count;


    public String getContainerNO() {
        return ContainerNO;
    }

    public void setContainerNO(String containerNO) {
        ContainerNO = containerNO;
    }

    public String getDestinationNO() {
        return DestinationNO;
    }

    public void setDestinationNO(String destinationNO) {
        DestinationNO = destinationNO;
    }

    public String getWaveOrderr() {
        return WaveOrderr;
    }

    public void setWaveOrderr(String waveOrderr) {
        WaveOrderr = waveOrderr;
    }

    public String getCategoryMode() {
        return CategoryMode;
    }

    public void setCategoryMode(String categoryMode) {
        CategoryMode = categoryMode;
    }

    public String getEndingTag() {
        return EndingTag;
    }

    public void setEndingTag(String endingTag) {
        EndingTag = endingTag;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }


}
