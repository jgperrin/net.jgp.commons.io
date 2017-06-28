package net.jgp.commons.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import net.jgp.commons.crypto.MD5;

public class DownloadManager {

	public static String getFilename(String url) {
		return download0(url, null);
	}

	private static String download0(String url, String fileName) {
		URL website;
		try {
			website = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		ReadableByteChannel rbc;
		try {
			rbc = Channels.newChannel(website.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		FileOutputStream fos;
		File f;
		if (fileName == null) {
			fileName = MD5.digest(url);
			f = new File("/Volumes/Pool/" + fileName + ".dl");
		} else {
			f = new File(fileName);
		}
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return fileName + ".dl";
	}

	public static String dowloadUrlAsFilename(String url, String fileName) {
		return download0(url, fileName);
	}

}
