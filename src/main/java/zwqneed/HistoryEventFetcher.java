package zwqneed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utility.MysqlInfo;

public class HistoryEventFetcher {
	public List<Event> getEvents(String full_name, String type) throws Exception{
		List<Event> events = new ArrayList<Event>();
		Connection connection = MysqlInfo.getMysqlConnection();
		String sql = "SELECT * FROM HistoryEvent WHERE full_name = ? and type = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, full_name);
		stmt.setString(2, type);
		
		ResultSet resultSet = stmt.executeQuery();
		while(resultSet.next()){
			String timeString = resultSet.getString("time").substring(0, 19);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time = sdf.parse(timeString);
			String person = resultSet.getString("person");
			String action = resultSet.getString("action");
			
			Event event = new Event(full_name, type, time, person, action);
			events.add(event);
		}
		resultSet.close();
		stmt.close();
		connection.close();
		return events;
	}
}
