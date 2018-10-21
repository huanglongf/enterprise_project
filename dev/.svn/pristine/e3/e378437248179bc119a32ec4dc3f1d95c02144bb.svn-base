package com.bt.workOrder.dao;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FailureReasonMapper<T> extends BaseMapper<T> {

      List<Map<String, Object>> queryFailureReasonData(Parameter parameter);

      int countFailureReasonData(Parameter parameter);

      int deleteById(String id);

      int addFailureReason(@Param("code") String code,@Param("reason") String reason);

     List<Map<String, Object>> selectFailureReason();

}
