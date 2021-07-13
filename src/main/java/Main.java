import control.DataManager;
import control.Listener;

public class Main {
    public static void main(String[] args) {
        DataManager dataManager = DataManager.getInstance();
        dataManager.loadData();
        new Listener(7355).run();
    }
}
