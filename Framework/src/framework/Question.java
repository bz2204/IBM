package framework;

import java.util.*;

import features.*;

public class Question {
	public String content;
	public ArrayList<IFeatureQ> features;
	
	public Question(String content) {
	    this.content = content;
	    features = QuestionFeat.getFeatures(content);
	}
}