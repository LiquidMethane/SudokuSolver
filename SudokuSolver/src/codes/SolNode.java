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
	
	public void display() {
		for (int x = 0; x < 9; x++) {
			System.out.print("  ");
			for (int y = 0; y < 9; y++) {
				System.out.print(grid[x][y]);
				if ((y + 1) % 3 == 0 && y != 8) {
					System.out.print("  |  ");
				} else {
					System.out.print("   ");
				}
			}
			if ((x + 1) % 3 == 0 && x != 8)
				System.out.print((char) 10 + "  -----------+-------------+------------\n");
			else if (x != 8)
				System.out.print((char) 10 + "             |             |            \n");
			else
				System.out.print("\n\n");
		}
		System.out.println();
	}
	
}
