
#if !defined(_FILE_)
#define _FILE_

/*
* MAT339 : Structures de donn�es
* Daniel Lemire, Ph.D.
*
* La file dans le travail 1 doit servir � mettre en oeuvre la
* m�moire "tampon" de la lecture.
*
* La classe File pr�sent�e ici est conforme aux
* notes de cours du professeur Jean Goulet.
*/
template <class TYPE>
class File  
{
public:
	File();
	virtual ~File();

	void enfiler(const TYPE&);
	TYPE defiler();
	bool estVide() const;
	void vider();
	const TYPE& tete() const;
protected:	
	class Cellule{
		public:
			Cellule(const TYPE &e,Cellule * precedent = NULL, Cellule * suivant = NULL) {
				mValeur = e;
				mPrecedent = precedent;
				mSuivant = suivant;
			}
			
			const TYPE& valeur() { return mValeur; }
			Cellule* precedent() { return mPrecedent; }
			void precedent(Cellule* precedent) { 
				mPrecedent = precedent; 
			}
			Cellule* suivant() { return mSuivant; }
		private:
			TYPE mValeur;
			Cellule* mPrecedent;
			Cellule* mSuivant;
	};
	File(const File&);
	Cellule *TETE,*QUEUE;

};

#endif // !defined(_FILE_)
