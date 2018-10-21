package com.lmis.sys.codeRule.enums;

public enum UpdateCycleEnum {


        每天("每天", "cycle_day"), 每周("每周", "cycle_week"), 每月("每月", "cycle_month"), 每年("每年", "cycle_year");
        // 成员变量
        private String name;
        private String index;

        // 构造方法
        private UpdateCycleEnum(String name, String index) {
            this.name = name;
            this.index = index;
        }

        // 普通方法
        public static String getName(String index) {
            for (UpdateCycleEnum c : UpdateCycleEnum.values()) {
                if (c.getIndex().equals(index)) {
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
