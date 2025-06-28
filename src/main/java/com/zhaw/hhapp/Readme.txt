Haushold Hub App (HHApp)
------------------------
The app offers an overview over different expense lists of several users.
The main functions are:
_ Add new type of list / new expense list
_ Add expense with amount [CHF], description, date, user
_ Summation of expenses in a list (per user)

To run the desktop-application run 'src/main/java/com/zhaw/hhapp/Main.java'
To run the web-application run 'src/main/java/com/zhaw/hhapp/BackendApplication.java' and open http://localhost:8080/

For the web-application you migth need to 'Add VM Options' in the 'Run/Debug Configurations':
--add-reads
com.example.hhapp=ALL-UNNAMED
--add-opens
com.example.hhapp/com.zhaw.hhapp.controller=ALL-UNNAMED
--add-opens
com.example.hhapp/com.zhaw.hhapp.dataLoader=ALL-UNNAMED
--add-opens
com.example.hhapp/com.zhaw.hhapp.manager=ALL-UNNAMED
--add-opens
com.example.hhapp/com.zhaw.hhapp.model=ALL-UNNAMED
--add-opens
com.example.hhapp/com.zhaw.hhapp.repository=ALL-UNNAMED
--add-opens
com.example.hhapp/com.zhaw.hhapp.service=ALL-UNNAMED