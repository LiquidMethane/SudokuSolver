package codes;

public class Node {

	private int solution;
	private boolean[] possible = new boolean[10];
	private int boxID;
	private int rowID;
	private int columnID;
	private boolean checkmark;
	private Node up;
	private Node down;
	private Node left;
	private Node right;

	public Node(int data) {
		for (int x = 1; x < 10; x++)
			possible[x] = true;

		this.solution = data;
		up = null;
		down = null;
		left = null;
		right = null;
	}

	public void displayEverything() {
		System.out.println("Solution:" + solution);
		System.out.println("BoxID:" + boxID);
		System.out.println("Possibilities:");
		for (int x = 1; x < 10; x++)
			System.out.println(x + ":" + possible[x]);
		System.out.println();
	}
	
	public boolean isCheckmark() {
		return checkmark;
	}

	public void setCheckmark(boolean checkmark) {
		this.checkmark = checkmark;
	}

	public int getRowID() {
		return rowID;
	}

	public void setRowID(int rowID) {
		this.rowID = rowID;
	}

	public int getColumnID() {
		return columnID;
	}

	public void setColumnID(int columnID) {
		this.columnID = columnID;
	}

	public int getBoxID() {
		return boxID;
	}

	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}

	public void setPossibilityFalse(int number) {
		possible[number] = false;
	}
	
	public boolean getPossibility(int number) {
		return possible[number];
	}
	
	public int checkPossibility() {
		for (int x = 1; x < 10; x++)
			if(possible[x])
				return x;
		return -1;
	}

	public int getSolution() {
		return solution;
	}

	public void setSolution(int data) {
		this.solution = data;
		for (int x = 0; x < 10; x++)// turn off all possibilities
			possible[x] = false;
		possible[data] = true;// turn on the possibility for the given data
	}

	public Node getUp() {
		return up;
	}

	public void setUp(Node up) {
		this.up = up;
	}

	public Node getDown() {
		return down;
	}

	public void setDown(Node down) {
		this.down = down;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

}
