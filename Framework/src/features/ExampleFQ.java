package features;

import framework.*;

public class ExampleFQ implements IFeatureQ {
    private int id;

    private double value;
    
    public ExampleFQ(int id, String c) {
        this.id = id;
        this.value = Double.parseDouble(c);
    }
    
    public int getID() {
        return id;
    }
    
    public double getValue() {
        return value;
    }
}