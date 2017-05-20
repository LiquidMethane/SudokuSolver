package codes;

public class LinkedList {

	private SolNode first;
	private SolNode last;
	private int count;
	
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
		count = 0;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void push(int grid[][]) {
		count++;
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
			count--;
		} else {
			last = last.getPrev();
			last.setNext(null);
			count--;
		}
	}
	
	
	
	
	
	
	
}
