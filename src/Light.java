public class Light extends Device {
    private int brightness;

    Light(String name, String protocol1) {
        super(name, protocol1);
        this.brightness = 50;
    }

    @Override
    public boolean setProperty(String property, String value) {
        switch (property) {
            case "status":
                if(value.equals("on") || value.equals("off")) {
                    this.status = value;
                    return true;
                }
                return false;
            case "brightness":
                try {
                    int brightness = Integer.parseInt(value);
                    if (brightness >= 0 && brightness <= 100) {
                        this.brightness = brightness;
                        return true;
                    }
                    return false;
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public String getInfo() {
        return name + " " + status + " " + brightness + " % " + protocol1;
    }

}
