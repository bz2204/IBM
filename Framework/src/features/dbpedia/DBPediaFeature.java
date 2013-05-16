package features.dbpedia;

import framework.*;

public class DBPediaFeature implements IFeatureC {
    String q, c;
    
    public DBPediaFeature(String question, String candidate) {
        q = question;
        c = candidate;
    }

    public double[] getValues() {
        return MainFunction.getValues(q, c);
    }
    
    public String getDescription() {
        return MainFunction.getDescription();
    }
}