package net.jgp.commons.io.download;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

import net.jgp.commons.log.Logger;
import net.jgp.commons.log.LoggerFactory;

public class DownloadManagerTest {
	private static final Logger log = LoggerFactory.getLogger(DownloadManagerTest.class);

	@Test
	public void testSmallFile() {
		String f = DownloadManager.getFilename("http://jgp.net/1.txt");
		if (f == null) {
			assert (false);
		}
	}

	@Test
	public void testBigFile() {
		log.debug("Starting download of big file");
		String filename = DownloadManager.getFilename(
				"https://opendurham.nc.gov/explore/dataset/north-carolina-school-performance-data/download/?format=json&timezone=America/New_York");
		if (filename == null) {
			fail("Download manager did not return a filename");
			return;
		}
		log.debug("Download returned: [{}]", filename);

		File f = new File("/tmp/" + filename);
		if (!f.exists()) {
			fail("File does not exist");
			return;
		}

		if (f.length() == 0) {
			fail("Download failed");
		}
	}

}
