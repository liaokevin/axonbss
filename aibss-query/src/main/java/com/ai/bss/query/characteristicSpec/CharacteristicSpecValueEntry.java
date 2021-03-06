package com.ai.bss.query.characteristicSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="RT_CHARACTER_VALUE")
public class CharacteristicSpecValueEntry{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name="CHARACTER_SPEC_ID")
	private CharacteristicSpecEntry characteristic;
	@Column
	private String value;
	@Column
	private String displayValue;
	@Column
	private boolean isDefault;
	@Column
	private long valueFrom;
	@Column
	private String code;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public long getValueFrom() {
		return valueFrom;
	}

	public void setValueFrom(long valueFrom) {
		this.valueFrom = valueFrom;
	}

	public long getValueTo() {
		return valueTo;
	}

	public void setValueTo(long valueTo) {
		this.valueTo = valueTo;
	}

	public long getRangeInterval() {
		return rangeInterval;
	}

	public void setRangeInterval(long rangeInterval) {
		this.rangeInterval = rangeInterval;
	}

	private long valueTo;
	   private long rangeInterval;	
	
	public CharacteristicSpecEntry getCharacteristic() {
		return this.characteristic;
	}

	
	public void setCharacteristic(CharacteristicSpecEntry characteristic) {
		this.characteristic=(CharacteristicSpecEntry)characteristic;
	}

	
	public String getCode() {
		return this.code;
	}

	
	public void setCode(String code) {
		this.code=code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
