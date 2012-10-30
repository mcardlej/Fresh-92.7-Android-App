@echo off
call mvn -version > nul 2>&1
if ERRORLEVEL 1 (goto :maven_doesnt_exist)
if defined ANDROID_HOME (goto :android_home_defined)
set /P ANDROID_HOME=The ANDROID_HOME environment variable isn't set, where is your Android SDK located?
:android_home_defined
echo WARNING: this will blow away any changes you've manually made to any existing eclipse project files (probably a good thing). Press enter to continue otherwise Ctrl+C to bail out
pause
rem we shouldn't have to pass the android.sdk.path param but why not
cd ..
mvn eclipse:clean eclipse:eclipse -DdownloadJavadoc -Dandroid.sdk.path=%ANDROID_HOME%
exit /B 0
:maven_doesnt_exist
echo ERROR: We can't find the 'mvn' command so we have nfi where maven is. Check out these instructions and then come back and try again: http://maven.apache.org/download.html#Windows_2000XP
exit /B 1
