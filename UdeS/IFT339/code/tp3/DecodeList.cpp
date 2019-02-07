#include <list>
#include <time.h>
#include <fstream>

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
	list<int> lst;
	list<int>::iterator it;
	/*
	* chrono top¦
	*/
	clock_t start = clock();
	// lecture des premiers 2 à la j éléments
	for(int indice = 0; indice < (1 << j); ++indice) {
		donnees >> ValeurTempo; 
		lst.push_back(ValeurTempo);
	}
	for( int iteration = 0;iteration + j < k; ++iteration) { 
		for(it = ++lst.begin(); it != lst.end(); ++it) {
			donnees >> ValeurTempo;
			lst.insert(it,ValeurTempo);

		}
		donnees >> ValeurTempo;
		lst.push_back(ValeurTempo);
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
	it = lst.begin();
	while( it != lst.end() ) {
		sortie << lst.front() << " ";
		++it;
		lst.pop_front();
	}
	sortie.close();
	return 0;
}
