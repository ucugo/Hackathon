package hackaton.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

/**
 * Specific test cases intended for the project's developers.
 * 
 * @author Sylvain Pedneault
 */
public class SpecificNotificationTests extends TestFoundation {

  /**
   * Execute this class from the command line to run tests.
   * 
   * @param args
   */
  public static void main(String[] args) {

    if (!verifyCorrectUsage(NotificationTest.class, args, "keystore-path", "keystore-password", "device-token", "[production|sandbox]", "[test-name]")) return;

    runTest(args);
  }

  private SpecificNotificationTests() {
  }

  /**
   * Push a test notification to a device, given command-line parameters.
   * 
   * @param args
   */
  private static void runTest(String[] args) {
    String keystore = args[0];
    String password = args[1];
    String token = args[2];
    boolean production = args.length >= 4 ? args[3].equalsIgnoreCase("production") : false;

    String testName = args.length >= 5 ? args[4] : null;
    if (testName == null || testName.length() == 0) testName = "default";

    try {
      SpecificNotificationTests.class.getDeclaredMethod("test_" + testName, String.class, String.class, String.class, boolean.class).invoke(null, keystore, password, token, production);
    } catch (NoSuchMethodException e) {
      System.out.println(String.format("Error: test '%s' not found.  Test names are case-sensitive", testName));
    } catch (Exception e) {
      (e.getCause() != null ? e.getCause() : e).printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private static void test_PushHelloWorld(String keystore, String password, String token, boolean production) throws hackaton.communication.exceptions.CommunicationException, hackaton.communication.exceptions.KeystoreException {
    List<hackaton.notification.PushedNotification> notifications = hackaton.Push.alert("Hello World!", keystore, password, production, token);
    NotificationTest.printPushedNotifications(notifications);
  }

  @SuppressWarnings("unused")
  private static void test_Issue74(String keystore, String password, String token, boolean production) {
    try {
      System.out.println("");
      System.out.println("TESTING 257-BYTES PAYLOAD WITH SIZE ESTIMATION ENABLED");
      /*
       * Expected result: PayloadMaxSizeProbablyExceededException when the alert
       * is added to the payload
       */
      pushSpecificPayloadSize(keystore, password, token, production, true, 257);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      System.out.println("");
      System.out.println("TESTING 257-BYTES PAYLOAD WITH SIZE ESTIMATION DISABLED");
      /*
       * Expected result: PayloadMaxSizeExceededException when the payload is
       * pushed
       */
      pushSpecificPayloadSize(keystore, password, token, production, false, 257);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      System.out.println("");
      System.out.println("TESTING 256-BYTES PAYLOAD");
      /* Expected result: no exception */
      pushSpecificPayloadSize(keystore, password, token, production, false, 256);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private static void test_Issue75(String keystore, String password, String token, boolean production) {
    try {
      System.out.println("");
      System.out.println("TESTING 257-BYTES PAYLOAD WITH SIZE ESTIMATION ENABLED");
      hackaton.notification.NewsstandNotificationPayload payload = hackaton.notification.NewsstandNotificationPayload.contentAvailable();
      debugPayload(payload);

      List<hackaton.notification.PushedNotification> notifications = hackaton.Push.payload(payload, keystore, password, production, token);
      NotificationTest.printPushedNotifications(notifications);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private static void test_Issue82(String keystore, String password, String token, boolean production) {
    try {
      System.out.println("");
      hackaton.notification.Payload payload = hackaton.notification.PushNotificationPayload.test();

      System.out.println("TESTING ISSUE #82 PART 1");
      List<hackaton.notification.PushedNotification> notifications = hackaton.Push.payload(payload, keystore, password, production, 1, token);
      NotificationTest.printPushedNotifications(notifications);
      System.out.println("ISSUE #82 PART 1 TESTED");

      System.out.println("TESTING ISSUE #82 PART2");
      hackaton.notification.AppleNotificationServer server = new hackaton.notification.AppleNotificationServerBasicImpl(keystore, password, production);
      hackaton.notification.transmission.NotificationThread thread = new hackaton.notification.transmission.NotificationThread(new hackaton.notification.PushNotificationManager(), server, payload, token);
      thread.setListener(NotificationTest.DEBUGGING_PROGRESS_LISTENER);
      thread.start();
      System.out.println("ISSUE #82 PART 2 TESTED");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private static void test_Issue87(String keystore, String password, String token) {
    try {
      System.out.println("TESTING ISSUES #87 AND #88");

      try (InputStream ks = new BufferedInputStream(new FileInputStream(keystore));) {
        hackaton.notification.transmission.PushQueue queue = hackaton.Push.queue(ks, password, false, 3);
        queue.start();
        queue.add(hackaton.notification.PushNotificationPayload.test(), token);
        queue.add(hackaton.notification.PushNotificationPayload.test(), token);
        queue.add(hackaton.notification.PushNotificationPayload.test(), token);
        queue.add(hackaton.notification.PushNotificationPayload.test(), token);
        Thread.sleep(10000);
        List<Exception> criticalExceptions = queue.getCriticalExceptions();
        for (Exception exception : criticalExceptions) {
          exception.printStackTrace();
        }
        Thread.sleep(10000);

        List<hackaton.notification.PushedNotification> pushedNotifications = queue.getPushedNotifications();
        NotificationTest.printPushedNotifications("BEFORE CLEAR:", pushedNotifications);

        queue.clearPushedNotifications();

        pushedNotifications = queue.getPushedNotifications();
        NotificationTest.printPushedNotifications("AFTER CLEAR:", pushedNotifications);

        Thread.sleep(50000);
        System.out.println("ISSUES #87 AND #88 TESTED");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private static void test_Issue88(String keystore, String password, String token) {
    try {
      System.out.println("TESTING ISSUES #88");

      // List<String> devices = new Vector<String>();
      // for (int i = 0; i < 5; i++) {
      // devices.add(token);
      // }
      // PushedNotifications notifications =
      // Push.payload(PushNotificationPayload.test(), keystore, password, false,
      // devices);
      hackaton.notification.transmission.PushQueue queue = hackaton.Push.queue(keystore, password, false, 1);
      queue.start();
      queue.add(hackaton.notification.PushNotificationPayload.test(), token);
      queue.add(hackaton.notification.PushNotificationPayload.test(), token);
      queue.add(hackaton.notification.PushNotificationPayload.test(), token);
      queue.add(hackaton.notification.PushNotificationPayload.test(), token);
      Thread.sleep(10000);

      hackaton.notification.PushedNotifications notifications = queue.getPushedNotifications();
      NotificationTest.printPushedNotifications(notifications);

      Thread.sleep(5000);
      System.out.println("ISSUES #88 TESTED");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private static void test_Issue99(String keystore, String password, String token, boolean production) {
    try {
      System.out.println("");
      System.out.println("TESTING ISSUE #99");
      hackaton.notification.PushNotificationPayload payload = hackaton.notification.PushNotificationPayload.complex();
      payload.addCustomAlertBody("Hello World!");
      payload.addCustomAlertActionLocKey(null);
      debugPayload(payload);

      List<hackaton.notification.PushedNotification> notifications = hackaton.Push.payload(payload, keystore, password, production, token);
      NotificationTest.printPushedNotifications(notifications);
      System.out.println("ISSUE #99 TESTED");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private static void test_Issue102(String keystore, String password, String token, boolean production) {
    try {
      System.out.println("");
      System.out.println("TESTING ISSUE #102");
      int devices = 10000;
      int threads = 20;
      boolean simulation = false;
      String realToken = token;
      token = null;

      try {

        System.out.println("Creating PushNotificationManager and AppleNotificationServer");
        hackaton.notification.AppleNotificationServer server = new hackaton.notification.AppleNotificationServerBasicImpl(keystore, password, production);
        System.out.println("Creating payload (simulation mode)");
        // Payload payload = PushNotificationPayload.alert("Hello World!");
        hackaton.notification.Payload payload = hackaton.notification.PushNotificationPayload.test();

        System.out.println("Generating " + devices + " fake devices");
        List<hackaton.devices.Device> deviceList = new ArrayList<>(devices);
        for (int i = 0; i < devices; i++) {
          String tokenToUse = token;
          if (tokenToUse == null || tokenToUse.length() != 64) {
            tokenToUse = "123456789012345678901234567890123456789012345678901234567" + (1000000 + i);
          }
          deviceList.add(new hackaton.devices.implementations.basic.BasicDevice(tokenToUse));
        }
        deviceList.add(new hackaton.devices.implementations.basic.BasicDevice(realToken));
        System.out.println("Creating " + threads + " notification threads");
        hackaton.notification.transmission.NotificationThreads work = new hackaton.notification.transmission.NotificationThreads(server, simulation ? payload.asSimulationOnly() : payload, deviceList, threads);
        // work.setMaxNotificationsPerConnection(10000);
        // System.out.println("Linking notification work debugging listener");
        // work.setListener(DEBUGGING_PROGRESS_LISTENER);

        System.out.println("Starting all threads...");
        long timestamp1 = System.currentTimeMillis();
        work.start();
        System.out.println("All threads started, waiting for them...");
        work.waitForAllThreads();
        long timestamp2 = System.currentTimeMillis();
        System.out.println("All threads finished in " + (timestamp2 - timestamp1) + " milliseconds");

        NotificationTest.printPushedNotifications(work.getSuccessfulNotifications());

      } catch (Exception e) {
        e.printStackTrace();
      }

      // List<PushedNotification> notifications = Push.payload(payload,
      // keystore, password, production, token);
      // NotificationTest.printPushedNotifications(notifications);
      System.out.println("ISSUE #102 TESTED");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private static void test_ThreadPoolFeature(String keystore, String password, String token, boolean production) throws Exception {
    try {
      System.out.println("");
      System.out.println("TESTING THREAD POOL FEATURE");

      hackaton.notification.AppleNotificationServer server = new hackaton.notification.AppleNotificationServerBasicImpl(keystore, password, production);
      hackaton.notification.transmission.NotificationThreads pool = new hackaton.notification.transmission.NotificationThreads(server, 3).start();
      hackaton.devices.Device device = new hackaton.devices.implementations.basic.BasicDevice(token);

      System.out.println("Thread pool started and waiting...");

      System.out.println("Sleeping 5 seconds before queuing payloads...");
      Thread.sleep(5 * 1000);

      for (int i = 1; i <= 4; i++) {
        hackaton.notification.Payload payload = hackaton.notification.PushNotificationPayload.alert("Test " + i);
        hackaton.notification.transmission.NotificationThread threadForPayload = (hackaton.notification.transmission.NotificationThread) pool.add(new hackaton.notification.PayloadPerDevice(payload, device));
        System.out.println("Queued payload " + i + " to " + threadForPayload.getThreadNumber());
        System.out.println("Sleeping 10 seconds before queuing another payload...");
        Thread.sleep(10 * 1000);
      }
      System.out.println("Sleeping 10 more seconds let threads enough times to push the latest payload...");
      Thread.sleep(10 * 1000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void pushSpecificPayloadSize(String keystore, String password, String token, boolean production, boolean checkWhenAdding, int targetPayloadSize) throws hackaton.communication.exceptions.CommunicationException, hackaton.communication.exceptions.KeystoreException, JSONException {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < targetPayloadSize - 20; i++)
      buf.append('x');

    String alertMessage = buf.toString();
    hackaton.notification.PushNotificationPayload payload = hackaton.notification.PushNotificationPayload.complex();
    if (checkWhenAdding) payload.setPayloadSizeEstimatedWhenAdding(true);
    debugPayload(payload);

    boolean estimateValid = payload.isEstimatedPayloadSizeAllowedAfterAdding("alert", alertMessage);
    System.out.println("Payload size estimated to be allowed: " + (estimateValid ? "yes" : "no"));
    payload.addAlert(alertMessage);
    debugPayload(payload);

    List<hackaton.notification.PushedNotification> notifications = hackaton.Push.payload(payload, keystore, password, production, token);
    NotificationTest.printPushedNotifications(notifications);
  }

  private static void debugPayload(hackaton.notification.Payload payload) {
    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    try {
      System.out.println("Payload size: " + payload.getPayloadSize());
    } catch (Exception e) {
      // swallow
    }
    try {
      System.out.println("Payload representation: " + payload);
    } catch (Exception e) {
      // swallow
    }
    System.out.println(payload.isPayloadSizeEstimatedWhenAdding() ? "Payload size is estimated when adding properties" : "Payload size is only checked when it is complete");
    System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
  }
}