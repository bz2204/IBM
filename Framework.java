import java.util.*;

interface IFeatureQ {
	public int getID();
	public double getValue();
}

interface IFeatureC {
	public int getID();
	public double getValue();
}

class Candidate {
	String content;
	Vector<IFeatureC> features;
	
	public double hashCode() {
		return content.hashCode();
	}
}

class Question {
	String content;
	Vector<IFeatureQ> features;
}

class Sample {
	Question question;
	HashMap<Candidate, Double> scores;
}

interface ILearningAlg {
	public void train(Collection<Sample> collection);
	public double predict(Question q, Candidate c);
}