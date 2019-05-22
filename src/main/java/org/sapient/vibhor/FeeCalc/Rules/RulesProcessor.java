package org.sapient.vibhor.FeeCalc.Rules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.sapient.vibhor.FeeCalc.Model.TxnAttributes;
import org.sapient.vibhor.FeeCalc.Rules.TxnRules.IntraDayTxn;
import org.sapient.vibhor.FeeCalc.SapeFeeCalcVibhor.FeeCalConstants;

public class RulesProcessor {
	
	public List<TxnAttributes> process(List<TxnAttributes> data){
		TxnRules rules = new TxnRules();
		
		List<TxnAttributes> intraDayData = 
				applyTxnRulesRecursevely(data, new ArrayList<TxnAttributes>(), 0, rules.i_txn_rule1);
		intraDayData.stream().forEach(e->e.setTxnFee(FeeCalConstants.INTRADAY_FEE));
		
		List<TxnAttributes> n1Data = diffList(data, intraDayData).stream().
				filter(item-> rules.n_txn_rule1.eval(item)).collect(Collectors.toList());
		n1Data.stream().forEach(e->e.setTxnFee(FeeCalConstants.NORMAL_TXN_1_FEE));
		
		List<TxnAttributes> n2Data = diffList(diffList(data, intraDayData), n1Data).stream().
				filter(item-> rules.n_txn_rule2.eval(item)).collect(Collectors.toList());
		n2Data.stream().forEach(e->e.setTxnFee(FeeCalConstants.NORMAL_TXN_2_FEE));
		
		List<TxnAttributes> n3Data = diffList(diffList(diffList(data, intraDayData), n1Data), n2Data).stream().
				filter(item-> rules.n_txn_rule3.eval(item)).collect(Collectors.toList());
		n3Data.stream().forEach(e->e.setTxnFee(FeeCalConstants.NORMAL_TXN_3_FEE));
		
		List<TxnAttributes> processedTxns = new ArrayList<TxnAttributes>();
		processedTxns.addAll(intraDayData);
		processedTxns.addAll(n1Data);
		processedTxns.addAll(n2Data);
		processedTxns.addAll(n3Data);
		
		processedTxns.sort(
				Comparator.comparing(TxnAttributes::getClientId).thenComparing(TxnAttributes::getTxnType)
				.thenComparing(TxnAttributes::getTxnDate).thenComparing(TxnAttributes::getTxnPriority));
		return processedTxns;
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
			final List<TxnAttributes> data, 
			final List<TxnAttributes> selected, int i,
	        final IntraDayTxn rule) {
		
			int lastIndex = i;
			if(lastIndex<data.size())
			{
	        TxnAttributes item = data.get(i);
	        List<TxnAttributes> expectedItems = data.stream().filter(d -> rule.eval(d, item)).collect(Collectors.toList());
	        if (expectedItems.size() > 0) {
	            expectedItems.stream().forEach(ei -> selected.add(ei));
	            selected.add(item);
	            List<TxnAttributes> reducedList = diffList(data, selected);
	            applyTxnRulesRecursevely(reducedList, selected, lastIndex, rule);
	        }
	        else {
	        	applyTxnRulesRecursevely(diffList(data, selected), selected, ++i, rule);
	        }
	}
	        return selected;
	    }

}
