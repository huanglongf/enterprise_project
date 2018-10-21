package com.jumbo.wms.manager.vmi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.manager.BaseManagerImpl;

/**
 * 通用VMI读取文件并备份接口实现
 * 
 * @author jinlong.ke
 * 
 */
@Service("vmiFileManager")
public class VmiFileManagerImpl extends BaseManagerImpl implements VmiFileManager {

    /**
     * 
     */
    private static final long serialVersionUID = 3608911514035793907L;
    @Autowired
    private VmiFactory vmiFactory;

    @Override
    public boolean inBoundreadFileIntoDB(String localFileDir, String bakFileDir, String fileStart, String vmiCode) {
        String line = null;
        boolean flag = false;

        BufferedReader buffRead = null;
        File[] files = null;
        try {
            File fileDir = new File(localFileDir);
            files = fileDir.listFiles();
            if (files == null || files.length == 0) {
                log.debug("{} is null, has no file ============================", localFileDir);
                flag = false;
                return flag;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                if (fileStart != null) {
                    Pattern pattern = Pattern.compile(fileStart);
                    Matcher matcher = pattern.matcher(file.getName());
                    if (!matcher.matches()) {
                        continue;
                    }
                }
                log.debug("file  name ===================={} ", file.getName());
                buffRead = new BufferedReader(new FileReader(file));
                List<String> results = new ArrayList<String>();
                while ((line = buffRead.readLine()) != null) {
                    results.add(line);
                }
                log.debug("results: **************** {}", results.size());
                VmiInterface vmiInterface = vmiFactory.getBrandVmi(vmiCode);
                flag = vmiInterface.inBoundInsertIntoDB(results);
                if (buffRead != null) buffRead.close();
                if (flag) {
                    FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                    file.delete();
                }
            }
        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                if (buffRead != null) buffRead.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return flag;
    }

}
