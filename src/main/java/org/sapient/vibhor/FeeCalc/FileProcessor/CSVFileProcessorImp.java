package org.sapient.vibhor.FeeCalc.FileProcessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	public List<List<String>> getParsedContent(String file) throws FileNotFoundException, IOException {
		List<List<String>> records = new ArrayList<List<String>>();
		try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
		    String[] values = null;
		    while ((values = csvReader.readNext()) != null) {
		        records.add(Arrays.asList(values));
		    }
		}
		records.remove(0);
		return records;
	}
	
	public void exportReport(List<String[]> stringArray) throws IOException {
		try {
			createIfNotPresent("src/main/java/resource/writtenAll.csv");
			Path path = Paths.get(
				      ClassLoader.getSystemResource("src/main/java/resource/writtenAll.csv").toURI());
			CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
		     writer.writeAll(stringArray);
		     writer.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
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
