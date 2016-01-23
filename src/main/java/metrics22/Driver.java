package metrics22;

import java.util.List;

public class Driver {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		args = new String[]{"java","wc"};
		if (args.length != 2) {
			System.out.println("r u ****ing kidding me?");
			return;
		}
		List<String> files = FileNameReader.getFileNames(args[1]);
		String type = args[0];
		Metrics2 metrics2 = new Metrics2();
		String metrics2Result = metrics2.getMetrics2Result(type, files);
		System.out.print(metrics2Result);
	}

}
