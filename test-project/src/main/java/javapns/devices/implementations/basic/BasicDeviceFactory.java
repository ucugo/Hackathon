package javapns.devices.implementations.basic;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javapns.devices.Device;
import javapns.devices.DeviceFactory;
import javapns.devices.exceptions.DuplicateDeviceException;
import javapns.devices.exceptions.NullDeviceTokenException;
import javapns.devices.exceptions.NullIdException;
import javapns.devices.exceptions.UnknownDeviceException;


public class BasicDeviceFactory implements DeviceFactory {

  /* A map containing all the devices, identified with their id */
  private Map<String, BasicDevice> devices;

  /**
   * Constructs a VolatileDeviceFactory
   */
  public BasicDeviceFactory() {
    this.devices = new HashMap<>();
  }

  /**
   * Add a device to the map
   * @param id The device id
   * @param token The device token
   * @throws javapns.devices.exceptions.DuplicateDeviceException
   * @throws javapns.devices.exceptions.NullIdException
   * @throws javapns.devices.exceptions.NullDeviceTokenException
   */
  @Override
  public Device addDevice(String id, String token) throws DuplicateDeviceException, NullIdException, NullDeviceTokenException, Exception {
    if ((id == null) || (id.trim().equals(""))) {
      throw new NullIdException();
    } else if ((token == null) || (token.trim().equals(""))) {
      throw new NullDeviceTokenException();
    } else {
      if (!this.devices.containsKey(id)) {
        token = token.trim().replace(" ", "");
        BasicDevice device = new BasicDevice(id, token, new Timestamp(Calendar.getInstance().getTime().getTime()));
        this.devices.put(id, device);
        return device;
      }
      throw new DuplicateDeviceException();
    }
  }

  /**
   * Get a device according to his id
   * @param id The device id
   * @return The device
   * @throws javapns.devices.exceptions.UnknownDeviceException
   * @throws javapns.devices.exceptions.NullIdException
   */
  @Override
  public Device getDevice(String id) throws UnknownDeviceException, NullIdException {
    if ((id == null) || (id.trim().equals(""))) {
      throw new NullIdException();
    }
    if (this.devices.containsKey(id)) {
      return this.devices.get(id);
    }
    throw new UnknownDeviceException();
  }

  /**
   * Remove a device
   * @param id The device id
   * @throws javapns.devices.exceptions.UnknownDeviceException
   * @throws javapns.devices.exceptions.NullIdException
   */
  @Override
  public void removeDevice(String id) throws UnknownDeviceException, NullIdException {
    if ((id == null) || (id.trim().equals(""))) {
      throw new NullIdException();
    }
    if (this.devices.containsKey(id)) {
      this.devices.remove(id);
    } else {
      throw new UnknownDeviceException();
    }
  }
}