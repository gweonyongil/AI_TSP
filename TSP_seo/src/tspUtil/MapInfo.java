package tspUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class MapInfo {
	//Singleton pattern
	private static MapInfo instance = null;
	private int numOfCity; // ���� ����
	private int distanceMap[][]; // �� ���ð��� �Ÿ� ����, symmetric matrix

	

	public static void setMapInfoInstance(String filename){
		instance = new MapInfo(filename);
	}
	
	public static MapInfo getInstance(){
		return instance;
	}
	
	private MapInfo(String filename) {
		this.setNumOfCity(filename);
		this.setDistanceMap(filename);
	}
	
	public int getNumOfCity() {
		return numOfCity;
	}
	
	public int[][] getDistanceMap() {
		return distanceMap;
	}

	public int getTwoCityDistance(int firstCityIndex, int secondCityIndex){
		return this.distanceMap[firstCityIndex][secondCityIndex];
	}
	
	//���� ���� �ʱ�ȭ
	//�� ���ð��� �Ÿ��� ��Ÿ���� 2���� ��ķν� symmetric�� ������� ����
	
	private void setDistanceMap(String filename) {
		this.distanceMap = new int[this.numOfCity][this.numOfCity];

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			System.exit(1);
		}

		for (int i = 0; i < numOfCity; i++) {
			try {
				String str = reader.readLine();

				String[] splitedStr = str.split(" ");

				for (int j = 0; j < splitedStr.length; j++) {
					this.distanceMap[i][j] = Integer.parseInt(splitedStr[j]);
				}

			} catch (IOException e) {
				System.err.println("File read error");
				System.exit(1);
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println("Reader close error");
			System.exit(1);
		}
		for(int i = 0; i < numOfCity; i++){
			for(int j = i + 1; j < numOfCity; j++){
				this.distanceMap[i][j] = this.distanceMap[j][i];
			}
		}
	}
	// ���Ͽ��� ������ ���ڸ� �ʱ�ȭ
	// ���� ���� = ������ �� ��
	private void setNumOfCity(String filename) {
		this.numOfCity = 0;

		LineNumberReader reader = null;

		try {
			reader = new LineNumberReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			System.exit(1);
		}
		String str = null;
		while (true) {
			try {
				str = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("File read error");
				System.exit(1);
			}
			if (str == null || str.equals(""))
				break;

			this.numOfCity++;
		}
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println("Reader close error");
			System.exit(1);
		}
	}
}
