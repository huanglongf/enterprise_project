package com.jumbo.web.action.baseinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.task.TaskLfStaOutThread;
import com.jumbo.util.ThreadPoolService;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.WareHouseManager;

public class LfStaConfigAction extends BaseJQGridProfileAction {

    @Autowired
    private ThreadPoolService threadPoolService;
    @Autowired
    private WareHouseManager wareHouseManager;

    /**
     * 
     */
    private static final long serialVersionUID = -7305629205401926879L;

    private File flTxt;

    public String importLfConfig() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (flTxt == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            // 读取TXT文件
            List<String> lisString = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(flTxt));// 构造一个BufferedReader类来读取文件
                String s = null;
                while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                    lisString.add(s);
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
            int j = 0;
            List<String> lisString1 = new ArrayList<String>();
            for (int i = 0; i < lisString.size(); i++) {
                lisString1.add(lisString.get(i));
                j++;
                if (j == 500) {
                    // 丢线程
                    String threadName = "importLfConfig";
                    TaskLfStaOutThread t = new TaskLfStaOutThread(wareHouseManager);
                    t.setListString(lisString1);
                    Thread t1 = new Thread(t, threadName);
                    threadPoolService.executeThread("FlStaImport", t1);
                    lisString1.clear();
                    j = 0;
                } else {
                    if (i == lisString.size() - 1) {
                        // 最后一行
                        String threadName = "importLfConfig";
                        TaskLfStaOutThread t = new TaskLfStaOutThread(wareHouseManager);
                        t.setListString(lisString1);
                        Thread t1 = new Thread(t, threadName);
                        threadPoolService.executeThread("FlStaImport", t1);
                    }
                }
            }
            threadPoolService.waitToFinish("FlStaImport");
        }
        return SUCCESS;
    }

    public File getFlTxt() {
        return flTxt;
    }

    public void setFlTxt(File flTxt) {
        this.flTxt = flTxt;
    }

}
