package tspUtil;

import java.util.Arrays;

public class PathCheck {
	/*
	 * ��� ����� �� ���
	 * ��� ������ ���� 5���� ���ø�
	 * int���� �迭��
	 * 0 1 2 3 4 0
	 * �Ǵ�
	 * 0 2 4 1 3 0
	 * ���� ���¸� ������(������� �������� ���ƾ��ϸ� ��� ������ index�� �ѹ��� �־����)
	 */
	public static int getPathCost(int [] path){
		
		int totalCost = 0;
		int [][] map = MapInfo.getInstance().getDistanceMap();
		
		for(int i = 0; i < path.length - 1;i++){
			totalCost += map[path[i]][path[i+1]];
		}
		return totalCost;
	}
	/*
	 * ��ΰ� ������ ������� Ȯ��
	 * ��� ������ ���� 5���� ���ø�
	 * int���� �迭��
	 * 0 1 2 3 4 0
	 * �Ǵ�
	 * 0 2 4 1 3 0
	 * ���� ���¸� ������(������� �������� ���ƾ��ϸ� ��� ������ index�� �ѹ��� �־����)
	 */
	public static boolean isPathDuplicated(int [] path){
		boolean [] visited = new boolean[MapInfo.getInstance().getNumOfCity()];
		
		Arrays.fill(visited, false);
		
		//������� �������� ������ Ȯ��
		if(path[0] != path[path.length-1]) return true;
		
		for(int i = 0; i < path.length-1;i++){
			if(visited[i]) return true;
			else visited[i] = true;
		}
		return false;
	}
	
	public static void printPath(int [] path){
		for(int i = 0; i < path.length; i++){
			System.out.print(path[i]+ " ");
		}
		System.out.println();
	}
}
