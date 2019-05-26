## Advertising Manager Web Application Test Task
Igor Chayun

Web application to manage advertising banners and categories. 
Banners and categories can be created, edited and deleted. 
The banner is selected via a standard HTTP request, 
with the category specified in the request parameters.

### Build instructions
To build executable .jar file type the following:

*mvn clean package*

To start App type the following:

*mvn spring-boot:run*

Go to url http://localhost:8080/banners

### Notes
* To create a new category http://localhost:8080/categories/new
* To create a new banner http://localhost:8080/banners/new
* To get the text of the banner, use the URL of the form: 
http://localhost:8080/bid?category=<REQ_NAME >