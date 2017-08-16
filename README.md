Data Engineer Interview Project
===

Implementation of sentimental analysis by using [Google Natural Language API](https://cloud.google.com/natural-language/)  and [Akka Http](http://akka.io/)

[![Build Status](http://img.shields.io/travis/rayyildiz/sentimental-analysis.svg?style=flat-square)](https://travis-ci.org/rayyildiz/sentimental-analysis) 


This project was created for showing usage of:

* [Akka Actor model](http://doc.akka.io/docs/akka/current/scala/actors.html) (Version 2.5.4)
* [Akka Http](http://doc.akka.io/docs/akka-http/current/scala/http/) (Version 10.0.9)
* Dependency Injection in Akka System
* [Google Natural Language](https://cloud.google.com/natural-language/) And [Translation API](https://cloud.google.com/translate/docs/) 

Install And Run
===

First of all, create Google Cloud account and create a project. Then you have to enable Google Natural API. More information [click here.](https://cloud.google.com/natural-language/docs/getting-started)

Install 
---
You need install ```sbt``` before . More info and install sbt click [Scala SBT](http://www.scala-sbt.org/0.13/docs/Setup.html)
Recommend version is **0.13.16**

Also you need to install Java 8. More info click [Jdk 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Google API is free for first 5000 request per month. 

Run
---
You need to define ```GOOGLE_APPLICATION_CREDENTIALS```. Currently there is file under ```./flux-decd0ff29d60.json```. But if you can run application ```run.sh``` or ```run.bat```, you don't need to set a environpment for it. 


> $ ./run.sh 
> test
> 
> run
> [info] Running me.rayyildiz.sentiment_analyzer.BootApplication
> [INFO] Server online at http://localhost:8080/
  
Usage
===

Ping Pong Demo
---
Test akka actor model.

**POST** _http://localhost:8080/ping_ Ping-Pong demo. To test application.

> curl -H "Content-Type: application/json"  -X POST -d '{"message":"hello"}' http://localhost:8080/ping
 
Health monitor
---

Monitoring.

**GET** _http://localhost:8080/health_  Health monitor

> curl -H "Content-Type: application/json"  -X GET -d '{"message":"hello"}' http://localhost:8080/health

Clean Text
---

Clean Text (from url, html-xml tags, symbols etc)

**POST** _http://localhost:8080/api/clean_ Clean Text

> curl -H "Content-Type: application/json"  -X POST -d '{"text":"<b>This <i>is</i> a html message</b>"}' http://localhost:8080/api/clean

Detection language
---

Language Check (detect and check that language is English)

**POST** _http://localhost:8080/api/detect_ Detection language

> curl -H "Content-Type: application/json"  -X POST -d '{"text":"This is a message"}' http://localhost:8080/api/detect

Word extraction
---

Extract Important Words (topics mentioned or words that are not common, use a public api) Extract Words Indicating Sentiment (words that tend to show feeling)

**POST** _http://localhost:8080/api/extract_ Word extraction

> curl -H "Content-Type: application/json"  -X POST -d '{"text":"This is a message"}' http://localhost:8080/api/extract

Sentimental analysis
---

Sentimental detection.

**POST** _http://localhost:8080/api/sentiment_ Sentimental analysis
   
> curl -H "Content-Type: application/json"  -X POST -d '{"text":"I am happy"}' http://localhost:8080/api/sentiment

Relationship Determination
---

Relationship determination between the feature and sentiment.

**POST** _http://localhost:8080/api/determination_ Relationship determination between the feature and sentiment.

> curl -H "Content-Type: application/json"  -X POST -d '{"text":"Ask not what your country can do for you, ask what you can do for your country."}' http://localhost:8080/api/determination

All Analysis
---

Clean, language detection, extraction and sentimental analysis.

**POST** _http://localhost:8080/api/analysis_ Clean, detection, extract, sentiment, determination all together.

> curl -H "Content-Type: application/json"  -X POST -d '{"text":"Ask not what your country can do for you, ask what you can do for your country."}' http://localhost:8080/api/analysis


Alternative 
===

You can use docker to run application. Run ```./build_docker.sh``` to build docker image, and ```./run_docker.sh``` to run container.
More info click [https://docs.docker.com/engine/installation/](https://docs.docker.com/engine/installation/)


Known Issues:
===

* Used [jsoup](https://jsoup.org/) to clean text. JSoup has a bug and performance issue for large text.