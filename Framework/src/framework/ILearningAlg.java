package framework;

import java.util.*;

public interface ILearningAlg {
	public void train(Collection<Sample> collection) throws Exception;
	public double predict(Question q, Candidate c) throws Exception;
}