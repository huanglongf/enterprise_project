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

import java.util.Map;

import com.jumbo.wms.manager.BaseManager;

/**
 * @author wanghua
 * 
 */
public interface CommonConfigManager extends BaseManager {


    /**
     * 获取vmi获取文件FTP相关信息
     * 
     * @return
     */
    Map<String, String> getVMIFTPConfig();


    Map<String, String> getZdhInvFTPConfig();

    /**
     * 获取NIKE FTP相关信息
     * 
     * @return
     */
    Map<String, String> getNikeFTPConfig();

    /**
     * 获取ids获取文件FTP相关信息
     * 
     * @return
     */
    Map<String, String> getIDSFTPConfig();

    /**
     * 获取JNJFTP相关信息
     * 
     * @return
     */
    Map<String, String> getJNJFTPConfig();

    /**
     * 获取ETAM FTP相关信息
     * 
     * @return
     */
    Map<String, String> getCommonFTPConfig(String group);

    /**
     * 获取Converse FTP相关信息
     * 
     * @return
     */
    Map<String, String> getConverseFTPConfig();

    /**
     * 获取Guess FTP相关信息
     * 
     * @return
     */
    Map<String, String> getGuessFTPConfig();

    /**
     * 获取EBS FTP相关信息
     * 
     * @return
     */
    Map<String, String> getEbsFTPConfig();

    /**
     * 获取全量库存TOPAC FTP相关信息
     * 
     * @return
     */
    Map<String, String> getPACFTPConfig();

    /**
     * Lmis获取0点库存 FTP相关信息
     * 
     * @return
     */
    Map<String, String> getLmisFTPConfig();

    /**
     * Nike-HK-获取装箱清单文案图片信息
     * 
     * @return
     */
    Map<String, String> getNikeHKFTPConfig();

}
