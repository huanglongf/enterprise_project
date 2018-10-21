package com.jumbo.wms.model.warehouse;

public enum DistributionRuleStatus {

	ENABLE(1), // 启用
	DISABLE(2); // 禁用
	

    private int value;

    private DistributionRuleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DistributionRuleStatus valueOf(int value) {
        switch (value) {
            case 1:
                return ENABLE;
            case 2:
                return DISABLE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
