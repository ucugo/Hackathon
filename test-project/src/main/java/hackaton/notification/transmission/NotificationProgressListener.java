package hackaton.notification.transmission;

/**
 * <h1>An event listener for monitoring progress of NotificationThreads</h1>
 * 
 * @author Sylvain Pedneault
 */
public interface NotificationProgressListener {
  public void eventAllThreadsStarted(NotificationThreads notificationThreads);

  public void eventThreadStarted(hackaton.notification.transmission.NotificationThread notificationThread);

  public void eventThreadFinished(hackaton.notification.transmission.NotificationThread notificationThread);

  public void eventConnectionRestarted(hackaton.notification.transmission.NotificationThread notificationThread);

  public void eventAllThreadsFinished(NotificationThreads notificationThreads);

  public void eventCriticalException(hackaton.notification.transmission.NotificationThread notificationThread, Exception exception);
}