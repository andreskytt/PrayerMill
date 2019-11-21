package ee.pmill.mill;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Sky {
    private LinkedList<String> contents;
    public final ApplicationEventPublisher eventPublisher;

    public Sky(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        contents = new LinkedList<>();
    }

    public List getRoll(){
        return contents;
    }

    public void add(String message){
        if(message != null) {
            contents.add(message);
            this.eventPublisher.publishEvent(message);
        }
    }
}
