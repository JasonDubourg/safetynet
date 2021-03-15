package com.safetynet.api.dto;

import java.util.List;

public class ChildAndParentsByAddress {

	private List<ChildByAddress> childByAddress;
	private List<ParentByAddress> parentByAddress;

	public List<ChildByAddress> getChildByAddress() {
		return childByAddress;
	}

	public void setChildByAddress(List<ChildByAddress> childByAddress) {
		this.childByAddress = childByAddress;
	}

	public List<ParentByAddress> getParentByAddress() {
		return parentByAddress;
	}

	public void setParentByAddress(List<ParentByAddress> parentByAddress) {
		this.parentByAddress = parentByAddress;
	}

	@Override
	public String toString() {
		return "ChildAndParentsByAddress [childByAddress=" + childByAddress + ", parentByAddress=" + parentByAddress
				+ "]";
	}

}
