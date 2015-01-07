package hackaton.communication;

import java.io.*;

/**
 * A basic and abstract implementation of the AppleServer interface
 * intended to facilitate rapid deployment.
 * 
 * @author Sylvain Pedneault
 */
public abstract class AppleServerBasicImpl implements AppleServer {

  private Object keystore;
  private final String password;
  private final String type;
  private String proxyHost;
  private int proxyPort;

  /**
   * Constructs a AppleServerBasicImpl object.
   * 
   * @param keystore The keystore to use (can be a File, an InputStream, a String for a file path, or a byte[] array)
   * @param password The keystore's password
   * @param type The keystore type (typically PKCS12)
   * @throws hackaton.communication.exceptions.KeystoreException thrown if an error occurs when loading the keystore
   */
  public AppleServerBasicImpl(Object keystore, String password, String type) throws hackaton.communication.exceptions.KeystoreException {
    KeystoreManager.validateKeystoreParameter(keystore);
    this.keystore = keystore;
    this.password = password;
    this.type = type;

    /* Make sure that the keystore reference is reusable. */
    this.keystore = KeystoreManager.ensureReusableKeystore(this, this.keystore);
  }

  @Override
  public InputStream getKeystoreStream() throws hackaton.communication.exceptions.InvalidKeystoreReferenceException {
    return KeystoreManager.streamKeystore(keystore);
  }

  @Override
  public String getKeystorePassword() {
    return password;
  }

  @Override
  public String getKeystoreType() {
    return type;
  }

  @Override
  public String getProxyHost() {
    return proxyHost;
  }

  @Override
  public int getProxyPort() {
    return proxyPort;
  }

  @Override
  public void setProxy(String proxyHost, int proxyPort) {
    this.proxyHost = proxyHost;
    this.proxyPort = proxyPort;
  }

}
