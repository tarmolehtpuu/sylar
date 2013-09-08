package ee.moo.skynet.inference;

import java.util.*;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 3:47 PM
 */
public class InferenceResult {

    private Map<String, Integer> known = new HashMap<String, Integer>();

    private Map<String, Boolean> unknownT = new HashMap<String, Boolean>();
    private Map<String, Boolean> unknownF = new HashMap<String, Boolean>();

    public InferenceResult(Map<String, Integer> known) {
        for (String key : known.keySet()) {
            set(key, known.get(key));
        }
    }

    public void set(String name, Integer value) {
        known.put(name, value);
    }

    public void record(String name, Integer value) {

        if (known.containsKey(name)) {
            throw new InferenceException(String.format("Unable to record() value for known variable: %s", name));
        }

        if (!unknownT.containsKey(name)) {
            unknownT.put(name, false);
        }

        if (!unknownF.containsKey(name)) {
            unknownF.put(name, false);
        }

        if (value == 1) {
            unknownT.put(name, true);
        }

        if (value == 0) {
            unknownF.put(name, true);
        }
    }

    public int get(String name) {

        if (known.containsKey(name)) {
            return known.get(name);
        }

        if (unknownT.get(name) && !unknownF.get(name)) {
            return 1;
        }

        if (unknownF.get(name) && !unknownT.get(name)) {
            return 0;
        }

        return -1;
    }

    public boolean containsKnown(String name) {
        return known.containsKey(name);
    }

    public boolean containsUnknown(String name) {
        return unknownT.containsKey(name) || unknownF.containsKey(name);
    }

    public List<String> getNames() {
        List<String> names = new ArrayList<String>();

        for (String name : known.keySet()) {
            names.add(name);
        }

        for (String name : unknownT.keySet()) {
            names.add(name);
        }

        Collections.sort(names);

        return names;
    }

    public int size() {
        return known.size() + unknownT.size();
    }
}
