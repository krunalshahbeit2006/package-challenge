package com.mobiquityinc.packer.util;

import com.mobiquityinc.packer.bean.Thing;

import java.util.Comparator;

/**
 * This class is used to sort things by highest cost and lowest weight
 *
 * @author ShahKA
 * @since 1/22/2018
 */
public class ThingComparatorByCostWeight implements Comparator<Thing> {

    @Override
    public int compare(Thing o1, Thing o2) {
        int difference = (int) (o2.getCost() - o1.getCost());
        if (difference == 0) {
            difference = (int) (o1.getWeight() - o2.getWeight());
        }

        return difference;
    }

}
