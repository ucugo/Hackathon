package hackaton.devices.implementations.basic;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import hackaton.devices.DeviceFactory;
import hackaton.devices.exceptions.DuplicateDeviceException;
import hackaton.devices.exceptions.NullIdException;


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
   * @throws hackaton.devices.exceptions.DuplicateDeviceException
   * @throws hackaton.devices.exceptions.NullIdException
   * @throws hackaton.devices.exceptions.NullDeviceTokenException
   */
  @Override
  public hackaton.devices.Device addDevice(String id, String token) throws DuplicateDeviceException, NullIdException, hackaton.devices.exceptions.NullDeviceTokenException, Exception {
    if ((id == null) || (id.trim().equals(""))) {
      throw new NullIdException();
    } else if ((token == null) || (token.trim().equals(""))) {
      throw new hackaton.devices.exceptions.NullDeviceTokenException();
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
   * @throws hackaton.devices.exceptions.UnknownDeviceException
   * @throws hackaton.devices.exceptions.NullIdException
   */
  @Override
  public hackaton.devices.Device getDevice(String id) throws hackaton.devices.exceptions.UnknownDeviceException, NullIdException {
    if ((id == null) || (id.trim().equals(""))) {
      throw new NullIdException();
    }
    if (this.devices.containsKey(id)) {
      return this.devices.get(id);
    }
    throw new hackaton.devices.exceptions.UnknownDeviceException();
  }

  /**
   * Remove a device
   * @param id The device id
   * @throws hackaton.devices.exceptions.UnknownDeviceException
   * @throws hackaton.devices.exceptions.NullIdException
   */
  @Override
  public void removeDevice(String id) throws hackaton.devices.exceptions.UnknownDeviceException, NullIdException {
    if ((id == null) || (id.trim().equals(""))) {
      throw new NullIdException();
    }
    if (this.devices.containsKey(id)) {
      this.devices.remove(id);
    } else {
      throw new hackaton.devices.exceptions.UnknownDeviceException();
    }
  }
}