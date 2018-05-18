#Brno Go Android Application

Implementation of client application for Brno Go server application.
It contains all necessary files.

##Setup

1. Run Android Studio (3.2 Canary 14) and open project.
2. Choose build variant.
3. Change server URL in UrlModule.
4. Build and run the application.

OR

1. Install apk from apk folder.
2. Run the application.

Note: The dev variant has as URL localhost of server.
The prod variant has URL provided by *Localtunnel* using command
 `lt --port 8080 --subdomain brnogoserver`.
