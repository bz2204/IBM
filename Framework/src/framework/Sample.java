package framework;

import java.util.*;

public class Sample {
	public Question question;
	public HashMap<Candidate, Double> scores;
	
	public Sample(Question q) {
	    this.question = q;
	    scores = new HashMap<Candidate, Double>();
	}
}