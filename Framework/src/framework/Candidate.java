package framework;

import java.util.*;

import features.*;

public class Candidate {
	public String content;
	public ArrayList<IFeatureC> features;
	
	public Candidate(String content) {
	    this.content = content;
	    features = CandidateFeat.getFeatures(content);
	}
	
	public int hashCode() {
		return content.hashCode();
	}
}