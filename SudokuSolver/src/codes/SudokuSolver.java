package codes;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import sun.security.util.DisabledAlgorithmConstraints;

public class SudokuSolver {

	public static void main(String[] args) throws IOException {

		LinkedGrid lg = new LinkedGrid(9);
		
		long start = System.currentTimeMillis();
		
//		for (int a = 0; a < 100; a++) {
//
//			for (int x = 0; x < 20; x++) {
//
//				for (int i = 1; i < 10; i++)
//					lg.uniSol();
//
//				for (int i = 1; i < 10; i++)
//					lg.recessUniSol(i);
//
//				for (int i = 1; i < 10; i++) {
//					lg.eliminationHoriz(i);
//					lg.eliminationVert(i);
//				}
//				
//				lg.elimination();
//			
//			}
//			
//		}
//		
//		if (!lg.complete())
		
		lg.guess(lg.getRoot());

		long span = System.currentTimeMillis() - start;
		System.out.println("Processing time: " + span + " milliseconds.\n");
		
		lg.display();
	}

}
