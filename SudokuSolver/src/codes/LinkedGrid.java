package codes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LinkedGrid {

	private Node root;
	private int dimension;
	// private int grid[][] = new int[9][9];

	LinkedList ll = new LinkedList();

	public LinkedGrid(int size) throws IOException {
		int count = 1;
		// Creating the first node
		this.dimension = size;
		root = new Node(0);
		Node marker = root;
		// Creating the first row
		for (int x = 0; x < dimension - 1; x++) {
			Node temp = new Node(0);
			marker.setRight(temp);
			temp.setLeft(marker);
			marker = temp;
		}
		Node rowMarker = root;
		for (int y = 0; y < dimension - 1; y++) {
			Node temp = new Node(0);
			rowMarker.setDown(temp);
			temp.setUp(rowMarker);
			for (int x = 0; x < dimension - 1; x++) {
				marker = temp;
				temp = new Node(0);
				marker.setRight(temp);
				temp.setLeft(marker);
				temp.getLeft().getUp().getRight().setDown(temp);
				temp.setUp(temp.getLeft().getUp().getRight());
			}
			rowMarker = rowMarker.getDown();
		}

		// At this point the whole grid is created
		// We will go through and set BoxID's

		Node temp = root;
		rowMarker = root;

		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				temp.setBoxID(x / 3 + 1 + (y / 3 * 3));
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		// display();
		// diagnose();
		// Populating the Grid from a file

		Scanner fileIn = new Scanner(new File("ReadSudoku.txt"));
		rowMarker = root;
		int number;
		while (rowMarker != null) {
			temp = rowMarker;
			while (temp != null) {

				number = fileIn.nextInt();
				if (number != 0)
					solve(temp, number);
				temp = temp.getRight();
				// display();
				// diagnose();
			}

			rowMarker = rowMarker.getDown();
		}
		fileIn.close();
	}

	public void solve(Node currentNode, int number) {
		currentNode.setSolution(number);

		Node temp = currentNode.getUp();
		while (temp != null) {
			temp.setPossibilityFalse(number);
			temp = temp.getUp();
		}

		temp = currentNode.getDown();
		while (temp != null) {
			temp.setPossibilityFalse(number);
			temp = temp.getDown();
		}

		temp = currentNode.getLeft();
		while (temp != null) {
			temp.setPossibilityFalse(number);
			temp = temp.getLeft();
		}

		temp = currentNode.getRight();
		while (temp != null) {
			temp.setPossibilityFalse(number);
			temp = temp.getRight();
		}

		// Setting all Boxes with the same BoxId to be false
		int currentBoxID = currentNode.getBoxID();
		temp = root;
		Node rowMarker = root;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (temp.getBoxID() == currentBoxID && temp != currentNode)
					temp.setPossibilityFalse(number);
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}

	}

	// public void display() {
	// Node rowMarker = root;
	// while (rowMarker != null) {
	// Node temp = rowMarker;
	// while (temp != null) {
	// // System.out.print(temp.getBoxID()+ " ");//.getSolution() + "
	// // ");
	// System.out.print(temp.getSolution() + " ");
	//
	// temp = temp.getRight();
	// }
	// System.out.println();
	// rowMarker = rowMarker.getDown();
	// }
	// }

	public void display() {
		int a[][] = new int[9][9];
		Node rm = root;
		int x = 0, y = 0;
		while (rm != null) {
			Node newNode = rm;
			while (newNode != null) {
				a[x][y] = newNode.getSolution();
				newNode = newNode.getRight();
				y++;
			}
			y = 0;
			rm = rm.getDown();
			x++;
		}

		for (int i = 0; i < 9; i++) {
			System.out.print("  ");
			for (int j = 0; j < 9; j++) {
				System.out.print(a[i][j]);
				if ((j + 1) % 3 == 0 && j != 8) {
					System.out.print("  |  ");
				} else {
					System.out.print("   ");
				}
			}
			if ((i + 1) % 3 == 0 && i != 8)
				System.out.print((char) 10 + "  -----------+-------------+------------\n");
			else if (i != 8)
				System.out.print((char) 10 + "             |             |            \n");
			else
				System.out.print("\n\n");
		}
		System.out.println();
	}

	public void diagnose() {
		Node temp = root;
		int choice = 0;
		Scanner input = new Scanner(System.in);
		do {
			temp.displayEverything();

			System.out.println("0.Exit");
			System.out.println("8.Go Up");
			System.out.println("2.Go Down");
			System.out.println("4.Go Left");
			System.out.println("6.Go Right");

			choice = input.nextInt();

			if (choice == 8)
				temp = temp.getUp();
			else if (choice == 2)
				temp = temp.getDown();
			else if (choice == 4)
				temp = temp.getLeft();
			else if (choice == 6)
				temp = temp.getRight();

		} while (choice != 0);

	}

	public int[][] toIntArray() {
		int grid[][] = new int[9][9];
		Node rm = root;
		int x = 0, y = 0;
		while (rm != null) {
			Node newNode = rm;
			while (newNode != null) {
				grid[x][y] = newNode.getSolution();
				newNode = newNode.getRight();
				y++;
			}
			y = 0;
			rm = rm.getDown();
			x++;
		}
		return grid;
	}

	public void restoreLinkedGrid() {
		int grid[][] = ll.last.getGrid();
		ll.pop();
		Node rm = root;
		int x = 0, y = 0;
		while (rm != null) {
			Node newNode = rm;
			while (newNode != null) {
				solve(newNode, grid[x][y]);
				newNode = newNode.getRight();
				y++;
			}
			y = 0;
			rm = rm.getDown();
			x++;
		}
	}

	public void storeLinkedGrid() {
		ll.push(toIntArray());
	}

	public boolean isComplete() {
		Node rm = root;
		Node newNode;
		while (rm != null) {
			newNode = rm;
			while (newNode != null) {
				if (newNode.getSolution() == 0)
					return false;
				newNode = newNode.getRight();
			}
			rm = rm.getDown();
		}
		return false;
	}

	public boolean isPossible() {
		Node rm = root;
		Node newNode;
		while (rm != null) {
			newNode = rm;
			while (newNode != null) {
				if (newNode.getSolution() == 0)
					for (int x = 1; x < 10; x++) {
						if (newNode.getPossibility(x))
							return true;
					}
				newNode = newNode.getRight();
			}
			rm = rm.getDown();
		}
		return false;
	}

	public Node firstEmpty() {
		Node rm = root;
		Node newNode;
		while (rm != null) {
			newNode = rm;
			while (newNode != null) {
				if (newNode.getSolution() == 0)
					return newNode;
				newNode = newNode.getRight();
			}
			rm = rm.getDown();
		}
		return root;
	}

	public boolean hasUniSol() {
		int count = 0;
		Node rm = root;
		Node newNode;
		while (rm != null) {
			newNode = rm;
			while (newNode != null) {
				if (newNode.getSolution() == 0) {
					count = 0;
					for (int x = 1; x < 10; x++)
						if (newNode.getPossibility(x))
							count++;

					if (count == 1) {
						return true;

					}
				}
				newNode = newNode.getRight();
			}
			rm = rm.getDown();
		}
		return false;
	}

	public void uniSol() {
		int count = 0;
		Node rm = root;
		Node newNode;
		while (rm != null) {
			newNode = rm;
			while (newNode != null) {
				if (newNode.getSolution() == 0) {
					count = 0;
					for (int x = 1; x < 10; x++)
						if (newNode.getPossibility(x)) {
							count++;
							if (count > 1)
								break;
						}

					if (count == 1)
						solve(newNode, newNode.checkPossibility());
				}
				newNode = newNode.getRight();
			}
			rm = rm.getDown();
		}
	}

}
