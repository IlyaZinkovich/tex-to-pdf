package by.bsu.web;

import java.util.Vector;

public class Task {
	
	private int q = 0;
	
	private int[] alpha; //q
	private int[][][] lambda;//qxKxU
	
	private int[][] d1Includes;//KxU
	private int[][] d1Values;//KxU
	
	private Vector<Graph.Edge> d0Edges = new Vector<>();//d0Size
	private int[][] d0Includes; //KxU
	private int[] d0Values;//U
	
	private int[][] aValues; //IxK
	
	private Graph graph = null;
	
	public Task(Graph graph) {
		this.graph = graph;
		d1Includes = new int[graph.getK()][graph.getU()];
		d1Values = new int[graph.getK()][graph.getU()];
		d0Includes = new int[graph.getK()][graph.getU()];
		d0Values = new int[graph.getU()];
		for (int j = 0; j < graph.getU(); j++) {
			d0Values[j] = 0;
		}
		for (int i = 0; i < graph.getK(); i++) {
			for (int j = 0; j < graph.getU(); j++) {
				d1Includes[i][j] = 0;
				d0Includes[i][j] = 0;
				d1Values[i][j] = 0;
			}
		}
		aValues = new int[graph.getI()][graph.getK()];
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	public int getQ() {
		return q;
	}
	
	public void setQ(int q) {
		this.q = q;
		alpha = new int[q];
		for (int i = 0; i < q; i++) {
			alpha[i] = 0;
		}
		lambda = new int[q][graph.getK()][graph.getU()];
		for (int i = 0; i < q; i++) {
			for (int j = 0; j < graph.getK(); j++) {
				for (int k = 0; k < graph.getU(); k++) {
					lambda[i][j][k] = 0;
				}
			}
		}
	}
	
	public int getD1Including(int k, int i) {
		return d1Includes[k - 1][i];
	}
	
	public void setD1Including(int k, int i) {
		int realK = k - 1;
		d1Includes[realK][i] = 1;
	}
	
	public int getD0Including(int k, int i) {
		return d0Includes[k - 1][i];
	}
	
	public void setD0Including(int i) {
		for (int j = 0; j < graph.getK(); j++) {
			d0Includes[j][i] = 1;
		}
	}
	
	public void removeD0Including(int k, int i) {
		int realK = k - 1;
		d0Includes[realK][i] = 0;
	}
	
	public int getLambda(int qValue, int k, int i) {
		return lambda[qValue - 1][k - 1][i];
	}
	
	public void setLambda(int qValue, int k, int i, int value) {
		lambda[qValue - 1][k - 1][i] = value;
	}
	
	public int getD1Value(int k, int i) {
		return d1Values[k - 1][i];
	}
	
	public void setD1Value(int k, int i, int value) {
		d1Values[k - 1][i] = value;
	}
	
	public int getD0Value(int i) {
		return d0Values[i];
	}
	
	public void setD0Value(int i, int value) {
		d0Values[i] = value;
	}
	
	public int getA(int i, int k) {
		return aValues[i - 1][k - 1];
	}
	
	public void setA(int i, int k, int value) {
		aValues[i - 1][k - 1] = value;
	}
	
	public int getAlpha(int qValue) {
		return alpha[qValue - 1];
	}
	
	public void setAlpha(int qValue, int value) {
		alpha[qValue - 1] = value;
	}
}
