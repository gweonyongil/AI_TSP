package main;

import java.util.Scanner;

import ga.Crossover;
import ga.Initializer;
import ga.Mutation;
import ga.MyGASearch;
import ga.NSCMutation;
import ga.PMXCrossover;
import ga.PseudoTournamentSelection;
import ga.RandomInitializer;
import ga.SAInitalizer;
import ga.Selection;
import ga.SwapMutation;
import greedy.NearestNeighbor;
import greedy.TwoOptSearch;
import sa.SASearch;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

public class MainClass {
	static long start;
	static long end;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.print("Enter Map name (Usually Sample.txt): ");
		// Scanner scan = new Scanner(System.in);
		// String mapName = scan.next();
		String mapName = "Sample.txt";
		MapInfo.setMapInfoInstance(mapName);
		// System.out.print("Select Alg (1. Nearest Neighbor, 2. Two-Opt, 3. SA,
		// 4. GA) : ");

		int input = 3;
		// scan.close();
		switch (input) {
		case 1:
			start = System.currentTimeMillis();
			NearestNeighbor nearestNeighbor = new NearestNeighbor();
			int[] path = nearestNeighbor.calculatePath(0);
			System.out.println("Nearest Neighbor: " + PathCheck.getPathCost(path));
			break;
		case 2:
			start = System.currentTimeMillis();
			TwoOptSearch twoOptSearch = new TwoOptSearch(1000000);
			int[] path2 = twoOptSearch.calculatePath(0);
			System.out.println("two opt search: " + PathCheck.getPathCost(path2));
			break;
		case 3:
			// double randomVal1 = Math.random();
			// int intVal1 = (int)(randomVal1 * 100) + 1;
			//
			double randomVal2;
			int intVal2;
			double plusVal2;
			double val2;
			int test_case = 100;
			double[] temperatureTrial = { 10, 20, 30, 50, 100, 1000 };
			double total_time = 0;

			int numOfCity = MapInfo.getInstance().getNumOfCity();
			for (int i = 0; i < test_case; i++) {
				randomVal2 = Math.random();
				intVal2 = (int) (randomVal2 * 20);
				plusVal2 = (double) intVal2 / 100;
				val2 = 0.8 + plusVal2;
				// System.out.println(Double.toString(val2));

				start = System.currentTimeMillis();
				//////////// Starting Set///////////////
				// System.out.println(Integer.toString(numOfCity));
				SASearch saSearch = new SASearch(temperatureTrial[2], 0.8, 100000, 1);
//				int[] path3 = saSearch.calculatePath(0);
//				int[] path3_2= saSearch.calculatePath(0);
//				System.out.println("SA search: " + PathCheck.getPathCost(path3));
//				System.out.println("SA search: " + PathCheck.getPathCost(path3_2));
				int[] twice_path = saSearch.calculatePath(0);
//				int[] twice_path = saSearch.twiceAndSelect(0, path3, path3_2);
				twice_path = saSearch.calculatePath(twice_path);
				System.out.println("SA search: " + PathCheck.getPathCost(twice_path));
				end = System.currentTimeMillis();
				System.out.println("실행 시간 : " + (end - start) / 1000.0);
//				System.out.println("Experiment" + Integer.toString(i + 1) + " End");
				total_time += (end - start);
			}
//			System.out.println("평균 시간 : " + Double.toString(total_time / test_case / 1000.0));
			break;
		case 4:
			start = System.currentTimeMillis();
			int populationSize = 100;
			int generationSize = 10000;

			// Initialize by SA
			Initializer saInitializer = new SAInitalizer(30, 0.8, 100000, 5);
			// Random Initialize
			Initializer randInitializer = new RandomInitializer();

			Selection ptSelection = new PseudoTournamentSelection(populationSize, 10);

			Mutation swapMutation = new SwapMutation(0.3);
			// Mutation nscMutation = new NSCMutation(0.3, 4);

			Crossover pmxCrossover = new PMXCrossover();

			MyGASearch myGASearch = new MyGASearch(populationSize, generationSize);

			myGASearch.setProcess(saInitializer, pmxCrossover, ptSelection, swapMutation);
			// myGASearch.setProcess(randInitializer, pmxCrossover, ptSelection,
			// swapMutation);

			int[] path4 = myGASearch.calculatePath(0);
			for (int i = 0; i < myGASearch.generationScore.length; i++) {
				System.out.println(myGASearch.generationScore[i]);
			}

			System.out.println("GA: " + PathCheck.getPathCost(path4));
			break;
		}
		// 끝에 아래와 같이 삽입
		// end = System.currentTimeMillis();
		// System.out.println("실행 시간 : " + (end - start) / 1000.0);
		// System.out.println("Experiment End");
	}
}
