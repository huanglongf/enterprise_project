package com.jumbo.wms.manager.task.vmi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;

@Service("coachTask")
public class CoachTask extends BaseManagerImpl {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2325179753299091177L;
    @Autowired
    private CommonConfigManager configManager;

    public void downloadFile() {
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.COACHFTP_CONFIG_GROUP);
        if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
            log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
            return;
        }
        String remotePath = config.get(Constants.COACH_FTP_REMOTE) + "/" + Constants.COACH_FTP_IN;
        if (remotePath == null || remotePath.equals("")) {
            log.debug("the remotePath path is not exist");
            return;
        }
        String localPath = config.get(Constants.COACH_FTP_LOCALPATH) + "/" + Constants.COACH_FTP_IN;

        if (localPath == null || localPath.equals("")) {
            log.debug("the remotePath path is not exist");
            return;
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String remoteBakPath = config.get(Constants.COACH_FTP_REMOTE) + "/" + Constants.COACH_FTP_BAK + "/" + Constants.COACH_FTP_IN + "/" + sdf.format(date);
        if (remoteBakPath == null || remoteBakPath.equals("")) {
            log.debug("the remoteBakPath path is not exist");
            return;
        }

        File dir = new File(localPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        int result = SFTPUtil.readFile(config.get(Constants.COACH_FTP_URL), config.get(Constants.COACH_FTP_PORT), config.get(Constants.COACH_FTP_USERNAME), config.get(Constants.COACH_FTP_PASSWORD), remotePath, localPath, remoteBakPath, true);
        if (result == 0) {
            log.debug("{}… ………download file success", new Object[] {new Date()});
        }
    }
}
