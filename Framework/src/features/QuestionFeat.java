package features;

import java.util.*;

import framework.*;

public class QuestionFeat {
    public static ArrayList<IFeatureQ> getFeatures(String content) {
        ArrayList<IFeatureQ> list = new ArrayList<IFeatureQ>();
        
        list.add(new ExampleFQ(content));
        
        return list;
    }
}