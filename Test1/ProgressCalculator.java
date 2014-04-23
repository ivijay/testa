package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;

public class ProgressCalculator {
 
//	http://{http address of service}/ws/{version}/{resourcepath}
	private String host;
	private String port;
	private String version;
	private String resourcePath;
	private String Message;
	private int progress;
	
	public ProgressCalculator(String host, String port, String version,
			String resourcePath) {
		super();
		this.host = host;
		this.port = port;
		this.version = version;
		this.resourcePath = resourcePath;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String progressMessage) {
		this.Message = progressMessage;
	}

	public void calculateProgress(String AppID) {
 
		String applicationURL = "http://" + host + ":" + port + "/" + "ws" + "/" + version + "/" + resourcePath + "/" + AppID; 
		
		try {
 
		URL url = new URL(applicationURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
 
		switch(conn.getResponseCode()) {
		
		case 200:
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				ResponseData s = new Gson().fromJson(br,ResponseData.class);
				
//				System.out.println(s);
				
				setProgress(s.app.getProgress());
				setMessage(s.app.toString());
				
				break;
		case 404:
			
			setProgress(-1);
			setMessage("Application AppID not found");
				break;
		default:
		
			setProgress(-99);
			setMessage("Unknown error");
			break;
		}
 
		 
		conn.disconnect();
 
	  } catch (MalformedURLException e) {
 
		  setProgress(-99);
		e.printStackTrace();
 
	  } catch (IOException e) {
 
		  setProgress(-99);
		e.printStackTrace();
 
	  }
 
	}
 
}

class ResponseData {
	App app;

	class App {
		private String id;
		private String user;
		private String name;
		private String queue;
		private String state;
		private String finalStatus;
		private int progress;
		private String trackingUI;
		private String trackingUrl;
		private String diagnostics;
		private long clusterId;
		private String applicationType; 
		private long startedTime;
		private long finishedTime;
		private long elapsedTime;
		private String amContainerLogs;
		private String amHostHttpAddress;
		
		public App(String id2) {
			this.id = id2;
		}

		public App() {
			// TODO Auto-generated constructor stub
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getQueue() {
			return queue;
		}

		public void setQueue(String queue) {
			this.queue = queue;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getFinalStatus() {
			return finalStatus;
		}

		public void setFinalStatus(String finalStatus) {
			this.finalStatus = finalStatus;
		}

		public int getProgress() {
			return progress;
		}

		public void setProgress(int progress) {
			this.progress = progress;
		}

		public String getTrackingUI() {
			return trackingUI;
		}

		public void setTrackingUI(String trackingUI) {
			this.trackingUI = trackingUI;
		}

		public String getDiagnostics() {
			return diagnostics;
		}

		public void setDiagnostics(String diagnostics) {
			this.diagnostics = diagnostics;
		}

		public long getClusterId() {
			return clusterId;
		}

		public void setClusterId(long clusterId) {
			this.clusterId = clusterId;
		}

		public long getStartedTime() {
			return startedTime;
		}

		public void setStartedTime(long startedTime) {
			this.startedTime = startedTime;
		}

		public long getFinishedTime() {
			return finishedTime;
		}

		public void setFinishedTime(long finishedTime) {
			this.finishedTime = finishedTime;
		}

		public long getElapsedTime() {
			return elapsedTime;
		}

		public void setElapsedTime(long elapsedTime) {
			this.elapsedTime = elapsedTime;
		}

		public String getAmContainerLogs() {
			return amContainerLogs;
		}

		public void setAmContainerLogs(String amContainerLogs) {
			this.amContainerLogs = amContainerLogs;
		}

		public String getAmHostHttpAddress() {
			return amHostHttpAddress;
		}

		public void setAmHostHttpAddress(String amHostHttpAddress) {
			this.amHostHttpAddress = amHostHttpAddress;
		} /* (non-Javadoc) * @see java.lang.Object#toString() */

		public String getTrackingUrl() {
			return trackingUrl;
		}

		public void setTrackingUrl(String trackingUrl) {
			this.trackingUrl = trackingUrl;
		}

		public String getApplicationType() {
			return applicationType;
		}

		public void setApplicationType(String applicationType) {
			this.applicationType = applicationType;
		}

		@Override
		public String toString() {
			return "Current status of application " + id + " is " + state
					+ " with progress " + progress + "%";
		}
	}
	
	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
	
	public String toString() {
		
		return app.toString();
	}
}


