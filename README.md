# Pre configured project for quick start

## Used tools
* Spring Boot (1.4.0.RELEASE)
* Spring Cloud
* Gradle (2.14.1)
  * FindBugs (plugin)
  * CheckStyle (plugin)

## Project capabilities
* Configuration microservice uses properties from `https://github.com/modestukasai/pre-configured-project-config`
* Gateway is Zuul edge service, connects all client side microservices
* Service discovery service uses Eeureka
* Authorization microservice uses OAuth2
* Generate jar version from git commit hash
* Automatially upload artifacts to nexus

## Project launch

Launch in this order (bootRun task):

1. `configuration`
2. `service-discovery`
3. `authorization` 
4. `project-a`
5. `gateway`

