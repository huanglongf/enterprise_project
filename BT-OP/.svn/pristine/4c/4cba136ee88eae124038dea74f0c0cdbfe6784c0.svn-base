package ${modulePackage}.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import ${modulePackage}.dao.${className}Mapper;
import ${modulePackage}.model.${className};
import ${modulePackage}.service.${className}Service;
@Service
public class ${className}ServiceImpl<T> extends ServiceSupport<T> implements ${className}Service<T> {

	@Autowired
    private ${className}Mapper<T> mapper;

	public ${className}Mapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
