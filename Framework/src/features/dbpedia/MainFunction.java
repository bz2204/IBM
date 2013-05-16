package features.dbpedia;

import java.net.*;
import java.io.*;
import java.util.regex.*;

public class MainFunction extends ConnectDBP {
	public static void exchange(double[] n, int i, int j) {
		double m = n[i];
		n[i] = n[j];
		n[j] = m;
	}
   public static void quicksort(double[] m, int start, int end) {
        if (start < end - 1) {
            double rand = Math.random();
            int i = start + (int)(rand * (end - start));
            exchange(m, i, end - 1);
            double pivot = m[end - 1];
            int j = start, k = start;
            for (i = start; i < end - 1; i++) {
                if (m[i] == pivot) {
                    exchange(m, i, j);
                    j++;
                    continue;
                }
                if (m[i] > pivot) {
                    exchange(m, i, j);
                    exchange(m, j, k);
                    j++;
                    k++;
                }
            }
            exchange(m, end - 1, j);
            j++;
            quicksort(m, start, k);
            quicksort(m, j, end);
            
        }
    
    }
	
	
	public static String getDescription() {
		return "DBpedia's matrix";
	}
	public static double[] getValues(String q, String c) {
		double[][] pre = process(q, c);
		
//		for (int i = 0 ;i < pre.length; i++)
//			for (int j = 0 ; j < pre.length; j++) 
//				System.out.print(pre[i][j] + (j == pre.length - 1? "\n" : ", "));
//		
		double[] re = new double[5];
		double[] pre1 = new double[pre.length * pre.length];
		for (int i = 0; i < pre1.length; i++)
			pre1[i] = pre[i / pre.length][i % pre.length];
		quicksort(pre1, 0, pre1.length);
		for (int i = 0; i < re.length; i++) 
			re[i] = pre1[i];

		return re;
	}
	
	public static double[][] process (String q, String c) {
		String q0 = q.replace('?', ' ');
		q0 = q0.replace('.', ' ');
		String[] q1 = q0.split(" ");
		int i, j, k;
		double[][] result = new double[q1.length][q1.length];
		String help = " ";
		for (i = 0; i < q1.length; i++) {		
			j = i;
			help = q1[j];
			k = 0;
			while (j < q1.length) {
				if (j != i)
					help += "_" + q1[j];
				//System.out.println(help);
				try {
					result[i][k++] = DBPRate(help, c);
				//	System.out.println(result[i][k - 1]);
				}
				catch (Exception e) {
				}
				j++;
				
			} 
		}
		return result;
	}
	public static void main(String[] args) {
		String question = "Who was the first president of United States of America?";
		String candidate = "Washington";
	/*	
	* 	double[][] re = (process(question, candidate));
	*	for (int i = 0 ;i < re.length; i++)
	*		for (int j = 0 ; j < re.length; j++) 
	*		System.out.print(re[i][j] + (j == re.length - 1? "\n" : ", "));
	*/	
		double[] re = getValues(question, candidate);
		for (int i = 0; i < re.length; i++)
			System.out.print(re[i]+ " ");
	}

}
