class Ship {
  protected double width, length, displacement, maxSpeed, x, y;
  protected String captain;

  Ship(String captain, double width, double length,
    double displacement, double maxSpeed, double x, double y) {

    this.captain = captain;
    this.width = width;
    this.length = length;
    this.displacement = displacement;
    this.maxSpeed = maxSpeed;
    this.x = x;
    this.y = y;
  }

  public void move(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getSpaceSize() {
    return width * length;
  }
}

class PassengerShip extends Ship {
  protected int maxPassengersCount, qualityRating, servicesCount, passengersCount = 0;
  protected double averageTicketPrice;

  PassengerShip(int maxPassengersCount, int qualityRating,
    int servicesCount, double averageTicketPrice,
    String captain, double width, double length,
    double displacement, double maxSpeed, double x, double y) {

    super(captain, width, length, displacement, maxSpeed, x, y);

    this.maxPassengersCount = maxPassengersCount;
    this.qualityRating = qualityRating;
    this.servicesCount = servicesCount;
    this.averageTicketPrice = averageTicketPrice;
  }

  public boolean hasRoom() {
    return passengersCount < maxPassengersCount;
  }

  public int addPassenger() {
    return ++passengersCount;
  }
}

class MilitaryShip extends Ship {
  protected int gunsCount, rounds, crewCount;
  protected double fireRange;
  protected String country;

  MilitaryShip(int gunsCount, int rounds, int crewCount,
    double fireRange, String country,
    String captain, double width, double length,
    double displacement, double maxSpeed, double x, double y) {

    super(captain, width, length, displacement, maxSpeed, x, y);

    this.gunsCount = gunsCount;
    this.rounds = rounds;
    this.crewCount = crewCount;
    this.fireRange = fireRange;
    this.country = country;
  }

  public boolean hasAmmo(int guns) {
    return rounds >= guns;
  }

  public int fire(int guns) {
    if (hasAmmo(guns)) {
      rounds -= guns <= gunsCount ? guns : gunsCount;
    }

    return rounds;
  }
}

class Submarine extends MilitaryShip {
  protected double z, maxDepth, maxSubmergedSpeed, maxAutonomousTime;
  private boolean isSubmerged = false, periscoping = false;

  Submarine(double z, double maxDepth, double maxSubmergedSpeed,
    double maxAutonomousTime,
    int gunsCount, int rounds, int crewCount,
    double fireRange, String country,
    String captain, double width, double length,
    double displacement, double maxSpeed, double x, double y) {

    super(gunsCount, rounds, crewCount,
      fireRange, country,
      captain, width, length,
      displacement, maxSpeed, x, y);

    this.z = z;
    this.maxDepth = maxDepth;
    this.maxSubmergedSpeed = maxSubmergedSpeed;
    this.maxAutonomousTime = maxAutonomousTime;
  }

  public void submerge() {
    isSubmerged = true;
  }

  public void surface() {
    isSubmerged = false;
  }

  public boolean risePersicope() {
    if (isSubmerged) {
      periscoping = true;
    }

    return periscoping;
  }
  public void retractPeriscope() {
    periscoping = false;
  }
}
