package codes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LinkedGrid {

	private Node root;
	private Node box[] = new Node[10];
	private int dimension;

	LinkedList ll = new LinkedList();

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

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
		Node rm = root;
		for (int y = 0; y < dimension - 1; y++) {
			Node temp = new Node(0);
			rm.setDown(temp);
			temp.setUp(rm);
			for (int x = 0; x < dimension - 1; x++) {
				marker = temp;
				temp = new Node(0);
				marker.setRight(temp);
				temp.setLeft(marker);
				temp.getLeft().getUp().getRight().setDown(temp);
				temp.setUp(temp.getLeft().getUp().getRight());
			}
			rm = rm.getDown();
		}

		// At this point the whole grid is created
		// We will go through and set BoxID's

		Node newNode = root;
		rm = root;

		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				newNode.setBoxID(x / 3 + 1 + (y / 3 * 3));
				newNode.setRowID(y + 1);
				newNode.setColumnID(x + 1);
				newNode = newNode.getRight();
			}
			rm = rm.getDown();
			newNode = rm;
		}
		// Populating the Grid from a file

		box[1] = root;
		box[2] = box[1].getRight().getRight().getRight();
		box[3] = box[2].getRight().getRight().getRight();
		box[4] = box[1].getDown().getDown().getDown();
		box[5] = box[2].getDown().getDown().getDown();
		box[6] = box[3].getDown().getDown().getDown();
		box[7] = box[4].getDown().getDown().getDown();
		box[8] = box[5].getDown().getDown().getDown();
		box[9] = box[6].getDown().getDown().getDown();

		Scanner fileIn = new Scanner(new File("ReadSudoku.txt"));
		rm = root;
		int number;
		while (rm != null) {
			newNode = rm;
			while (newNode != null) {

				number = fileIn.nextInt();
				if (number != 0)
					solve(newNode, number);
				newNode = newNode.getRight();
			}

			rm = rm.getDown();
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
		display();
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

	public boolean hasUniSol() {// kinda useless
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

					if (count == 1)
						return true;
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

	public void recessUniSol(int i) {
		Node rm;
		Node newNode;
		Node temp = null;
		int count = 0;

		rm = root;
		while (rm != null) {
			count = 0;
			newNode = rm;
			while (newNode != null) {
				if (newNode.getPossibility(i)) {
					count++;
					temp = newNode;
				}
				if (count > 1)
					break;
				newNode = newNode.getRight();
			}
			if (count == 1)
				solve(temp, i);
			rm = rm.getDown();
		}

		Node cm = root;
		while (cm != null) {
			count = 0;
			newNode = cm;
			while (newNode != null) {
				if (newNode.getPossibility(i)) {
					count++;
					temp = newNode;
				}
				if (count > 1)
					break;
				newNode = newNode.getDown();
			}
			if (count == 1)
				solve(temp, i);
			cm = cm.getRight();
		}

		for (int x = 1; x < 10; x++) {
			Node bm = box[x];
			for (int a = 0; a < 3; a++) {
				newNode = bm;
				for (int b = 0; b < 3; b++) {
					if (newNode.getPossibility(i)) {
						count++;
						temp = newNode;
					}
					if (count > 1)
						break;
					newNode = newNode.getRight();
				}
				if (count == 1)
					solve(temp, i);
				bm = bm.getDown();
			}
		}
	}

	public boolean checkBoxHoriz(int boxID, int i) {
		Node bm = box[boxID];
		Node newNode;
		for (int x = 0; x < 3; x++) {
			newNode = bm;
			for (int y = 0; y < 3; y++) {
				if (!newNode.isCheckmark())
					if (newNode.getPossibility(i))
						return false;
				newNode = newNode.getRight();
			}
			bm = bm.getDown();
		}
		return true;
	}

	public boolean checkBoxVert(int boxID, int i) {
		Node bm = box[boxID];
		Node newNode;
		for (int x = 0; x < 3; x++) {
			newNode = bm;
			for (int y = 0; y < 3; y++) {
				if (!newNode.isCheckmark())
					if (newNode.getPossibility(i))
						return false;
				newNode = newNode.getDown();
			}
			bm = bm.getRight();
		}
		return true;
	}

	public void setFalseHoriz(Node newNode, int i) {
		Node temp = newNode;
		while (temp.getLeft() != null) {
			temp = temp.getLeft();
			if (!temp.isCheckmark())
				temp.setPossibilityFalse(i);
		}

		temp = newNode;
		while (temp.getRight() != null) {
			temp = temp.getRight();
			if (!temp.isCheckmark())
				temp.setPossibilityFalse(i);
		}
	}

	public void setFalseVert(Node newNode, int i) {
		Node temp = newNode;
		while (temp.getUp() != null) {
			temp = temp.getUp();
			if (!temp.isCheckmark())
				temp.setPossibilityFalse(i);
		}

		temp = newNode;
		while (temp.getDown() != null) {
			temp = temp.getDown();
			if (!temp.isCheckmark())
				temp.setPossibilityFalse(i);
		}
	}

	public void setFalseBox(Node newNode, int i) {
		Node rm = box[newNode.getBoxID()];
		Node temp;
		for (int x = 0; x < 3; x++) {
			temp = rm;
			for (int y = 0; y < 3; y++) {
				temp.setPossibilityFalse(i);
				temp = temp.getRight();
			}
			rm = rm.getDown();
		}
	}

	public void setCheckMarkFalse() {
		Node rm = root;
		Node newNode;
		while (rm != null) {
			newNode = rm;
			while (newNode != null) {
				newNode.setCheckmark(false);
				newNode = newNode.getRight();
			}
			rm = rm.getDown();
		}
	}

	public void elimination(int i) {

		// the first algorithm
		int count = 0;
		for (int x = 1; x < 10; x++) {
			Node bm = box[x];
			Node newNode = null;
			count = 0;
			for (int a = 0; a < 3; a++) {
				newNode = bm;
				for (int b = 0; b < 3; b++) {
					if (newNode.getPossibility(i) && newNode.getSolution() == 0) {
						count++;
						newNode.setCheckmark(true);
					}
					newNode = newNode.getRight();
				}
				if (count == 2 || count == 3) {
					if (checkBoxHoriz(x, i)) {
						setFalseHoriz(bm, i);
					}
				}
				bm = bm.getDown();
				setCheckMarkFalse();
			}

			Node cm = box[x];
			for (int c = 0; c < 3; c++) {
				newNode = cm;
				count = 0;
				for (int d = 0; d < 3; d++) {
					if (newNode.getPossibility(i) && newNode.getSolution() == 0) {
						count++;
						newNode.setCheckmark(true);
					}
					newNode = newNode.getDown();
				}
				if (count == 2 || count == 3) {
					if (checkBoxVert(x, i)) {
						setFalseVert(cm, i);
					}
				}
				cm = cm.getRight();
				setCheckMarkFalse();
			}
		}

	}

	public int[] PosArr(Node newNode) {
		int arr[] = new int[2];
		int count = 0;
		for (int x = 1; x < 10; x++) {
			if (newNode.getPossibility(x))
				arr[count++] = x;
		}
		return arr;
	}

	public void elimination() {// of course not working
		// Node rm = root;
		// int count = 0;
		// Node newNode = null;
		// Node temp = null;
		// boolean pair = true;
		// int PosArr[][] = new int[2][2];
		// while (rm != null) {
		// newNode = rm;
		// count = 0;
		// while (newNode != null) {
		// count = 0;
		// for (int i = 1; i < 10; i++)
		// if (newNode.getPossibility(i))
		// count++;
		// if (count == 2) {
		// count = 0;
		// PosArr[0] = PosArr(newNode);
		// temp = rm;
		// while (temp != null) {
		// count = 0;
		// for (int a = 1; a < 10; a++)
		// if (temp.getPossibility(a))
		// count++;
		// if (count == 2) {
		// PosArr[1] = PosArr(temp);
		// }
		// for (int q = 0; q < 2; q++)
		// if (PosArr[0][q] != PosArr[1][q])
		// pair = false;
		// if (pair = true) {
		// for (int i = 0; i < 2; i++)
		// setFalseHoriz(rm, PosArr[0][i]);
		// if (newNode.getBoxID() == temp.getBoxID()) {
		// for (int b = 0; b < 2; b++) {
		// setFalseBox(newNode, b);
		// newNode.setPossibilityTrue(b);
		// temp.setPossibilityTrue(b);
		// }
		// }
		// }
		// }
		// }
		// newNode = newNode.getRight();
		// }
		// rm = rm.getDown();
		// }
	}

}
