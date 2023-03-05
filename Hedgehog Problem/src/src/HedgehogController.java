package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HedgehogController {
	static int totalRows = 0;
	static int m = 0;
	static int n = 0;	
	static String[][] apples;
	static int[][] maxApplesAtCoords;

	/**
	 * <p>
	 * This method is used to scan the input.txt file.
	 * It will look through the file and store the m and n variables so they can be accessed later on.
	 * Then it will create an apples 2D array and maxApplesAtCoords 2D array of m and n size. 
	 * Apples array mimics what is seen in the file and maxApplesAtCoords will be a completely empty array which will get updated with the max possible apples at each coord later on.
	 * @throws FileNotFoundException
	 */
	static void scanInputFile() throws FileNotFoundException {
		File inputFile = new File("C:\\Users\\luke_\\Desktop\\input.txt");
		if(inputFile != null) {
			Scanner fileScanner = new Scanner(inputFile);
			while(fileScanner.hasNextLine()) {
				if(totalRows == 0) {
					String sizeString = fileScanner.nextLine();
					String[] splitSizeString = sizeString.split(" ");
					m = Integer.parseInt(splitSizeString[0]);
					n = Integer.parseInt(splitSizeString[1]);
					totalRows++;
					apples = new String[m][n];
					maxApplesAtCoords = new int[m][n];
				} else {
					String rowFromFile = fileScanner.nextLine();
					String[] applesFromRow = rowFromFile.split(" ");					
					for(int i = 0; i < n; i++) {
						apples[totalRows-1][i] = applesFromRow[i];
					}
					totalRows++;
				}
			}
			fileScanner.close();	
		}
	}

	/**
	 * This method is the first call from the Model and is used to call the recursive calculateMaxApplesAtCoords method.
	 * 
	 * @return an int which will be the max total apples possible for the given input file
	 */
	static int calculateMaxApplesAtCoords() {
		return calculateMaxApplesAtCoords(0, 0);
	}

	/**
	 * This method is used to recursively work through each tile from the apples array starting from the top left tile (apples[0][0]) and moving down until the bottom row of that column is reached.
	 * At each step, depending on the current mAxis and nAxis, logic occurs to determine what max value should be stored in the maxApplesAtCoords array for the current coords.
	 * Once the last row is reached, if we are not at the bottom right tile, repeat the process by setting nAxis to 0 and moving along one column by mAxis+1.
	 * 
	 * @param mAxis - The current m value being recursively changed
	 * @param nAxis - The current m value being recursively changed
	 * @return maxApplesAtCoords[mAxis][nAxis] - This will be the highest possible apples and it is located in the bottom right tile
	 */
	static int calculateMaxApplesAtCoords(int mAxis, int nAxis) {
		if(mAxis == 0 && nAxis == 0) {
			maxApplesAtCoords[0][0] = Integer.parseInt(apples[0][0]);
		} else if (mAxis == 0) {
			maxApplesAtCoords[mAxis][nAxis] = maxApplesAtCoords[mAxis][nAxis-1] + Integer.parseInt(apples[mAxis][nAxis]);
		} else if(nAxis == 0) {
			maxApplesAtCoords[mAxis][nAxis] = maxApplesAtCoords[mAxis-1][nAxis] + Integer.parseInt(apples[mAxis][nAxis]);
		} else {
			maxApplesAtCoords[mAxis][nAxis] = Math.max(maxApplesAtCoords[mAxis][nAxis-1] + Integer.parseInt(apples[mAxis][nAxis]), maxApplesAtCoords[mAxis-1][nAxis] + Integer.parseInt(apples[mAxis][nAxis]));
		}

		if(mAxis == m-1 && nAxis == n-1) {
			return maxApplesAtCoords[mAxis][nAxis];
		} else if(nAxis != n-1){
			return calculateMaxApplesAtCoords(mAxis, nAxis + 1);		
		} else {
			nAxis = 0;
			mAxis = mAxis + 1;
			return calculateMaxApplesAtCoords(mAxis, nAxis);
		}		
	}

	/**
	 * This method is used for generating the output.txt file which will contain the one number for the max total apples.
	 * 
	 * @param maxTotalApples
	 */
	public static void createOutputFile(int maxTotalApples) {
		FileWriter outputFile;
		try {
			outputFile = new FileWriter("C:\\\\Users\\\\luke_\\\\Desktop\\\\output.txt");
			outputFile.write(String.valueOf(maxTotalApples));
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
}
