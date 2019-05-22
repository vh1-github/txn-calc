package org.sapient.vibhor.FeeCalc.FileProcessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sapient.vibhor.FeeCalc.Model.TxnAttributes;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSVFileProcessorImp implements FileProcessor {

	public List<String[]> getParsedContent(String file) throws FileNotFoundException, IOException {
		List<String[]> records = new ArrayList<String[]>();
		try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
		    String[] values = null;
		    while ((values = csvReader.readNext()) != null) {
		        records.add(values);
		    }
		}
		records.remove(0);
		return records;
	}
	
	public void exportReport(List<String[]> stringArray) throws IOException {
		createIfNotPresent("src/main/java/resource/Sample_output.csv");
		CSVWriter writer = new CSVWriter(new FileWriter("src/main/java/resource/Sample_output.csv"));
		 writer.writeAll(stringArray);
		 writer.close();
	}
	
	private void createIfNotPresent(String path) throws IOException{
		 try {
		        File file = new File(path);
		        if (!file.exists()) {
		            file.createNewFile();
		        } else {
		            FileOutputStream writer = new FileOutputStream(path);
		            writer.write(("").getBytes());
		            writer.close();
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}

}
