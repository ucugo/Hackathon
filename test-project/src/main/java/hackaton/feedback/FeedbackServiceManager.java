package hackaton.feedback;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.LinkedList;

import hackaton.communication.exceptions.CommunicationException;
import hackaton.communication.exceptions.KeystoreException;
import hackaton.devices.Device;
import hackaton.devices.DeviceFactory;
import hackaton.devices.implementations.basic.BasicDevice;
import hackaton.devices.implementations.basic.BasicDeviceFactory;

import javax.net.ssl.SSLSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("deprecation")
public class FeedbackServiceManager {
  protected static final Logger logger              = LoggerFactory.getLogger(FeedbackServiceManager.class);

  /* Length of the tuple sent by Apple */
  private static final int      FEEDBACK_TUPLE_SIZE = 38;

  @Deprecated
  private DeviceFactory         deviceFactory;

  /**
   * Constructs a FeedbackServiceManager with a supplied DeviceFactory.
   * 
   * @deprecated The DeviceFactory-based architecture is deprecated.
   */
  @Deprecated
  public FeedbackServiceManager(DeviceFactory deviceFactory) {
    this.setDeviceFactory(deviceFactory);
  }

  /**
   * Constructs a FeedbackServiceManager with a default basic DeviceFactory.
   */
  public FeedbackServiceManager() {
    this.setDeviceFactory(new BasicDeviceFactory());
  }

  /**
   * Retrieve all devices which have un-installed the application w/Path to
   * keystore
   * 
   * @param server
   *          Connection information for the Apple server
   * @return List of Devices
   * @throws IOException
   * @throws FileNotFoundException
   * @throws CertificateException
   * @throws NoSuchAlgorithmException
   * @throws KeyStoreException
   * @throws KeyManagementException
   * @throws UnrecoverableKeyException
   */
  /**
   * @throws hackaton.communication.exceptions.KeystoreException
   * @throws hackaton.communication.exceptions.CommunicationException
   */
  @SuppressWarnings("resource")
  public LinkedList<Device> getDevices(AppleFeedbackServer server) throws KeystoreException, CommunicationException {
    hackaton.feedback.ConnectionToFeedbackServer connectionHelper = new hackaton.feedback.ConnectionToFeedbackServer(server);
    SSLSocket socket = connectionHelper.getSSLSocket();
    return getDevices(socket);
  }

  /**
   * Retrieves the list of devices from an established SSLSocket.
   * 
   * @param socket
   * @return Devices
   * @throws hackaton.communication.exceptions.CommunicationException
   */
  private LinkedList<Device> getDevices(SSLSocket socket) throws CommunicationException {
    // Compute
    LinkedList<Device> listDev = null;
    try (InputStream socketStream = socket.getInputStream();) {
      // Read bytes
      byte[] b = new byte[1024];
      ByteArrayOutputStream message = new ByteArrayOutputStream();
      int nbBytes = 0;
      // socketStream.available can return 0
      // http://forums.sun.com/thread.jspa?threadID=5428561
      while ((nbBytes = socketStream.read(b, 0, 1024)) != -1) {
        message.write(b, 0, nbBytes);
      }

      listDev = new LinkedList<>();
      byte[] listOfDevices = message.toByteArray();
      int nbTuples = listOfDevices.length / FEEDBACK_TUPLE_SIZE;
      logger.debug("Found: [" + nbTuples + "]");
      for (int i = 0; i < nbTuples; i++) {
        int offset = i * FEEDBACK_TUPLE_SIZE;

        // Build date
        int index = 0;
        int firstByte = 0;
        int secondByte = 0;
        int thirdByte = 0;
        int fourthByte = 0;
        long anUnsignedInt = 0;

        firstByte = (0x000000FF & listOfDevices[offset]);
        secondByte = (0x000000FF & listOfDevices[offset + 1]);
        thirdByte = (0x000000FF & listOfDevices[offset + 2]);
        fourthByte = (0x000000FF & listOfDevices[offset + 3]);
        index = index + 4;
        anUnsignedInt = (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte) & 0xFFFFFFFFL;
        Timestamp timestamp = new Timestamp(anUnsignedInt * 1000);

        // Build device token length
        int deviceTokenLength = listOfDevices[offset + 4] << 8 | listOfDevices[offset + 5];

        // Build device token
        String deviceToken = "";
        int octet = 0;
        for (int j = 0; j < 32; j++) {
          octet = (0x000000FF & listOfDevices[offset + 6 + j]);
          deviceToken = deviceToken.concat(String.format("%02x", octet));
        }

        // Build device and add to list
        /*
         * Create a basic device, as we do not want to go through the factory
         * and create a device in the actual database...
         */
        Device device = new BasicDevice();
        device.setToken(deviceToken);
        device.setLastRegister(timestamp);
        listDev.add(device);
        logger.info("FeedbackManager retrieves one device :  " + timestamp + ";" + deviceTokenLength + ";" + deviceToken + ".");
      }

      // Close the socket and return the list

    } catch (Exception e) {
      logger.debug("Caught exception fetching devices from Feedback Service");
      throw new CommunicationException("Problem communicating with Feedback service", e);
    }

    finally {
      try {
        socket.close();
      } catch (Exception e) {
        // swallow
      }
    }
    return listDev;
  }

  // /**
  // * Set the proxy if needed
  // * @param host the proxyHost
  // * @param port the proxyPort
  // * @deprecated Configuring a proxy with this method affects overall JVM
  // proxy settings.
  // * Use AppleFeedbackServer.setProxy(..) to set a proxy for JavaPNS only.
  // */
  // public void setProxy(String host, String port) {
  // this.proxySet = true;
  //
  // System.setProperty("http.proxyHost", host);
  // System.setProperty("http.proxyPort", port);
  //
  // System.setProperty("https.proxyHost", host);
  // System.setProperty("https.proxyPort", port);
  // }

  /**
   * 
   * @param deviceFactory
   * @deprecated The DeviceFactory-based architecture is deprecated.
   */
  @Deprecated
  public void setDeviceFactory(DeviceFactory deviceFactory) {
    this.deviceFactory = deviceFactory;
  }

  /**
   * 
   * @return a device factory
   * @deprecated The DeviceFactory-based architecture is deprecated.
   */
  @Deprecated
  public DeviceFactory getDeviceFactory() {
    return deviceFactory;
  }

}