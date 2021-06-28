package enrolleeProcessor;

import java.util.ArrayList;
import java.util.List;
/*
My editor, VS Code is telling me I should take these 2 imports out: Enrolle and CsvReader. 
But I like it for the readability.
*/
import enrolleeProcessor.Enrollee;
import enrolleeProcessor.CsvReader;
import enrolleeProcessor.EnrolleeProviderSeperator;

public class Procedure {

    private List<List<String>> abstractedData;
    private List<Enrollee> enrollees;

    public Procedure(){

        this.readCSV();
        this.initEnrollees();
        this.convertDataRowsToEnrollees();
        try {
            this.seperateEnrollees();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    

    private void readCSV(){
        CsvReader r = new CsvReader("input.csv");
        this.setAllData(r.getRows());
    }

    private void setAllData(List<List<String>> data){
        this.abstractedData = data;
    }

    private void initEnrollees(){
        this.enrollees = new ArrayList<Enrollee>();
    }

    private void convertDataRowsToEnrollees(){

        for (List<String> row : this.abstractedData) {
            Enrollee e = new Enrollee(row);
            this.addEnrollee(e);
        }

    }

    private void addEnrollee(Enrollee e){
        this.enrollees.add(e);
    }

    private void seperateEnrollees() throws Exception{
        new EnrolleeProviderSeperator(this.enrollees);

        
    }



}
