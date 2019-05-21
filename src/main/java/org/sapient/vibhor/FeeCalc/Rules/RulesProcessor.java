package org.sapient.vibhor.FeeCalc.Rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.Collectors;

import org.sapient.vibhor.FeeCalc.Model.TxnAttributes;
import org.sapient.vibhor.FeeCalc.Rules.TxnRules.IntraDayTxn;

public class RulesProcessor {
	
	public List<TxnAttributes> process(List<TxnAttributes> data){
		TxnRules rules = new TxnRules();
		List<TxnAttributes> intraDayData = 
				applyTxnRulesRecursevely(data, new ArrayList<TxnAttributes>(), 0, rules.i_txn_rule1);
		intraDayData.stream().forEach(e->e.setTxnFee("10"));
		
		List<TxnAttributes> n1Data = diffList(data, intraDayData).stream().
				filter(item-> rules.n_txn_rule1.eval(item)).collect(Collectors.toList());
		n1Data.stream().forEach(e->e.setTxnFee("500"));
		
		List<TxnAttributes> n2Data = diffList(diffList(data, intraDayData), n1Data).stream().
				filter(item-> rules.n_txn_rule2.eval(item)).collect(Collectors.toList());
		n2Data.stream().forEach(e->e.setTxnFee("100"));
		
		List<TxnAttributes> n3Data = diffList(diffList(diffList(data, intraDayData), n1Data), n2Data).stream().
				filter(item-> rules.n_txn_rule3.eval(item)).collect(Collectors.toList());
		n3Data.stream().forEach(e->e.setTxnFee("50"));
		
		List<TxnAttributes> listC = new ArrayList<TxnAttributes>();
		listC.addAll(intraDayData);
		listC.addAll(n1Data);
		listC.addAll(n2Data);
		listC.addAll(n3Data);
		return listC;
	}
	
	public List<TxnAttributes> diffList(List<TxnAttributes> l1, List<TxnAttributes> l2){
		Set<String> ids = l2.stream()
		        .map(TxnAttributes::getTxnId)
		        .collect(Collectors.toSet());
		return l1.stream()
		        .filter(it -> !ids.contains(it.getTxnId()))
		        .collect(Collectors.toList());
	}
	
	public List<TxnAttributes> applyTxnRulesRecursevely(
			List<TxnAttributes> data, List<TxnAttributes> selected, int i, IntraDayTxn rule){
		List<TxnAttributes> selectedItems= selected;
		if(i==0){
			selectedItems.add(data.get(i));
			return applyTxnRulesRecursevely(data, selectedItems, ++i, rule);
		}
		if(data.size()-1>i){
			TxnAttributes item = data.get(i);
			if(selectedItems.stream().filter(txnItem ->
			rule.eval(txnItem, item)).count()>0){
				selectedItems.add(item);
			}
			return applyTxnRulesRecursevely(data, selectedItems, ++i, rule);
		}
		return selectedItems.size()>1? selectedItems: Collections.emptyList();
	}

}
