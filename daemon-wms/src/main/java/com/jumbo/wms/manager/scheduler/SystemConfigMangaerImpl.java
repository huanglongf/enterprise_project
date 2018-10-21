package com.jumbo.wms.manager.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@Service("systemConfigMangaer")
public class SystemConfigMangaerImpl implements SystemConfigMangaer {

    private Map<String, String> cacheMap = new HashMap<String, String>();

    // @Autowired
    // private AppConfigManager appConfigManager;

    // @Override
    // public void init() {
    // // TODO Auto-generated method stub
    // cacheMap = appConfigManager.getAll();
    // }
    @Override
    public String getValue(String key) {
        // TODO Auto-generated method stub
        return cacheMap.get(key);
    }

}
