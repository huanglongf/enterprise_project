package com.jumbo.wms.manager;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;

import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.BaseModel;

@Aspect
public class CodeGenerationAspect implements Ordered {


    static final Logger logger = LoggerFactory.getLogger(CodeGenerationAspect.class);

    @Autowired
    private SequenceManager sequenceManager;

    @Before("execution(public * com.jumbo.webservice..*.create*(..))" + " && args(model,..)")
    public void setSequenceForOthers(BaseModel model) {
        setSequence(model);
    }

    @Before("execution(public * com.jumbo.wms.manager..*.create*(..))" + " && args(model,..)")
    public void setSequence(BaseModel model) {
        String className = model.getClass().getName();
        logger.debug("Model Class:{}", className);
        if (sequenceManager.getCodePattern().containsKey(className)) {
            String property = sequenceManager.getCodePattern().get(className).get(SequenceManager.PROPERTY);
            String genCondition = sequenceManager.getCodePattern().get(className).get(SequenceManager.GEN_CONDITION);
            boolean gen = true;
            if (genCondition != null && !genCondition.equals("")) {
                Method m = ClassUtils.getMethodIfAvailable(model.getClass(), genCondition, (Class[]) null);
                if (m != null) try {
                    gen = (Boolean) m.invoke(model, (Object[]) null);
                } catch (Exception e) {
                    // exception here
                    logger.error("Code generator encounts one error:{}", e);
                    gen = false;
                }
            }
            if (gen) {
                String code = sequenceManager.getCode(className, model);
                try {
                    PropertyUtils.setProperty(model, property, code);
                } catch (Exception e) {
                    // exception here
                    logger.error("Code generator encounts one error:{}", e);
                }
                logger.debug("Code Generation Complete with result:{}", code);
            }
        }
    }



    public SequenceManager getSequenceManager() {
        return sequenceManager;
    }

    public void setSequenceManager(SequenceManager sequenceManager) {
        this.sequenceManager = sequenceManager;
    }

    public int getOrder() {
        return 1;
    }
}
