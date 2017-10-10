import java.text.DecimalFormat;

abstract class Employee {
  String name, position, location;

  Employee(String name, String position, String location) {
    this.name = name;
    this.position = position;
    this.location = location;
  }

  public abstract double getPayment();

  @Override
  public String toString() {
    final String payment = DecimalFormat.getCurrencyInstance().format(getPayment());
    return String.format("%s %s %s %s", name, position, location, payment);
  }

  /**
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the position
   */
  public String getPosition() {
    return position;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param position the position to set
   */
  public void setPosition(String position) {
    this.position = position;
  }
}

class StaffEmployee extends Employee {
  private double paymentBase;

  StaffEmployee(String name, String position, String location, double paymentBase) {
    super(name, position, location);
    this.paymentBase = paymentBase;
  }

  /**
   * @return the paymentBase
   */
  public double getPaymentBase() {
    return paymentBase;
  }

  /**
   * @param paymentBase the paymentBase to set
   */
  public void setPaymentBase(double paymentBase) {
    this.paymentBase = paymentBase;
  }

  @Override
  public double getPayment() {
    return paymentBase * 0.87;
  }
}


class HourlyEmployee extends Employee {

  private double hourlyRate, hoursSpent;

  HourlyEmployee(String name, String position, String location, double hourlyRate, double hoursSpent) {
    super(name, position, location);
    this.hourlyRate = hourlyRate;
    this.hoursSpent = hoursSpent;
  }

  /**
   * @return the hourlyRate
   */
  public double getHourlyRate() {
    return hourlyRate;
  }

  /**
   * @param hourlyRate the hourlyRate to set
   */
  public void setHourlyRate(double hourlyRate) {
    this.hourlyRate = hourlyRate;
  }

  /**
   * @return the hoursSpent
   */
  public double getHoursSpent() {
    return hoursSpent;
  }

  /**
   * @param hoursSpent the hoursSpent to set
   */
  public void setHoursSpent(double hoursSpent) {
    this.hoursSpent = hoursSpent;
  }

	@Override
	public double getPayment() {
		return hourlyRate * hoursSpent * 0.87;
	}

}

class Salesman extends Employee {

  private double fee, earned;

  Salesman(String name, String position, String location, double fee, double earned) {
    super(name, position, location);
    this.fee = fee;
    this.earned = earned;
  }

  /**
   * @return the fee
   */
  public double getFee() {
    return fee;
  }

  /**
   * @return the earned
   */
  public double getEarned() {
    return earned;
  }

  /**
   * @param fee the fee to set
   */
  public void setFee(double fee) {
    this.fee = fee;
  }

  /**
   * @param earned the earned to set
   */
  public void setEarned(double earned) {
    this.earned = earned;
  }

	@Override
	public double getPayment() {
		return fee * earned * 0.87;
	}

}

class EmployeeApp {
  public static void main(String[] args) {
    Employee emp;
    StaffEmployee staffEmp = new StaffEmployee("Jon", "engineer", "IT dep", 100000);
    HourlyEmployee hrlEmpl = new HourlyEmployee("Sam", "designer", "home", 50, 100);
    Salesman saleEmp = new Salesman("Steve", "CEO", "headquarters", .75, 999999999);
    emp = staffEmp;
    System.out.println(emp);
    emp = hrlEmpl;
    System.out.println(emp);
    emp = saleEmp;
    System.out.println(emp);
  }
}
