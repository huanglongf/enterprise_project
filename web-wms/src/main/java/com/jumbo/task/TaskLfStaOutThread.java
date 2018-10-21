package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.manager.warehouse.WareHouseManager;


public class TaskLfStaOutThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TaskLfStaOutThread.class);

    private List<String> listString;

    private WareHouseManager wareHouseManager;

    public TaskLfStaOutThread(WareHouseManager wareHouseManager) {
        this.wareHouseManager = wareHouseManager;
    }


    @Override
    public void run() {
        for (String s : listString) {
            try {
                String[] ssStrings = s.split("\t");
                wareHouseManager.importLfSta(ssStrings[0], ssStrings[1], ssStrings[2], Double.parseDouble(ssStrings[3].trim()), ssStrings[5], ssStrings[4]);
            } catch (Exception e) {
                logger.error("TaskLfStaOutThread importLfSta error values: " + s, e);
            }
        }
    }

    public List<String> getListString() {
        return listString;
    }

    public void setListString(List<String> listString) {
        this.listString = listString;
    }


}
