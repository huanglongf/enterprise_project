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

package com.jumbo.wms.model.trans;

public enum SkuTagType {
	ANY_MATCHING(1), // 任意匹配标签
	COMPLETE_MATCHING(2), // 完全匹配
	CONTAIN_MATCHING(3), // 包含匹配
	ACTIVE_TAGS(5);// 活动标签
	private int value;

	private SkuTagType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static SkuTagType valueOf(int value) {
		switch (value) {
		case 1:
			return ANY_MATCHING;
		case 2:
			return COMPLETE_MATCHING;
		case 3:
			return CONTAIN_MATCHING;
		case 5:
			return ACTIVE_TAGS;
		default:
			throw new IllegalArgumentException();
		}
	}
}
