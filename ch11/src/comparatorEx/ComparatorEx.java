package comparatorEx;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class ComparatorEx {

	public static void main(String[] args) {
		
		HashSet set = new HashSet();
		String[] strArr = {"cat","Dog","lion","tiger"};
		
		Arrays.sort(strArr);
		System.out.println("strArr ="+Arrays.toString(strArr));
		
		Arrays.sort(strArr, String.CASE_INSENSITIVE_ORDER);
		System.out.println("strArr="+Arrays.toString(strArr));
		
		Arrays.sort(strArr, new Decendig());
		System.out.println("strArr="+Arrays.toString(strArr));
		

	}

}

class Decendig implements Comparator{
	
	public int compare(Object o1, Object o2) {
		if( o1 instanceof Comparable && o2 instanceof Comparable) {
			Comparable c1 = (Comparable)o1;
			Comparable c2 = (Comparable)o2;
			return c1.compareTo(c2)*-1;
			
		}
		return -1;
	}
}