package src;
import java.io.FileNotFoundException;
public class HedgehogModel {

	public static void main(String args[]) throws FileNotFoundException
	{
		HedgehogController.scanInputFile();
		HedgehogController.createOutputFile(HedgehogController.calculateMaxApplesAtCoords());
	}
}
