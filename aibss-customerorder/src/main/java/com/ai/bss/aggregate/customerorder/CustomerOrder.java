package com.ai.bss.aggregate.customerorder;

import java.util.LinkedHashSet;
import java.util.Set;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.ai.bss.api.base.CharacteristicValue;
import com.ai.bss.api.customerorder.CustomerOrderId;
import com.ai.bss.api.customerorder.CustomerOrderItemId;
import com.ai.bss.api.customerorder.event.BuyOrderItemCreatedEvent;
import com.ai.bss.api.customerorder.event.CompleteOrderRequestedEvent;
import com.ai.bss.api.customerorder.event.OrderCancelConfirmedEvent;
import com.ai.bss.api.customerorder.event.OrderCancelRequestedEvent;
import com.ai.bss.api.customerorder.event.OrderCanceledEvent;
import com.ai.bss.api.customerorder.event.OrderCompletedEvent;
import com.ai.bss.api.customerorder.event.OrderConfirmedEvent;
import com.ai.bss.api.customerorder.event.OrderDeliverRequestedEvent;
import com.ai.bss.api.customerorder.event.OrderDeliveredEvent;
import com.ai.bss.api.customerorder.event.OrderFinishedEvent;
import com.ai.bss.api.customerorder.event.OrderGoodsReceivedEvent;
import com.ai.bss.api.customerorder.event.OrderGoodsReturnedEvent;
import com.ai.bss.api.customerorder.event.OrderInitializedEvent;
import com.ai.bss.api.customerorder.event.OrderItemDeliverRequestedEvent;
import com.ai.bss.api.customerorder.event.OrderItemDeliveredEvent;
import com.ai.bss.api.customerorder.event.OrderItemGoodsReceivedEvent;
import com.ai.bss.api.customerorder.event.OrderItemGoodsReturnedEvent;
import com.ai.bss.api.customerorder.event.OrderItemReturnGoodsRequestedEvent;
import com.ai.bss.api.customerorder.event.OrderPaidEvent;
import com.ai.bss.api.customerorder.event.OrderRefundedEvent;
import com.ai.bss.api.customerorder.event.OrderReturnGoodsRequestedEvent;
import com.ai.bss.api.product.dto.BuyOffer;

public class CustomerOrder extends AbstractAnnotatedAggregateRoot {
	@AggregateIdentifier
	private CustomerOrderId customerOrderId;
	private String state;
	private String customerId;
	private Set<AbstractCustomerOrderItem> orderItems=new LinkedHashSet<AbstractCustomerOrderItem>();
	private Set<CharacteristicValue> characterValues = new LinkedHashSet<CharacteristicValue>();

	
	public CustomerOrder(){
		
	}
	public CustomerOrder(CustomerOrderId customerOrderId,Set<BuyOffer> offers,Set<CharacteristicValue> characterValues){		
		OrderInitializedEvent event= new OrderInitializedEvent();
		event.setCustomerOrderId(customerOrderId);
		event.setCharacterValues(characterValues);
		event.setOffers(offers);
		apply(event);
	}
	
	@EventHandler
	public void onOrderInitialized(OrderInitializedEvent event){
		this.customerOrderId=event.getCustomerOrderId();
		this.characterValues=event.getCharacterValues();
	}
	
	public void addBuyOfferItem(BuyOffer buyOffer){
		BuyOrderItemCreatedEvent event= new BuyOrderItemCreatedEvent();
		CustomerOrderItemId customerOrderItemId = new CustomerOrderItemId();
		BuyCustomerOrderItem orderItem=new  BuyCustomerOrderItem(customerOrderId,customerOrderItemId,buyOffer);
		this.addOrderItem(orderItem);
		event.setCustomerOrderId(customerOrderId);
		event.setCustomerOrderItemId(customerOrderItemId);
		event.setBuyOffer(buyOffer);
		apply(event);
	}
	
