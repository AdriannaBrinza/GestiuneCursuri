package com.itfactory;

import com.itfactory.data.DataLoader;
import com.itfactory.utils.*;
import com.itfactory.exceptii.*;

public class Main {

    public static void main(String[] args) throws NotUniqueException {
        DataLoader data = new DataLoader();
        data.loadData();

        Utils.printMenu();
        while (true) {
            Utils.parseOptions(data.getDataMap());
        }
    }
}