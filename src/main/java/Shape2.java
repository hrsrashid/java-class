import java.util.Scanner;

abstract class Shape2 {
  public abstract double square();
  public abstract void init(Scanner scanner);
}

class Circle2 extends Shape2 {
  private double r;

  Circle2() {
    r = 0;
  }

  Circle2(double r) {
    this.r = r;
  }

  @Override
  public double square() {
    return Math.PI * r * r;
  }

  @Override
  public void init(Scanner scanner) {
    this.r = scanner.nextDouble();
  }

  @Override
  public String toString() {
    return String.format("Circle (r = %.2f) with area = %.4f", r, square());
  }
}


class Triangle2 extends Shape2 {

  private double a, b, c;

  Triangle2() {
    a = b = c = 0;
  }

  Triangle2(double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

	@Override
	public double square() {
    double p = perimeter() / 2;
		return Math.sqrt(p*(p - a)*(p - b)*(p - c));
  }

  public double perimeter() {
    return a + b + c;
  }

	@Override
	public void init(Scanner scanner) {
    a = scanner.nextDouble();
    b = scanner.nextDouble();
    c = scanner.nextDouble();
  }

  @Override
  public String toString() {
    return String.format("Triangle (%.2f %.2f %.2f) with area = %.4f", a, b, c, square());
  }
}


class Rectangle2 extends Shape2 {

  protected double a, b;

  Rectangle2() {
    a = b = 0;
  }

  Rectangle2(double a, double b) {
    this.a = a;
    this.b = b;
  }

	@Override
	public double square() {
		return a * b;
	}

	@Override
	public void init(Scanner scanner) {
    a = scanner.nextDouble();
    b = scanner.nextDouble();
  }

  @Override
  public String toString() {
    return String.format("Rectangle (%.2f x %.2f) with area = %.4f", a, b, square());
  }
}


class Square2 extends Rectangle2 {

  Square2() {
    a = 0;
  }

  Square2(double a) {
    super(a, a);
  }

  @Override
  public void init(Scanner scanner) {
    a = scanner.nextDouble();
    b = a;
  }

  @Override
  public String toString() {
    return String.format("Square (%.2f) with area = %.4f", a, square());
  }
}


class ShapeApp2 {
  public static void main(String[] args) {
    Shape2[] shapes = new Shape2[]{
      new Circle2(),
      new Triangle2(),
      new Rectangle2(),
      new Square2()
    };

    Scanner scanner = new Scanner(System.in);

    for (Shape2 shape : shapes) {
      shape.init(scanner);
    }

    System.out.println("------------- Shapes -----------");

    for (Shape2 shape : shapes) {
      System.out.println(shape);
    }
  }
}
