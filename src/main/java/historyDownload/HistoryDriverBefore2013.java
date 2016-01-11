package historyDownload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class HistoryDriverBefore2013 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		HistoryDriverBefore2013 historyDriver = new HistoryDriverBefore2013();
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("config.properties").getPath();
		properties.load(new FileInputStream(new File(path)));
		String start = properties.getProperty("date.start");
		String end = properties.getProperty("date.end");
		historyDriver.filterData(start, end);
	}

	public void filterData(String start, String end) {
		String hour =start;
		try {
			Properties properties = new Properties();
			String path = Thread.currentThread().getContextClassLoader()
					.getResource("config.properties").getPath();
			properties.load(new FileInputStream(new File(path)));
			String tmpFilePlace = properties.getProperty("tmp.file.place");
			//tmpFilePlace = "~/Desktop/";
			HistoryFilterBefore2013.init();
			DownloadFile downloadFile = new DownloadFile();
			DecompressFile decompressFile = new DecompressFile();
			while (!hour.equals(end)) {
				System.out.println("aa");
				try{
					if (HistoryFilterBefore2013.validate(hour)) {
						String url = "http://data.githubarchive.org/" + hour
								+ ".json.gz";
						String gzoutput = tmpFilePlace+ hour + ".json.gz";
						String jsonoutput = tmpFilePlace+ hour + ".json";
						File gzFile = new File(gzoutput);
						File jsonFile = new File(jsonoutput);
						if(jsonFile.exists()){
							HistoryFilterBefore2013.hashFilter(jsonFile, hour);
						}else if(gzFile.exists()){
							boolean decompress = decompressFile.decompress(gzoutput);
							if (!decompress) {
								System.err.println("解压失败" + gzoutput);
								hour = getNextHour(hour);
								continue;
							}
							HistoryFilterBefore2013.hashFilter(jsonFile, hour);
							jsonFile.delete();
						}else{
							System.err.println(gzoutput+"数据缺失");
						}
					}					
				}catch(Exception e){
					try {
						FileWriter fw = new FileWriter("history log",true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(hour+"\n");
						bw.flush();
						bw.close();
						fw.close();
						e.printStackTrace();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}					
				}

				hour = getNextHour(hour);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getNextHour(String input) {
		String result = "";
		String[] monthName = { "00", "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
		String[] dayName = { "00", "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31" };
		int[] days = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		String[] items = input.split("-");
		int year = Integer.parseInt(items[0]);
		int month = Integer.parseInt(items[1]);
		int day = Integer.parseInt(items[2]);
		int hour = Integer.parseInt(items[3]);

		// February 29 days
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			days[2] = 29;
		}

		hour++;
		if (hour > 23) {
			hour = hour - 24;
			day++;
		}
		if (day > days[month]) {
			day = 1;
			month++;
		}
		if (month > 12) {
			month = 1;
			year++;
		}

		result = year + "-" + monthName[month] + "-" + dayName[day] + "-"
				+ hour;
		return result;
	}
}
