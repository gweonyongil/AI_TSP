package sa;

import java.util.Arrays;

import greedy.TwoOptSearch;
import tspUtil.GetTwoRandomNumber;
import tspUtil.PathCheck;
import tspUtil.Sorting;
import tspUtil.TSPAlgorithm;

public class SASearch extends TSPAlgorithm{
	private double temperature;
	private double deltaTemperature;
	private int numOfNextHop;
	
	private TwoOptSearch twoOptSearch;
	
	public SASearch(double temperature, double deltaTemperature, int limitTrial, int numOfNextHop) {
		this.twoOptSearch = new TwoOptSearch(limitTrial);
		this.setSAParameter(temperature, deltaTemperature);
		this.numOfNextHop = numOfNextHop;
	}

	public void setSAParameter(double temperature, double deltaTemperature) {
		if (temperature <= 0) {
			System.err.println("Temperature must be bigger than 0");
			System.exit(1);
		}
		this.temperature = temperature;
		if (!(deltaTemperature < 1 && deltaTemperature > 0)) {
			System.err.println("delta value of temperature must be between 0 to 1");
			System.exit(1);
		}
		this.deltaTemperature = deltaTemperature;
	}

	@Override
	public int[] calculatePath(int startPoint) {
		// TODO Auto-generated method stub
		int[] path = this.twoOptSearch.calculatePath(startPoint);
		path = this.calculatePath(path);
		return path;
	}

	@Override
	public int[] calculatePath(int[] path) {
		// TODO Auto-generated method stub

		int[] copyPath = Arrays.copyOf(path, path.length);
		int bestScore = PathCheck.getPathCost(copyPath);
		while (this.temperature > 1) {
			
			int[] trialPath = Arrays.copyOf(copyPath, copyPath.length);
			
			for(int i = 0; i < this.numOfNextHop ; i++){
				int[] twoRandNumber = GetTwoRandomNumber.getTwoRandomNumber();
				int firstPoint = twoRandNumber[0];
				int secondPoint = twoRandNumber[1];
				this.twoOptSearch.swapPath(trialPath, firstPoint, secondPoint);
			}
			
			trialPath = this.twoOptSearch.calculatePath(trialPath);
			
			int trialScore = PathCheck.getPathCost(trialPath);
			if (Math.random() < this.getAcceptProbability(bestScore, trialScore)) {
				if(trialScore > bestScore){
//					System.out.println(bestScore);
				}
				copyPath = Arrays.copyOf(trialPath, trialPath.length);
				bestScore = trialScore;
			} 
			this.temperature *= this.deltaTemperature;
		}
		return copyPath;
	}

	private double getAcceptProbability(int bestScore, int trialScore) {
		if (bestScore > trialScore)
			return 1;
		else {
			return Math.pow(Math.E, -(trialScore - bestScore) / this.temperature);
		}
	}
	
	public int[] twiceAndSelect(int startpoint, int[] path1, int[] path2) {
		int[] path3 = new int[this.numOfCity + 1];
		boolean[] visited3 = new boolean[this.numOfCity];
		Arrays.fill(visited3, false);
		visited3[0] = true;

		int[][] sub_path = new int[this.numOfCity + 1][this.numOfCity + 1];
		int sub_path_index = 0;
		for (int path1_index = 0; path1_index < this.numOfCity; path1_index++) {
			for (int path2_index = 0; path2_index < this.numOfCity; path2_index++) {
				if (path1[path1_index] == path2[path2_index]) {
					int temp = path1[path1_index];
					while (path1[path1_index] == path2[path2_index]) {
						sub_path[temp][sub_path_index++] = path2[path2_index];
						path1_index++;
						path2_index++;
						if (path1_index == this.numOfCity
								||path2_index == this.numOfCity) {
							// System.out.println("@@");
							break;
						}
						// System.out.println(Integer.toString(path2[path2_index]));
					}
					// System.out.println("----------------");
					sub_path_index = 0;
					break;
				}
			}
		}

		path3[0] = startpoint;
		path3[this.numOfCity] = startpoint;
		for (int i = 0; i < this.numOfCity; i++) {
			int[] indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path3[i]]);
			for (int j = 0; j < this.numOfCity; j++) {
				if (!visited3[indexOfSortedArr[j]]) {
					if (sub_path[indexOfSortedArr[j]][0] + sub_path[indexOfSortedArr[j]][1] > 0) {
						for (int k = 0; k < this.numOfCity; k++) {
							if (sub_path[indexOfSortedArr[j]][k] + sub_path[indexOfSortedArr[j]][k + 1] > 0) {
								path3[i + 1] = sub_path[indexOfSortedArr[j]][k];
								visited3[sub_path[indexOfSortedArr[j]][k]] = true;
								i++;
								indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path3[i]]);
							} else {
								if (k != 0)
									i--;
								break;
							}
						}
						break;
					} else {
						path3[i + 1] = indexOfSortedArr[j];
						visited3[indexOfSortedArr[j]] = true;
						break;
					}
				}
			}
		}
		return path3;
	}
}
