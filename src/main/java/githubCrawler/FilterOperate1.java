package githubCrawler;

public class FilterOperate1 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FilterThread1 filterThread1 = new FilterThread1();
		filterThread1.start();
		
		while(true){
			if(!filterThread1.isAlive()){
				filterThread1 = new FilterThread1();
				filterThread1.start();
			}
		}
	}
}
