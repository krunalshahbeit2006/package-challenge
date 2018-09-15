package com.mobiquityinc.packer.bean;

/**
 * This class is pojo of thing with an list of attributes used to choose right thing
 *
 * @author ShahKA
 * @since 1/22/2018
 */
public class Thing {

    private String _index;
    private float _weight;
    private float _cost;

    public Thing(final String index, final float weight, final float cost) {
        this._index = index;
        this._weight = weight;
        this._cost = cost;
    }

    public String getIndex() {
        return _index;
    }

    public void setIndex(String _index) {
        this._index = _index;
    }

    public float getWeight() {
        return _weight;
    }

    public void setWeight(float _weight) {
        this._weight = _weight;
    }

    public float getCost() {
        return _cost;
    }

    public void setCost(float _cost) {
        this._cost = _cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Thing thing = (Thing) o;

        if (Float.compare(thing._weight, _weight) != 0)
            return false;
        if (Float.compare(thing._cost, _cost) != 0)
            return false;
        return _index.equals(thing._index);

    }

    @Override
    public int hashCode() {
        int result = _index.hashCode();
        result = 31 * result + (_weight != +0.0f ? Float.floatToIntBits(_weight) : 0);
        result = 31 * result + (_cost != +0.0f ? Float.floatToIntBits(_cost) : 0);
        return result;
    }

    @Override
    public String toString() {
        return ("[" + "index : " + _index + ", weight : " + _weight + ", cost : " + _cost + "]");
    }

}