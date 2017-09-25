package tspUtil;

public class TwoRandomNumber {
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		TwoRandomNumber x = (TwoRandomNumber)obj;
		if(this.getNum1() == x.getNum1() &&
				this.getNum2() == x.getNum2())
		{
			return true;
		}
		else
			return false;
	}

	int num1;
	int num2;

	public TwoRandomNumber(int num1, int num2) {
		this.num1 = num1;
		this.num2 = num2;
	}

	public int getNum1() {
		return num1;
	}

	public int getNum2() {
		return num2;
	}
}
