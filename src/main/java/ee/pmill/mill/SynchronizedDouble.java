package ee.pmill.mill;

class SynchronizedDouble {
    double value;
    public synchronized void setValue(double force){
        value = force;
    }

    public synchronized void addValue(double addedForce){
        value = value + addedForce;
    }
    public double getValue(){
        return value;
    }
}
