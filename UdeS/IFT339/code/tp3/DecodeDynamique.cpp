#include <fstream>
#include <time.h>
using namespace std;

int main (int arg, char ** argl) {
	if (arg < 3) {
		cout << "Vous devez saisir deux noms de fichiers." << endl;
		return -1;
	}
	/*
	* On pourrait rendre ce code plus robuste.
	* On choisit de ne pas s'en soucier.
	* Il y a aussi de meilleurs approches.
	*/
	ifstream donnees(argl[1]);
	
	int k, j;
	donnees >> k;
	donnees >> j;
	if(!donnees) {
		cout << "Incapable de lire dans le fichier " << argl[1] << endl;
		donnees.close();
		return -1;
	}
	cout <<  " k = "<< k << endl;
	cout <<  " j = "<< j << endl;
	int ValeurTempo = 0;
	int * tableau = new int[1 << j];
	/*
	* chrono top¦
	*/
	clock_t start = clock();
	// lecture des premiers 2 à la j éléments
	for(int indice = 0; indice < (1 << j); ++indice) {
		donnees >> ValeurTempo;
		tableau[indice] = ValeurTempo;
	}
	for( int iteration = 0;iteration + j < k; ++iteration) { 
		// on prolonge le tableau... dynamiquement?
		{
			int * tmp = new int[1 << (j + iteration + 1)];
			for(int indice = 0; indice < (1 << (j + iteration)) ; ++indice) {
				tmp[indice << 1] = tableau[indice];
			}
			delete[] tableau;
			tableau = tmp;
			
		}
		for(int indice = 0; indice < (1 << (j + iteration)); ++indice) {
			donnees >> ValeurTempo;
			tableau[(indice << 1) + 1] = ValeurTempo;
		}
	}
	clock_t finish = clock();
	/*
	* chrono pop¦
	*/
	double NombreDeSecondes =  (double)(finish - start) / CLOCKS_PER_SEC;
	cout << "Temps écoulé "<< NombreDeSecondes << " s" << endl;
	donnees.close();
	cout << "Les données furent lues avec succès!" << endl;
	ofstream sortie(argl[2]);
	if(!sortie) {
		cout << "Incapable d'écrire le résultat dans " << argl[2] << "." << endl;
		return -1;
	}
	cout << "Écriture dans le fichier "<< argl[2] << "." << endl;
	for(int indice = 0; indice < (1 << k); ++indice) {
			sortie << tableau[indice] << " ";
	}
	delete[] tableau;
	sortie.close();
	return 0;
}
