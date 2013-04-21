import java.util.*;

// Use Double.NaN to denote missing values
public interface IFeatureQ {
	public int getID();
	public double getValue();
}

public interface IFeatureC {
	public int getID();
	public double getValue();
}

public class Candidate {
	public String content;
	public ArrayList<IFeatureC> features;
	
	public int hashCode() {
		return content.hashCode();
	}
}

public class Question {
	public String content;
	public ArrayList<IFeatureQ> features;
}

public class Sample {
	public Question question;
	public HashMap<Candidate, Double> scores;
}

public interface ILearningAlg {
	public void train(Collection<Sample> collection) throws Exception;
	public double predict(Question q, Candidate c) throws Exception;
}
