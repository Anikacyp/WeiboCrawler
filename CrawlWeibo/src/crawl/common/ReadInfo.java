package crawl.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadInfo {
	BufferedReader reader=null;
	String dir=System.getProperty("user.dir")+"\\writeInfo\\userid.txt";
	File file=new File(dir);
	public int level;
	public ReadInfo()
	{
	
		try {
			reader=new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Object> getArray()
	{
		ArrayList<Object> arrayList=new ArrayList<Object>();
		String line;
		try {
			level=Integer.parseInt(reader.readLine());
			while((line=reader.readLine())!=null)
			{
				arrayList.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return arrayList;
	}
}
