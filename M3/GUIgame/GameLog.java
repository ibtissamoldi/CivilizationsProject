package M3.GUIgame;



public class GameLog {
	
    public static DialogPanel log;

    public static void info(String msg) {
        log.AddMessage("[INFO] " + msg);
    }

    public static void error(String msg) {
        log.AddMessage("[ERROR] " + msg);
    }

    public static void warning(String msg) {
        log.AddMessage("[WARNING] " + msg);
    }

    public static void battle(String msg) {
        log.AddMessage("⚔ [BATTLE] " + msg);
    }
}



