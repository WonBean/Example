package Exercise6_24;

public class Exercise6_24 {
	public static void main(String[] args) {
		int value = 5;
		System.out.println(value + "�� ���밪:" + abs(value));
		value = -10;
		System.out.println(value + "�� ���밪:" + abs(value));
	}

	private static int abs(int value) {
		return value<0 ? -(value) : value;
	}
}