package org.sapient.vibhor.FeeCalc.FileProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.sapient.vibhor.FeeCalc.Model.TxnAttributes;

public interface FileProcessor {
	
	public List<String[]> getParsedContent(String string) throws FileNotFoundException, IOException;
	public void exportReport(List<String[]> stringArray) throws IOException;

}
