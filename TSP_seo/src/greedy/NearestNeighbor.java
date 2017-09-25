package greedy;

import java.nio.file.Path;
import java.util.Arrays;

import tspUtil.MapInfo;
import tspUtil.PathCheck;
import tspUtil.Sorting;
import tspUtil.TSPAlgorithm;

public class NearestNeighbor extends TSPAlgorithm {
	MapInfo mapinfo;

	public NearestNeighbor() {
		super();
	}

	/*
	 * 시작점으로부터 시작해서 방문되지 않은 가장 가까운 노드로 방문
	 */
	@Override
	public int[] calculatePath(int startPoint) {
		// TODO Auto-generated method stub
		int[] path = new int[this.numOfCity + 1];

		// path[0] = startPoint;
		// path[this.numOfCity] = startPoint;
		// path = this.calculatePath(path);
		path = this.twiceAndSelect(path, startPoint);

		return path;
	}

	@Override
	public int[] calculatePath(int[] path) {
		// TODO Auto-generated method stub
		boolean[] visited = new boolean[this.numOfCity];
		Arrays.fill(visited, false);
		visited[0] = true;

		for (int i = 0; i < this.numOfCity; i++) {
			int[] indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path[i]]);
			for (int j = 0; j < this.numOfCity; j++) {
				if (!visited[indexOfSortedArr[j]]) {
					int same_index = procSameLeng(indexOfSortedArr, path[0], path[i]);
					if (same_index == 0) {
						path[i + 1] = indexOfSortedArr[j];
						visited[indexOfSortedArr[j]] = true;
						break;
					} else {
						int[] new_indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path[indexOfSortedArr[j]]]);
						int min = MapInfo.getInstance().getTwoCityDistance(indexOfSortedArr[j],
								new_indexOfSortedArr[1]);
						path[i + 1] = indexOfSortedArr[j];
						visited[indexOfSortedArr[j]] = true;
						int min_index = j;
						for (int k = j + 1; k <= same_index; k++) {
							new_indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path[indexOfSortedArr[k]]]);
							if (min > MapInfo.getInstance().getTwoCityDistance(new_indexOfSortedArr[1],
									indexOfSortedArr[k])) {
								min = MapInfo.getInstance().getTwoCityDistance(new_indexOfSortedArr[1],
										indexOfSortedArr[k]);
								min_index = k;
							}
						}
						path[i + 1] = indexOfSortedArr[min_index];
						visited[indexOfSortedArr[min_index]] = true;
						break;
					}

				}
			}
		}
		System.out.println("NN Path search: " + PathCheck.getPathCost(path));
		return path;
	}

	public int procSameLeng(int[] indexOfSortedArr, int startpoint, int path_point) {
		int same_index = 1;
		if (indexOfSortedArr[1] == startpoint)
			return 0;
		while (MapInfo.getInstance().getTwoCityDistance(indexOfSortedArr[same_index], path_point) == MapInfo
				.getInstance().getTwoCityDistance(indexOfSortedArr[same_index + 1], path_point)) {
			same_index++;
			// System.out.println(Integer.toString(same_index));
		}
		return same_index;
	}

	public int[] twiceAndSelect(int[] path, int startpoint) {
		int[] path1 = new int[this.numOfCity + 1];
		int[] path2 = new int[this.numOfCity + 1];
		int[] path3 = new int[this.numOfCity + 1];

		boolean[] visited1 = new boolean[this.numOfCity];
		Arrays.fill(visited1, false);

		boolean[] visited2 = new boolean[this.numOfCity];
		Arrays.fill(visited2, false);

		boolean[] visited3 = new boolean[this.numOfCity];
		Arrays.fill(visited3, false);

		visited1[0] = true;
		visited2[0] = true;
		visited3[0] = true;

		path1[0] = (int) (Math.random() * 1000) % (numOfCity - 1) + 1;
		path1[0] = 0;
		path1[this.numOfCity] = path1[0];
		for (int i = 0; i < this.numOfCity; i++) {
			int[] indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path1[i]]);
			for (int j = 0; j < this.numOfCity; j++) {
				if (!visited1[indexOfSortedArr[j]]) {
					path1[i + 1] = indexOfSortedArr[j];
					visited1[indexOfSortedArr[j]] = true;
					break;
				}
			}
		}
		path2[0] = (int) (Math.random() * 1000) % (numOfCity - 1) + 1;
		path2[this.numOfCity] = path2[0];
		for (int i = 0; i < this.numOfCity; i++) {
			int[] indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path2[i]]);
			for (int j = 0; j < this.numOfCity; j++) {
				if (!visited2[indexOfSortedArr[j]]) {
					path2[i + 1] = indexOfSortedArr[j];
					visited2[indexOfSortedArr[j]] = true;
					break;
				}
			}
		}

		System.out.println("NN Path1 search: " + PathCheck.getPathCost(path1));
		System.out.println("NN Path2 search: " + PathCheck.getPathCost(path2));

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
						if (path1_index == this.numOfCity || path2_index == this.numOfCity) {
							break;
						}
					}
					sub_path_index = 0;
					break;
				}
			}
		}
		// System.out.println(Integer.toString(MapInfo.getInstance().getTwoCityDistance(path1[0],
		// path2[0])));
		path3[0] = startpoint;
		path3[this.numOfCity] = path3[0];

		for (int i = 0; i < this.numOfCity; i++) {
			int[] indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path3[i]]);
			for (int j = 0; j < this.numOfCity; j++) {
				if (!visited3[indexOfSortedArr[j]]) {
					if (sub_path[indexOfSortedArr[j]][0] + sub_path[indexOfSortedArr[j]][1] > 0) {
						for (int k = 0; k < this.numOfCity - 1; k++) {
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
		System.out.println("NN Small search: " + PathCheck.getPathCost(path3));

		return path3;
	}
}
