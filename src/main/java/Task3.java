interface Pair<T> {
  void add(T x);
  void sub(T x);
  void mul(T x);
  void div(T x);
}


class Complex implements Pair<Complex> {

  private double re, im;

  Complex(double re, double im) {
    set(re, im);
  }

  /**
   * @return the re
   */
  public double getRe() {
    return re;
  }

  public void set(double re, double im) {
    this.re = re;
    this.im = im;
  }

  public void set(Complex c) {
    set(c.re, c.im);
  }

	@Override
	public void add(Complex x) {
    re += x.re;
    im += x.im;
	}

	@Override
	public void sub(Complex x) {
    re -= x.re;
    im -= x.im;
	}

	@Override
	public void mul(Complex x) {
    double new_re = re * x.re - im * x.im;
    im = re * x.im + x.re * im;
    re = new_re;
	}

	@Override
	public void div(Complex x) {
    Complex x_conj = x.getConjugated();
    mul(x_conj);
    x_conj.mul(x);
    re /= x_conj.getRe();
    im /= x_conj.getRe();
  }

  @Override
  public String toString() {
    return String.format("(%5.2f + %5.2fi)", re, im);
  }

  private Complex getConjugated() {
    return new Complex(re, -im);
  }
}


class FuzzyNumber implements Pair<FuzzyNumber> {

  private double a, x, b;

  FuzzyNumber(double a, double x, double b) {
    set(a, x, b);
  }

  public void set(double a, double x, double b) {
    this.a = a;
    this.b = b;
    this.x = x;
  }

  public void set(FuzzyNumber n) {
    set(n.a, n.x, n.b);
  }

  @Override
  public String toString() {
    return String.format("(%5.2f, %5.2f, %5.2f)", a, x, b);
  }

	@Override
	public void add(FuzzyNumber n) {
    double new_a = x + n.x - a - b,
      new_x = x + n.x;
    b = x + n.x + n.a + n.b;
    a = new_a;
    x = new_x;
	}

	@Override
	public void sub(FuzzyNumber n) {
    double new_a = x - n.x - a - b,
    new_x = x - n.x;
    b = x - n.x + n.a + n.b;
    a = new_a;
    x = new_x;
	}

	@Override
	public void mul(FuzzyNumber n) {
    double new_a = x * n.x - n.x * a - x * b + a * b,
    new_x = x * n.x;
    b = x * n.x + n.x * n.a + x * n.b + n.a * n.b;
    a = new_a;
    x = new_x;
	}

	@Override
	public void div(FuzzyNumber n) {
    double new_a = (x - a)/(n.x + n.b),
    new_x = x / n.x;
    b = (x + n.a)/(n.x - b);
    a = new_a;
    x = new_x;
  }
}


class Task3 {
  public static void main(String[] args) {
    Complex c1 = new Complex(1, 2),
      c2 = new Complex(2, 1),
      c3 = new Complex(0, 0);
    c3.set(c1);
    c3.add(c2);
    System.out.println(String.format("%s + %s = %s", c1, c2, c3));
    c3.set(c1);
    c3.sub(c2);
    System.out.println(String.format("%s - %s = %s", c1, c2, c3));
    c3.set(c1);
    c3.mul(c2);
    System.out.println(String.format("%s * %s = %s", c1, c2, c3));
    c3.set(c1);
    c3.div(c2);
    System.out.println(String.format("%s / %s = %s", c1, c2, c3));

    System.out.println("---------------------------------------------------------");

    FuzzyNumber fn1 = new FuzzyNumber(1, 2, 3),
      fn2 = new FuzzyNumber(-3, 2, 1),
      fn3 = new FuzzyNumber(0, 0, 0);
    fn3.set(fn1);
    fn3.add(fn2);
    System.out.println(String.format("%s + %s = %s", fn1, fn2, fn3));
    fn3.set(fn1);
    fn3.sub(fn2);
    System.out.println(String.format("%s - %s = %s", fn1, fn2, fn3));
    fn3.set(fn1);
    fn3.mul(fn2);
    System.out.println(String.format("%s * %s = %s", fn1, fn2, fn3));
    fn3.set(fn1);
    fn3.div(fn2);
    System.out.println(String.format("%s / %s = %s", fn1, fn2, fn3));
  }
}
