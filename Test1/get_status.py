import urllib2
import json
import urllib

# Current timestamp
#timestamp = datetime.datetime.fromtimestamp(time.time()).strftime('%Y%m%d %H:%M:%S')
#headers = {'Accept': 'application/json>'}
           
#url = 'http://localhost:8088/ws/v1/cluster/apps/application_1398362840864_0001'

appID="application_1398362840864_0001"
url = 'http://localhost:8088/ws/v1/cluster/apps/' + appID
u = urllib.urlopen(url)

try:
	resp = urllib2.urlopen(url).read()
	data = json.loads(resp.decode('utf8'))
	RestResponse = "success"
except urllib2.HTTPError, e:
	#print "HTTP error: %d" % e.code
	RestResponse = "HTTPError"

if RestResponse == "success":
		progress = data["app"]["progress"]
		state = data["app"]["state"]
		finalStatus = data["app"]["finalStatus"]
elif RestResponse == "HTTPError":
		progress = 110
		state = "failure"
		finalStatus = "application ID not found"
else:
		progress = 120
		state = "failure"
		finalStatus = "Unknown error"
	
print(progress)
print(state)
print(finalStatus)

