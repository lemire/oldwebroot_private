
#if !defined(_POINT_)
#define _POINT_

#include <math.h>
#include <iostream>

using namespace std;

class Point  
{
public:
	Point(const int& x, const int& y);

	Point();

	Point (const Point&);

	virtual ~Point();

	// Euclide
	const int norme() const;

	const Point operator-(const Point&) const;


	const Point operator+(const Point&) const;
	
	const int& abscisse() const {return X;}
	const int& ordonnee() const {return Y;}
	/*
	* Aire sous-tendue par les deux "vecteurs".
	* On triche un peu ici... voir le produit vectoriel...
	*/
	const int operator*(const Point& P) const {
			int aire = X * P.Y - Y * P.X;
			return aire >= 0 ? aire : -aire;
	}

	const Point& operator=(const Point&);






	/*
	* On s'assure que tout le monde a la même définition
	* d'un arrondi!
	*/
	static const int arrondi(const double& x) {
		int f;
		if(x<0)
			return(-arrondi(-x));
		f=(int) floor(x);
		if(x-f>=0.5)
			f++;
		return(f);
	}

	friend istream& operator>> (istream& is, Point& P) {
		int x,y;
		is >> x;
		char c;
		is >> c;
		is >> y;
		P = Point(x,y);
		return is;
	}

	friend ostream& operator<< (ostream& os,const Point& P) {
		return os << P.abscisse() << "," << P.ordonnee();
		
	}

private:
	int X,Y;
	


};


#endif // !defined(_POINT_)
