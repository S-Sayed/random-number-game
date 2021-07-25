package com.ssayed.model;

public class ApiResponse {

	private int addedNumber;
	private int resultingNumber;

	public ApiResponse(int addedNumber, int resultingNumber) {
		this.addedNumber = addedNumber;
		this.resultingNumber = resultingNumber;
	}

	public ApiResponse() {
	}

	public int getAddedNumber() {
		return addedNumber;
	}

	public void setAddedNumber(int addedNumber) {
		this.addedNumber = addedNumber;
	}

	public int getResultingNumber() {
		return resultingNumber;
	}

	public void setResultingNumber(int resultingNumber) {
		this.resultingNumber = resultingNumber;
	}

	@Override
	public String toString() {
		return "Response [addedNumber=" + addedNumber + ", resultingNumber=" + resultingNumber + "]";
	}
}
