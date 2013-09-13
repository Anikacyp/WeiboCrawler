package crawl.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteInfo {
	FileWriter write;
	public WriteInfo()
	{
		String dir=System.getProperty("user.dir")+"\\writeInfo\\userid.txt";
			try {
				write=new FileWriter(new File(dir),false);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public void writefile(String info){
		try {
			write.write(info+"\r\n");
			write.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void closefile(){
		try {
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
