interface Shape {
  double perimeter();
  double square();
  void print();
}

class Rectangle implements Shape {

  double a, b;

  Rectangle(double a, double b) {
    this.a = a;
    this.b = b;
  }

	@Override
	public double perimeter() {
		return 2 * (a + b);
	}

	@Override
	public double square() {
		return a * b;
  }

  @Override
  public String toString() {
    return String.format("w: %.2f, h: %.2f; S: %.2f, P: %.2f", a, b, square(), perimeter());
  }

  @Override
  public void print() {
    System.out.println(this);
  }
}

class Circle implements Shape {

  double r;

  Circle(double r) {
    this.r = r;
  }

  @Override
  public double perimeter() {
    return 2 * Math.PI * r;
  }

  @Override
  public double square() {
    return Math.PI * r * r;
  }

  @Override
  public String toString() {
    return String.format("r: %.2f; S: %.2f, P: %.2f", r, square(), perimeter());
  }

  @Override
  public void print() {
    System.out.println(this);
  }
}

class Trapezoid implements Shape {

  double a, b, c, d;

  Trapezoid(double a, double b, double c, double d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }

	@Override
	public double perimeter() {
		return a + b + c + d;
	}

	@Override
	public double square() {
		return (a + b) / 2 * Math.sqrt(c*c - (Math.pow((b-a), 2) + c*c - d*d)/(2*(b-a)));
  }

  @Override
  public String toString() {
    return String.format(
      "a: %.2f, b: %.2f, c: %.2f, d: %.2f; S: %.2f, P: %.2f",
      a, b, c, d,
      square(), perimeter()
      );
  }

  @Override
  public void print() {
    System.out.println(this);
  }
}


class ShapeApp {
  public static void main(String[] args) {
    Shape[] shapes = new Shape[] {
      new Rectangle(2.134, 8.43984),
      new Circle(3.14159),
      new Trapezoid(1.14343, 2.498349, 3.9841834, 4.438149)
    };

    for (Shape shape : shapes) {
      shape.print();
    }
  }
}
