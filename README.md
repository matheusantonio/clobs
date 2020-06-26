# clobs
*Clojure Bookmark Service*


A web api for a bookmarks application developed in Clojure.  My intention is to integrate it with a Vue page app in the future.

My intention is for this to have the same features as my other Bookmarks project (https://github.com/matheusantonio/belicious), this time as a web api that serves a web page, both for learning purposes.

It uses:
* Ring/Jetty for web server
* Compojure for routing
* Ring session middleware for session management
* next-jdbc for database connection
* MySql as databasey
* Enlive and http-kit for web scrapping (getting titles by url)
* Leiningen for project creation & dependencies management