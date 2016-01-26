package metrics22;

import java.util.List;

public class Driver {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		if (args.length != 2) {
			System.out.println("r u ****ing kidding me?");
			return;
		}
		List<String> files = FileNameReader.getFileNames(args[1]);
		String type = args[0];
		Metric1 metric1=new Metric1();
		String metric1result=metric1.getMetrics1Result(type, files);
		Metrics2 metrics2 = new Metrics2();
		String metrics2Result = metrics2.getMetrics2Result(type, files);
		Metrcis3 metrcis3 = new Metrcis3();
		String metrics3Result = metrcis3.getMetrics2Result(files);
		System.out.print(metric1result+metrics2Result+metrics3Result);
	}

}
