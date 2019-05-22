package org.sapient.vibhor.FeeCalc.SapeFeeCalcVibhor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sapient.vibhor.FeeCalc.FileProcessor.CSVFileProcessorImp;
import org.sapient.vibhor.FeeCalc.FileProcessor.FileProcessor;
import org.sapient.vibhor.FeeCalc.Model.TxnAttributes;
import org.sapient.vibhor.FeeCalc.Rules.RulesProcessor;
import org.sapient.vibhor.FeeCalc.SapeFeeCalcVibhor.FeeCalConstants;

public class App 
{
	
    public static void main( String[] args )
    {
    	long startTime = System.nanoTime();
        System.out.println( "Initiating File Processing" );
        FileProcessor fProcessor = new CSVFileProcessorImp();
        try {
        	//Read File
			List<String[]> rawData = fProcessor.getParsedContent(FeeCalConstants.SRC_FILE_PATH);
			List<TxnAttributes> data = new ArrayList<TxnAttributes>();
			rawData.stream().forEach(item-> 
			data.add(new TxnAttributes(item[0], item[1], item[2],item[3], item[4], item[5], item[6])));
            
			//Process records
			RulesProcessor rProcessor = new RulesProcessor();
            List<TxnAttributes> processedTxns = rProcessor.process(data);
            
            //Export report
            List<String[]> writableContent = new ArrayList<String[]>();
            processedTxns.stream().forEach(item->writableContent.add(item.toString().split(",")));
            fProcessor.exportReport(writableContent);
            
            long stopTime = System.nanoTime();
            long elapsedTime = stopTime - startTime;
            double seconds = (double)elapsedTime / 1_000_000_000.0;
            System.out.println("records processed successfully in "+seconds+" seconds");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
