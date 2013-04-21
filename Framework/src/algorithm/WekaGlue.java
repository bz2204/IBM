package algorithm;

import framework.*;

import java.util.*;

import weka.core.*;
import weka.classifiers.*;

public class WekaGlue implements ILearningAlg {
    Classifier classifier;
    Instances instances;
    
    public WekaGlue(Classifier c) {
        classifier = c;
    }
    
    public void train(Collection<Sample> samples) throws Exception{
        FastVector attr = new FastVector();
        int max = 0;
        for (IFeatureQ q : samples.iterator().next().question.features) {
            max = q.getID() > max ? q.getID() : max;
            attr.addElement(new Attribute(new Integer(q.getID()).toString()));
        }
        for (IFeatureC c : samples.iterator().next().scores.keySet().iterator().next().features)
            attr.addElement(new Attribute(new Integer(c.getID() + max + 1).toString()));
        attr.addElement(new Attribute("score"));

        instances = new Instances("Samples", attr, samples.size());
        for (Sample s : samples) {
            for (Map.Entry<Candidate, Double> entry : s.scores.entrySet()) {
                Instance inst = new Instance(attr.size());
                int index = 0;
                for (IFeatureQ q : s.question.features)
                    if (q.getValue() != Double.NaN)
                        inst.setValue(instances.attribute(index++), q.getValue());
                    else
                        inst.setMissing(instances.attribute(index++));
                
                for (IFeatureC c : entry.getKey().features)
                    if (c.getValue() != Double.NaN)
                        inst.setValue(instances.attribute(index++), c.getValue());
                    else
                        inst.setMissing(instances.attribute(index++));
                
                inst.setValue(instances.attribute(index), entry.getValue());
                
                instances.add(inst);
            }
        }
        instances.setClassIndex(attr.size() - 1);
        
        classifier.buildClassifier(instances);
    }
    
    public double predict(Question q, Candidate c) throws Exception {
        Instance inst = new Instance(instances.numAttributes());
        int index = 0;
        for (IFeatureQ fq : q.features)
            if (fq.getValue() != Double.NaN)
                inst.setValue(instances.attribute(index++), fq.getValue());
            else
                inst.setMissing(instances.attribute(index++));
        
        for (IFeatureC fc : c.features)
            if (fc.getValue() != Double.NaN)
                inst.setValue(instances.attribute(index++), fc.getValue());
            else
                inst.setMissing(instances.attribute(index++));
        inst.setMissing(instances.attribute(index));
        
        return classifier.classifyInstance(inst);
    }
}