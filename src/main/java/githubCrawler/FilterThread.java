package githubCrawler;

public class FilterThread extends Thread {

	@Override
	public void run(){
		super.run();
		RepoFilter repoFilter = new RepoFilter();
		repoFilter.main(null);
	}
}
