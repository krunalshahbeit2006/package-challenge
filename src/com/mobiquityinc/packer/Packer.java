package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.bean.Thing;
import com.mobiquityinc.packer.util.ThingComparatorByCostWeight;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The class has static method named pack. This method accepts the absolute path to a test file as a String. It does return the solution as a String.
 * class should throw an com.mobiquityinc.exception.APIException if incorrect parameters are being passed.
 * Additional constraints:
 * 1. Max weight that a package can take is ≤ 100
 * 2. There might be up to 15 items you need to choose from
 * 3. Max weight and cost of an item is ≤ 100
 *
 * @author ShahKA
 * @since 1/22/2018
 */
public class Packer {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String KEY_VALUE_SEPARATOR = ":";
    private static final String VALUES_SEPARATOR_START = "\\(";
    private static final String VALUES_SEPARATOR_END = "\\)";
    private static final String VALUE_SEPARATOR = ",";
    private static final String CURRENCY_EURO = "€";
    private static final String NOT_FOUND = "-";

    private Packer() {

    }

    public static String pack(final String strTestFileAbsolutePath) throws APIException {
        boolean isExists;
        String line;
        String solution = null;

        List<Thing> thingsChosenList;
        Map<Float, List<Thing>> packageMap;

        Path testFileAbsolutePath;

        /**
         * Get an absolute path of the test file
         */
        /*System.out.println("Trying to check absolute path to a test file exists: " + strTestFileAbsolutePath);*/
        testFileAbsolutePath = Paths.get(strTestFileAbsolutePath);
        /*isExists = Files.exists(testFileAbsolutePath);*/
        try {
            /*if (!isExists) {
                *//*System.err.println("Checked absolute path to a test file does not exists: " + testFileAbsolutePath);*//*
                throw new FileNotFoundException("Checked absolute path to a test file does not exists: " + testFileAbsolutePath);
            }
            System.out.println("Checked absolute path to a test file does not exists: " + testFileAbsolutePath);*/

            System.out.println("Trying to read test file: " + testFileAbsolutePath);
            System.out.println();
            /**
             * Get the reader to read file in buffer
             */
            try (BufferedReader br = Files.newBufferedReader(testFileAbsolutePath, CHARSET)) {
                /**
                 * Read the lines of the test file until end of line
                 */
                while ((line = br.readLine()) != null) {
                    //collect all fields into string array
                    packageMap = getPackage(line);
                    thingsChosenList = getChosenThings(packageMap);
                    solution = getSolution(thingsChosenList);
                    System.out.println("solution: " + solution);
                    System.out.println();
                }
            } catch (Exception e) {
                throw new APIException(e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new APIException(e.getMessage(), e);
        }

        return solution;
    }

    /**
     * Method takes line from file as an parameter and
     * Check if the line has key value and
     * Collect the key values in array and
     * Stop reading file and collecting key values if the key values are not present and
     * Assign key into Package weight and values into thing's index, weight, and cost and
     * Create an object thing from index, weight, and cost
     * Create a map of package which holds package limit and object of things to satisfy one-many relationship and
     * Returns a map of package
     */
    private static Map<Float, List<Thing>> getPackage(final String line) throws APIException {
        final int valuesArrLen;
        final float packageWeightLimit;
        final String keyStr;
        final String valuesStr;

        final String[] lineVariables;
        final String[] valuesArr;
        final Map<Float, List<Thing>> packageMap = new LinkedHashMap<>();

        String[] valueArr;
        final List<Thing> thingsList;

        int cnt;
        float weight;
        float cost;
        String value;
        String weightStr;
        String costStr;
        String index;

        /**
         * Check if the line has key value
         */
        if (!line.contains(KEY_VALUE_SEPARATOR)) {
            throw new IllegalArgumentException(
                    "The package data provided in test file is incorrect format, program expects field values to be colon (':') separated");
        }

        /**
         * Collect the key values in array
         */
        lineVariables = line.split(KEY_VALUE_SEPARATOR);

        /**
         * Stop reading file and collecting key values if the key values are not present
         */
        if (lineVariables == null) {
            throw new NullPointerException("Checked test file does not contains package data");
        } else if (lineVariables.length == 0) {
            throw new NullPointerException("Checked total number of package data in test file is: 0");
        }

        keyStr = lineVariables[0].trim();
        valuesStr = lineVariables[1].trim();

        /*System.out.println();
        System.out.println("keyStr: " + keyStr);
        System.out.println("valuesStr: " + valuesStr);
        System.out.println();*/
        /**
         * Assign key into Package weight
         */
        packageWeightLimit = Float.parseFloat(keyStr);
        /*System.out.print("packageWeightLimit: " + packageWeightLimit);*/

        if (packageWeightLimit > 100) {
            throw new APIException("Max weight that a package can take is ≤ 100");
        }

        /**
         * Assign values into thing's index, weight, and cost
         */
        valuesArr = valuesStr.trim().split(VALUES_SEPARATOR_START);

        if (valuesArr == null) {
            throw new IllegalArgumentException(
                    "The package things data provided in test file is incorrect format, program expects things values to be comma (',') separated in following order (index, weight, cost)");
        }

        valuesArrLen = valuesArr.length - 1;

        if (valuesArrLen > 15) {
            throw new APIException("There might be up to 15 items you need to choose from");
        }
        /*System.out.print(", valuesArr::length: " + valuesArrLen);*/
        /*System.out.println();*/

        thingsList = new ArrayList<>();

        for (String valueStr : valuesArr) {
            value = valueStr.trim().replaceAll(VALUES_SEPARATOR_END, "");
            if (value == null || "".equals(value.trim())) {
                /*System.out.println("value has blank spaces at start/end");*/
            } else {
                valueArr = value.split(VALUE_SEPARATOR);
                index = valueArr[0].trim();
                weightStr = valueArr[1].trim();
                costStr = valueArr[2].trim();
                /*System.out.println("indexStr: " + index + ", weightStr: " + weightStr + ", costStr: " + costStr);*/
                weight = Float.parseFloat(weightStr);
                cost = Float.parseFloat(costStr.replaceAll(CURRENCY_EURO, ""));
                /*System.out.println("index: " + index + ", weight: " + weight + ", cost: " + cost);*/
                if (weight > 100 || cost > 100) {
                    throw new APIException("Max weight and cost of an item is ≤ 100");
                }
                /**
                 * Create an object thing from index, weight, and cost
                 */
                thingsList.add(new Thing(index, weight, cost));
                /**
                 * Create a map of package which holds package limit and object of things to satisfy one-many relationship
                 */
                packageMap.put(packageWeightLimit, thingsList);
            }
        }
        /*System.out.println();*/

        return packageMap;
    }

    /**
     * Method takes map of package which holds package limit and object of things as parameter and
     * Loop thru map of package which holds package limit and object of things and
     * Assign key into Package weight and list of values into an object of things and
     * Sort an object of things by highest cost, and lowest weight
     * Loop thru each things and apply the business logic to choose things with high cost, and low weight, and collect indexes of those things
     * Choose things with weight less than the package limit
     * Put each chosen things with high cost, low weight, and their indexes collected
     * Sort an object of things by highest cost, and lowest weight
     * Returns a list of chosen things
     */
    private static List<Thing> getChosenThings(final Map<Float, List<Thing>> packageMap) {
        int cnt;
        float packageWeightLimit;
        float weight;
        float cost;
        float weights;
        float costs;
        String index;
        StringBuilder indexes;

        Thing thingChosen;
        List<Thing> thingsList;
        List<Thing> thingsChosenList = null;

        /*System.out.println();*/
        /**
         * Loop thru map of package which holds package limit and object of things
         */
        for (Map.Entry<Float, List<Thing>> entry : packageMap.entrySet()) {
            /**
             * Assign key into Package weight
             */
            packageWeightLimit = entry.getKey();
            /**
             * Assign list of values into an object of things
             */
            thingsList = entry.getValue();
            /*System.out.println("packageWeightLimit: " + packageWeightLimit + ", thingsList::size: " + thingsList.size());*/
            /**
             * Sort an object of things by highest cost, and lowest weight
             */
            Collections.sort(thingsList, new ThingComparatorByCostWeight());
            thingsChosenList = new ArrayList<>();
            /**
             * Loop thru each things and apply the business logic to choose things with high cost, and low weight, and collect indexes of those things
             */
            for (Thing thing : thingsList) {
                /*System.out.println();
                System.out.println(thing);*/
                indexes = new StringBuilder();
                weights = 0.0f;
                costs = 0.0f;
                for (Thing thing2 : thingsList) {
                    /*System.out.println("\t" + thing2);*/
                    index = thing2.getIndex();
                    weight = thing2.getWeight();
                    cost = thing2.getCost();
                    /**
                     * Choose things with weight less than the package limit
                     */
                    if ((weights + weight) <= packageWeightLimit) {
                        indexes.append(index).append(",");
                        weights += weight;
                        costs += cost;
                    }
                }
                if (indexes != null && !"".equals(indexes.toString().trim())) {
                    thingChosen = new Thing(indexes.toString(), weights, costs);
                    if (!thingsChosenList.contains(thingChosen)) {
                        /**
                         * Put each chosen things with high cost, low weight, and their indexes collected
                         */
                        thingsChosenList.add(thingChosen);
                    }
                }
            }
        }
        /**
         * Sort an object of things by highest cost, and lowest weight
         */
        Collections.sort(thingsChosenList, new ThingComparatorByCostWeight());

        return thingsChosenList;
    }

    /**
     * Method takes list of things as parameter and
     * Gets the indexes of chosen thing with highest cost, lowest weight and
     * Returns a solution
     */
    private static String getSolution(final List<Thing> thingsChosenList) {
        String solution;
        String index;
        Thing thingsChosen;


        /*System.out.println();
        System.out.println(
                "Print which things to put into the package so that the total weight is less than or equal to the package limit and the total cost is as large as possible.");
        for (Thing thing : thingsChosenList) {
            System.out.println(thing);
        }*/
        /**
         * Get the indexes of chosen thing with highest cost, lowest weight
         */
        if (thingsChosenList == null || thingsChosenList.isEmpty()) {
            solution = NOT_FOUND;
        } else {
            thingsChosen = thingsChosenList.get(0);
            index = thingsChosen.getIndex();
            solution = index.substring(0, index.length() - 1);
        }
        /*System.out.println("solution: " + solution);*/
        System.out.println();

        return solution;
    }

}
