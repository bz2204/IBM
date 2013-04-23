package features;

import java.util.*;

import framework.*;

public class CandidateFeat {
    public static ArrayList<IFeatureC> getFeatures(String content, Question q) {
        ArrayList<IFeatureC> list = new ArrayList<IFeatureC>();
        
        list.add(new ExampleFC(content));
        
        return list;
    }
}