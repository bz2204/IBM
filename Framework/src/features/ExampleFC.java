package features;

import framework.*;

public class ExampleFC implements IFeatureC {
    private int id;

    private double value;
    
    public ExampleFC(int id, String c) {
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