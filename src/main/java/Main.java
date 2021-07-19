import control.DataManager;
import control.Listener;

public class Main {

    public static void main(String[] args) {
        DataManager dataManager = DataManager.getInstance();
        dataManager.loadData();
        Listener listener = new Listener(7355);
        listener.run();
    }
}
