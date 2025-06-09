Haushold Hub App (HHApp)
------------------------
To run the desktop-application run 'src/main/java/com/zhaw/hhapp/Main.java'
To run the web-application run 'src/main/java/com/zhaw/hhapp/BackendApplication.java'

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