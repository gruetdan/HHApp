package com.zhaw.hhapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
        private double amount;
        private String description;
        private String date;
    private String userName;

    public Expense(double amount, String description, String date, String userName) {
            this.amount = amount;
            this.description = description;
            if (userName.length() > 0){
                this.userName = userName;
            } else{
                this.userName = System.getProperty("user.name");
            }
            if (date.length() > 0){

                this.date = date;
            } else{
                this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public String getUserName() {
            return userName;
        }

        public String getDate() {
            return date;
        }

        @Override
        public String toString() {
            //System.out.println(userName);
            return String.format("%.2f,%s,%s,%s", amount, description, date, userName);
        }
}
