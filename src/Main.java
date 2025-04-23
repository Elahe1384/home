import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SmartHomeSystem system = new SmartHomeSystem();

        int q = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < q; i++) {
            String[] command = scanner.nextLine().split(" ");
            String cmd = command[0];
            String result;

            switch (cmd) {
                case "add_device":
                    result = system.addDevice(command[1], command[2], command[3]);
                    break;
                case "set_device":
                    result = system.setDevice(command[1], command[2], command[3]);
                    break;
                case "remove_device":
                    result = system.removeDevice(command[1]);
                    break;
                case "list_devices":
                    result = system.listDevices();
                    break;
                case "add_rule":
                    result = system.addRule(command[1], command[2], command[3]);
                    break;
                case "check_rules":
                    result = system.checkRules(command[1]);
                    break;
                case "list_rules":
                    result = system.listRules();
                    break;
                default:
                    result = "invalid command";
            }

            System.out.println(result);
        }

        scanner.close();
    }
}