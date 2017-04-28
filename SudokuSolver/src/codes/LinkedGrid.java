package codes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LinkedGrid {

	private Node root;
	private int dimension;

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

		Scanner fileIn = new Scanner(new File("Sudoku.txt"));
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

	public void display() {
		Node rowMarker = root;
		while (rowMarker != null) {
			Node temp = rowMarker;
			while (temp != null) {
				// System.out.print(temp.getBoxID()+ " ");//.getSolution() + "
				// ");
				System.out.print(temp.getSolution() + " ");

				temp = temp.getRight();
			}
			System.out.println();
			rowMarker = rowMarker.getDown();
		}
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

}
