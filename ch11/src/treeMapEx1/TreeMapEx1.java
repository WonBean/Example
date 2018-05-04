package treeMapEx1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



public class TreeMapEx1 {

	public static void main(String[] args) {
		String[] data = {"A","K","A","K","D","K","A","K","K","K","Z","D"};//�迭�� ���ڿ��� ����
		TreeMap map = new TreeMap();	//Ʈ���� ����
		
		for(int i=0; i< data.length; i++) {	//�迭ũ�⸸ŭ �ݺ�
			if(map.containsKey(data[i])) {	//Ʈ���ʿ� ������ key(data(i))�� �ִ��� üũ
				Integer value = (Integer)map.get(data[i]);	//key�� ���� ������ ��ü�� ��ȯ
				map.put(data[i], new Integer(value.intValue()+1));	//Ű���� ���� ���� 1�� ���� ������ ��ü�� ����
			}else {
				map.put(data[i], new Integer(1));	//���� ���� Ű�� ������ Ű���� 1�� ����
			}
		}

		
		Iterator it = map.entrySet().iterator();	//map�� key�� value�� ���� �����ϰ� �ֱ� ������ entrySet keySet�� set�� ���ؼ� ���� ���� �� �ִ�.
		
		System.out.println("= �⺻���� =");
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();	//Emtry���� (key,value)�� ��� ���� �̷��� ���� ������, ����ȯ
			int value = ((Integer)entry.getValue()).intValue();
			System.out.println(entry.getKey() + " : " + printBar('#', value) + " " + value);
		}
		System.out.println();
		
		Set set = map.entrySet();
		List list = new ArrayList<>(set);
		
		Collections.sort(list, new ValueComparator());// ������  �����
		
		it = list.iterator();
		
		System.out.println("= ���� ũ�Ⱑ ū ������ ���� =");
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			int value = ((Integer)entry.getValue()).intValue();
			System.out.println(entry.getKey() + " : " + printBar('#', value) + " " + value);
			
		}
		
		
	}

	private static String printBar(char ch, int value) {
		char[] bar = new char[value];
		
		for(int i=0; i< bar.length;i++) {
			bar[i]=ch;		
			}
		return new String(bar);
	}

	static class ValueComparator implements Comparator{	//����� ���� ������ ���� ���!

		@Override
		public int compare(Object o1, Object o2) {
			if(o1 instanceof Map.Entry && o2 instanceof Map.Entry ) {	//��ȿ��üũ
				Map.Entry e1 = (Map.Entry)o1;
				Map.Entry e2 = (Map.Entry)o2;
				
				int v1 = ((Integer)e1.getValue()).intValue();
				int v2 = ((Integer)e2.getValue()).intValue();
				
				return v2 - v1;	//���� v2�� ũ�� ��� ������ ����
			}
			return -1;
		}
		
	}
}