package net.jgp.commons.io.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import net.jgp.commons.crypto.MD5;
import net.jgp.commons.log.Logger;
import net.jgp.commons.log.LoggerFactory;

public class DownloadManager {
	private static final Logger log = LoggerFactory.getLogger(DownloadManager.class);

	public static String getFilename(String url) {
		return download0(url, null);
	}

	private static String download0(String url, String fileName) {
		URL website;
		try {
			website = new URL(url);
		} catch (MalformedURLException e) {
			log.error("Error: {}, could not download file", e.getMessage(), e);
			return null;
		}
		ReadableByteChannel rbc;
		try {
			rbc = Channels.newChannel(website.openStream());
		} catch (IOException e) {
			log.error("Error: {}, could not download file", e.getMessage(), e);
			return null;
		}
		FileOutputStream fos;
		File f;
		if (fileName == null) {
			fileName = MD5.digest(url);
			f = new File("/tmp/" + fileName + ".dl");
		} else {
			f = new File(fileName);
		}
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			log.error("File {} not found: {}", f.getName(), e.getMessage(), e);
			return null;
		}
		try {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (IOException e) {
			log.error("I/O exception while copying from stream: {}", e.getMessage(), e);
			return null;
		}
		try {
			fos.close();
		} catch (IOException e) {
			log.warning("I/O exception while cloing file {} not found: {}", f.getName(), e.getMessage(), e);
		}
		try {
			rbc.close();
		} catch (IOException e) {
			log.warning("I/O exception while cloing the stream from stream: {}", e.getMessage(), e);
		}
		return fileName + ".dl";
	}

	public static String dowloadUrlAsFilename(String url, String fileName) {
		return download0(url, fileName);
	}

}
