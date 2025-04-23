import java.util.*;

public class SmartHomeSystem {
    private Map<String, Device> devices = new HashMap<>();
    private List<Rule> rules = new ArrayList<>();

    public String addDevice(String type, String name, String protocol) {
        if (devices.containsKey(name))
            return "duplicate device name";

        if (!protocol.equals("WiFi") && !protocol.equals("Bluetooth"))
            return "invalid input";

        Device device = createDevice(type, name, protocol);
        if (device == null)
            return "invalid input";

        devices.put(name, device);
        return "device added successfully";
    }

    private Device createDevice(String type, String name, String protocol) {
        if (type.equals("light"))
            return new Light(name, protocol);
        if (type.equals("thermostat"))
            return new Thermostat(name, protocol);
        return null;
    }

    public String setDevice(String name, String property, String value) {
        if (!devices.containsKey(name))
            return "device not found";

        Device device = devices.get(name);
        if (device.setProperty(property, value))
            return "device updated successfully";

        return isPropertyValid(property) ? "invalid value" : "invalid property";
    }

    private boolean isPropertyValid(String property) {
        return property.equals("status") ||
                property.equals("brightness") ||
                property.equals("temperature");
    }

    public String removeDevice(String name) {
        if (!devices.containsKey(name))
            return "device not found";

        devices.remove(name);
        removeRulesForDevice(name);
        return "device removed successfully";
    }

    private void removeRulesForDevice(String name) {
        rules.removeIf(rule -> rule.getDeviceName().equals(name));
    }

    public String listDevices() {
        if (devices.isEmpty())
            return "";

        StringBuilder result = new StringBuilder();
        for (Device device : devices.values()) {
            if (result.length() > 0)
                result.append("\n");
            result.append(device.getInfo());
        }
        return result.toString();
    }

    public String addRule(String deviceName, String time, String action) {
        if (!devices.containsKey(deviceName))
            return "device not found";

        if (!isValidTime(time))
            return "invalid time";

        if (!action.equals("on") && !action.equals("off"))
            return "invalid action";

        if (hasDuplicateRule(deviceName, time))
            return "duplicate rule";

        rules.add(new Rule(deviceName, time, action));
        return "rule added successfully";
    }

    private boolean hasDuplicateRule(String deviceName, String time) {
        for (Rule rule : rules) {
            if (rule.getDeviceName().equals(deviceName) && rule.getTime().equals(time))
                return true;
        }
        return false;
    }

    public String checkRules(String time) {
        if (!isValidTime(time))
            return "invalid time";

        for (Rule rule : rules) {
            if (rule.getTime().equals(time)) {
                Device device = devices.get(rule.getDeviceName());
                if (device != null)
                    device.setProperty("status", rule.getAction());
            }
        }
        return "rules checked";
    }

    public String listRules() {
        if (rules.isEmpty())
            return "";

        return String.join("\n", rules.stream().map(Rule -> rules.toString()).toList());    }

    private boolean isValidTime(String time) {
        try {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
        } catch (Exception e) {
            return false;
        }
    }
}