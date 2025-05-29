package com.zhaw.hhapp.service;

import com.zhaw.hhapp.manager.ExpensesManager;

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
}
