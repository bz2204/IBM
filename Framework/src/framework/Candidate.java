package framework;

import java.util.*;

import features.*;

public class Candidate {
	public String content;
	public ArrayList<IFeatureC> features;
	
	public Question q;
	
	public Candidate(String content, Question question) {
	    this.content = content;
	    q = question;

	    features = CandidateFeat.getFeatures(content, q);
	}
	
	public int hashCode() {
		return content.hashCode();
	}
}