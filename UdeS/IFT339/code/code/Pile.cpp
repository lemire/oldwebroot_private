
#include "Pile.h"

#include <assert.h>
template <class TYPE>
Pile<TYPE>::Pile() {	
	premier = NULL;
}

template <class TYPE>
Pile<TYPE>::~Pile() {
	vider();
}

template <class TYPE>
void Pile<TYPE>::empiler(const TYPE& e) {
	premier = new Cellule(e,premier);
}

template <class TYPE>
TYPE Pile<TYPE>::depiler() {
	assert(!estVide());
	TYPE valeur = premier->element();
	Cellule *aDetruire=premier;
	premier= premier->suivant();
	delete aDetruire;
	return valeur;
}

template <class TYPE>
bool Pile<TYPE>::estVide() const {
	return premier == NULL;
}

template <class TYPE>
void Pile<TYPE>::vider() {
	while(!estVide()) depiler();
}
