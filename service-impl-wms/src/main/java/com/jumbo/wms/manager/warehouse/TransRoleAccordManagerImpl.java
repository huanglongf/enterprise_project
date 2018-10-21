package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.warehouse.TransRoleAccordDao;
import com.jumbo.dao.warehouse.TransRoleDao;
import com.jumbo.util.StringUtils;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.trans.TransRole;
import com.jumbo.wms.model.trans.TransRoleAccord;
import com.jumbo.wms.model.trans.TransRoleAccordCommand;
import com.jumbo.wms.model.trans.TransRoleAccordStatus;

/**
 * 渠道快递规则状态变更
 * 
 * @author sj
 * 
 */
@Transactional
@Service("transRoleAccordManager")
public class TransRoleAccordManagerImpl extends BaseManagerImpl implements TransRoleAccordManager {

    private static final long serialVersionUID = -2433849937629967243L;
    @Autowired
    private TransRoleAccordDao transRoleAccordDao;
    @Autowired
    private TransRoleDao transRoleDao;
    @Autowired
    private UserDao userDao;

    @Resource(name = "transRoleAccordReader")
    private ExcelReader transRoleAccordReader;

    @Override
    public Pagination<TransRoleAccordCommand> findTransRoleAccord(int start, int pageSize, TransRoleAccordCommand trac, Long id, Sort[] sorts) {
        String roleCode = null;
        String roleName = null;
        String channelCode = null;
        String channelName = null;
        Integer intStatus = null;
        if (trac != null) {
            if (trac.getRoleCode() != null && !trac.getRoleCode().equals("")) {
                roleCode = trac.getRoleCode();
            }
            if (trac.getRoleName() != null && !trac.getRoleName().equals("")) {
                roleName = trac.getRoleName();
            }
            if (trac.getChannelCode() != null && !trac.getChannelCode().equals("")) {
                channelCode = trac.getChannelCode();
            }
            if (trac.getChannelName() != null && !trac.getChannelName().equals("")) {
                channelName = trac.getChannelName();
            }
            if (trac.getIntStatus() != null) {
                intStatus = trac.getIntStatus();
            }
        }
        return transRoleAccordDao.findTransRoleAccord(start, pageSize, channelCode, channelName, intStatus, roleCode, roleName, new BeanPropertyRowMapperExt<TransRoleAccordCommand>(TransRoleAccordCommand.class), sorts);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus transRoleAccordImport(File file, Long userId) {
        log.debug("===========transRoleAccordImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<TransRoleAccordCommand> trac = null;
        ReadStatus rs = null;
        try {
            rs = transRoleAccordReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.TRANS_ROLE_ACCORD_EXCEL_PARSE_ERROR));
                return rs;
            }
            trac = (List<TransRoleAccordCommand>) beans.get("data");
            transRoleAccordImportTable(trac, userId);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("transRoleAccordImport ,user : {}",userId,ex);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void transRoleAccordImportTable(List<TransRoleAccordCommand> trac, Long userId) {
        if (trac.size() == 0) {
            throw new BusinessException(ErrorCode.TRANS_ROLE_ACCORD_IS_NULL);
        } else {
            for (TransRoleAccordCommand t : trac) {
                if (!StringUtils.hasText(t.getRoleCode())) {
                    throw new BusinessException(ErrorCode.TRANS_ROLE_CODE_IS_NULL);
                }
                if (!StringUtils.hasText(t.getChangeTime().toString())) {
                    throw new BusinessException(ErrorCode.TRANS_CHANGE_TIME_IS_NULL);
                }
                if (!StringUtils.hasText(t.getStrRoleIsAvailable())) {
                    throw new BusinessException(ErrorCode.TRANS_STATUS_IS_NULL);
                }
                if (!StringUtils.hasText(t.getPriority().toString())) {
                    throw new BusinessException(ErrorCode.TRANS_PRIORITY_IS_NULL);
                }
                TransRole tr = transRoleDao.findTransRoleByCode(t.getRoleCode().trim());
                if (tr == null) {
                    throw new BusinessException(ErrorCode.TRANS_ROLE_IS_NOT_EXIT, new Object[] {t.getRoleCode().trim()});
                }
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t.getChangeTime());
                boolean status = false;
                if (!"是".equals(t.getStrRoleIsAvailable().trim()) && !"否".equals(t.getStrRoleIsAvailable().trim())) {
                    throw new BusinessException(ErrorCode.EXCEL_IMPORT_STATUS_ERROR, new Object[] {t.getStrRoleIsAvailable().trim()});
                } else {
                    if ("是".equals(t.getStrRoleIsAvailable().trim())) {
                        status = true;
                    } else if ("否".equals(t.getStrRoleIsAvailable().trim())) {
                        status = false;
                    }
                }
                int priority = t.getPriority();
                if (priority < 1 || priority > 9999) {
                    throw new BusinessException(ErrorCode.PRIORITY_ERROR, new Object[] {priority});
                }
                TransRoleAccord trc = new TransRoleAccord();
                trc.setRoleId(tr);
                try {
                    trc.setChangeTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date));
                } catch (ParseException e) {
                    if (log.isErrorEnabled()) {
                        log.error("ParseException:", e);
                    }
                }
                trc.setRoleIsAvailable(status);
                trc.setPriority(priority);
                trc.setStatus(TransRoleAccordStatus.AVAILABLE);
                trc.setCreateTime(new Date());
                User user = userDao.getByPrimaryKey(userId);
                if (user == null) {
                    throw new BusinessException(ErrorCode.USER_NOT_FOUND);
                }
                trc.setCreateUser(user);
                trc.setLastOperationer(user);
                trc.setLastOperation(new Date());
                transRoleAccordDao.save(trc);
            }
        }
    }

    @Override
    public void updateTransRoleAccord(Long id, Date changeTime, Long roleIsAvailable, Long priority, Date lastOptionTime, Long userId) {
        transRoleAccordDao.updateTransRoleAccord(id, changeTime, roleIsAvailable, priority, lastOptionTime, userId);
    }

    @Override
    public List<TransRoleAccordCommand> findAvailableTransRoleAccord(String changeTime) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(changeTime);
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error("ParseException:", e);
            }
        }
        return transRoleAccordDao.findAvailableTransRoleAccord(date, new BeanPropertyRowMapper<TransRoleAccordCommand>(TransRoleAccordCommand.class));
    }

    @Override
    public void updateTransRole(Long id, Long roleId, Integer roleIsAvailable, Integer priority) {
        if (roleIsAvailable == 0) {// 渠道快递规则中的是否启用0对应渠道快递维护中的状态2
            roleIsAvailable = 2;
        }
        int retVal = transRoleDao.updateTransRoleById(roleId, roleIsAvailable, priority);
        TransRoleAccord trc = transRoleAccordDao.getByPrimaryKey(id);
        if (retVal == 1) {
            trc.setStatus(TransRoleAccordStatus.FINISHED);
        } else {
            trc.setStatus(TransRoleAccordStatus.FAILURE);
        }
        trc.setLastOperation(new Date());
        transRoleAccordDao.save(trc);
    }
}
