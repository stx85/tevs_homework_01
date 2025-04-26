package fh.bswe.statusserver.helper;

import java.util.List;

public class EurekaApplication {
    private String name;
    private List<EurekaInstance> instance;

    // Getter und Setter
    public String getName() {
        return name;
    }

    public List<EurekaInstance> getInstance() {
        return instance;
    }
}
