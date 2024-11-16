package datastorage;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class DataStorage {

    public static HashMap<String, Object> storage = new HashMap<>();

    public synchronized static <V> void putOnStorage(String key, V value) {
        storage.put(key, value);
        log.info(key + " are saved to DataStorage");
    }

    @SuppressWarnings("unchecked")
    public synchronized static <V> V getFromStorage(String key) {
        log.info(key + " are retrieved from DataStorage");
        return (V) storage.get(key);
    }

    public synchronized static void clearStorage() {
        storage.clear();
        log.info("Clearing DataStorage");
    }

}
