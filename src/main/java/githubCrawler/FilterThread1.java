package githubCrawler;

public class FilterThread1 extends Thread {
	@Override
	public void run(){
		super.run();
		RepoFilter1 repoFilter1 = new RepoFilter1();
		repoFilter1.main(null);
	}
}
