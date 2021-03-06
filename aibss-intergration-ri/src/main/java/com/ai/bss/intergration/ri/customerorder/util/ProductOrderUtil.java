package com.ai.bss.intergration.ri.customerorder.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ai.bss.api.activation.command.AbstractActivateProductCommand;
import com.ai.bss.api.activation.command.ActivateProductCommand;
import com.ai.bss.api.activation.command.DeactivateProductCommand;
import com.ai.bss.api.activation.command.ResumeProductCommand;
import com.ai.bss.api.activation.command.SuspendProductCommand;
import com.ai.bss.api.activation.command.UpdateProductAttrCommand;
import com.ai.bss.api.delivery.command.AbstractDeliveryProductCommand;
import com.ai.bss.api.delivery.command.DeliveryProductCommand;
import com.ai.bss.query.api.customerorder.OrderItemActionEnum;
import com.ai.bss.query.api.customerorder.OrderItemEntry;
import com.ai.bss.query.api.customerorder.OfferOrderProductRelEntry;
import com.ai.bss.query.api.customerorder.ProductOrderEntry;
import com.ai.bss.query.api.customerorder.ProductOrderStateEnum;
import com.ai.bss.query.api.customerorder.ProductOrderSuspendReasonEntry;
import com.ai.bss.query.api.productspecification.ProductSpecificationEntry;
import com.ai.bss.query.api.productspecification.ProductSpecificationTypeEnum;
import com.ai.bss.query.api.customerorder.ProductOrderCharacterValueEntry;

@Component
public class ProductOrderUtil implements IProductOrderUtil{
	@Autowired
	public RestTemplate client;
	@Autowired
	private CommandBus commandBus;
	
	public ProductOrderUtil() {
		
	}
	
	public FutureCallback activateProductOrder(OrderItemEntry orderItem,ProductOrderEntry orderProduct, String productSpecCode) throws Exception{
		FutureCallback callback = new FutureCallback();
		if (!needDelivery(orderProduct,productSpecCode) && needActivation(orderProduct,productSpecCode)){
			AbstractActivateProductCommand command= getActivateCommand(orderItem,orderProduct);
			if (null!=command){				
				commandBus.dispatch(new GenericCommandMessage<AbstractActivateProductCommand>(command),callback);
			}
		}
		return callback;
	}

	public boolean needActivation(ProductOrderEntry orderProduct, String productSpecCode) throws Exception{
		boolean retValue=false;
		if (productSpecCode.equalsIgnoreCase(ProductSpecificationTypeEnum.NetWork.name())){
			if (orderProduct.getState().equalsIgnoreCase(ProductOrderStateEnum.Activating.name())){
				retValue=false;
			}else{
				//check dependency
				Set<ProductOrderEntry> activationDependOns=orderProduct.getActivationDependOns();
				if(!activationDependOns.isEmpty()){
					retValue=true;
					// if any depend on product is not active, this product should wait for.
					for (ProductOrderEntry activationDependOn : activationDependOns) {
						if (!activationDependOn.getState().equalsIgnoreCase(ProductOrderStateEnum.Activated.name())){
							retValue=false;
							break;
						}						
					}					
				}else{
					Set<ProductOrderEntry> activationDependOnDeliveries=orderProduct.getActivationDependOnDeliveries();
					retValue=true;
					// if any depend on product is not delivery, this product should wait for.
					for (ProductOrderEntry activationDependOn : activationDependOnDeliveries) {
						if (!activationDependOn.getState().equalsIgnoreCase(ProductOrderStateEnum.Delivered.name())){
							retValue=false;
							break;
						}						
					}
				}
			}			
		}
		return retValue;
	}
	
	public AbstractActivateProductCommand getActivateCommand(OrderItemEntry orderItem,ProductOrderEntry orderProduct) throws Exception{
		AbstractActivateProductCommand command=null;
		if(orderProduct.getAction().equalsIgnoreCase(OrderItemActionEnum.Create.name())){
			command=new ActivateProductCommand();
		}else if (orderProduct.getAction().equalsIgnoreCase(OrderItemActionEnum.Delete.name())){
			command=new DeactivateProductCommand();
		}else if (orderProduct.getAction().equalsIgnoreCase(OrderItemActionEnum.Update.name())){
			Set<ProductOrderCharacterValueEntry> toBeUpdateProductOrderAttrs=new LinkedHashSet<>();
			Set<ProductOrderCharacterValueEntry> productOrderAttrs = orderProduct.getProductOrderCharacterValues();
			for (ProductOrderCharacterValueEntry productOrderCharacterValue : productOrderAttrs) {
				if (productOrderCharacterValue.getAction().equalsIgnoreCase(OrderItemActionEnum.Unchanged.name())){
					toBeUpdateProductOrderAttrs.add(productOrderCharacterValue);
				}
			}
			if (!toBeUpdateProductOrderAttrs.isEmpty()){
				command = new UpdateProductAttrCommand();
				((UpdateProductAttrCommand)command).setProductOrderCharacterValues(toBeUpdateProductOrderAttrs);
			}			
		}else{
			//Suspend/Resume
			Set<ProductOrderSuspendReasonEntry> suspendReasons=orderProduct.getProductOrderSuspendReasons();
			int suspendCount=0;
			int resumeCount=0;
			for (ProductOrderSuspendReasonEntry productOrderSuspendReason: suspendReasons) {
				if (productOrderSuspendReason.getAction().equalsIgnoreCase(OrderItemActionEnum.Create.name())){
					suspendCount++;
				}else if(productOrderSuspendReason.getAction().equalsIgnoreCase(OrderItemActionEnum.Delete.name())) {
					resumeCount++;
				}
			}
			if(suspendCount>0&&resumeCount>0){
				throw new Exception("Suspend orders and resume orders can not exist at the same time!");
			}else if (suspendCount>0){
				command = new SuspendProductCommand(); 
			}else if (resumeCount>0){
				command = new ResumeProductCommand(); 
			}
		}
		if (null!=command){
			command.setCustomerOrderId(String.valueOf(orderItem.getCustomerOrder().getCustomerOrderId()));
			command.setOfferOrderId(orderItem.getItemOffer().getId());
			command.setProductId(orderProduct.getId());
			//command.setEffectiveTime(orderProduct.getEffectiveTime());
			//command.setExpireTime(orderProduct.getExpireTime());
		}
		return command;
	}
	
	public boolean needDelivery(ProductOrderEntry orderProduct,String productSpecCode) throws Exception{
		boolean retValue=false;
		if (productSpecCode.equalsIgnoreCase(ProductSpecificationTypeEnum.RealObject.name())
				&& !orderProduct.getState().equalsIgnoreCase(ProductOrderStateEnum.Delivered.name())){
			retValue=true;
		}
		return retValue;
	}
	
	public FutureCallback deliveryProductOrder(OrderItemEntry orderItem,ProductOrderEntry orderProduct,String productSpecCode) throws Exception{
		FutureCallback callback = new FutureCallback();
		if (needDelivery(orderProduct,productSpecCode)){
			AbstractDeliveryProductCommand command= new DeliveryProductCommand();
			if (null!=command){				
				commandBus.dispatch(new GenericCommandMessage<AbstractDeliveryProductCommand>(command),callback);
			}
		}
		return callback;
	}
}
