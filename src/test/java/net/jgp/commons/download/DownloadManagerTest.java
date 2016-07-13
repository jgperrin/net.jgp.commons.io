package net.jgp.downloadproxy;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class DownloadManagerTest {

	@Test
	public void testSmallFile() {
		String f = DownloadManager.getFilename("http://jgp.net/1.txt");
		if (f == null) {
			assert (false);
		}
	}

	@Test
	public void testBigFile() {
		String f = DownloadManager.getFilename("https://opendurham.nc.gov/explore/dataset/north-carolina-school-performance-data/download/?format=json&timezone=America/New_York");
		if (f == null) {
			assert (false);
		}
	}

}
