package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpressReturnStorageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExpressReturnStorageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNull() {
            addCriterion("warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNotNull() {
            addCriterion("warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeEqualTo(String value) {
            addCriterion("warehouse_code =", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotEqualTo(String value) {
            addCriterion("warehouse_code <>", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThan(String value) {
            addCriterion("warehouse_code >", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_code >=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThan(String value) {
            addCriterion("warehouse_code <", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_code <=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLike(String value) {
            addCriterion("warehouse_code like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotLike(String value) {
            addCriterion("warehouse_code not like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIn(List<String> values) {
            addCriterion("warehouse_code in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotIn(List<String> values) {
            addCriterion("warehouse_code not in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeBetween(String value1, String value2) {
            addCriterion("warehouse_code between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("warehouse_code not between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNull() {
            addCriterion("warehouse_name is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNotNull() {
            addCriterion("warehouse_name is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameEqualTo(String value) {
            addCriterion("warehouse_name =", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotEqualTo(String value) {
            addCriterion("warehouse_name <>", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThan(String value) {
            addCriterion("warehouse_name >", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_name >=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThan(String value) {
            addCriterion("warehouse_name <", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThanOrEqualTo(String value) {
            addCriterion("warehouse_name <=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLike(String value) {
            addCriterion("warehouse_name like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotLike(String value) {
            addCriterion("warehouse_name not like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIn(List<String> values) {
            addCriterion("warehouse_name in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotIn(List<String> values) {
            addCriterion("warehouse_name not in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameBetween(String value1, String value2) {
            addCriterion("warehouse_name between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotBetween(String value1, String value2) {
            addCriterion("warehouse_name not between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andJobNoIsNull() {
            addCriterion("job_no is null");
            return (Criteria) this;
        }

        public Criteria andJobNoIsNotNull() {
            addCriterion("job_no is not null");
            return (Criteria) this;
        }

        public Criteria andJobNoEqualTo(String value) {
            addCriterion("job_no =", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoNotEqualTo(String value) {
            addCriterion("job_no <>", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoGreaterThan(String value) {
            addCriterion("job_no >", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoGreaterThanOrEqualTo(String value) {
            addCriterion("job_no >=", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoLessThan(String value) {
            addCriterion("job_no <", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoLessThanOrEqualTo(String value) {
            addCriterion("job_no <=", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoLike(String value) {
            addCriterion("job_no like", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoNotLike(String value) {
            addCriterion("job_no not like", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoIn(List<String> values) {
            addCriterion("job_no in", values, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoNotIn(List<String> values) {
            addCriterion("job_no not in", values, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoBetween(String value1, String value2) {
            addCriterion("job_no between", value1, value2, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoNotBetween(String value1, String value2) {
            addCriterion("job_no not between", value1, value2, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNull() {
            addCriterion("job_type is null");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNotNull() {
            addCriterion("job_type is not null");
            return (Criteria) this;
        }

        public Criteria andJobTypeEqualTo(String value) {
            addCriterion("job_type =", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotEqualTo(String value) {
            addCriterion("job_type <>", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThan(String value) {
            addCriterion("job_type >", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThanOrEqualTo(String value) {
            addCriterion("job_type >=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThan(String value) {
            addCriterion("job_type <", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThanOrEqualTo(String value) {
            addCriterion("job_type <=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLike(String value) {
            addCriterion("job_type like", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotLike(String value) {
            addCriterion("job_type not like", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeIn(List<String> values) {
            addCriterion("job_type in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotIn(List<String> values) {
            addCriterion("job_type not in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeBetween(String value1, String value2) {
            addCriterion("job_type between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotBetween(String value1, String value2) {
            addCriterion("job_type not between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIsNull() {
            addCriterion("store_code is null");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIsNotNull() {
            addCriterion("store_code is not null");
            return (Criteria) this;
        }

        public Criteria andStoreCodeEqualTo(String value) {
            addCriterion("store_code =", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeNotEqualTo(String value) {
            addCriterion("store_code <>", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeGreaterThan(String value) {
            addCriterion("store_code >", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeGreaterThanOrEqualTo(String value) {
            addCriterion("store_code >=", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeLessThan(String value) {
            addCriterion("store_code <", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeLessThanOrEqualTo(String value) {
            addCriterion("store_code <=", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeLike(String value) {
            addCriterion("store_code like", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeNotLike(String value) {
            addCriterion("store_code not like", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIn(List<String> values) {
            addCriterion("store_code in", values, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeNotIn(List<String> values) {
            addCriterion("store_code not in", values, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeBetween(String value1, String value2) {
            addCriterion("store_code between", value1, value2, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeNotBetween(String value1, String value2) {
            addCriterion("store_code not between", value1, value2, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreNameIsNull() {
            addCriterion("store_name is null");
            return (Criteria) this;
        }

        public Criteria andStoreNameIsNotNull() {
            addCriterion("store_name is not null");
            return (Criteria) this;
        }

        public Criteria andStoreNameEqualTo(String value) {
            addCriterion("store_name =", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameNotEqualTo(String value) {
            addCriterion("store_name <>", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameGreaterThan(String value) {
            addCriterion("store_name >", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameGreaterThanOrEqualTo(String value) {
            addCriterion("store_name >=", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameLessThan(String value) {
            addCriterion("store_name <", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameLessThanOrEqualTo(String value) {
            addCriterion("store_name <=", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameLike(String value) {
            addCriterion("store_name like", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameNotLike(String value) {
            addCriterion("store_name not like", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameIn(List<String> values) {
            addCriterion("store_name in", values, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameNotIn(List<String> values) {
            addCriterion("store_name not in", values, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameBetween(String value1, String value2) {
            addCriterion("store_name between", value1, value2, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameNotBetween(String value1, String value2) {
            addCriterion("store_name not between", value1, value2, "storeName");
            return (Criteria) this;
        }

        public Criteria andRelatedNoIsNull() {
            addCriterion("related_no is null");
            return (Criteria) this;
        }

        public Criteria andRelatedNoIsNotNull() {
            addCriterion("related_no is not null");
            return (Criteria) this;
        }

        public Criteria andRelatedNoEqualTo(String value) {
            addCriterion("related_no =", value, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoNotEqualTo(String value) {
            addCriterion("related_no <>", value, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoGreaterThan(String value) {
            addCriterion("related_no >", value, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoGreaterThanOrEqualTo(String value) {
            addCriterion("related_no >=", value, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoLessThan(String value) {
            addCriterion("related_no <", value, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoLessThanOrEqualTo(String value) {
            addCriterion("related_no <=", value, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoLike(String value) {
            addCriterion("related_no like", value, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoNotLike(String value) {
            addCriterion("related_no not like", value, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoIn(List<String> values) {
            addCriterion("related_no in", values, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoNotIn(List<String> values) {
            addCriterion("related_no not in", values, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoBetween(String value1, String value2) {
            addCriterion("related_no between", value1, value2, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andRelatedNoNotBetween(String value1, String value2) {
            addCriterion("related_no not between", value1, value2, "relatedNo");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderIsNull() {
            addCriterion("re_epistatic_order is null");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderIsNotNull() {
            addCriterion("re_epistatic_order is not null");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderEqualTo(String value) {
            addCriterion("re_epistatic_order =", value, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderNotEqualTo(String value) {
            addCriterion("re_epistatic_order <>", value, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderGreaterThan(String value) {
            addCriterion("re_epistatic_order >", value, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderGreaterThanOrEqualTo(String value) {
            addCriterion("re_epistatic_order >=", value, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderLessThan(String value) {
            addCriterion("re_epistatic_order <", value, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderLessThanOrEqualTo(String value) {
            addCriterion("re_epistatic_order <=", value, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderLike(String value) {
            addCriterion("re_epistatic_order like", value, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderNotLike(String value) {
            addCriterion("re_epistatic_order not like", value, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderIn(List<String> values) {
            addCriterion("re_epistatic_order in", values, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderNotIn(List<String> values) {
            addCriterion("re_epistatic_order not in", values, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderBetween(String value1, String value2) {
            addCriterion("re_epistatic_order between", value1, value2, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderNotBetween(String value1, String value2) {
            addCriterion("re_epistatic_order not between", value1, value2, "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReProvinceIsNull() {
            addCriterion("re_province is null");
            return (Criteria) this;
        }

        public Criteria andReProvinceIsNotNull() {
            addCriterion("re_province is not null");
            return (Criteria) this;
        }

        public Criteria andReProvinceEqualTo(String value) {
            addCriterion("re_province =", value, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceNotEqualTo(String value) {
            addCriterion("re_province <>", value, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceGreaterThan(String value) {
            addCriterion("re_province >", value, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("re_province >=", value, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceLessThan(String value) {
            addCriterion("re_province <", value, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceLessThanOrEqualTo(String value) {
            addCriterion("re_province <=", value, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceLike(String value) {
            addCriterion("re_province like", value, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceNotLike(String value) {
            addCriterion("re_province not like", value, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceIn(List<String> values) {
            addCriterion("re_province in", values, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceNotIn(List<String> values) {
            addCriterion("re_province not in", values, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceBetween(String value1, String value2) {
            addCriterion("re_province between", value1, value2, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReProvinceNotBetween(String value1, String value2) {
            addCriterion("re_province not between", value1, value2, "reProvince");
            return (Criteria) this;
        }

        public Criteria andReCityIsNull() {
            addCriterion("re_city is null");
            return (Criteria) this;
        }

        public Criteria andReCityIsNotNull() {
            addCriterion("re_city is not null");
            return (Criteria) this;
        }

        public Criteria andReCityEqualTo(String value) {
            addCriterion("re_city =", value, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityNotEqualTo(String value) {
            addCriterion("re_city <>", value, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityGreaterThan(String value) {
            addCriterion("re_city >", value, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityGreaterThanOrEqualTo(String value) {
            addCriterion("re_city >=", value, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityLessThan(String value) {
            addCriterion("re_city <", value, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityLessThanOrEqualTo(String value) {
            addCriterion("re_city <=", value, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityLike(String value) {
            addCriterion("re_city like", value, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityNotLike(String value) {
            addCriterion("re_city not like", value, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityIn(List<String> values) {
            addCriterion("re_city in", values, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityNotIn(List<String> values) {
            addCriterion("re_city not in", values, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityBetween(String value1, String value2) {
            addCriterion("re_city between", value1, value2, "reCity");
            return (Criteria) this;
        }

        public Criteria andReCityNotBetween(String value1, String value2) {
            addCriterion("re_city not between", value1, value2, "reCity");
            return (Criteria) this;
        }

        public Criteria andReStateIsNull() {
            addCriterion("re_state is null");
            return (Criteria) this;
        }

        public Criteria andReStateIsNotNull() {
            addCriterion("re_state is not null");
            return (Criteria) this;
        }

        public Criteria andReStateEqualTo(String value) {
            addCriterion("re_state =", value, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateNotEqualTo(String value) {
            addCriterion("re_state <>", value, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateGreaterThan(String value) {
            addCriterion("re_state >", value, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateGreaterThanOrEqualTo(String value) {
            addCriterion("re_state >=", value, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateLessThan(String value) {
            addCriterion("re_state <", value, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateLessThanOrEqualTo(String value) {
            addCriterion("re_state <=", value, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateLike(String value) {
            addCriterion("re_state like", value, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateNotLike(String value) {
            addCriterion("re_state not like", value, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateIn(List<String> values) {
            addCriterion("re_state in", values, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateNotIn(List<String> values) {
            addCriterion("re_state not in", values, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateBetween(String value1, String value2) {
            addCriterion("re_state between", value1, value2, "reState");
            return (Criteria) this;
        }

        public Criteria andReStateNotBetween(String value1, String value2) {
            addCriterion("re_state not between", value1, value2, "reState");
            return (Criteria) this;
        }

        public Criteria andReWeightIsNull() {
            addCriterion("re_weight is null");
            return (Criteria) this;
        }

        public Criteria andReWeightIsNotNull() {
            addCriterion("re_weight is not null");
            return (Criteria) this;
        }

        public Criteria andReWeightEqualTo(BigDecimal value) {
            addCriterion("re_weight =", value, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightNotEqualTo(BigDecimal value) {
            addCriterion("re_weight <>", value, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightGreaterThan(BigDecimal value) {
            addCriterion("re_weight >", value, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("re_weight >=", value, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightLessThan(BigDecimal value) {
            addCriterion("re_weight <", value, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("re_weight <=", value, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightIn(List<BigDecimal> values) {
            addCriterion("re_weight in", values, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightNotIn(List<BigDecimal> values) {
            addCriterion("re_weight not in", values, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_weight between", value1, value2, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_weight not between", value1, value2, "reWeight");
            return (Criteria) this;
        }

        public Criteria andReLengthIsNull() {
            addCriterion("re_length is null");
            return (Criteria) this;
        }

        public Criteria andReLengthIsNotNull() {
            addCriterion("re_length is not null");
            return (Criteria) this;
        }

        public Criteria andReLengthEqualTo(BigDecimal value) {
            addCriterion("re_length =", value, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthNotEqualTo(BigDecimal value) {
            addCriterion("re_length <>", value, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthGreaterThan(BigDecimal value) {
            addCriterion("re_length >", value, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("re_length >=", value, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthLessThan(BigDecimal value) {
            addCriterion("re_length <", value, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("re_length <=", value, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthIn(List<BigDecimal> values) {
            addCriterion("re_length in", values, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthNotIn(List<BigDecimal> values) {
            addCriterion("re_length not in", values, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_length between", value1, value2, "reLength");
            return (Criteria) this;
        }

        public Criteria andReLengthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_length not between", value1, value2, "reLength");
            return (Criteria) this;
        }

        public Criteria andReWidthIsNull() {
            addCriterion("re_width is null");
            return (Criteria) this;
        }

        public Criteria andReWidthIsNotNull() {
            addCriterion("re_width is not null");
            return (Criteria) this;
        }

        public Criteria andReWidthEqualTo(BigDecimal value) {
            addCriterion("re_width =", value, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthNotEqualTo(BigDecimal value) {
            addCriterion("re_width <>", value, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthGreaterThan(BigDecimal value) {
            addCriterion("re_width >", value, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("re_width >=", value, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthLessThan(BigDecimal value) {
            addCriterion("re_width <", value, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("re_width <=", value, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthIn(List<BigDecimal> values) {
            addCriterion("re_width in", values, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthNotIn(List<BigDecimal> values) {
            addCriterion("re_width not in", values, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_width between", value1, value2, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReWidthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_width not between", value1, value2, "reWidth");
            return (Criteria) this;
        }

        public Criteria andReHigthIsNull() {
            addCriterion("re_higth is null");
            return (Criteria) this;
        }

        public Criteria andReHigthIsNotNull() {
            addCriterion("re_higth is not null");
            return (Criteria) this;
        }

        public Criteria andReHigthEqualTo(BigDecimal value) {
            addCriterion("re_higth =", value, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthNotEqualTo(BigDecimal value) {
            addCriterion("re_higth <>", value, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthGreaterThan(BigDecimal value) {
            addCriterion("re_higth >", value, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("re_higth >=", value, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthLessThan(BigDecimal value) {
            addCriterion("re_higth <", value, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("re_higth <=", value, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthIn(List<BigDecimal> values) {
            addCriterion("re_higth in", values, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthNotIn(List<BigDecimal> values) {
            addCriterion("re_higth not in", values, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_higth between", value1, value2, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReHigthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_higth not between", value1, value2, "reHigth");
            return (Criteria) this;
        }

        public Criteria andReVolumnIsNull() {
            addCriterion("re_volumn is null");
            return (Criteria) this;
        }

        public Criteria andReVolumnIsNotNull() {
            addCriterion("re_volumn is not null");
            return (Criteria) this;
        }

        public Criteria andReVolumnEqualTo(BigDecimal value) {
            addCriterion("re_volumn =", value, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnNotEqualTo(BigDecimal value) {
            addCriterion("re_volumn <>", value, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnGreaterThan(BigDecimal value) {
            addCriterion("re_volumn >", value, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("re_volumn >=", value, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnLessThan(BigDecimal value) {
            addCriterion("re_volumn <", value, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnLessThanOrEqualTo(BigDecimal value) {
            addCriterion("re_volumn <=", value, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnIn(List<BigDecimal> values) {
            addCriterion("re_volumn in", values, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnNotIn(List<BigDecimal> values) {
            addCriterion("re_volumn not in", values, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_volumn between", value1, value2, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andReVolumnNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("re_volumn not between", value1, value2, "reVolumn");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andPlanQtyIsNull() {
            addCriterion("plan_qty is null");
            return (Criteria) this;
        }

        public Criteria andPlanQtyIsNotNull() {
            addCriterion("plan_qty is not null");
            return (Criteria) this;
        }

        public Criteria andPlanQtyEqualTo(Integer value) {
            addCriterion("plan_qty =", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyNotEqualTo(Integer value) {
            addCriterion("plan_qty <>", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyGreaterThan(Integer value) {
            addCriterion("plan_qty >", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_qty >=", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyLessThan(Integer value) {
            addCriterion("plan_qty <", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyLessThanOrEqualTo(Integer value) {
            addCriterion("plan_qty <=", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyIn(List<Integer> values) {
            addCriterion("plan_qty in", values, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyNotIn(List<Integer> values) {
            addCriterion("plan_qty not in", values, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyBetween(Integer value1, Integer value2) {
            addCriterion("plan_qty between", value1, value2, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_qty not between", value1, value2, "planQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyIsNull() {
            addCriterion("actual_qty is null");
            return (Criteria) this;
        }

        public Criteria andActualQtyIsNotNull() {
            addCriterion("actual_qty is not null");
            return (Criteria) this;
        }

        public Criteria andActualQtyEqualTo(Integer value) {
            addCriterion("actual_qty =", value, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyNotEqualTo(Integer value) {
            addCriterion("actual_qty <>", value, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyGreaterThan(Integer value) {
            addCriterion("actual_qty >", value, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_qty >=", value, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyLessThan(Integer value) {
            addCriterion("actual_qty <", value, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyLessThanOrEqualTo(Integer value) {
            addCriterion("actual_qty <=", value, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyIn(List<Integer> values) {
            addCriterion("actual_qty in", values, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyNotIn(List<Integer> values) {
            addCriterion("actual_qty not in", values, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyBetween(Integer value1, Integer value2) {
            addCriterion("actual_qty between", value1, value2, "actualQty");
            return (Criteria) this;
        }

        public Criteria andActualQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_qty not between", value1, value2, "actualQty");
            return (Criteria) this;
        }

        public Criteria andBatchNumberIsNull() {
            addCriterion("batch_number is null");
            return (Criteria) this;
        }

        public Criteria andBatchNumberIsNotNull() {
            addCriterion("batch_number is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNumberEqualTo(Integer value) {
            addCriterion("batch_number =", value, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberNotEqualTo(Integer value) {
            addCriterion("batch_number <>", value, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberGreaterThan(Integer value) {
            addCriterion("batch_number >", value, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("batch_number >=", value, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberLessThan(Integer value) {
            addCriterion("batch_number <", value, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberLessThanOrEqualTo(Integer value) {
            addCriterion("batch_number <=", value, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberIn(List<Integer> values) {
            addCriterion("batch_number in", values, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberNotIn(List<Integer> values) {
            addCriterion("batch_number not in", values, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberBetween(Integer value1, Integer value2) {
            addCriterion("batch_number between", value1, value2, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andBatchNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("batch_number not between", value1, value2, "batchNumber");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andTransportCodeIsNull() {
            addCriterion("transport_code is null");
            return (Criteria) this;
        }

        public Criteria andTransportCodeIsNotNull() {
            addCriterion("transport_code is not null");
            return (Criteria) this;
        }

        public Criteria andTransportCodeEqualTo(String value) {
            addCriterion("transport_code =", value, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeNotEqualTo(String value) {
            addCriterion("transport_code <>", value, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeGreaterThan(String value) {
            addCriterion("transport_code >", value, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeGreaterThanOrEqualTo(String value) {
            addCriterion("transport_code >=", value, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeLessThan(String value) {
            addCriterion("transport_code <", value, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeLessThanOrEqualTo(String value) {
            addCriterion("transport_code <=", value, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeLike(String value) {
            addCriterion("transport_code like", value, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeNotLike(String value) {
            addCriterion("transport_code not like", value, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeIn(List<String> values) {
            addCriterion("transport_code in", values, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeNotIn(List<String> values) {
            addCriterion("transport_code not in", values, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeBetween(String value1, String value2) {
            addCriterion("transport_code between", value1, value2, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportCodeNotBetween(String value1, String value2) {
            addCriterion("transport_code not between", value1, value2, "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportNameIsNull() {
            addCriterion("transport_name is null");
            return (Criteria) this;
        }

        public Criteria andTransportNameIsNotNull() {
            addCriterion("transport_name is not null");
            return (Criteria) this;
        }

        public Criteria andTransportNameEqualTo(String value) {
            addCriterion("transport_name =", value, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameNotEqualTo(String value) {
            addCriterion("transport_name <>", value, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameGreaterThan(String value) {
            addCriterion("transport_name >", value, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameGreaterThanOrEqualTo(String value) {
            addCriterion("transport_name >=", value, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameLessThan(String value) {
            addCriterion("transport_name <", value, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameLessThanOrEqualTo(String value) {
            addCriterion("transport_name <=", value, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameLike(String value) {
            addCriterion("transport_name like", value, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameNotLike(String value) {
            addCriterion("transport_name not like", value, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameIn(List<String> values) {
            addCriterion("transport_name in", values, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameNotIn(List<String> values) {
            addCriterion("transport_name not in", values, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameBetween(String value1, String value2) {
            addCriterion("transport_name between", value1, value2, "transportName");
            return (Criteria) this;
        }

        public Criteria andTransportNameNotBetween(String value1, String value2) {
            addCriterion("transport_name not between", value1, value2, "transportName");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeIsNull() {
            addCriterion("itemtype_code is null");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeIsNotNull() {
            addCriterion("itemtype_code is not null");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeEqualTo(String value) {
            addCriterion("itemtype_code =", value, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeNotEqualTo(String value) {
            addCriterion("itemtype_code <>", value, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeGreaterThan(String value) {
            addCriterion("itemtype_code >", value, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("itemtype_code >=", value, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeLessThan(String value) {
            addCriterion("itemtype_code <", value, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeLessThanOrEqualTo(String value) {
            addCriterion("itemtype_code <=", value, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeLike(String value) {
            addCriterion("itemtype_code like", value, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeNotLike(String value) {
            addCriterion("itemtype_code not like", value, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeIn(List<String> values) {
            addCriterion("itemtype_code in", values, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeNotIn(List<String> values) {
            addCriterion("itemtype_code not in", values, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeBetween(String value1, String value2) {
            addCriterion("itemtype_code between", value1, value2, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeNotBetween(String value1, String value2) {
            addCriterion("itemtype_code not between", value1, value2, "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameIsNull() {
            addCriterion("itemtype_name is null");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameIsNotNull() {
            addCriterion("itemtype_name is not null");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameEqualTo(String value) {
            addCriterion("itemtype_name =", value, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameNotEqualTo(String value) {
            addCriterion("itemtype_name <>", value, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameGreaterThan(String value) {
            addCriterion("itemtype_name >", value, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("itemtype_name >=", value, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameLessThan(String value) {
            addCriterion("itemtype_name <", value, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameLessThanOrEqualTo(String value) {
            addCriterion("itemtype_name <=", value, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameLike(String value) {
            addCriterion("itemtype_name like", value, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameNotLike(String value) {
            addCriterion("itemtype_name not like", value, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameIn(List<String> values) {
            addCriterion("itemtype_name in", values, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameNotIn(List<String> values) {
            addCriterion("itemtype_name not in", values, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameBetween(String value1, String value2) {
            addCriterion("itemtype_name between", value1, value2, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameNotBetween(String value1, String value2) {
            addCriterion("itemtype_name not between", value1, value2, "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonIsNull() {
            addCriterion("refuse_reason is null");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonIsNotNull() {
            addCriterion("refuse_reason is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonEqualTo(String value) {
            addCriterion("refuse_reason =", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonNotEqualTo(String value) {
            addCriterion("refuse_reason <>", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonGreaterThan(String value) {
            addCriterion("refuse_reason >", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonGreaterThanOrEqualTo(String value) {
            addCriterion("refuse_reason >=", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonLessThan(String value) {
            addCriterion("refuse_reason <", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonLessThanOrEqualTo(String value) {
            addCriterion("refuse_reason <=", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonLike(String value) {
            addCriterion("refuse_reason like", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonNotLike(String value) {
            addCriterion("refuse_reason not like", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonIn(List<String> values) {
            addCriterion("refuse_reason in", values, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonNotIn(List<String> values) {
            addCriterion("refuse_reason not in", values, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonBetween(String value1, String value2) {
            addCriterion("refuse_reason between", value1, value2, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonNotBetween(String value1, String value2) {
            addCriterion("refuse_reason not between", value1, value2, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseIsNull() {
            addCriterion("physical_warehouse is null");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseIsNotNull() {
            addCriterion("physical_warehouse is not null");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseEqualTo(String value) {
            addCriterion("physical_warehouse =", value, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseNotEqualTo(String value) {
            addCriterion("physical_warehouse <>", value, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseGreaterThan(String value) {
            addCriterion("physical_warehouse >", value, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("physical_warehouse >=", value, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseLessThan(String value) {
            addCriterion("physical_warehouse <", value, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseLessThanOrEqualTo(String value) {
            addCriterion("physical_warehouse <=", value, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseLike(String value) {
            addCriterion("physical_warehouse like", value, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseNotLike(String value) {
            addCriterion("physical_warehouse not like", value, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseIn(List<String> values) {
            addCriterion("physical_warehouse in", values, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseNotIn(List<String> values) {
            addCriterion("physical_warehouse not in", values, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseBetween(String value1, String value2) {
            addCriterion("physical_warehouse between", value1, value2, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseNotBetween(String value1, String value2) {
            addCriterion("physical_warehouse not between", value1, value2, "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIsNull() {
            addCriterion("return_order is null");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIsNotNull() {
            addCriterion("return_order is not null");
            return (Criteria) this;
        }

        public Criteria andReturnOrderEqualTo(String value) {
            addCriterion("return_order =", value, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNotEqualTo(String value) {
            addCriterion("return_order <>", value, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderGreaterThan(String value) {
            addCriterion("return_order >", value, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderGreaterThanOrEqualTo(String value) {
            addCriterion("return_order >=", value, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderLessThan(String value) {
            addCriterion("return_order <", value, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderLessThanOrEqualTo(String value) {
            addCriterion("return_order <=", value, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderLike(String value) {
            addCriterion("return_order like", value, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNotLike(String value) {
            addCriterion("return_order not like", value, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIn(List<String> values) {
            addCriterion("return_order in", values, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNotIn(List<String> values) {
            addCriterion("return_order not in", values, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderBetween(String value1, String value2) {
            addCriterion("return_order between", value1, value2, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNotBetween(String value1, String value2) {
            addCriterion("return_order not between", value1, value2, "returnOrder");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }
        
        public Criteria andWaybillEqualTo(String value) {
            addCriterion("waybill =", value, "waybill");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserIsNull() {
            addCriterion("deblock_user is null");
            return (Criteria) this;
        }

        public Criteria andDeblockUserIsNotNull() {
            addCriterion("deblock_user is not null");
            return (Criteria) this;
        }

        public Criteria andDeblockUserEqualTo(String value) {
            addCriterion("deblock_user =", value, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserNotEqualTo(String value) {
            addCriterion("deblock_user <>", value, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserGreaterThan(String value) {
            addCriterion("deblock_user >", value, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserGreaterThanOrEqualTo(String value) {
            addCriterion("deblock_user >=", value, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserLessThan(String value) {
            addCriterion("deblock_user <", value, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserLessThanOrEqualTo(String value) {
            addCriterion("deblock_user <=", value, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserLike(String value) {
            addCriterion("deblock_user like", value, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserNotLike(String value) {
            addCriterion("deblock_user not like", value, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserIn(List<String> values) {
            addCriterion("deblock_user in", values, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserNotIn(List<String> values) {
            addCriterion("deblock_user not in", values, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserBetween(String value1, String value2) {
            addCriterion("deblock_user between", value1, value2, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserNotBetween(String value1, String value2) {
            addCriterion("deblock_user not between", value1, value2, "deblockUser");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLikeInsensitive(String value) {
            addCriterion("upper(warehouse_code) like", value.toUpperCase(), "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLikeInsensitive(String value) {
            addCriterion("upper(warehouse_name) like", value.toUpperCase(), "warehouseName");
            return (Criteria) this;
        }

        public Criteria andJobNoLikeInsensitive(String value) {
            addCriterion("upper(job_no) like", value.toUpperCase(), "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobTypeLikeInsensitive(String value) {
            addCriterion("upper(job_type) like", value.toUpperCase(), "jobType");
            return (Criteria) this;
        }

        public Criteria andStoreCodeLikeInsensitive(String value) {
            addCriterion("upper(store_code) like", value.toUpperCase(), "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreNameLikeInsensitive(String value) {
            addCriterion("upper(store_name) like", value.toUpperCase(), "storeName");
            return (Criteria) this;
        }

        public Criteria andRelatedNoLikeInsensitive(String value) {
            addCriterion("upper(related_no) like", value.toUpperCase(), "relatedNo");
            return (Criteria) this;
        }

        public Criteria andReEpistaticOrderLikeInsensitive(String value) {
            addCriterion("upper(re_epistatic_order) like", value.toUpperCase(), "reEpistaticOrder");
            return (Criteria) this;
        }

        public Criteria andReProvinceLikeInsensitive(String value) {
            addCriterion("upper(re_province) like", value.toUpperCase(), "reProvince");
            return (Criteria) this;
        }

        public Criteria andReCityLikeInsensitive(String value) {
            addCriterion("upper(re_city) like", value.toUpperCase(), "reCity");
            return (Criteria) this;
        }

        public Criteria andReStateLikeInsensitive(String value) {
            addCriterion("upper(re_state) like", value.toUpperCase(), "reState");
            return (Criteria) this;
        }

        public Criteria andStateLikeInsensitive(String value) {
            addCriterion("upper(state) like", value.toUpperCase(), "state");
            return (Criteria) this;
        }

        public Criteria andTransportCodeLikeInsensitive(String value) {
            addCriterion("upper(transport_code) like", value.toUpperCase(), "transportCode");
            return (Criteria) this;
        }

        public Criteria andTransportNameLikeInsensitive(String value) {
            addCriterion("upper(transport_name) like", value.toUpperCase(), "transportName");
            return (Criteria) this;
        }

        public Criteria andItemtypeCodeLikeInsensitive(String value) {
            addCriterion("upper(itemtype_code) like", value.toUpperCase(), "itemtypeCode");
            return (Criteria) this;
        }

        public Criteria andItemtypeNameLikeInsensitive(String value) {
            addCriterion("upper(itemtype_name) like", value.toUpperCase(), "itemtypeName");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonLikeInsensitive(String value) {
            addCriterion("upper(refuse_reason) like", value.toUpperCase(), "refuseReason");
            return (Criteria) this;
        }

        public Criteria andPhysicalWarehouseLikeInsensitive(String value) {
            addCriterion("upper(physical_warehouse) like", value.toUpperCase(), "physicalWarehouse");
            return (Criteria) this;
        }

        public Criteria andReturnOrderLikeInsensitive(String value) {
            addCriterion("upper(return_order) like", value.toUpperCase(), "returnOrder");
            return (Criteria) this;
        }

        public Criteria andCreateUserLikeInsensitive(String value) {
            addCriterion("upper(create_user) like", value.toUpperCase(), "createUser");
            return (Criteria) this;
        }

        public Criteria andDeblockUserLikeInsensitive(String value) {
            addCriterion("upper(deblock_user) like", value.toUpperCase(), "deblockUser");
            return (Criteria) this;
        }

        public Criteria andRemarkLikeInsensitive(String value) {
            addCriterion("upper(remark) like", value.toUpperCase(), "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}