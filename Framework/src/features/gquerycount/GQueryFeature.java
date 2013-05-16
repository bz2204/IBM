package features.gquerycount;

import framework.*;
import features.*;

public class GQueryFeature implements IFeatureC {
    private static String description = "Google_Query_Count";
    
    private GQueryCount gqc;
    
    private Thread t;
    
    public GQueryFeature(String q, String c) {
        try {
            gqc = new GQueryCount(q, c);
            gqc.setStart(0);
        
            t = new Thread(new Runnable() {
                public void run() {
                    try {
                        synchronized(gqc) {
                            gqc.query();
                        
                            gqc.notify();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getDescription() {
        return description;
    }
    
    public double[] getValues() {
        synchronized(gqc) {
            int[] count = gqc.indCount();
            double[] res = new double[1];
            for (int i : count)
                res[0] += i;
            
            if (count == null || count.length == 0)
                res[0] = Double.NaN;

            return res;
        }
    }
}