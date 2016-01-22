package githubCrawler;

public class FilterOperate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FilterThread filterThread = new FilterThread();
		filterThread.start();
		
		while(true){
			if(!filterThread.isAlive()){
				filterThread = new FilterThread();
				filterThread.start();
			}
		}
	}

}
