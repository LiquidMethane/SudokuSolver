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
			for (int i = 1; i < 10; i++)
				lg.elimination(i);
//			lg.diagnose();
			
		}
//		lg.elimination();
		
		lg.display();

		lg.diagnose();
	}

}
