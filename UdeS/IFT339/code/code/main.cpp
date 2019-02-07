#include "Point.h"
#include "Triangle.h"
#include "File.cpp"
#include "Pile.cpp"
#include <iostream>

int main(int na, char **argv) {
		Point a(3,0);
		Point b(0,4);
		Point c(0,0);
		Triangle t(a,b,c);
		std::cout << t.aire() << std::endl;
		Pile<Point> pile;
		pile.empiler(a);
		pile.empiler(b);
		pile.empiler(c);
		std::cout << pile.depiler() << std::endl;
		std::cout << pile.depiler() << std::endl;
		std::cout << pile.depiler() << std::endl;
		File<Point> file;		
		file.enfiler(a);
		file.enfiler(b);
		file.enfiler(c);
		std::cout<< "enfilage ok!"<<std::endl;
		std::cout << file.defiler() << std::endl;
		std::cout << file.defiler() << std::endl;
		std::cout << file.defiler() << std::endl;
		file.enfiler(c);
		std::cout << file.defiler() << std::endl;
		std::cout<< "defilage ok!"<<std::endl;
		return 0;

}