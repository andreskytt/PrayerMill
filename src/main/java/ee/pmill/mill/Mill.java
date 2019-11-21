package ee.pmill.mill;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

import static java.lang.System.currentTimeMillis;

@Component
public class Mill {
    private final Log logger = LogFactory.getLog(this.getClass());
    private long lastSpeedAdjustment;

    //    In Prayers per second
    private PrayerRoll roll;
    private long lastSpin;

    private final int SPIN_PAUSE = 100;
    private final int SPIN_DURATION = 100;

    private final double F_FRICTION = 1;
    private final double F_SPIN = 10;

    private final SynchronizedDouble spinForce = new SynchronizedDouble();
    private final SynchronizedDouble speed =  new SynchronizedDouble();

    @Scheduled(fixedDelay = SPIN_DURATION)
    private void changeSpeed() {
            double f = spinForce.getValue() - F_FRICTION;
            // a = F/m
            // v = v0 + ta
            synchronized (speed) {
                speed.addValue((currentTimeMillis() - lastSpeedAdjustment) * f / (roll.getRollLength() * 1000));
                if(speed.getValue() < 0){
                    speed.setValue(0);
                }
            }
            lastSpeedAdjustment = currentTimeMillis();
            spinForce.setValue(0);
    }

    @Scheduled(fixedDelay = SPIN_PAUSE)
    private void sendPrayers() {
        logger.debug("Sending prayers");
        int prayerSent = 0;
        while ((float) prayerSent * 1000 / (currentTimeMillis() - lastSpin) < speed.getValue()) {
            sky.add(roll.getNext());
            prayerSent++;
        }
        lastSpin = currentTimeMillis();
    }

    private static class PrayerRoll{
        private final LinkedList<String> contents;
        private int rollPosition;

        PrayerRoll() {
            contents = new LinkedList<String>();
        }

        LinkedList<String> getContents() {
            return contents;
        }

        void add(String message){
            contents.add(message);
        }

        int getRollLength(){
            return contents.size();
        }

        String getNext(){
            synchronized (contents) {
                if(rollPosition >= contents.size()){
                    rollPosition = 0;
                }
                if(rollPosition < contents.size()){
                    return contents.get(rollPosition++);
                }else {
                    return null;
                }
            }
        }
    }

    @Autowired
    private Sky sky;

    public Mill() {
        roll = new PrayerRoll();
        lastSpeedAdjustment = currentTimeMillis();

    }

    public double spin(){
        spinForce.addValue(F_SPIN);
        return speed.getValue();
    }

    public void add(String prayer){
        roll.add(prayer);
    }
}
