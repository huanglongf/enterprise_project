/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.manager.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import com.jumbo.dao.task.CommonConfigDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.task.CommonConfig;

// import com.jumbo.util.k3.ConsConfig;

/**
 * @author wanghua
 * 
 */
@Service("commonConfigManager")
public class CommonConfigManagerImpl extends BaseManagerImpl implements CommonConfigManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5705419675162179911L;
    // private Map<String, String> k3configCache = new HashMap<String, String>();

    @Autowired
    private CommonConfigDao commonConfigDao;

    // public Map<String, String> getK3Config() {
    // if (k3configCache.keySet().size() == 0) {
    // List<CommonConfig> configs =
    // commonConfigDao.findCommonConfigListByParaGroup(ConsConfig.CONFIG_GROUP_K3COMMON);
    // if (configs != null && configs.size() > 0) {
    // for (CommonConfig config : configs) {
    // k3configCache.put(config.getParaName(), config.getParaValue());
    // }
    // }
    // }
    // return k3configCache;
    // }

    // public String getK3StartDate() {
    // String s = getK3Config().get(ConsConfig.K3_STARTDATE);
    // return StringUtils.hasLength(s) ? s : ConsConfig.DEFAULT_K3_STARTDATE;
    // }

    public Map<String, String> getVMIFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_VMIFTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    public Map<String, String> getNikeFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_NIKE_FTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    // 获取ids获取文件FTP相关信息
    public Map<String, String> getIDSFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_IDSFTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    // 获取JNJFTP相关信息
    public Map<String, String> getJNJFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_JNJFTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    public Map<String, String> getCommonFTPConfig(String group) {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(group);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    public Map<String, String> getConverseFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_CONVERSEFTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    public Map<String, String> getGuessFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_GUESSFTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }


    public Map<String, String> getZdhInvFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_ZHDINVFTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    public Map<String, String> getEbsFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.EBS_INVENTORY);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    public Map<String, String> getPACFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.PAC_INVENTORY);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    // 获取LmisFTP相关信息
    @Override
    public Map<String, String> getLmisFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_LMISFTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    // 获取NikeHKFTP相关信息
    @Override
    public Map<String, String> getNikeHKFTPConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.CONFIG_GROUP_NIKE_HK_SFTP);
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }
}
