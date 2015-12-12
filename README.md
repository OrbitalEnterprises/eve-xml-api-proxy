# EVE XML API Proxy

This module provides a servlet which exposes a REST API for accessing the [EVE XML API](https://github.com/OrbitalEnterprises/evexmlapi) which in turn accesses the EVE Online XML API server endpoints.  When run as a servlet, this module therefore acts as a proxy for the EVE Online servers.

We use [Swagger](http://swagger.io) to expose a REST API, which in turn makes it trivial to generate documentation and experiment with the API, as well as generate client libraries in various languages.

We run a live instance of this servlet at https://evexmlapi.orbital.enterprises.  The examples below show how to use Swagger with the live instance.  However, you can also run the servlet locally, and use an appropriate local URI with the Swagger tools.  There are instructions below for running a local instance using [Tomcat 7](http://tomcat.apache.org/download-70.cgi).

## Build

### Maven

We use [Maven](http://maven.apache.org) to build EVE XML API proxy, and publish to [Maven Central](http://search.maven.org/).  The easiest way to incorporate the proxy into your own code is to add the following dependency to your pom.xml:

```xml
<dependency>
    <groupId>enterprises.orbital</groupId>
    <artifactId>eve-xml-api-proxy</artifactId>
    <version>1.0.0</version>
</dependency>
```

You can find more details about the artifact [here](http://mvnrepository.com/artifact/enterprises.orbital/eve-xml-api-proxy).

### Non-Maven

The EVE XML API proxy has the following dependencies which you'll need to build and run properly:

* [Orbital eve-xml-api v1.0.0](https://github.com/OrbitalEnterprises/eve-xml-api)
* [javax.servlet-api 3.1.0](http://search.maven.org/#artifactdetails%7Cjavax.servlet%7Cjavax.servlet-api%7C3.1.0%7Cjar)
* [swagger-jersey-jaxrs 1.5.4](http://search.maven.org/#artifactdetails%7Cio.swagger%7Cswagger-jersey-jaxrs%7C1.5.4%7Cjar)
* [slf4j-log4j12 1.6.3](http://search.maven.org/#artifactdetails%7Corg.slf4j%7Cslf4j-log4j12%7C1.6.3%7Cjar)
* [jersey-core 1.18.1](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey%7Cjersey-core%7C1.18.1%7Cjar)
* [jersey-json 1.18.1](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey%7Cjersey-json%7C1.18.1%7Cjar)
* [jersey-servlet 1.18.1](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey%7Cjersey-servlet%7C1.18.1%7Cjar)
* [jersey-multipart 1.18.1](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey.contribs%7Cjersey-multipart%7C1.18.1%7Cjar)
* [jersey-server 1.18.1](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey%7Cjersey-server%7C1.18.1%7Cjar)

If you want to run unit tests, you'll also need:

* [jersey-client 1.18.1](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey%7Cjersey-client%7C1.18.1%7Cjar)
* [junit 4.12](http://search.maven.org/#artifactdetails%7Cjunit%7Cjunit%7C4.12%7Cjar)

## Usage

### Viewing Documentation and Trying the API with Swagger

The EVE XML API proxy has Swagger annotated endpoints which makes it easy to use the Swagger tooling.  For the live proxy, the Swagger configuration file is available at https://evexmlapi.orbital.enterprises/swagger.json.  This URI can be provided as input to Swagger tooling in order to view documentation and sample the API.  For example, the following link will invoke the [Swagger UI online demo](http://petstore.swagger.io) against the live proxy: http://petstore.swagger.io/?url=https://evexmlapi.orbital.enterprises/swagger.json.  From the UI demo, you can then invoke any of the exposed API endpoints. 

### Using Swagger to Generate Client Code

The REST API can be accessed directly following the API documentation.  However, you can also use the Swagger configuration file to automatically generate appropriate client code.  There are two ways to do this.

For a Javascript client, there is no need to generate client code statically.  Instead, one can use the [Swagger Javascript](https://github.com/swagger-api/swagger-js) module.  For example, the following HTML snippet will create a Javascript client from the live proxy:

```html
<!-- Set up Swagger -->
<script src='https://raw.githubusercontent.com/swagger-api/swagger-js/master/browser/swagger-client.min.js' type='text/javascript'></script>
<script type="text/javascript">
  var url = "https://evexmlapi.orbital.enterprises/swagger.json";
  window.swagger = new SwaggerClient({ 
    url: url,
    success: function() { /* called when the client is ready */ }
  });
</script>    
```

You can then invoke the client as follows:

```javascript
window.swagger.Server.ServerStatus({}, {}, function () { /* called on success */ }, function () { /* called on failure */ });
```

For a non-Javascript client, you can use the [Swagger Editor online demo](http://editor.swagger.io/#/).  Type in "https://evexmlapi.orbital.enterprises/swagger.json" under "File->Import URL..", then use the "Generate Client" menu to download an appropriate client.
 



