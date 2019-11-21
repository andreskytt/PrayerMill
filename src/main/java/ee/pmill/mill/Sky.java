package ee.pmill.mill;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Sky {
    private LinkedList<String> contents;

    public Sky() {
        contents = new LinkedList<String>();
    }

    public List getRoll(){
        return contents;
    }

    public void add(String message){
        if(message != null) {
            contents.add(message);
        }
    }
}
