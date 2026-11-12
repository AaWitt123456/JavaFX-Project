package com.example.awjavafxapplication.Model;

/**
 * this controls the inhouse parts
 *
 */

public class inHouse extends Part {
    private int machineId;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public inHouse(int id, String name, double price, int stock, int min, int max, int machineId) {

        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }

    /**
     *
     * @return machineId
     */
    public int getMachineId() {

        return machineId;
    }


    }

