import framework.*;
import algorithm.*;
import features.*;

import java.util.*;

import weka.classifiers.functions.*;
import weka.classifiers.rules.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ArrayList<Sample> samples = new ArrayList<Sample>();
    
        Question q = new Question("Who is the president of the US?");
        Sample s = new Sample(q);
        Candidate c = new Candidate("Barack Obama", q);
        s.scores.put(c, 10d);
        c = new Candidate("Elizabeth II", q);
        s.scores.put(c, 0d);
        samples.add(s);
        
        WekaGlue alg = new WekaGlue(new LinearRegression());
        alg.train(samples);
        
        q = new Question("Who is the president of the US?");
        c = new Candidate("George Bush", q);
        System.out.println(alg.predict(q, c));
    }
}