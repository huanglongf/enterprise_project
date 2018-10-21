package com.jumbo.wms.manager.ftp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.ConstantsFtpConifg;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.util.SFTPUtil;

@Service("ftpUtilManager")
public class FtpUtilManagerImpl extends BaseManagerImpl implements FtpUtilManager {

    private static final long serialVersionUID = 5576675692535972127L;
    @Autowired
    private CommonConfigManager configManager;

    public void downloadFile(String groupName, String remotePackageName, String localPackageName) {
        try {
            Map<String, String> config = configManager.getCommonFTPConfig(groupName);
            String url = config.get(ConstantsFtpConifg.FTP_KEY_URL);
            String port = config.get(ConstantsFtpConifg.FTP_KEY_PORT);
            String username = config.get(ConstantsFtpConifg.FTP_KEY_USERNAME);
            String password = config.get(ConstantsFtpConifg.FTP_KEY_PASSWORD);
            String remotePath = config.get(ConstantsFtpConifg.FTP_KEY_REMOTE_DOWNLOAD_PATH);
            if (StringUtils.hasText(remotePackageName)) {
                remotePath = remotePath + "/" + remotePackageName;
            }
            String localPath = config.get(ConstantsFtpConifg.FTP_KEY_LOCAL_DOWNLOAD_PATH);
            if (StringUtils.hasText(localPackageName)) {
                localPath = localPath + "/" + localPackageName;
            }
            String regularExpression = config.get(ConstantsFtpConifg.FTP_KEY_FILE_NAME_REGULAR_EXPRESSION);
            SFTPUtil.readFile(url, port, username, password, remotePath, localPath, null, true, regularExpression);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
