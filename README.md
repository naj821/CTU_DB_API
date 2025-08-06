✅ Install Java Development Kit (JDK)
Download the JDK from Oracle:
👉 https://www.oracle.com/java/technologies/downloads/

After installing, set up your environment variables:

➤ Set JAVA_HOME
<details> <summary>📍 Windows Instructions</summary>
Press the <kbd>Windows</kbd> key and search for env, then press Enter

Click on Environment Variables

Under User Variables or System Variables, click New

Add the following:

Variable Name: JAVA_HOME

Variable Value: C:\Program Files\Java\jdk-21 --change the version you're using

Find and select the Path variable, then click Edit

Click New and add: %JAVA_HOME%\bin

</details>
*******************************************
✅ Install PostgreSQL
Download PostgreSQL from:
👉 https://www.postgresql.org/download/

For visual guidance, see this YouTube tutorial:
▶️ PostgreSQL Installation Guide

✅ Set Up the Database
Once PostgreSQL is installed, open a terminal and run: createdb ctu_db
This will create a new database named ctu_db.
********************************************
📦 .env Template
Create a .env in the root folder:
--Paste this
spring.datasource.url=jdbc:postgresql://localhost:5432/ctu_db
spring.datasource.username=hi
spring.datasource.password=JBalbon
spring.datasource.driver-class-name=org.postgresql.Driver


