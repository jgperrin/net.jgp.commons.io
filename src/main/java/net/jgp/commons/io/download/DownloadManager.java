package net.jgp.commons.io.download;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

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
			log.error("Error: {}, URL is not valid", e.getMessage(), e);
			return null;
		}

		File f;
		if (fileName == null) {
			fileName = MD5.digest(url);
			f = new File("/tmp/" + fileName + ".dl");
		} else {
			f = new File(fileName);
		}

		try {
			FileUtils.copyURLToFile(website, f);
		} catch (IOException e) {
			log.error("Error: {}, could not download file", e.getMessage(), e);
			return null;
		}

		return fileName + ".dl";
	}

	public static String dowloadUrlAsFilename(String url, String fileName) {
		return download0(url, fileName);
	}

}
