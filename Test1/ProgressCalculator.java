package json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//import json.ResponseData.App;

import com.google.gson.Gson;

public class ParseJson {

	public static void main(String[] args) throws IOException {
		
				String result = new String(Files.readAllBytes(Paths
						.get("C:\\Users\\463683\\eclipse\\YARN-test\\src\\result.txt")));

		System.out.println(result);
		ResponseData s = new Gson().fromJson(result, ResponseData.class);
		
		System.out.println(s);
		
		System.out.println("completion: " + s.app.getProgress());

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
