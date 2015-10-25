package historyDownload;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class DecompressFile {

	public boolean decompress(String inFileName) {
		try {   
			//judge if a *.gz file
			if (!getExtension(inFileName).equalsIgnoreCase("gz")) {   
                System.err.println("File name must have extension of \".gz\"");   
                System.exit(1);   
            }   

            System.out.println("Opening the compressed file.");  
            
            //init gzstream, fos
            GZIPInputStream in = null;   
            try {   
                in = new GZIPInputStream(new FileInputStream(inFileName));   
            } catch(FileNotFoundException e) {   
                System.err.println("File not found. " + inFileName);     
            }   
  
            System.out.println("Open the output file.");   
            String outFileName = getFileName(inFileName);   
            FileOutputStream out = null;   
           try {   
                out = new FileOutputStream(outFileName);   
            } catch (FileNotFoundException e) {   
                System.err.println("Could not write to file. " + outFileName);   
                System.exit(1);   
            }   
  
            System.out.println("Transfering bytes from compressed file to" +outFileName);   
            byte[] buf = new byte[1024];   
            int len;   
            try{
                while((len = in.read(buf)) > 0) {   
                    out.write(buf, 0, len);   
                } 	
            }catch(NullPointerException e){
            	
            }
  
            System.out.println("success decompress");   
            in.close();   
            out.close();   
        } catch (IOException e) {   
            e.printStackTrace();  
			try {
				FileWriter fw = new FileWriter("history log",true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(inFileName+"\n");
				bw.flush();
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
            return false;
        }
		return true;   
	}
	
	public static String getFileName(String f) {   
        String fname = "";   
        int i = f.lastIndexOf('.');   
  
        if (i > 0 &&  i < f.length() - 1) {   
            fname = f.substring(0,i);   
        }        
        return fname;   
    }  
	
    public static String getExtension(String f) {   
        String ext = "";   
        int i = f.lastIndexOf('.');   
  
        if (i > 0 &&  i < f.length() - 1) {   
            ext = f.substring(i+1);   
        }        
        return ext;   
    }  
}
