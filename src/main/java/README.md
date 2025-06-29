# Household Hub
Project ZHAW CAS OOP/25.19, 2025,  by Anouk Allenspach and Daniel Grüter

## Content
- [Overview](#Overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#Usage)
- [Authors](#Authors)

## Overview
**Household Hub** is an application for managing expenses for individuals and groups (e.g., shared flats, trips).  
The app consists of a JavaFX desktop application and a Spring Boot–based REST API with a web frontend.  
The goal was to build a locally runnable, extensible, and privacy-friendly solution—without any cloud dependency.

## Features
- Record, edit, and delete expenses
- Automatic balance calculation per user and expense list
- View and edit data via JavaFX or browser
- REST API for external integration
- Data storage using local text files

## Technologies Used
| Area               | Technology                         |
|--------------------|------------------------------------|
| Backend            | Java, Spring Boot, REST API        |
| Frontend (Desktop) | JavaFX, FXML                       |
| Frontend (Web)     | HTML, CSS, JavaScript              |
| Data Storage       | Local Text Files (and H2 Database) |
| Build & Structure  | Maven, MVC                         |
| Testing            | JUnit                              |

## Installation

### Prerequisites
<!-- TODO: List required software (Java version, Maven, Docker, etc.) -->
- Java 21.0.6
- Apache Maven 3.9.10

### Setup the Project
#### Step 1: Clone the Repository
```bash
git clone https://github.com/gruetdan/HHApp.git
cd HHApp-DG2_AA1
```
#### Step 2: Build the Frontend

The frontend is based on JavaFX/FXML and does not require a separate build step. Run 
```
src/main/java/com/zhaw/hhapp/Main.java
```

#### Step 3: Build the Backend

Compile and package the Spring Boot application by running 
```
src/main/java/com/zhaw/hhapp/BackendApplication.java
```

The application will start on `http://localhost:8080/`.

In case you get **IllegalAccessException** allow JVM explicitly to access the following packages. Therefore add the following to '**Add VM Options**' (in the 'Run/Debug Configurations'):
``` bash
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
```

#### Step 4: Run Tests

Execute unit tests in
``` bash
src/test/java
```

## Usage

### Desktop Application
#### Main Window 

- Displays all expense lists.
- Buttons to edit, delete and evaluate a selected expense list.

A new list ist automatically created when a new name of a list is written in the text field and the butten 'Edit list' is clicked.

#### Expense List Window

- Displays all items of a selected expense list.
- Options to add, delete and export items.
- Close the window to get back to the main window. 

### Web Application
#### Main Page (index.html)

- Displays all items of all the expense lists. 
- Options to add, edit, delete and save. 

Always click the save button before leaving the page. 

## Authors

Created by [allenano](https://github.com/allenano) and [gruetdan](https://github.com/gruetdan).