package crawl.common;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;

import crawl.sina.GetSinaInfo;
import crawl.tencent.GetTencentInfo;

public class GetCrawledInfo extends Thread {

	private static Scanner scanner;
	static String username;
	static String psd;
	Thread thread;

	public static void main(String args[]) {
		//System.out.println("Say hello!");
		/*Getbasicinfo();
		GetTencentInfo tencent;
		tencent = new GetTencentInfo(username, psd);
		tencent.getUserInfo();*/
		// GetSinaInfo sina=new GetSinaInfo(username,psd);
		String account="";
		String psd="";
		GetSinaInfo sina=new GetSinaInfo(account,psd);
		// GetSinaInfo sina=new GetSinaInfo("aiqingdabbs@sina.com", "qq1234");
		//sina.GetStarted();
	}

	public static void Getbasicinfo() {
		scanner = new Scanner(System.in);
		System.out.println("UserName:");
		username = scanner.nextLine();
		System.out.println("Password is:");
		psd = scanner.nextLine();
	}
}
