package enrolleeProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrolleeProviderSeperator {

    List<Enrollee> enrollees;
    List<List<Enrollee>> enrolleesSepByProvider;

    public EnrolleeProviderSeperator(List<Enrollee> enrollees) throws Exception{
        this.setEnrollees(enrollees);
        try {
            this.makeDistinctProviderFiles();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setEnrollees(List<Enrollee> enrollees){
        this.enrollees = enrollees;
    }

    private void makeDistinctProviderFiles() throws Exception{

        List<String> distinctProviderNames = new ArrayList<String>();

        for(Enrollee e: enrollees){
        
            String providerName = e.getProviderName();

            if(!distinctProviderNames.contains(providerName)){

                distinctProviderNames.add(providerName);

            }

        }
        
        for(String provider : distinctProviderNames){

            List<Enrollee> filteredEnrollees = enrollees.stream().filter(
                enrollee -> enrollee.getProviderName().contains(provider)).collect(Collectors.toList()
            );

            //create new csv writer and write each to individual file

            String providerFileName = provider+"-enrollees.csv";

            File providerFile = new File(providerFileName);

            try {
                providerFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            } 

            FileOutputStream oFile = new FileOutputStream(providerFile, false); 

            PrintWriter pw = new PrintWriter(oFile);
            
            for (Enrollee e : filteredEnrollees){

                pw.println(
                    e.getUserID()
                    +","+e.getFirstName()
                    +","+e.getLastName()
                    +","+e.getVersion()
                    +","+e.getProviderName()
                );
                
                
            }

            pw.flush();
            pw.close();

           
            //do first sort by userID(index 0), then by version(index 3) so that we can dedupe by userID in a special loop
            new MultiColumnCsvSort(providerFileName, true, 0, 3);
            
            //finished deduping now, do final sort by last name(index 2), then by first name(index 1)
            new MultiColumnCsvSort(providerFileName, false, 2, 1);

                
        }

        

        

    }
}
