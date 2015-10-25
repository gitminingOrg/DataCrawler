package historyDownload;

import java.io.File;

public class HistoryDriver {
	public static void main(String[] args){
		HistoryDriver historyDriver = new HistoryDriver();
		String start = "2015-01-01-0";
		String end = "2015-01-02-0";
		historyDriver.filterData(start, end);
	}
	
	public void filterData(String start, String end){
		HistoryFilter.init();
		DownloadFile downloadFile = new DownloadFile();
		DecompressFile decompressFile = new DecompressFile();
		String hour = start;
		while(!hour.equals(end)){
			String url = "http://data.githubarchive.org/"+hour+".json.gz";
			String gzoutput = "/Users/owenchen/Documents/javaTmpFile/"+hour+".json.gz";
			String jsonoutput = "/Users/owenchen/Documents/javaTmpFile/"+hour+".json";
			boolean download = downloadFile.download(url, gzoutput);
			if(!download){
				System.err.println("下载失败"+gzoutput);
				continue;
			}
			boolean decompress = decompressFile.decompress(gzoutput);
			if(!decompress){
				System.err.println("解压失败"+gzoutput);
				break;
			}
			File gzFile = new File(gzoutput);
			File jsonFile = new File(jsonoutput);
			HistoryFilter.hashFilter(jsonFile);
			gzFile.delete();
			jsonFile.delete();
			hour=getNextHour(hour);
		}
		
	}
	
	public static String getNextHour(String input){
		String result= "";
		String[] monthName= {"00","01","02","03","04","05","06","07","08","09","10","11","12"};
		String[] dayName= {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		int[] days = {0,31,28,31,30,31,30,31,31,30,31,30,31};
		
		String[] items = input.split("-");
		int year = Integer.parseInt(items[0]);
		int month = Integer.parseInt(items[1]);
		int day = Integer.parseInt(items[2]);
		int hour = Integer.parseInt(items[3]);
		
		//February 29 days
		if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
			days[2] = 29;
		}
		
		hour++;
		if(hour > 23){
			hour = hour - 24;
			day++;
		}
		if(day > days[month]){
			day = 1;
			month++;
		}
		if(month > 12){
			month = 1;
			year++;
		}
		
		result = year+"-"+monthName[month]+"-"+dayName[day]+"-"+hour;
		return result;
	}
}
