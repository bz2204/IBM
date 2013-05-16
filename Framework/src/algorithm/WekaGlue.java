package algorithm;

import framework.*;

import java.util.*;

import weka.core.*;
import weka.classifiers.*;

class UpdateNotSupportedException extends Exception {
    private String classifier;
    
    UpdateNotSupportedException(String c) {
        classifier = c;
    }
    
    public String toString() {
        return "Update operation is not supported by " + classifier;
    }
}

public class WekaGlue implements ILearningAlg {
    Classifier classifier;
    Instances instances;
    
    public WekaGlue(Classifier c) {
        classifier = c;
    }
    
    public void train(Collection<Sample> samples) throws Exception {
        FastVector attr = new FastVector();
        for (IFeatureQ q : samples.iterator().next().question.features) {
            int index = 0;
            for (double value : q.getValues())
                attr.addElement(new Attribute(q.getDescription() + "_" + (index++)));
        }
        for (IFeatureC c : samples.iterator().next().scores.keySet().iterator().next().features) {
            int index = 0;
            for (double value : c.getValues())
                attr.addElement(new Attribute(c.getDescription() + "_" + (index++)));
        }
        attr.addElement(new Attribute("score"));

        instances = new Instances("Samples", attr, samples.size());
        for (Sample s : samples) {
            for (Map.Entry<Candidate, Double> entry : s.scores.entrySet()) {
                Instance inst = new Instance(attr.size());
                int index = 0;
                for (IFeatureQ q : s.question.features)
                    for (double value : q.getValues())
                        if (value != Double.NaN)
                            inst.setValue(instances.attribute(index++), value);
                        else
                            inst.setMissing(instances.attribute(index++));
                
                for (IFeatureC c : entry.getKey().features)
                    for (double value : c.getValues())
                        if (value != Double.NaN)
                            inst.setValue(instances.attribute(index++), value);
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
            for (double value : fq.getValues())
                if (value != Double.NaN)
                    inst.setValue(instances.attribute(index++), value);
                else
                    inst.setMissing(instances.attribute(index++));
        
        for (IFeatureC fc : c.features)
            for (double value : fc.getValues())
                if (value != Double.NaN)
                    inst.setValue(instances.attribute(index++), value);
                else
                    inst.setMissing(instances.attribute(index++));
        inst.setMissing(instances.attribute(index));
        
        return classifier.classifyInstance(inst);
    }
    
    public void update(Question q, Candidate c, double score) throws Exception {
        if (classifier instanceof UpdateableClassifier) {
            UpdateableClassifier uc = (UpdateableClassifier)classifier;
            
            if (instances == null) {
                FastVector v = new FastVector();
                for (IFeatureQ fq : q.features) {
                    int index = 0;
                    for (double value : fq.getValues()) {
                        Attribute attr = new Attribute(fq.getDescription() + "_" + index);
                        v.addElement(attr);
                    }
                }
                
                for (IFeatureC fc : c.features) {
                    int index = 0;
                    for (double value : fc.getValues()) {
                        Attribute attr = new Attribute(fc.getDescription() + "_" + index);
                        v.addElement(attr);
                    }
                }
                
                v.addElement(new Attribute("score"));
                instances = new Instances("Samples", v, 100);
                
                instances.setClassIndex(instances.numAttributes() - 1);
            }
            
            Instance inst = new Instance(instances.numAttributes());
            int index = 0;
            for (IFeatureQ fq : q.features)
                for (double value : fq.getValues())
                    if (value != Double.NaN)
                        inst.setValue(instances.attribute(index++), value);
                    else
                        inst.setMissing(instances.attribute(index++));
            
            for (IFeatureC fc : c.features)
                for (double value : fc.getValues())
                    if (value != Double.NaN)
                        inst.setValue(instances.attribute(index++), value);
                    else
                        inst.setMissing(instances.attribute(index++));
            inst.setValue(instances.attribute(index), score);
            instances.add(inst);
            inst.setDataset(instances);

            uc.updateClassifier(inst);
        } else {
            throw new UpdateNotSupportedException(classifier.toString());
        }
    }
}