package codes;

import java.io.IOException;
import java.util.Scanner;

public class SudokuSolver {

	public static void main(String[] args) throws IOException{
		
		LinkedGrid lg = new LinkedGrid(9);
		
		
//		guess(){
//			run logical routines
//			for (guess = 1; guess < 10; guess++){
//				if(possibilities.contains(guess)){
//					stack.push(grid);
//					if(!grid.guess(guess)){
//						grid = stack.getLast();
//					}
//				}
//			}
//		}
		

		
		for (int x = 0; x < 20; x++) {
			
//			lg.uniSol();
			
			for (int i = 1; i < 10; i++)
				lg.uniSol();
			for (int i = 1; i < 10; i++)
				lg.recessUniSol(i);
			for (int i = 1; i < 10; i++) {
				lg.eliminationHoriz(i);
				lg.eliminationVert(i);
			}
//			lg.display();
//			lg.displayPoss();
				
//			lg.diagnose();
			lg.elimination();
		}
		

		
//		lg.setFalseHoriz(lg.getRoot(), 1);
//		lg.setFalseVert(lg.getRoot(), 1);
//		lg.setFalseBox(lg.getRoot(), 1);
		
		
		lg.display();

//		lg.diagnose();
//		lg.displayPoss();
		
	}

}
