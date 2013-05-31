package ee.moo.skynet.inference;

import java.util.*;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 3:47 PM
 */
public class InferenceResult {

    private Map<String, Integer> data = new HashMap<String, Integer>();

    public Integer get(String name) {
        return data.get(name);
    }

    public List<String> getNames() {

        List<String> names = new ArrayList<String>(data.size());

        for (String name : data.keySet()) {
            names.add(name);
        }

        Collections.sort(names);

        return names;
    }

    public void set(String name, Integer value) {
        data.put(name, value);
    }

    public boolean contains(String name) {
        return data.containsKey(name);
    }
}
