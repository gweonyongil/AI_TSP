package tspUtil;
import java.util.LinkedList;
import java.util.Queue;

public class GetTwoRandomNumber {
	public static Queue q = new LinkedList();
	static int QUEUE_SIZE = 5;
	public static int[] getTwoRandomNumber(){
		int[] arr = new int[2];
		int numOfCity = MapInfo.getInstance().getNumOfCity();
		int firstNum = (int)(Math.random()*1000) % (numOfCity-1) + 1;
		int secondNum = (int)(Math.random()*1000) % (numOfCity-1) + 1;
		
		while(firstNum == secondNum) secondNum = (int)(Math.random()*1000) % (numOfCity-1) + 1;
		
		arr[0] = Math.min(firstNum, secondNum);
		arr[1] = Math.max(firstNum, secondNum);
		
		TwoRandomNumber twoRN = new TwoRandomNumber(arr[0], arr[1]);
		if(q.size() < QUEUE_SIZE){
			q.offer(twoRN);
		}
		else
		{
			if(q.contains(twoRN))
				getTwoRandomNumber();
			else
			{
				q.poll();
				q.offer(twoRN);
			}
		}
		
		return arr;
	}
}
