package features;

import framework.*;

public class ExampleFQ implements IFeatureQ {
    private static String description = "ExampleFQ";

    private double value;
    
    public ExampleFQ(String c) {
        this.value = Double.parseDouble(c);
    }
    
    public String getDescription() {
        return description;
    }
    
    public double[] getValues() {
        double[] ret = new double[2];
        ret[0] = value;
        ret[1] = -value;
        return ret;
    }
}