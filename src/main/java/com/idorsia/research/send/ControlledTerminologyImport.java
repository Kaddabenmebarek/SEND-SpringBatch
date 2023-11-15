package com.idorsia.research.send;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ControlledTerminologyImport {
	
	private static final String URL = "https://evs.nci.nih.gov/ftp1/CDISC/SEND/Archive/SEND%20Terminology%20";
	private static final String PATH = "sas/inputs/CT/";
	private static final String SOURCEFILENAME = "CDISC_SEND_Terminology.txt";
	//private static final String FINALFILENAME = "SEND_Terminology.csv";
	private static final String FINALFILENAME = "SEND_Terminology.txt";
	private static final int CONNTIMEOUT = 300;
	private static final int READTIMEOUT = 15;
	private static String absolutePath;
	
	public static void main(String[] args) {
		String dateVersion = args[0];
		cleanDirectory();
		downloadAndProcessFile(dateVersion);
		launchBatch();
	}
	
	private static void downloadAndProcessFile(String dateVersion) {
		int status = 0;
		String sourcefile = absolutePath + PATH + SOURCEFILENAME;
		try {
			Path path = new File(sourcefile).toPath();
			URL url = new URL(URL + dateVersion + ".txt");
			status = getSendTerminologyFile(path, url, CONNTIMEOUT, READTIMEOUT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (status == 0) {
			String finalfile = absolutePath + PATH + FINALFILENAME;
			try {				
				formatContent(sourcefile, finalfile, dateVersion);
				System.out.println("import finished");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int getSendTerminologyFile(Path file, URL url, int connectTimeout, int readTimeout)
			throws IOException {
		Files.createDirectories(file.getParent());
		URLConnection conn = url.openConnection();
		if (connectTimeout > 0)
			conn.setConnectTimeout(connectTimeout * 1000);
		if (readTimeout > 0)
			conn.setReadTimeout(readTimeout * 1000);
		int ret = 0;
		boolean lineRead = false;
		try (InputStream is = conn.getInputStream()) {
			try (BufferedInputStream in = new BufferedInputStream(is);
					OutputStream fout = Files.newOutputStream(file)) {
				final byte data[] = new byte[8192];
				int count;
				while ((count = in.read(data)) > 0) {
					fout.write(data, 0, count);
				}
			}
		} catch (java.io.IOException e) {
			int httpcode = 999;
			try {
				httpcode = ((HttpURLConnection) conn).getResponseCode();
			} catch (Exception ee) {
			}
			if (lineRead && e instanceof java.net.SocketTimeoutException)
				ret = 1;
			else if (e instanceof FileNotFoundException && httpcode >= 400 && httpcode < 500)
				ret = 2;
			else if (httpcode >= 400 && httpcode < 600)
				ret = 3;
			else if (e instanceof java.net.SocketTimeoutException)
				ret = 4;
			else if (e instanceof java.net.ConnectException)
				ret = 5;
			else if (e instanceof java.net.UnknownHostException)
				ret = 6;
			else
				throw e;
		}
		return ret;
	}

	private static void formatContent(String inputfile, String outputfile,String dateVersion) throws FileNotFoundException, IOException {
		File sourceFile = new File(inputfile);
		FileReader fr = new FileReader(sourceFile);
		BufferedReader br = new BufferedReader(fr);
		String s = br.readLine();
		File csvFile = new File(outputfile);
		FileWriter fw = new FileWriter(csvFile);
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			boolean firstLineRead = false;
			while (s != null) {
				String sClean = "";
				if(!firstLineRead) {
					s = StringUtils.replace(s, " (Yes/No)", "");
					s = StringUtils.replace(s, "(s)", "");
					s += "\t" + "DateVersion";
				}
				s = s.replace("\t", "|");
				if(firstLineRead) {					
					sClean = s+"|"+dateVersion;
				}else {
					sClean = s;
				}
				firstLineRead = true;
				bw.write(sClean);
				bw.newLine();
				System.out.println(sClean);
				s = br.readLine();
			}
		} finally {
			br.close();
			bw.flush();
			bw.close();
		}
	}
	
	public static void launchBatch() {
		
		String[] springConfig  = {"spring/batch/config/context.xml","spring/batch/config/database.xml","spring/batch/jobs/cTerminologyToDb.xml"};
		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);	
		JobParameters jobParameters = new JobParameters();
		Job jobToLaunch = (Job) context.getBean("cTerminologyToDbJob");
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		try {
			JobExecution ctImportExecution = jobLauncher.run(jobToLaunch, jobParameters);
			System.out.println("CT Import Exit Status : " + ctImportExecution.getStatus());
			if(ctImportExecution.getStatus() == BatchStatus.COMPLETED) {
				cleanDirectory();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Process finished");
	}
	
	private static void cleanDirectory() {
//		try {
			//TODO check path when jar built
			//File folder = new ClassPathResource(PATH).getFile();
			//absolutePath = folder.getPath() + "/";
			String p = "C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\";
			absolutePath = p;
			File folder = new File(p + PATH);
			File[] listFiles = folder.listFiles();
			for (File file : listFiles) {
				file.delete();
			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}
	
}
