import sys
from optparse import OptionParser

# Exit statuses recognized by Nagios
UNKNOWN = -1
OK = 0
WARNING = 1
CRITICAL = 2
#######################################################################################################
#  Python script to retrive the all YARN application status
#  
# Configuration:
# Current verion of YARN REST API is v1
# update the host, port in the below script
# the job state - valid values are: NEW, INITED, RUNNING, SUCCEEDED, FAILED, KILL_WAIT, KILLED, ERROR
# the elapsedTime - The elapsed time since the application started (in ms)
#######################################################################################################

def getAppStatus(host,port,appname,thresholdTime): # return YARN applications' status
    import urllib2
    import json
    import urllib
    import fnmatch
    import subprocess
    import os
    
    version = "v1"                        # version
    resourcePath = "cluster/apps"        # resourcepath
    
    tempmsg = ''
    hrc = 0
    rc=0
    count=0
    
    #REST API url http://{http address of service}/ws/{version}/{resourcepath}
#     url = "http://" + host + ":" + port + "/" + "ws" + "/" + version + "/" + resourcePath + "/"
    
    try:
#         u = urllib.urlopen(url)
#         resp = urllib2.urlopen(url).read()              #call rest api
        resp = '{"apps":{"app":[{"id":"application_1399483006167_0002","user":"vijay","name":"word count","queue":"default","state":"RUNNING","finalStatus":"UNDEFINED","progress":5.0,"trackingUI":"ApplicationMaster","trackingUrl":"http://vijay:8088/proxy/application_1399483006167_0002/","diagnostics":"","clusterId":1399483006167,"applicationType":"MAPREDUCE","startedTime":1399483178215,"finishedTime":0,"elapsedTime":5514,"amContainerLogs":"http://vijay:8042/node/containerlogs/container_1399483006167_0002_01_000001/vijay","amHostHttpAddress":"vijay:8042"},{"id":"application_1399483006167_0001","user":"vijay","name":"word mean","queue":"default","state":"RUNNING","finalStatus":"SUCCEEDED","progress":100.0,"trackingUI":"History","trackingUrl":"http://vijay:8088/proxy/application_1399483006167_0001/jobhistory/job/job_1399483006167_0001","diagnostics":"","clusterId":1399483006167,"applicationType":"MAPREDUCE","startedTime":1399483128530,"finishedTime":1399483146578,"elapsedTime":18048,"amContainerLogs":"http://vijay:8042/node/containerlogs/container_1399483006167_0001_01_000001/vijay","amHostHttpAddress":"vijay:8042"}]}}'
        data = json.loads(resp.decode('utf8'))          # parse json
        RestResponse = "success"
    except urllib2.HTTPError, e:
        #print "HTTP error: %d" % e.code
        RestResponse = "HTTPError"
    except Exception, e:
        RestResponse = "failure"
         
    # print(RestResponse)
    try:
        if RestResponse == "success":
    #        print("success")
            response = data["apps"]["app"]
            WS_int_thresholdTime = int(thresholdTime)
            for i in range(0,len(response)):
                #
                #wild search on application name
                #
#                 if fnmatch.fnmatch(response[i]["name"], appname):
                if response[i]["name"] == appname:
                    if response[i]["state"] == "RUNNING":
                        count = count + 1
                        if response[i]["elapsedTime"] > WS_int_thresholdTime:
                            tempmsg = tempmsg + '\n'+ "WARNING - "+ response[i]["id"] +"\t" + response[i]["name"] + "\t" + response[i]["state"] + "\t" + str(response[i]["elapsedTime"])
                            rc = WARNING
                            if rc > hrc:
                                hrc = rc
                        else:
                            tempmsg = tempmsg + '\n'+ "OK - "+ response[i]["id"] +"\t" + response[i]["name"] + "\t" + response[i]["state"] + "\t" + str(response[i]["elapsedTime"])
                            rc = OK
                            if rc > hrc:
                                hrc = rc
                    elif ((response[i]["state"] == "FAILED") or (response[i]["state"] == "KILLED")):
                        tempmsg = tempmsg + '\n'+ "CRITICAL - "+ response[i]["id"] +"\t" + response[i]["name"] + "\t" + response[i]["finalStatus"] + "\t" + str(response[i]["elapsedTime"])
                        rc = CRITICAL
                        if rc > hrc:
                            hrc = rc
            result = {'hrc': hrc, 'msg': tempmsg,'count': count}
    #         print(result)
        elif RestResponse == "HTTPError":
            response = "error"
            hrc = 3
            tempmsg = "HTTP Error - Please check REST API connectivity"
            result = {'hrc': hrc, 'msg': tempmsg,'count': count}
    #         print("error")
        else:
            state = "failure"
            finalStatus = "Unknown error"
            hrc = 3
            tempmsg = "Unknown response from REST API"
            result = {'hrc': hrc, 'msg': tempmsg,'count': count}
    #         print("failure")
    except Exception, e:
        print(e)
        hrc = 3
        tempmsg = "Unknown error in parsing REST API response"
        result = {'hrc': hrc, 'msg': tempmsg,'count': count}
        
#     print result
    return result;

#############################

parser = OptionParser()
parser.add_option('-H', '--hostname', dest='hostname')
parser.add_option('-P', '--port', dest='port')
parser.add_option('-T', '--thresholdTime', dest='thresholdTime')
parser.add_option('-A', '--appname', dest='appname')

parser.add_option('-v', '--verbose', dest='verbose', action='store_true',
    default=False)
parser.add_option('-q', '--quiet', dest='verbose', action='store_false')

options, args = parser.parse_args()

# Check for required options
for option in ('hostname', 'port','appname'):
    if not getattr(options, option):
        print 'CRITICAL - %s not specified' % option.capitalize()
        raise SystemExit, CRITICAL
    
if getattr(options, 'thresholdTime'):
    WS_thresholdTime = options.thresholdTime
else:
    WS_thresholdTime = 0
    
result = getAppStatus(options.hostname,options.port,options.appname,WS_thresholdTime)
hrc = result['hrc']
msg = result['msg']
count = result['count']

# hrc=0
# msg=''
# count=0

if count == 0 and hrc == 0:
    heading = 'WARNING - Currently there are no ' + options.appname + ' applications running'
    hrc = 1
elif count > 0 and hrc == 0:
    heading = "OK - All "  + options.appname + " applications are running fine"
elif count > 0 and hrc == 1:
    heading = "WARNING - At least one of the following application has reached the Threshold elapsed time"

if hrc == 0:
    print heading
    print msg
    sys.exit(0)
elif hrc == 1:
    print heading
    print msg
    sys.exit(1)
elif hrc == 2:
    print "At least one of the following application has failed "
    print msg
    sys.exit(2)
elif hrc == 3:
    print "CRITICAL - UKNOWN error.."
    print msg
    sys.exit(3)
else:
    print "CRITICAL - UKNOWN error.." 
    sys.exit(3)
