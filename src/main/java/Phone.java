class Phone {
  protected String manufacturer, connectivity;
  protected double batteryTime;

  Phone(String manufacturer, String connectivity, double batteryTime) {
    this.manufacturer = manufacturer;
    this.connectivity = connectivity;
    this.batteryTime = batteryTime;
  }

  /**
   * @return the batteryTime
   */
  public double getBatteryTime() {
    return batteryTime;
  }

  /**
   * @return the connectivity
   */
  public String getConnectivity() {
    return connectivity;
  }

  /**
   * @return the manufacturer
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * @param batteryTime the batteryTime to set
   */
  public void setBatteryTime(double batteryTime) {
    this.batteryTime = batteryTime;
  }

  /**
   * @param connectivity the connectivity to set
   */
  public void setConnectivity(String connectivity) {
    this.connectivity = connectivity;
  }

  /**
   * @param manufacturer the manufacturer to set
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  @Override
  public String toString() {
    return String.format("%s (%s) ~ %.1f hrs.", manufacturer, connectivity, batteryTime);
  }

  public void print() {
    System.out.println(this);
  }
}


class Smartphone extends Phone {
  private int osVersion;

  Smartphone(String manufacturer, String connectivity, double batteryTime, int osVersion) {
    super(manufacturer, connectivity, batteryTime);
    this.osVersion = osVersion;
  }

  @Override
  public String toString() {
    return super.toString() + " v." + osVersion;
  }

  /**
   * @param osVersion the osVersion to set
   */
  public void setOsVersion(int osVersion) {
    this.osVersion = osVersion;
  }
}


class PhoneApp {
  public static void main(String[] args) {
    Phone phone = new Phone("Siemens", "GSM", 24);
    phone.print();
    phone = new Smartphone("Apple", "LTE", 11.5, 11);
    phone.print();
  }
}
