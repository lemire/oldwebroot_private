
#include "File.h"

#include <assert.h>

template <class TYPE>
File<TYPE>::File() {
	TETE = NULL;
	QUEUE = NULL;
}

template <class TYPE>
File<TYPE>::~File() {
	vider();
}

template <class TYPE>
void File<TYPE>::enfiler(const TYPE& f) {
	Cellule * AncienneTete = TETE;
	TETE = new Cellule(f,NULL,TETE);
	if(AncienneTete != NULL)
		AncienneTete->precedent(TETE);
	if (QUEUE == NULL)
		QUEUE = TETE;
}

template <class TYPE>
TYPE File<TYPE>::defiler() {
	assert( !estVide());
	TYPE valeur = QUEUE->valeur();
	Cellule* aDetruire = QUEUE;
	QUEUE = QUEUE->precedent();
	delete aDetruire;
	return valeur;
}

template <class TYPE>
bool File<TYPE>::estVide() const {
	return QUEUE == NULL;
}

template <class TYPE>
void File<TYPE>::vider() {
	while(!estVide()) defiler();
}

