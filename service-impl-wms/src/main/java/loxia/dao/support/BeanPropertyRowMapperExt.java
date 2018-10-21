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

package loxia.dao.support;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

import com.jumbo.wms.model.BaseModel;

/**
 * 一个Bean,
 * 
 * @Column(name="IS_AVAILABLE") public Boolean getIsAvailable() { return isAvailable; }
 *                              一般情况下,我们isAvailable=bean.property,数据库的字段对应的是带下划线的格式：IS_AVAILABLE
 * @ManyToOne(fetch=FetchType.LAZY)
 * @JoinColumn(name="PARENT_OU_ID")
 * @Index(name="IDX_OU_POU") public OperationUnit getParentUnit() { return parentUnit; }
 *                           但是这种类似于有外键的情况,数据库对应的字段就不是一般的property+'_'的格式
 *                           基于以上情况,对org.springframework.jdbc.core.BeanPropertyRowMapper做了稍许改动
 * @see BeanPropertyRowMapperExt#initialize(Class) 同时对Enum类型的property添加了类型转换
 * @see BeanPropertyRowMapperExt#initBeanWrapper(BeanWrapper)
 * @author wanghua
 * @see org.springframework.jdbc.core.BeanPropertyRowMapper
 */
public class BeanPropertyRowMapperExt<T> implements RowMapper<T> {
    protected static final Logger log = LoggerFactory.getLogger(BeanPropertyRowMapperExt.class);

