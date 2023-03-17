# Our Board
Our Board is a management page library implemented using the Spring Framework.   
With Our Board, you can perform CRUD operations (Create, Read, Update, Delete) on the information stored in the database.   
In addition, Our Board automatically recognizes information about database tables and generates management pages for those tables.

<br/>

## Features
- Automatic recognition of database table information
- CRUD (Create, Read, Update, Delete) operations for database information
- Support for search functionality
- Support for pagination functionality

<br/>

## Installation
For Maven projects, add the following dependency in your **pom.xml** file
```xml
<dependency>
    <groupId>com.twentiethcenturygangsta</groupId>
    <artifactId>our-board</artifactId>
    <version>1.0</version>
</dependency>
```
For Gradle projects, add the following dependency in your **build.gradle** file:
```groovy
dependencies {
    implementation 'com.twentiethcenturygangsta:our-board:1.0'
}
```

<br/>

## Usage
1. Add ``@EnableOurBoard`` to the class with ``@SpringBootApplication`` annotation.
2. Register a ``@Bean`` method.
   ```java
   @Bean
   public OurBoardClient jamBoardClient() throws Exception {
       UserDatabaseCredentials userDatabaseCredentials = new UserDatabaseCredentials(
               url, //spring.datasource.url
               userName, //spring.datasource.username
               password //spring.datasource.password
       );
       UserCredentials userCredentials = new UserCredentials(
               "dbdbdbdbdbdbdbq", //encryptKey
               "admin", //memberId
               "adminpassword" //password
       );

       return OurBoardClient.builder()
               .userDatabaseCredentials(userDatabaseCredentials)
               .userCredentials(userCredentials)
               .build();
   }
   ``` 
3. Use the ``@OurBoardEntity`` annotation on the class with the ``@Entity`` annotation.
4. Write group and description in ``@OurBoardEntity``   
   (Group is the group to which the table belongs. Description is a description of the table.)
5. Use the ``@OurBoardColumn`` annotation on the class field with the ``@OurBoardEntity`` annotation.
6. Write description in ``@OurBoardColumn``  
   (Description is a description of the column.)
   ```java
   @Entity
   @OurBoardEntity(group = "AUTHENTICATION AND AUTHORIZATION", description = "Admin Account")
   public class User {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @OurBoardColumn(description = "Login Id")
        private Long id;
   
        @OurBoardColumn(description = "Login Password")
        private String pw;
   
        @OurBoardColumn(description = "User Name")
        private String name;
   
        // ...
   }
   ``` 
7. Access **http://localhost:8080/our-board/admin** in your browser.
8. Log in with the ID and password you wrote in the properties file.
9. Perfect! Now you can conveniently manage your database with Our Board!

<br/>

## License
Our Board is released under the MIT License. See the **[LICENSE][licenseLink]** file for details.

<br/>

## Contributing
If you find any issues or have suggestions for improving Our Board, feel free to open a GitHub issue or submit a pull request.   
We welcome contributions from the community!!


[licenseLink]: LICENSE.md
