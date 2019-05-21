package org.sapient.vibhor.FeeCalc.Rules;

import org.sapient.vibhor.FeeCalc.Model.TxnAttributes;

public class TxnRules {
	
	public boolean applyIntradayRule(TxnAttributes item1, TxnAttributes item2, IntraDayTxn txn) {
		return txn.eval(item1, item2);
	   }
	
	public boolean applyNormalRule(TxnAttributes txnItem, NormalTxn txn) {
	      return txn.eval(txnItem);
	   }
	
	IntraDayTxn i_txn_rule1 = (TxnAttributes txnItem1, TxnAttributes txnItem2) ->
	txnItem1.getClientId().equals(txnItem2.getClientId())
	&& txnItem1.getSecurityId().equals(txnItem2.getSecurityId())
	&& txnItem1.getTxnDate().equals(txnItem2.getTxnDate())
	&& ! txnItem1.getTxnType().equals(txnItem2.getTxnType());
	
	NormalTxn n_txn_rule1 = (TxnAttributes txnItem) -> "Y".equals(txnItem.getTxnPriority());
	
	NormalTxn n_txn_rule2 = (TxnAttributes txnItem) -> 
	"N".equals(txnItem.getTxnPriority()) && ("SELL".equals(txnItem.getTxnType()) || "WITHDRAW".equals(txnItem.getTxnType()));
	
	NormalTxn n_txn_rule3 = (TxnAttributes txnItem) -> 
	"N".equals(txnItem.getTxnPriority()) && ("BUY".equals(txnItem.getTxnType()) || "DEPOSIT".equals(txnItem.getTxnType()));

	interface IntraDayTxn {
		boolean eval(TxnAttributes a, TxnAttributes b);
	 }

	interface NormalTxn {
		boolean eval(TxnAttributes a);
	 }
}
