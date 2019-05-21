package org.sapient.vibhor.FeeCalc.Model;

import java.time.LocalDate;
public class TxnAttributes {
	
	public TxnAttributes(String txnId, String clientId, String securityId, String txnType,
			String txnDate,String mktValue, String priority) {
		this.txnId = txnId;
		this.clientId = clientId;
		this.securityId = securityId;
		this.txnType = txnType;
		this.txnDate = txnDate;
		this.txnMktValue = mktValue;
		this.txnPriority = priority;
		this.txnFee = "";
	}
	private String txnId;
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnMktValue() {
		return txnMktValue;
	}
	public void setTxnMktValue(String txnMktValue) {
		this.txnMktValue = txnMktValue;
	}
	public String getTxnPriority() {
		return txnPriority;
	}
	public void setTxnPriority(String txnPriority) {
		this.txnPriority = txnPriority;
	}
	private String clientId;
	private String securityId;
	private String txnType;
	private String txnDate;
	private String txnMktValue;
	private String txnPriority;
	private String txnFee;
	public String getTxnFee() {
		return txnFee;
	}
	@Override
	public String toString() {
		return this.getTxnId()+","+this.getClientId()+","+this.getSecurityId()+","+this.getTxnType()+","+this.getTxnDate()
		+","+this.getTxnMktValue()+","+this.getTxnFee();
	}
	public void setTxnFee(String txnFee) {
		this.txnFee = txnFee;
	}

}
