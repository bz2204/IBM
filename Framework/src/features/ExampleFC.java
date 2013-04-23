package features;

import framework.*;

public class ExampleFC implements IFeatureC {
    private static String description = "ExampleFC";

    private double value;
    
    public ExampleFC(String c) {
        this.value = Double.parseDouble(c);
    }
    
    public String getDescription() {
        return description;
    }
    
    public double[] getValues() {
        double[] ret = new double[1];
        ret[0] = value;
        return ret;
    }
}