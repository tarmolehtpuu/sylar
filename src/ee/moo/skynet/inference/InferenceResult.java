package ee.moo.skynet.inference;

import java.util.*;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 3:47 PM
 */
public class InferenceResult {

    private Map<String, Integer> data = new HashMap<String, Integer>();

    public InferenceResult() {
    }

    public InferenceResult(Map<String, Integer> values) {
        for (String key : values.keySet()) {
            data.put(key, values.get(key));
        }
    }

    public int get(String name) {
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

    public int size() {
        return data.size();
    }

    public boolean contains(String name) {
        return data.containsKey(name);
    }
}
