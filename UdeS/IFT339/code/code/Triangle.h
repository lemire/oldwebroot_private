
#if !defined(_TRIANGLE_)
#define _TRIANGLE_

#include "Point.h"

class Triangle  
{
public:
	Triangle(const Point& a, const Point& b, const Point& c);

	const Point& point1() const;
	const Point& point2() const;
	const Point& point3() const;

	const int aire() const;

	virtual ~Triangle();

private:
	Point A, B, C;

};

#endif // !defined(_TRIANGLE_)
