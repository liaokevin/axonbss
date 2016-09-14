package com.ai.bss.query.api.customerorder;

import javax.persistence.Entity;

import com.ai.bss.query.api.product.AbstractProduct;
@Entity
public class OrderItemProductEntry extends AbstractProduct {

	public OrderItemProductEntry() {
	}
	
	private long asisVersion;
	
	//updated after archived
	private long tobeVersion;
	
	public long getAsisVersion() {
		return asisVersion;
	}

	public void setAsisVersion(long asisVersion) {
		this.asisVersion = asisVersion;
	}

	public long getTobeVersion() {
		return tobeVersion;
	}

	public void setTobeVersion(long tobeVersion) {
		this.tobeVersion = tobeVersion;
	}


}
