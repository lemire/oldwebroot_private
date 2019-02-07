
#include "Triangle.h"

Triangle::Triangle(const Point& a, const Point& b, const Point& c)
{
	A = a;
	B = b;
	C = c;
}

Triangle::~Triangle()
{

}

const Point& Triangle::point1() const {
	return A;
}

const Point& Triangle::point2() const {
	return B;
}

const Point& Triangle::point3() const {
	return C;
}

const int Triangle::aire() const {
	return Point::arrondi( (A - B) * (A - C) / 2.0 );

}
