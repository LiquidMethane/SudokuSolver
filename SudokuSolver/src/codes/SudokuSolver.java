package codes;

import java.io.IOException;
import java.util.Scanner;

public class SudokuSolver {

	public static void main(String[] args) throws IOException{
		
		LinkedGrid lg = new LinkedGrid(9);
		
//		System.out.println(lg.firstEmpty().getSolution());
		
//		while (!lg.isComplete()) {
//			
//			while (lg.hasUniSol())
//				lg.uniSol();
//			
//			lg.storeLinkedGrid();
//			
//			
////			lg.solve(lg.firstEmpty(), );
//			
//			
//			
//			
//			
//		}
		
		
		
		for (int x = 0; x < 20; x++) {
			
			lg.uniSol();
			for (int i = 1; i < 10; i++)
				lg.elimination(i);
			for (int i = 1; i < 10; i++)
				lg.recessUniSol(i);
			
		}
//		lg.recessSol(6);
		lg.display();
		
//		lg.uniSol();
//		lg.display();
//		System.out.println("backup1");
//		lg.backup();
//		lg.uniSol();
//		System.out.println("backup2");
//		lg.backup();
//		lg.uniSol();
//		System.out.println("backup3");
//		lg.backup();
//		System.out.println("restore3");
//		lg.restoreLinkedGrid();
//		lg.display();
//		System.out.println("restore2");
//		lg.restoreLinkedGrid();
//		lg.display();
//		System.out.println("restore1");
//		lg.restoreLinkedGrid();
//		lg.display();
	}

}
