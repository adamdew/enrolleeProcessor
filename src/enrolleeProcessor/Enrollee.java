package enrolleeProcessor;

import java.util.List;

public class Enrollee {

    private List<String> enrolleeData;

    public Enrollee(List<String> enrolleeData){
       this.setEnrolleeData(enrolleeData); 
    }

    private void setEnrolleeData(List<String> enrolleeData){
        this.enrolleeData = enrolleeData;
    }

    public String getUserID(){
        return this.enrolleeData.get(0);
    }

    public String getFirstName(){
        return this.enrolleeData.get(1);
    }

    public String getLastName(){
        return this.enrolleeData.get(2);
    }

    public String getVersion(){
        return this.enrolleeData.get(3);
    }

    public String getProviderName(){
        return this.enrolleeData.get(4);
    }

}
