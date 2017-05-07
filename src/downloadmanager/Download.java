package downloadmanager;

import java.io.*;
import java.net.*;
import java.util.*;

//This class downloads a file from a URL
class Download extends Observable implements Runnable {
	// Max size of the download buffer
	private static final int MAX_BUFFER_SIZE = 1024;

	// Status names
	public static final String STATUSES[] = { "Downloading", "Paused", "Complete", "Cancelled", "Error" };

	// Status codes
	public static final int DOWNLOADING = 0;
	public static final int PAUSED = 1;
	public static final int COMPLETE = 2;
	public static final int CANCELLED = 3;
	public static final int ERROR = 4;

	private URL url; // Download URL
	private int size; // Size of the downloads in bytes
	private int downloaded; // number of bytes downloaded
	private int status; // Current status of download

	// Constructor for download
	public Download(URL url) {
		this.url = url;
		size = -1;
		downloaded = 0;
		status = DOWNLOADING;

		// Begin the Download
		download();
	}

	// Get this download's url
	public String getUrl() {
		return url.toString();
	}

	// Get this download size
	public int getSize() {
		return size;
	}

	// Get this download's progress
	public float getProgress() {
		return ((float) downloaded / size) * 100;
	}

	// Get the downloads status
	public int getStatus() {
		return status;
	}

	// Pause this download
	public void pause() {
		status = PAUSED;
		stateChanged();
	}

	// Resume this download
	public void resume() {
		status = DOWNLOADING;
		stateChanged();
		download();
	}

	// Cancel this download
	public void cancel() {
		status = CANCELLED;
		stateChanged();
	}

	// Mark this download as having an error
	private void error() {
		status = ERROR;
		stateChanged();
	}

	// Start or resume downloading
	private void download() {
		Thread thread = new Thread(this);
		thread.start();
	}

	// Get file name portion of URL
	private String getFileName(URL url) {
		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	// Download file.
	public void run() {
		RandomAccessFile file = null;
		InputStream stream = null;

		try {
			// open connection to URL
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// specify which portion of the file to download.
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

			// connect to server
			connection.connect();

			// make sure response code is in the 200 range.
			if (connection.getResponseCode() / 100 != 2)
				error();

			// check for valid content length
			int contentLength = connection.getContentLength();
			if (contentLength < 1)
				error();

			// set the size of the download if it hasn't been already set
			if (size == -1) {
				size = contentLength;
				stateChanged();
			}

			// open file and seek to the end of it
			file = new RandomAccessFile(getFileName(url), "rw");
			file.seek(downloaded);

			stream = connection.getInputStream();

			while (status == DOWNLOADING) {
				// size buffer according to how much of the file is left to
				// download

				byte buffer[];
				if (size - downloaded > MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}

				// read from server into buffer
				int read = stream.read(buffer);
				if (read == -1)
					break;

				// write buffer to file.
				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}

			// change status to complete if this point was reached because
			// downloading has finshed
			if (status == DOWNLOADING) {
				status = COMPLETE;
				stateChanged();
			}
		} catch (Exception e) {
			error();
		} finally {
			// close file
			if (file != null) {
				try {
					file.close();
				} catch (Exception e) {
				}
			}

			// close connection to server
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
				}
			}
		}
	}

	// notify observers that this download's status has changed.
	private void stateChanged() {
		setChanged();
		notifyObservers();
	}
}
