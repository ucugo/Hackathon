package hackaton.feedback;

import java.security.KeyStore;

import hackaton.communication.ConnectionToAppleServer;
import hackaton.communication.exceptions.KeystoreException;
import hackaton.notification.AppleNotificationServer;


public class ConnectionToFeedbackServer extends ConnectionToAppleServer {
  public ConnectionToFeedbackServer(AppleFeedbackServer feedbackServer) throws KeystoreException {
    super(feedbackServer);
  }

  public ConnectionToFeedbackServer(AppleNotificationServer server, KeyStore keystore) {
    super(server, keystore);
  }

  @Override
  public String getServerHost() {
    return ((AppleFeedbackServer) getServer()).getFeedbackServerHost();
  }

  @Override
  public int getServerPort() {
    return ((AppleFeedbackServer) getServer()).getFeedbackServerPort();
  }
}