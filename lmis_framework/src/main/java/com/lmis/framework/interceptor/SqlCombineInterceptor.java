package com.lmis.framework.interceptor;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmis.common.util.OauthUtil;
import com.lmis.common.util.SqlCombineHelper;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

/**
 * @author xuyisu
 * @Description: TODO(MyBatis 在mybatis执行前将要执行的sql加上数据权限的拼装)
 * @date 2017年05月07日
 * @todo TODO
 */
@Intercepts({ @Signature(type = Executor.class,method = "query",args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
        //, @Signature(type = Executor.class,method = "update",args = { MappedStatement.class, Object.class })
})
public class SqlCombineInterceptor implements Interceptor{

    private static Log logger = LogFactory.getLog(SqlCombineInterceptor.class);

    static int MAPPED_STATEMENT_INDEX = 0;// 这是对应上面的args的序号

    static int PARAMETER_INDEX = 1;

    static int ROWBOUNDS_INDEX = 2;

    static int RESULT_HANDLER_INDEX = 3;

    @Autowired
    private OauthUtil oauthUtil;

    @Override
    public Object intercept(Invocation invocation) throws Throwable{
        // logger.info("第一次Sql拼装");
        try{

            final Object[] queryArgs = invocation.getArgs();
            final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
            final Object parameter = queryArgs[PARAMETER_INDEX];
            // System.out.println(JSONUtil.toJsonStr(parameter));
            if(parameter==null)
                return invocation.proceed();
            final BoundSql boundSql = ms.getBoundSql(parameter);// 获得查询语句对像
            String sql = boundSql.getSql();// 获得查询语句
            String jsonStr = JSONUtil.toJsonStr(parameter);

            //针对手动数据过滤处理
            if (SqlCombineHelper.getLocalSqlCombine() != null && SqlCombineHelper.getLocalSqlCombine().booleanValue()){
                if (!StrUtil.containsAnyIgnoreCase(sql, "sql_combine_pwr_org")){
                    logger.error("=========>>>>数据权限错误,起仔细看提示！！！");
                    logger.error("=========>>>>原因:["+sql+"]查询中缺少sql_combine_pwr_org字段,请及时在查询中添加   pwr_org as sql_combine_pwr_org   ");
                    throw new Exception("数据权限错误");
                }
                CombileSqlNew(sql, jsonStr, ms, boundSql, invocation, queryArgs);
            }

            return invocation.proceed();
        }finally{
            SqlCombineHelper.clearLocalPage();
        }
    }

    /**
     * 重新拼装sql
     * 
     * @author xuyisu
     * @date 2018年05月10日 下午4:16:23
     * @param sql
     * @param jsonStr
     * @param ms
     * @param boundSql
     * @param invocation
     * @param queryArgs
     * @param isSql
     */
    public void CombileSqlNew(String sql,String jsonStr,MappedStatement ms,BoundSql boundSql,Invocation invocation,Object[] queryArgs){
        sql = GetCombileSql(sql);

        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());// 重新new一个查询语句对像
        MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSource(newBoundSql));// 把新的查询放到statement里
        queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        String methonName = invocation.getMethod().getName();
        logger.info("methodName__________:" + methonName);
    }

    /**
     * 拼接sql
     * 
     * @param sql
     * @return
     * @throws Exception
     */
    public String GetCombileSql(String sql){
        String curentUserId = oauthUtil.GetCurentUserId();
        if (curentUserId == null || curentUserId == ""){
            logger.info("SqlCombineInterceptor.class 100行处出错   问题原因：拿去数据权限是没有提供当前用户信息");
            return null;
        }
        return "select * from (" + sql + ") sqlcombine WHERE sqlcombine.sql_combine_pwr_org IN ( SELECT  org_id  FROM  sys_user_role_org  WHERE user_id = '" + curentUserId + "' and is_deleted=0 and is_disabled=0  ) ";
    }

    @Override
    public Object plugin(Object target){
        if (target instanceof Executor){
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties){
        // TODO Auto-generated method stub

    }

    /**
     * 构建新的MappedStatement
     * 
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource){
        Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0){
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        // setStatementTimeout()
        builder.timeout(ms.getTimeout());

        // setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        // setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        // setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 重写ibatis中的bounfSql 属性
     * 
     * @author xsh11040
     *
     */
    public static class BoundSqlSource implements SqlSource{

        private BoundSql boundSql;

        public BoundSqlSource(BoundSql boundSql){
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject){
            return boundSql;
        }
    }

}
