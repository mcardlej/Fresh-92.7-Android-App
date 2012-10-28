# Quick start list #
1. [Download](http://developer.android.com/sdk/index.html) and [install](http://developer.android.com/sdk/installing/index.html) the Android SDK
2. [Update the Android SDK](http://developer.android.com/sdk/installing/adding-packages.html) to include **version 15** of the API
3. [Download](http://maven.apache.org/download.html) and [install](http://maven.apache.org/download.html#Installation) Apache Maven3 (and maybe read [Maven in 5 minutes](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html))
4. Clone the GitHub repo (it's similar to [this](https://help.github.com/articles/fork-a-repo))
5. Run `mvn eclipse:eclipse` in the **Fresh 92.7** directory to generate eclipse project files. This is the directory that contains the **pom.xml** file; all maven commands need to be run from here.   
Note: if you get an error because maven can't find your android SDK, run the same command but pass the path to your SDK `mvn eclipse:eclipse -Dandroid.sdk.path="/path/to/your/sdk"`
6. Fire up eclipse and [import the project](http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.platform.doc.user%2Ftasks%2Ftasks-importproject.htm)
7. Start hacking
