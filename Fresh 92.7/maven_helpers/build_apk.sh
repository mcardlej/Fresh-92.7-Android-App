#!/bin/bash
command mvn -version > /dev/null 2>&1 && echo "Yay, we found maven, continuing..." || { 
	echo "ERROR: We can't find the 'mvn' command so we have nfi where maven is. Check out these instructions and then come back and try again: http://maven.apache.org/download.html#Unix-based_Operating_Systems_Linux_Solaris_and_Mac_OS_X"; 
	exit 1; 
}
if [ -z "$ANDROID_HOME" ]; then
   echo "The ANDROID_HOME environment variable isn't set, where is your Android SDK located?"
   read ANDROID_HOME
fi
cd ..
mvn eclipse:clean install -Dandroid.sdk.path=$ANDROID_HOME
