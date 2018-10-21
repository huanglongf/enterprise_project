package com.lmis.sys.codeRule.enums;

public enum RuleConfigEnum {


        常量("常量", "constant"), 日期("日期", "date"), 时间("时间", "time"), 日期时间("日期时间", "datetime"),自增长("自增长", "growth"),随机数("随机数", "random");
        // 成员变量
        private String name;
        private String index;

        // 构造方法
        private RuleConfigEnum(String name, String index) {
            this.name = name;
            this.index = index;
        }

        // 普通方法
        public static String getName(String index) {
            for (RuleConfigEnum c : RuleConfigEnum.values()) {
                if (c.getIndex() .equals(index)) {
                    return c.name;
                }
            }
            return null;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

}
