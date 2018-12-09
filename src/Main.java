import utils.FileUtil;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            FileUtil.write(3, "./input.data");
            int input = FileUtil.read("./input.data");
            System.out.println(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
