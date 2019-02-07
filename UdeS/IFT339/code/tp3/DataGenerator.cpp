#include <fstream>
#include <stdlib.h>

using namespace std;

int main(int argn, char** argl) {
	// génère un fichier de données étant donné k et j
	if( argn < 4) {
		cout << "Saisissez k et l puis le nom d'un fichier de sortie." << endl;
		return -1;
	}
	int k = atoi(argl[1]);
	int j = atoi(argl[2]);
	cout << "k = " << k << " j = "<< j;
	int * data = new int[ 1 << k ];
	for (int indice = 0; indice < (1 << k) ; ++indice) { 
		data[indice] = indice % 100;
	}
	
	ofstream sortie(argl[3]);
	sortie << k << " " << j << " ";
	for(int indice = 0; indice < (1 << j) ; ++indice) {
		sortie << data[indice << (k - j)] << " ";
	}
	for( int iteration = 0;iteration + j < k; ++iteration) { 
		for(int indice = 0; indice < (1 << (j + iteration)); ++indice) {
			sortie << data[(1 << (k - j - iteration - 1)) * (2*indice+1)] << " ";
		}
	}	
	sortie.close();
	delete[] data;
	
}
