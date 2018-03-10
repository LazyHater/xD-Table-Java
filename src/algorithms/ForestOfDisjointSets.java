package algorithms;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ForestOfDisjointSets {
	int[] parent;

	ForestOfDisjointSets(int N) {
		parent = new int[N];
		for (int i = 0; i < parent.length; i++)
			parent[i] = i;
	}

	int find(int x) {
		int current = x;
		while (current != parent[current]) {
			current = parent[current];
		}
		return current;
	}

	void Union(int x, int y) {
		int a = find(x);
		int b = find(y);
		parent[a] = b;
	}
	
	ArrayList<ArrayList<Integer>> getGroups(){
		ArrayList<ArrayList<Integer>> groups = new ArrayList<ArrayList<Integer>>();
		HashSet<Integer> groupsIds = new HashSet<Integer>();
		
		//collect unique ids
		for(int i : parent) 
			groupsIds.add(find(i));
		
		for(int id : groupsIds){
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(int i = 0; i < parent.length; i++){
				if(find(i) == id)
					list.add(i);
			}
			groups.add(list);
		}
		return groups;
	}
}