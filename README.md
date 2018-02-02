Develop Web application using Spring boot
* Tool used
1. Spring Boot
2. Gradle
3. git
4. mysql
* steps 
1. initial project
spring init -d=web,data-rest,data-jpa --build=gradle -g=org.exampel.demo -a=demo-backend -j=1.8 --package-name=org.example.demo.be -n=BackendDemo
* -d: dependencies
* -g: group id
* -a: artifact id
* --package-name: spring application package
* -n: spring application name
This will generate a zip file called demo-backend.zip
2. unzip 
```bash
unzip -d demo-backend demo-backend.zip
```
3. put into git repository
create a repository in github with url https://github.com/wxlfrank/spring-backend.git
```bash
git init
vim .gitignore
git add * .gitignore
git commit -m "init spring backend project in gradle"
git remote add origin https://github.com/wxlfrank/spring-backend.git
git push --set-upstream origin master
git push
```
4. run the applicaton
```bash
gradle bootrun
```
Error happens because database is not configured yet

5. database setting

5.1 create a database (test) owned by a user (test) with password (password) in mysql by running the bash script file
```bash
#!/bin/bash
# create database $1 which is managered by user $2 
echo '-------create database and user-------'
usage(){
	echo "Usage: $0 database_name user_name"
	exit $#
}
if [ $# -lt '2' ]; then 
	echo "The bash needs 2 parameters, but you provider only $# parameter"
	usage
fi

read -s -p "Enter password for user $2:" password
echo ""
echo "In the following, enter password for root at mysql"

command="CREATE DATABASE IF NOT EXISTS $1;"
command="${command}CREATE USER IF NOT EXISTS '$2' IDENTIFIED BY '$password';"
command="${command}GRANT ALL PRIVILEGES ON $1.* TO '$2';"
echo $command | mysql -u root -p  
echo "create database $1 for user $2 successfully"
```
5.2 configure database in the project by adding the following lines in src/main/resources/application.properties
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/test?useSSL=false
spring.datasource.username=test
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show_sql=false
spring.jpa.hibernate.format_sql=true
spring.jpa.properties.hibernate.hbm2ddl=update
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.properties.hibernate.connection.CharSet=utf-8
spring.jpa.properties.hibernate.connection.useUnicode=true
spring.jpa.properties.hibernate.connection.characterEncoding=utf-8
spring.jpa.properties.hibernate.globally_quoted_identifiers=true
```
5.3 add mysql library in the project by adding dependency in build.gradle
```groove
dependencies {
	//------
	compile('mysql:mysql-connector-java')
	//------
}
```
Now you can run the application in the embeded tomcat by gradle bootrun
6 Deploy your application to local or remote tomcat server
6.1 Modify your application class (BackendDemoApplication)
From
```java
@SpringBootApplication
public class BackendDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDemoApplication.class, args);
	}
}
```
To
```java
@SpringBootApplication
public class BackendDemoApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BackendDemoApplication.class, args);
	}
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BackendDemoApplication.class);
    }
}
```
