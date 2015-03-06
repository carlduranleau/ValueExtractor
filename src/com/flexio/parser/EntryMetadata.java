package com.flexio.parser;

import java.util.Date;

public class EntryMetadata {
	private String receiptId;
	private String clientId;
	private Date creationDate;
	
	public EntryMetadata () {
		
	}
	
	public EntryMetadata (String pClientId, Date pCreationDate, String pReceiptId) {
		this.clientId = pClientId;
		this.creationDate = pCreationDate;
		this.receiptId = pReceiptId;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	
	
}
