package org.sapient.vibhor.FeeCalc.SapeFeeCalcVibhor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sapient.vibhor.FeeCalc.FileProcessor.CSVFileProcessorImp;
import org.sapient.vibhor.FeeCalc.FileProcessor.FileProcessor;
import org.sapient.vibhor.FeeCalc.Model.TxnAttributes;
import org.sapient.vibhor.FeeCalc.Rules.RulesProcessor;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Initiating File Processing" );
        FileProcessor fProcessor = new CSVFileProcessorImp();
        try {
			List<List<String>> rawData = fProcessor.getParsedContent("src/main/java/resource/Sample_input.csv");
			List<TxnAttributes> data = new ArrayList<TxnAttributes>();
			rawData.stream().forEach(item-> 
			data.add(new TxnAttributes(item.get(0), item.get(1), item.get(2),item.get(3), item.get(4), item.get(5), item.get(6))));
            RulesProcessor rProcessor = new RulesProcessor();
            List<TxnAttributes> processedTxns = rProcessor.process(data);
            List<String[]> writableContent = new ArrayList<String[]>();
            processedTxns.stream().forEach(item->writableContent.add(item.toString().split(",")));
            fProcessor.exportReport(writableContent);
            System.out.println(processedTxns);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
