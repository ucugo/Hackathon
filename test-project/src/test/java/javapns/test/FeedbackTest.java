package javapns.test;

import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;

/**
 * A command-line test facility for the Feedback Service.
 * <p>Example:  <code>java -cp "[required libraries]" javapns.test.FeedbackTest keystore.p12 mypass</code></p>
 * 
 * <p>By default, this test uses the sandbox service.  To switch, add "production" as a third parameter:</p>
 * <p>Example:  <code>java -cp "[required libraries]" javapns.test.FeedbackTest keystore.p12 mypass production</code></p>
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
      List<Device> devices = Push.feedback(keystore, password, production);

      for (Device device : devices) {
        System.out.println("Inactive device: " + device.getToken());
      }
    } catch (CommunicationException e) {
      e.printStackTrace();
    } catch (KeystoreException e) {
      e.printStackTrace();
    }
  }
}