Work time tracker


Reference documentation
________________
Installation and set up
Before being able to use Work time tracker, you’ll need to have the following software installed:


* JDK (at least v 11)
* MySQL
* Any Java IDE (Instructions for using Intellij are provided)
* A web browser


Currently, the back end is configured to communicate with a MySQL database located at localhost:3306. You should therefore set up your database accordingly. Please follow the instructions in appendix 1 to do this.


Once your database instance is up and running, you’ll need to execute some SQL to create the schema and tables that the application uses. This script can be found in WorkTimeTracker/src/main/SQL/InitiationScript.sql. You now have the database set up with no data in.


Before you can start using the application, you need to start the back end running. You can do this using any IDE. I’ve provided instructions for importing my project into Intellij in appendix 2.


Using the app
Once the back end and the database are running, you can begin using the app. Navigate to “WorkTimeTracker\src\main\Front end” and run Login.html. Here, you can create an account by selecting the Register link. Then you can login and start creating and searching through entries.
________________
Appendix 1
1. Download MySQL installer: https://dev.mysql.com/downloads/workbench/
2. Create a local instance using port 3306, set the username as ‘user’ and the password as ‘password’.
3. Select File > Open SQL script then choose the SQL initialisation script mentioned.
4. Run the SQL queries in order (or select them all and hit run).




Appendix 2
1. Go to File > New > Project from version control > Git then paste in the following url:               https://github.com/AndrewCushing/WorkTimeTracker.git
2. Select an appropriate directory to save the project in then click clone.
3. The IDE should prompt you to import Maven settings, select import.
4. Navigate to src/main/java/FrontEndInterface and open MyAPI.
5. Select run to the left of the class declaration or main method. The back end is now running and will accept requests from the front end and interact with the database.
