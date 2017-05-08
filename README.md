# Download Manager

## Java based Download Manager Source Code

The Download Manager uses a simple yet effective GUI interface built with Java's Swing libraries. The use of Swing gives the interface a crisp, modern look and feel.

The GUI maintains a list of downloads that are currently being managed. Each download is the lists reports its URL, size of file in bytes, progress as percentage towards completion, and current status. The downloads can each be in one of the following different states:
Downloading, Paused, Complete Error or Cancelled. The GUI also has controls for adding downloads to the list and for changing the state of each download in the list. When a download in the list is selected, depending upon its current state, it can be paused, resumed, cancelled, or removed from the list altogether.

The Download Manager is broken into few classes for natural separation of functional components. These are-

1. Download.java
2. DownloadsTableModel.java
3. ProgressRenderer.java
4. DownloadManager.java

The Download Managerclass is responsible for the GUI interface and makes use of the DownloadsTableModel and ProgressRenderer classes for displaying the current lists of the downloads. The Downloads class represents a "managed" download and is responsible for performing the actula downloading of a file.


