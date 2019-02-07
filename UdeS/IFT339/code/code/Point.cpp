
#include "Point.h"
#include <iostream>


Point::Point(const int& x, const int& y) {
	X = x;
	Y = y;
}

Point::Point(const Point& p) {
	X = p.X;
	Y = p.Y;
}

Point::Point() {
	X = 0;
	Y = 0;
}

Point::~Point(){

}

const int Point::norme() const {
	return arrondi(sqrt(X * X + Y * Y));
}

const Point Point::operator-(const Point& p)  const {
	return Point(X - p.X,Y - p.Y);
}

const Point Point::operator+(const Point& p)  const {
	return Point(X + p.X,Y + p.Y);
}

const Point& Point::operator=(const Point& p) {
	X = p.X;
	Y = p.Y;
	return *this;
}