package yugioh.client.controller;

public class Utilities {

    public static void preprocessResult(String result) throws Exception {
        if (result.startsWith("Error: ")) throw new Exception(result);
    }

}
