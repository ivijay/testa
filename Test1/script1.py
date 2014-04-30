#######################################################################################################
#  Python script to retrive the all YARN application status
#  
# Configuration:
# Current verion of YARN REST API is v1
# update the host, port in the below script
#######################################################################################################

import urllib2
import json
import urllib

host = "localhost"					# IP address of the job history server
port = "8088"						# port
version = "v1"						# version
resourcePath = "cluster/apps"		# resourcepath

#url = "http://" + host + ":" + port + "/" + "ws" + "/" + version + "/" + resourcePath + "/" + appID
url = "http://" + host + ":" + port + "/" + "ws" + "/" + version + "/" + resourcePath + "/" 
u = urllib.urlopen(url)

print("start")

try:
	resp = urllib2.urlopen(url).read()
	data = json.loads(resp.decode('utf8'))
	RestResponse = "success"
except urllib2.HTTPError, e:
	#print "HTTP error: %d" % e.code
	RestResponse = "HTTPError"
	
print(RestResponse)
try:
	if RestResponse == "success":
		print("success")
		#response = data["apps"]["app"][1]["id"]
		response = data["apps"]["app"]
		#print(id)
		for i in range(0,len(response)):
			if((response[i]["name"].find("storm")) > -1):
				print(response[i]["id"] +"\t" + response[i]["name"] +"\t" + response[i]["finalStatus"])
	elif RestResponse == "HTTPError":
		response = "error"
		print("error")
	else:
		progress = 120
		state = "failure"
		finalStatus = "Unknown error"
		print("failure")

except Exception, e:
	print("failure")

	
	#print(progress)
	#print(state)
	#print(finalStatus)
	
	#return progress
