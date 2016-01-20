package metrics22;

import java.util.ArrayList;
import java.util.List;

public class LineIdentifier {
	public static List paserLineEnd(String content){
		List<Integer> lines = new ArrayList<Integer>();
		int length = content.length();
		//check if chatAt i is \n
		for(int i=0; i<length; i++){
			if(content.charAt(i)=='\n'){
				lines.add(i);
			}
		}
		return lines;
	}
}
