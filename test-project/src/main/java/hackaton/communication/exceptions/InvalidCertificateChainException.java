package hackaton.communication.exceptions;

/**
 * Thrown when we try to contact Apple with an invalid keystore or certificate chain.
 * @author Sylvain Pedneault
 *
 */
public class InvalidCertificateChainException extends KeystoreException {
  /**
   * Constructor
   */
  public InvalidCertificateChainException() {
    super("Invalid certificate chain!  Verify that the keystore you provided was produced according to specs...");
  }

  /**
   * Constructor with custom message
   * @param message
   */
  public InvalidCertificateChainException(String message) {
    super("Invalid certificate chain (" + message + ")!  Verify that the keystore you provided was produced according to specs...");
  }
}