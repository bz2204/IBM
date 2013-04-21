import framework.*;
import algorithm.*;
import features.*;

import java.util.*;

import weka.classifiers.functions.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ArrayList<Sample> samples = new ArrayList<Sample>();
    
        int i, j;
        for (i = -10; i < 10; ++i) {
            Question q = new Question(Integer.toString(i));
            Sample s = new Sample(q);
            for (j = -10; j < 10; ++j) {
                Candidate c = new Candidate(Integer.toString(j));
                s.scores.put(c, (double)i + j);
            }
            samples.add(s);
        }
        
        WekaGlue alg = new WekaGlue(new LinearRegression());
        alg.train(samples);
        
        Question q = new Question("0.5");
        Candidate c = new Candidate("0.7");
        System.out.println(alg.predict(q, c));
    }
}