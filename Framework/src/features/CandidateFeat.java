package features;

import java.util.*;

import framework.*;
import features.gquerycount.*;

public class CandidateFeat {
    public static ArrayList<IFeatureC> getFeatures(String content, Question q) {
        ArrayList<IFeatureC> list = new ArrayList<IFeatureC>();
        
        //list.add(new ExampleFC(content));
        list.add(new GQueryFeature(q.content, content));
        
        return list;
    }
}