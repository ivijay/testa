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
import fnmatch

host = "localhost"					# IP address of the job history server
port = "8088"						# port
version = "v1"						# version
resourcePath = "cluster/apps"		# resourcepath

#url of the REST API
url = "http://" + host + ":" + port + "/" + "ws" + "/" + version + "/" + resourcePath + "/" 
u = urllib.urlopen(url)

#print("start")

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
#		print("success")
		response = data["apps"]["app"]
		for i in range(0,len(response)):
			#
			#wild search on application name
			#
			if fnmatch.fnmatch(response[i]["name"], '*word*'):
				print(response[i]["id"] +"\t" + response[i]["name"] +"\t" + response[i]["finalStatus"] + "\t" + str(response[i]["elapsedTime"]))
				#print(response[i]["elapsedTime"])
				#
				#Here nagios plugin can be called for each application ID, to display its staus in console.
				#
	elif RestResponse == "HTTPError":
		response = "error"
		print("error")
	else:
		progress = 120
		state = "failure"
		finalStatus = "Unknown error"
		print("failure")

except Exception, e:
	print(e)

