package metrics22;

public class Metrcis2 {
	public void singleLen(String content){
		int lineCount=0;
		int spaceCount=0;
		int senCount=0;
		
		boolean quote = false; 
		int length = content.length();
		for(int i=0; i<length; i++){
			if(content.charAt(i) == '"'){
				quote=quote?true:false;
			}
			
			if(!quote){
				if(content.charAt(i) == '\n'){
					lineCount++;
				}
				if(content.charAt(i) == ' '){
					spaceCount++;
				}
				if(content.charAt(i) == ';'){
					senCount++;
				}
			}
		}
	}
}
