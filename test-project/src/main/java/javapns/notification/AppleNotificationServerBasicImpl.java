package javapns.notification;

import javapns.communication.AppleServerBasicImpl;
import javapns.communication.ConnectionToAppleServer;
import javapns.communication.exceptions.KeystoreException;

/**
 * Basic implementation of the AppleNotificationServer interface,
 * intended to facilitate rapid deployment.
 * 
 * @author Sylvain Pedneault
 */
public class AppleNotificationServerBasicImpl extends AppleServerBasicImpl implements AppleNotificationServer {
  private String host;
  private int port;

  /**
   * Communication settings for interacting with Apple's default production or sandbox notification server.
   * This constructor uses the recommended keystore type "PCKS12".
   * 
   * @param keystore a keystore containing your private key and the certificate signed by Apple (File, InputStream, byte[], KeyStore or String for a file path)
   * @param password the keystore's password
   * @param production true to use Apple's production servers, false to use the sandbox
   * @throws javapns.communication.exceptions.KeystoreException thrown if an error occurs when loading the keystore
   */
  public AppleNotificationServerBasicImpl(Object keystore, String password, boolean production) throws KeystoreException {
    this(keystore, password, ConnectionToAppleServer.KEYSTORE_TYPE_PKCS12, production);
  }

  /**
   * Communication settings for interacting with Apple's default production or sandbox notification server.
   * 
   * @param keystore a keystore containing your private key and the certificate signed by Apple (File, InputStream, byte[], KeyStore or String for a file path)
   * @param password the keystore's password
   * @param type the keystore's type
   * @param production true to use Apple's production servers, false to use the sandbox
   * @throws javapns.communication.exceptions.KeystoreException thrown if an error occurs when loading the keystore
   */
  public AppleNotificationServerBasicImpl(Object keystore, String password, String type, boolean production) throws KeystoreException {
    this(keystore, password, type, production ? PRODUCTION_HOST : DEVELOPMENT_HOST, production ? PRODUCTION_PORT : DEVELOPMENT_PORT);
  }

  /**
   * Communication settings for interacting with a specific Apple Push Notification Server.
   * 
   * @param keystore a keystore containing your private key and the certificate signed by Apple (File, InputStream, byte[], KeyStore or String for a file path)
   * @param password the keystore's password
   * @param type the keystore's type
   * @param host a specific APNS host
   * @param port a specific APNS port
   * @throws javapns.communication.exceptions.KeystoreException thrown if an error occurs when loading the keystore
   */
  public AppleNotificationServerBasicImpl(Object keystore, String password, String type, String host, int port) throws KeystoreException {
    super(keystore, password, type);
    this.host = host;
    this.port = port;
  }

  @Override
  public String getNotificationServerHost() {
    return host;
  }

  @Override
  public int getNotificationServerPort() {
    return port;
  }
}