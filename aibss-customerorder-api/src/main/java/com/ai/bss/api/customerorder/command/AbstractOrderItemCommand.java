package com.ai.bss.api.customerorder.command;

import com.ai.bss.api.base.BaseCommand;
import com.ai.bss.api.customerorder.CustomerOrderId;
import com.ai.bss.api.customerorder.CustomerOrderItemId;

public class AbstractOrderItemCommand extends BaseCommand {
	private CustomerOrderId customerOrderId;
	private CustomerOrderItemId customerOrderItemId;
	public AbstractOrderItemCommand() {
		// TODO Auto-generated constructor stub
	}
	public CustomerOrderId getCustomerOrderId() {
		return customerOrderId;
	}
	public void setCustomerOrderId(CustomerOrderId customerOrderId) {
		this.customerOrderId = customerOrderId;
	}
	public CustomerOrderItemId getCustomerOrderItemId() {
		return customerOrderItemId;
	}
	public void setCustomerOrderItemId(CustomerOrderItemId customerOrderItemId) {
		this.customerOrderItemId = customerOrderItemId;
	}

}
