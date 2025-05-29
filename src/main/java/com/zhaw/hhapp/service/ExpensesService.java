package com.zhaw.hhapp.service;

import com.zhaw.hhapp.manager.ExpensesManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Service-Klasse für Geschäftslogik rund um Ausgabenlisten.
 * Trennt die Logik klar von der UI (Controller).
 */
public class ExpensesService {
    /**
     * Legt eine neue Ausgabenliste mit gegebenem Namen an.
     * Gibt true zurück, wenn die Liste erfolgreich angelegt wurde.
     * Gibt false zurück, wenn bereits eine Liste mit dem Namen existiert
     * oder der Name ungültig ist (leer/null).
     *
     * @param name Name der neuen Ausgabenliste
     * @return true, wenn erfolgreich angelegt, sonst false
     */

    public boolean addExpenseList(String name) {
        // Prüfe auf leeren oder ungültigen Namen
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Prüfe, ob die Liste schon existiert
        if (ExpensesManager.getExpenseList(name) != null) {
            return false;
        }
        // Liste anlegen
        ExpensesManager.addExpenseList(name);
        return true;
    }

    public ArrayList<String> listTxtFiles() {


        File directory = new File("ExpenseLists");
        // Überprüfe, ob der Ordner existiert, falls nicht, erstelle ihn
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String directoryPath = System.getProperty("user.dir")+ File.separator + "ExpenseLists";

        System.out.println("listTxtFiles() got startet reading "+directoryPath);

        File folder = new File(directoryPath);
        File[] files = folder.listFiles();

        ArrayList<String> fileList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    //String fileType = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1) : "Unbekannt";
                    //System.out.println(fileName + " - Typ: " + fileType);
                    fileList.add(fileName);
                    System.out.println(fileName);
                } else {
                    System.out.println(file.getName() + " - Typ: Ordner");
                }
            }
        } else {
            System.out.println("Der Ordner existiert nicht oder ist leer.");
        }
        return fileList;
    }
}
