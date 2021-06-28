package enrolleeProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {

    private List<List<String>> rows;

    public CsvReader(String filepath){
        this.setRows(this.parseFile(filepath));
    }

    private void setRows(List<List<String>> rows){
        this.rows = rows;
    }

    public List<List<String>> getRows(){
        return this.rows;
    }

    private List<List<String>> parseFile(String filePath){

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
        
    }


    
}
