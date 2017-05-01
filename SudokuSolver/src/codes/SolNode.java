package codes;

public class SolNode {
	
	private int grid[][];
	private SolNode prev;
	private SolNode next;
	
	public SolNode() {
		prev = null;
		next = null;
	}
	
	public SolNode(int grid[][]) {
		this.grid = grid;
		prev = null;
		next = null;
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public SolNode getPrev() {
		return prev;
	}
	public void setPrev(SolNode prev) {
		this.prev = prev;
	}
	public SolNode getNext() {
		return next;
	}
	public void setNext(SolNode next) {
		this.next = next;
	}

	
}
