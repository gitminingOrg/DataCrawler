package analysis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import userInfoFetch.UserDeal;

public class TestUser {
	private static Log log = LogFactory.getLog(TestUser.class.getName());
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserDeal userDeal = new UserDeal();
		userDeal.fetchUser("pjhyett", 3);
		log.debug("interesting");
		
	}

}
