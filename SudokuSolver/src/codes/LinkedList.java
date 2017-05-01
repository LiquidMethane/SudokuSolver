package codes;

public class LinkedList {

	SolNode first;
	SolNode last;
	
	public SolNode getFirst() {
		return first;
	}
	public void setFirst(SolNode first) {
		this.first = first;
	}
	public SolNode getLast() {
		return last;
	}
	public void setLast(SolNode last) {
		this.last = last;
	}
	
	public LinkedList() {
		first = null;
		last = null;
	}
	
	public void push(int grid[][]) {
		SolNode newNode = new SolNode(grid);
		if (first == null)
			first = newNode;
		else {
			last.setNext(newNode);
			newNode.setPrev(last);
		}
		last = newNode;
	}
	
	public void pop() {
		if (last == null) {
		} else if (last == first) {
			first = null;
			last = null;
		} else {
			last = last.getPrev();
			last.setNext(null);
		}
	}
	
	
	
	
	
	
	
}