    /**
     * Initialize the mapping metadata for the given class.
     * 
     * @param mappedClass the mapped class.
     */
    protected void initialize(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        this.mappedFields = new HashMap<String, PropertyDescriptor>();
        this.mappedProperties = new HashSet<String>();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                this.mappedFields.put(pd.getName().toLowerCase(), pd);
                String underscoredName = underscoreName(pd.getName());
                if (!pd.getName().toLowerCase().equals(underscoredName)) {
                    this.mappedFields.put(underscoredName, pd);
                }
                String annotationName = getAnnotationName(pd);
                if (!annotationName.equals(underscoredName)) {
                    this.mappedFields.put(annotationName, pd);
                }
                this.mappedProperties.add(pd.getName());
            }
        }
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    protected void initBeanWrapper(BeanWrapper bw) {
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            Class returnType = pd.getReadMethod().getReturnType();
            if (returnType.isEnum()) {
                EnumPropertyEditor pe = new EnumPropertyEditor();
                pe.setDelegate(pd.getReadMethod().getReturnType());
                bw.registerCustomEditor(pd.getReadMethod().getReturnType(), pe);
            } else if (BaseModel.class.isAssignableFrom(pd.getReadMethod().getReturnType())) {
                BaseModelPropertyEditor pe = new BaseModelPropertyEditor();
                pe.setDelegate(pd.getReadMethod().getReturnType());
                bw.registerCustomEditor(pd.getReadMethod().getReturnType(), pe);
            }
        }
    }

    private String getAnnotationName(PropertyDescriptor pd) {
        String name = pd.getName();
        Method readMethod = pd.getReadMethod();
        if (readMethod != null) {
            Column column = pd.getReadMethod().getAnnotation(Column.class);
            if (column != null) {
                name = column.name();
            } else {
                JoinColumn joinColumn = pd.getReadMethod().getAnnotation(JoinColumn.class);
                if (joinColumn != null) {
                    name = joinColumn.name();
                }
            }
        }
        return name.toLowerCase();
    }

    /** The class we are mapping to */
    private Class<T> mappedClass;

    /** Whether we're strictly validating */
    private boolean checkFullyPopulated = false;

    /** Whether we're defaulting primitives when mapping a null value */
    private boolean primitivesDefaultedForNullValue = false;

    /** Map of the fields we provide mapping for */
    private Map<String, PropertyDescriptor> mappedFields;

    /** Set of bean properties we provide mapping for */
    private Set<String> mappedProperties;


    /**
     * Create a new BeanPropertyRowMapper for bean-style configuration.
     * 
     * @see #setMappedClass
     * @see #setCheckFullyPopulated
     */
    public BeanPropertyRowMapperExt() {}

    /**
     * Create a new BeanPropertyRowMapper, accepting unpopulated properties in the target bean.
     * <p>
     * Consider using the {@link #newInstance} factory method instead, which allows for specifying
     * the mapped type once only.
     * 
     * @param mappedClass the class that each row should be mapped to
     */
    public BeanPropertyRowMapperExt(Class<T> mappedClass) {
        initialize(mappedClass);
    }

    /**
     * Create a new BeanPropertyRowMapper.
     * 
     * @param mappedClass the class that each row should be mapped to
     * @param checkFullyPopulated whether we're strictly validating that all bean properties have
     *        been mapped from corresponding database fields
     */
    public BeanPropertyRowMapperExt(Class<T> mappedClass, boolean checkFullyPopulated) {
        initialize(mappedClass);
        this.checkFullyPopulated = checkFullyPopulated;
    }


    /**
     * Set the class that each row should be mapped to.
     */
    public void setMappedClass(Class<T> mappedClass) {
        if (this.mappedClass == null) {
            initialize(mappedClass);
        } else {
            if (!this.mappedClass.equals(mappedClass)) {
                throw new InvalidDataAccessApiUsageException("The mapped class can not be reassigned to map to " + mappedClass + " since it is already providing mapping for " + this.mappedClass);
            }
        }
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case. Any upper case letters are
     * converted to lower case with a preceding underscore.
     * 
     * @param name the string containing original name
     * @return the converted name
     */
    private String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            result.append(name.substring(0, 1).toLowerCase());
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                if (s.equals(s.toUpperCase())) {
                    result.append("_");
                    result.append(s.toLowerCase());
                } else {
                    result.append(s);
                }
            }
        }
        return result.toString();
    }

    /**
     * Get the class that we are mapping to.
     */
    public final Class<T> getMappedClass() {
        return this.mappedClass;
    }

    /**
     * Set whether we're strictly validating that all bean properties have been mapped from
     * corresponding database fields.
     * <p>
     * Default is <code>false</code>, accepting unpopulated properties in the target bean.
     */
    public void setCheckFullyPopulated(boolean checkFullyPopulated) {
        this.checkFullyPopulated = checkFullyPopulated;
    }

    /**
     * Return whether we're strictly validating that all bean properties have been mapped from
     * corresponding database fields.
     */
    public boolean isCheckFullyPopulated() {
        return this.checkFullyPopulated;
    }

    /**
     * Set whether we're defaulting Java primitives in the case of mapping a null value from
     * corresponding database fields.
     * <p>
     * Default is <code>false</code>, throwing an exception when nulls are mapped to Java
     * primitives.
     */
    public void setPrimitivesDefaultedForNullValue(boolean primitivesDefaultedForNullValue) {
        this.primitivesDefaultedForNullValue = primitivesDefaultedForNullValue;
    }

    /**
     * Return whether we're defaulting Java primitives in the case of mapping a null value from
     * corresponding database fields.
     */
    public boolean isPrimitivesDefaultedForNullValue() {
        return primitivesDefaultedForNullValue;
    }


    /**
     * Extract the values for all columns in the current row.
     * <p>
     * Utilizes public setters and result set metadata.
     * 
     * @see java.sql.ResultSetMetaData
     */
    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Assert.state(this.mappedClass != null, "Mapped class was not specified");
        T mappedObject = BeanUtils.instantiate(this.mappedClass);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        initBeanWrapper(bw);

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Set<String> populatedProperties = (isCheckFullyPopulated() ? new HashSet<String>() : null);

        for (int index = 1; index <= columnCount; index++) {
            String column = JdbcUtils.lookupColumnName(rsmd, index);
            PropertyDescriptor pd = this.mappedFields.get(column.replaceAll(" ", "").toLowerCase());
            if (pd != null) {
                try {
                    Object value = getColumnValue(rs, index, pd);
                    if (log.isDebugEnabled() && rowNumber == 0) {
                        log.debug("Mapping column '" + column + "' to property '" + pd.getName() + "' of type " + pd.getPropertyType());
                    }
                    try {
                        bw.setPropertyValue(pd.getName(), value);
                    } catch (TypeMismatchException e) {
                        if (value == null && primitivesDefaultedForNullValue) {
                            log.debug("Intercepted TypeMismatchException for row " + rowNumber + " and column '" + column + "' with value " + value + " when setting property '" + pd.getName() + "' of type " + pd.getPropertyType() + " on object: "
                                    + mappedObject);
                        } else {
                            throw e;
                        }
                    }
                    if (populatedProperties != null) {
                        populatedProperties.add(pd.getName());
                    }
                } catch (NotWritablePropertyException ex) {
                    throw new DataRetrievalFailureException("Unable to map column " + column + " to property " + pd.getName(), ex);
                }
            }
        }

        if (populatedProperties != null && !populatedProperties.equals(this.mappedProperties)) {
            throw new InvalidDataAccessApiUsageException("Given ResultSet does not contain all fields " + "necessary to populate object of class [" + this.mappedClass + "]: " + this.mappedProperties);
        }

        return mappedObject;
    }


    /**
     * Retrieve a JDBC object value for the specified column.
     * <p>
     * The default implementation calls
     * {@link JdbcUtils#getResultSetValue(java.sql.ResultSet, int, Class)}. Subclasses may override
     * this to check specific value types upfront, or to post-process values return from
     * <code>getResultSetValue</code>.
     * 
     * @param rs is the ResultSet holding the data
     * @param index is the column index
     * @param pd the bean property that each result object is expected to match (or
     *        <code>null</code> if none specified)
     * @return the Object value
     * @throws SQLException in case of extraction failure
     * @see org.springframework.jdbc.support.JdbcUtils#getResultSetValue(java.sql.ResultSet, int,
     *      Class)
     */
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
    }
}
