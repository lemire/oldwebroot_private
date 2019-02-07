#include "PileSurDisque.h"

int main() {
	PileSurDisque<int> psd;
	psd.empiler(1);
	psd.empiler(2);
	psd.empiler(3);
	cout << psd.depiler() << endl;
	cout << psd.depiler() << endl;
}
