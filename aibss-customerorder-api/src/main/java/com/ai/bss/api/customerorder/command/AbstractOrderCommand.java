package com.ai.bss.api.customerorder.command;

import java.util.LinkedHashSet;
import java.util.Set;

import com.ai.bss.api.base.BaseCommand;
import com.ai.bss.api.base.CharacteristicValue;
import com.ai.bss.api.customer.CustomerId;
import com.ai.bss.api.customerorder.CustomerOrderId;

public abstract class AbstractOrderCommand extends BaseCommand {
	private CustomerId customerId;
	private CustomerOrderId customerOrderId;
	private Set<CharacteristicValue> characterValues = new LinkedHashSet<CharacteristicValue>();
	public AbstractOrderCommand() {
		// TODO Auto-generated constructor stub
	}
	public CustomerOrderId getCustomerOrderId() {
		return customerOrderId;
	}
	public void setCustomerOrderId(CustomerOrderId customerOrderId) {
		this.customerOrderId = customerOrderId;
	}
	public Set<CharacteristicValue> getCharacterValues() {
		return characterValues;
	}
	public void setCharacterValues(Set<CharacteristicValue> characterValues) {
		this.characterValues = characterValues;
	}
	
	public void addCharacterValues(CharacteristicValue characterValue) {
		this.characterValues.add(characterValue);
	}
	public CustomerId getCustomerId() {
		return customerId;
	}
	public void setCustomerId(CustomerId customerId) {
		this.customerId = customerId;
	}

}
