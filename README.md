# tweeter

Tweeter is an Android client and AWS Java server implementation of a clone of Twitter. This project was developed and completed by Chris McClain, Curtis Larsen and Nathaniel Bennett as part of a course on design patterns. 

The client code can be found in Tweeter/app and Tweeter/shared; AWS server code is in Tweeter/server and Tweeter/shared. The Android client code makes use of the MVP (Model/View/Presenter), and both client and server code are layered to separate presentation, business code and data access.

Through the use of AWS SQS message queues, the end result of this project was a social media app capable of propogating and updating status feeds for tens or hundreds of thousands of users within a relatively small period of time (<60 seconds) whenever a user posted a status, without incurring any noticable charges for AWS services. It was a fun project!

