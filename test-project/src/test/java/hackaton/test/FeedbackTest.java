package hackaton.test;

import java.util.List;

import hackaton.communication.exceptions.CommunicationException;
import hackaton.communication.exceptions.KeystoreException;

/**
 * A command-line test facility for the Feedback Service.
 * <p>Example:  <code>java -cp "[required libraries]" FeedbackTest keystore.p12 mypass</code></p>
 * 
 * <p>By default, this test uses the sandbox service.  To switch, add "production" as a third parameter:</p>
 * <p>Example:  <code>java -cp "[required libraries]" FeedbackTest keystore.p12 mypass production</code></p>
 * 
 * @author Sylvain Pedneault
 */
public class FeedbackTest extends TestFoundation {
  /**
   * Execute this class from the command line to run tests.
   * 
   * @param args
   */
  public static void main(String[] args) {

    /* Verify that the test is being invoked */
    if (!verifyCorrectUsage(FeedbackTest.class, args, "keystore-path", "keystore-password", "[production|sandbox]"))
      return;

    /* Get a list of inactive devices */
    feedbackTest(args);
  }

  private FeedbackTest() {
  }

  private static void feedbackTest(String[] args) {
    String keystore = args[0];
    String password = args[1];
    boolean production = args.length >= 3 ? args[2].equalsIgnoreCase("production") : false;
    try {
      List<hackaton.devices.Device> devices = hackaton.Push.feedback(keystore, password, production);

      for (hackaton.devices.Device device : devices) {
        System.out.println("Inactive device: " + device.getToken());
      }
    } catch (CommunicationException e) {
      e.printStackTrace();
    } catch (KeystoreException e) {
      e.printStackTrace();
    }
  }
}