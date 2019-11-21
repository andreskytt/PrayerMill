package ee.pmill.api;

import ee.pmill.mill.Mill;
import ee.pmill.mill.Sky;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class APIController {

    @Autowired
    private Mill mill;

    @Autowired
    private Sky sky;

    @GetMapping("api/spin")
    public Double spin(){
        return mill.spin();
    }

    @GetMapping("api/roll")
    public List roll(){
        return sky.getRoll();
    }

    @PostMapping("api/add")
    public void add(@RequestParam("prayer") String prayer){
        mill.add(prayer);
    }


}
