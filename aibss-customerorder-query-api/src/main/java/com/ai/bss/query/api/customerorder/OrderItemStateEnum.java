package com.ai.bss.query.api.customerorder;

public enum OrderItemStateEnum {
	Initial,
	Running,
	Suspended,
	Aborted,
	AwaitingAbort,
	Completed;
}
