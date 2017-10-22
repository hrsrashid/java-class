import java.util.Arrays;

class Transport {
  protected double distance, capacity, velocity;

  Transport(double distance, double capacity, double velocity) {
    this.distance = distance;
    this.capacity = capacity;
    this.velocity = velocity;
  }

  public double countTrafficPerDay() {
    return capacity * velocity / distance;
  }

  /**
   * @return the capacity
   */
  public double getCapacity() {
    return capacity;
  }

  /**
   * @return the distance
   */
  public double getDistance() {
    return distance;
  }

  /**
   * @return the velocity
   */
  public double getVelocity() {
    return velocity;
  }

  /**
   * @param capacity the capacity to set
   */
  public void setCapacity(double capacity) {
    this.capacity = capacity;
  }

  /**
   * @param distance the distance to set
   */
  public void setDistance(double distance) {
    this.distance = distance;
  }

  /**
   * @param velocity the velocity to set
   */
  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }
}


class TransportMethod extends Transport {

  protected String type, name;
  protected double price;

  TransportMethod(double distance, double capacity, double velocity, String type, String name, double price) {
    super(distance, capacity, velocity);
    this.type = type;
    this.name = name;
    this.price = price;
  }

  public double countTotalPrice() {
    return price * distance / capacity;
  }

  @Override
  public String toString() {
    return String.format("%10s | %10s | %6.2f | %6.2f | %6.2f | $%10.2f | $%10.2f",
      type, name, distance, capacity, velocity, price, countTotalPrice()
    );
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the price
   */
  public double getPrice() {
    return price;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(double price) {
    this.price = price;
  }
}


class TransportApp {
  public static void main(String[] args) {
    TransportMethod[] transports = new TransportMethod[100];

    for (int i = 0; i < transports.length; i++) {
      transports[i] = new TransportMethod(Math.random()*100, Math.random()*10, Math.random() * 30, "Type " + i, "Name " + i, Math.random() * 1000);
    }

    Arrays.sort(transports, (tm1, tm2) -> (int)Math.ceil(tm1.countTotalPrice() - tm2.countTotalPrice()));

    System.out.println(String.format("%3s | %10s | %10s | %6s | %6s | %6s | %11s | %11s",
      "#", "type", "name", "dist", "cap", "vel", "price", "total"
    ));

    System.out.println("-----------------------------------------------------------------------------------------");

    for (int i = 0; i < transports.length; i++) {
      System.out.println(String.format("%3d | %s", i + 1, transports[i]));
    }
  }
}
