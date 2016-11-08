# Client Manager Readme

Client Manager is a web app that allow user to manage the clients of a web design company. It allows manage client, web hosting and social networworks information. 

It was developed to cover a real company needs and cause I cannot continue its improvement I decided release it as free software.

It is an Beta app, it's working but has some technical debt and needs to improve presentation architecture. 

I decided to use [Vaadin](http://vaadin.com/) in presentation to explore this framework possibilities to build web apps. This framework allows develop of GWT and now Polymer applications with a very easy approach.

The app has Spring Security based authentication but authorization has to be developed.

The technology used in this app is:

* Spring Security.
* Spring Framework
* Spring Data
* Aspectj
* Vaadin

And has integration with Amazon S3 to allow drop documents associated to a client.

To allow run it some environment variables must be provided:

* DATABASE_URL: that has database connection string. E.g.- postgres://postgres:postgres@localhost:5432/clientmanagerbd
* AMAZON_BUCKET: S3 Amazon Bucket
* AMAZON_ACCESSKEY: Amazon Acceskey
* AMAZON_SECRETKEY: Amazon Secretkey

I decide this approach cause imho it's the better approach when using Heroku to deploy de application

I hope it be useful to someone.