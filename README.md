# LookBook - Shop Manager

<details> 
  <summary>Table of Contents</summary>

  1. [Description](#description)
  1. [Technologies](#technologies)
  1. [Dependencies](#dependencies)
  1. [Getting Started](#getting-started)
  1. [License](#license)
  1. [Contact Me](#contact-me)
</details>

## Description
LookBook is a Java console application that manages the users, clothes and sales of an hypothetical shop, given their respective `.csv` file. 

The application offers up to 5 main managment features:
```
1. View all second-hand clothes in the database
2. Buy available clothes
3. Return back a clothing
4. Add a new user
5. Export a file with all the available clothes
```
> __Note__: The file will be exported in a folder named `exported-data` placed in the same folder where you are running the application.

## Technologies
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

## Dependencies
* [Opencsv](https://opencsv.sourceforge.net/)
* [ASCII Table](http://www.vandermeer.de/projects/skb/java/asciitable/)
* [Commons IO](https://commons.apache.org/proper/commons-io/description.html)

## Getting Started
### Prerequisites 
* [Java JDK 17](https://www.oracle.com/it/java/technologies/downloads/#java17)
* [Apache Maven 3.9+](https://maven.apache.org/download.cgi)

### Installation
```sh
1. Clone the repository
git clone https://github.com/cavaliernicola/Shop-Manager.git

2. Go to the project directory
Example: cd to/path/of/project

3. Build the application 
mvn package
# At this point two .jar files will be generated, we will use the 'jar-with-dependencies' file. 

4. Go to the target folder where Maven compiled the source 
cd target

5. Run the application
java -jar LookBook-1.0-jar-with-dependencies.jar
```

## License
Distributed under Apache 2.0 License. See [`LICENSE`](LICENSE) for more information.

## Contact Me
* Author: Nicola Cavalier 
* Email: cavaliernicola@gmail.com