	public void confirmOrder(){
		OrderConfirmedEvent event=new OrderConfirmedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void payOrder(){
		OrderPaidEvent event=new OrderPaidEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void requestDeliveryOrder(){
		OrderDeliverRequestedEvent event=new OrderDeliverRequestedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void requestDeliveryOrderItem(String[] itemIds){
		for (String orderItemId : itemIds) {
			OrderItemDeliverRequestedEvent event=new OrderItemDeliverRequestedEvent();
			event.setCustomerOrderId(customerOrderId);
			event.setCustomerOrderItemId(new CustomerOrderItemId(orderItemId));
			apply(event);
		}		
	}
	
	public void finishDeliveryOrder(){
		OrderDeliveredEvent event=new OrderDeliveredEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void finishDeliveryOrderItem(String[] itemIds){
		for (String orderItemId : itemIds) {
			OrderItemDeliveredEvent event=new OrderItemDeliveredEvent();
			event.setCustomerOrderId(customerOrderId);
			event.setCustomerOrderItemId(new CustomerOrderItemId(orderItemId));
			apply(event);
		}		
	}
	
	public void requestCompleteOrder(){
		CompleteOrderRequestedEvent event=new CompleteOrderRequestedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void completeOrder(){
		OrderCompletedEvent event=new OrderCompletedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void finishOrder(){
		OrderFinishedEvent event=new OrderFinishedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void requestCancelOrder(){
		OrderCancelRequestedEvent event=new OrderCancelRequestedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void confirmCancelOrder(){
		OrderCancelConfirmedEvent event=new OrderCancelConfirmedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void requestReturnGoodsOrder(){
		OrderReturnGoodsRequestedEvent event=new OrderReturnGoodsRequestedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void requestReturnItemGoodsOrder(String[] itemIds){
		for (String orderItemId : itemIds) {
			OrderItemReturnGoodsRequestedEvent event=new OrderItemReturnGoodsRequestedEvent();
			event.setCustomerOrderId(customerOrderId);
			event.setCustomerOrderItemId(new CustomerOrderItemId(orderItemId));
			apply(event);
		}		
	}
	
	public void returnGoodsOrder(){
		OrderGoodsReturnedEvent event=new OrderGoodsReturnedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void returnItemGoodsOrder(String[] itemIds){
		for (String orderItemId : itemIds) {
			OrderItemGoodsReturnedEvent event=new OrderItemGoodsReturnedEvent();
			event.setCustomerOrderId(customerOrderId);
			event.setCustomerOrderItemId(new CustomerOrderItemId(orderItemId));
			apply(event);
		}		
	}
	
	public void receiveGoodsOrder(){
		OrderGoodsReceivedEvent event=new OrderGoodsReceivedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void receiveItemGoodsOrder(String[] itemIds){
		for (String orderItemId : itemIds) {
			OrderItemGoodsReceivedEvent event=new OrderItemGoodsReceivedEvent();
			event.setCustomerOrderId(customerOrderId);
			event.setCustomerOrderItemId(new CustomerOrderItemId(orderItemId));
			apply(event);
		}		
	}
	
	
	
	public void refundOrder(){
		OrderRefundedEvent event=new OrderRefundedEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public void cancelOrder(){
		OrderCanceledEvent event=new OrderCanceledEvent();
		event.setCustomerOrderId(customerOrderId);
		apply(event);
	}
	
	public CustomerOrderId getCustomerOrderId() {
		return customerOrderId;
	}
	public void setCustomerOrderId(CustomerOrderId customerOrderId) {
		this.customerOrderId = customerOrderId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Set<AbstractCustomerOrderItem> getOrderItems() {
		return orderItems;
	}
	private void addOrderItem(AbstractCustomerOrderItem orderItem){
		if (!orderItems.contains(orderItem)){
			orderItems.add(orderItem);
		}
	}
	public Set<CharacteristicValue> getCharacterValues() {
		return characterValues;
	}
	public void setCharacterValues(Set<CharacteristicValue> characterValues) {
		this.characterValues = characterValues;
	}
	
	public void addCharacterValues(CharacteristicValue characterValue) {
		if (!this.characterValues.contains(characterValue)){
			this.characterValues.add(characterValue);
		}
	}

}