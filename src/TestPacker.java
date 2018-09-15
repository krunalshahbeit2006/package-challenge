import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;

public class TestPacker {

    public static void main(String[] args) {
        // write your code here
        try {
            String solution = Packer.pack("C:/workspace/temp/assessment/PackageChallenge/src/TestFile.txt");
            /*System.out.println();
            System.out.println("solution: " + solution);
            System.out.println();*/
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

}
