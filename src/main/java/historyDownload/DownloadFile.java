package historyDownload;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFile {
	/**
	 * make sure if file exists
	 * 
	 * @param pathAndFile
	 * @return
	 */
	private boolean fileExist(String pathAndFile) {
		File file = new File(pathAndFile);
		if (file.exists())
			return true;
		else
			return false;
	}

	// file downloaded size
	private long fileSize(String pathAndFile) {
		File file = new File(pathAndFile);
		return file.length();
	}

	// change file name ,remove .tp
	private void fileRename(String fName, String nName) {
		File file = new File(fName);
		file.renameTo(new File(nName));
		file.delete();
	}

	public boolean download(String urlString, String fileName) {
		// init parameters
		URL url = null;
		HttpURLConnection urlc = null;
		DataOutputStream dos = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;

		// where to store
		String localFile = fileName;

		// when uncompleted .tp
		String localFile_bak = localFile + ".tp";

		long fileSize = 0;
		long start = System.currentTimeMillis();
		int len = 0;
		byte[] bt = new byte[1024];
		RandomAccessFile raFile = null;

		// file total size
		long TotalSize = 0;
		try {
			url = new URL(urlString);
			urlc = (HttpURLConnection) url.openConnection();
			TotalSize = Long.parseLong(urlc.getHeaderField("Content-Length"));
			System.out.println("file size:" + TotalSize);
			// urlc.disconnect();//先断开，下面再连接，否则下面会报已经连接的错误
			urlc = (HttpURLConnection) url.openConnection();
			// if file exists and name is .tp, means download uncompleted,
			// continue
			if (fileExist(localFile_bak)) {
				System.out.println("文件续传中...");
				// get uncomplete file size
				fileSize = fileSize(localFile_bak);
				System.out.println("fileSize:" + fileSize);
				// User-Agent
				// urlc.setRequestProperty("User-Agent","NetFox");
				// set continue place
				urlc.setRequestProperty("RANGE", "bytes=" + fileSize + "-");
				// urlc.setRequestProperty("RANGE",
				// "bytes="+fileSize);//这样写不行，不能少了这个"-".
				// set receive info
				urlc.setRequestProperty("Accept",
						"image/gif,image/x-xbitmap,application/msword,*/*");
				raFile = new RandomAccessFile(localFile_bak, "rw");// 随机方位读取
				raFile.seek(fileSize);// 定位指针到fileSize位置
				bis = new BufferedInputStream(urlc.getInputStream());
				while ((len = bis.read(bt)) > 0) {// 循环获取文件
					raFile.write(bt, 0, len);
				}
				System.out.println("file continue finished！");
			} else {
				// first time download , .tp
				fos = new FileOutputStream(localFile_bak);
				dos = new DataOutputStream(fos);
				bis = new BufferedInputStream(urlc.getInputStream());
				System.out.println("receiving file first time...");
				int test = 0;
				// fetch file
				while ((len = bis.read(bt)) > 0) {
					dos.write(bt, 0, len);
					test++;
				}
			}
			System.out.println("total time："
					+ (System.currentTimeMillis() - start) / 1000 + "s");
			if (bis != null)
				bis.close();
			if (dos != null)
				dos.close();
			if (fos != null)
				fos.close();
			if (raFile != null)
				raFile.close();
			System.out.println("localFile_bak:" + fileSize(localFile_bak));
			// complete & rename
			if (fileSize(localFile_bak) == TotalSize) {
				fileRename(localFile_bak, localFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
