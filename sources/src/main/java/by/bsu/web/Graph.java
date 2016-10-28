package by.bsu.web;

public class Graph {
	
	private int I;
	private int U;
	private int K;
	
	private int lastIndex = 0;
	
	private Edge[] edges;
	
	private int[][] nets;
	
	public Graph() {
		I = 0;
		U = 0;
		edges = null;
		nets = null;
	}
	
	public Graph(int I, int U) {
		this.I = I;
		this.U = U;
		edges = new Edge[U];
		for (int i = 0; i < U; i++) {
			edges[i] = new Edge();
		}
	}
	
	public int getI() {
		return I;
	}
	
	public int getU() {
		return U;
	}
	
	public boolean addEdge(int begin, int end) {
		Edge edge = new Edge(begin, end);
		for (int i = 0; i < U; i++) {
			if (edges[i].equals(edge)) {
				return false;
			}
		}
		edges[lastIndex] = edge;
		lastIndex++;
		return true;
	}
	
	public Integer getEdgeIndex(int begin, int end) {
		Edge edge = new Edge(begin, end);
		for (int i = 0; i < U; i++) {
			if (edges[i].equals(edge)) {
				return i;
			}
		}
		return null;
	}
	
	public int getK() {
		return K;
	}
	
	public void setK(int K) {
		this.K = K;
		nets = new int[K][U];
		for (int i = 0; i < K; i++) {
			for (int j = 0; j < U; j++) {
				nets[i][j] = 0;
			}
		}
	}
	
	public boolean setEdge(Edge edge, int k) {
		if (k <=0 || k > this.K) {
			return false;
		}
		int j = -1;
		for (int i = 0; i < U; i++) {
			if (edges[i].equals(edge)) {
				j = i;
				break;
			}
		}
		if (j != -1) {
			int realK = k - 1;
			nets[realK][j] = 1;
			return true;
		} else {
			return false;
		}
	}

	public Edge[] getEdges() {
		return edges;
	}

	public void setEdges(Edge[] edges) {
		this.edges = edges;
	}

	public int[][] getNets() {
		return nets;
	}

	public void setNets(int[][] nets) {
		this.nets = nets;
	}
	
	public Edge getEdgeByIndex(int index) {
		return edges[index];
	}

	public static class Edge {

		private int begin;
		private int end;

		public Edge() {
			begin = 0;
			end = 0;
		}

		public Edge(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}

		public boolean equals(Edge edge) {
			return (this.begin == edge.getBegin() && this.end == edge.getEnd());
		}

		public int getBegin() {
			return begin;
		}

		public void setBegin(int begin) {
			this.begin = begin;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}
	}
}
