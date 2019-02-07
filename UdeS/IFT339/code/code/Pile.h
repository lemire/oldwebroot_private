
#if !defined(_PILE_)
#define _PILE_

/*
* MAT339 : Structures de donn�es
* Daniel Lemire, Ph.D.
*
* Dans le travail 1, la Pile doit nous servir
* de r�ceptacle pour les triangles trouv�s.
*
* La classe File pr�sent�e ici est conforme aux
* notes de cours du professeur Jean Goulet.
*/
template <class TYPE>
class Pile  
{
public:
	Pile();
	virtual ~Pile();
	const Pile& operator=(const Pile&);
	void empiler(const TYPE&);
	TYPE depiler();
	void vider();
	bool estVide() const;
	
protected:
	

	Pile(const Pile&);
	
	class Cellule {
		public:
			Cellule(const TYPE& e, Cellule * s=NULL) {
				mElement=e;
				mSuivant=s;
			}
			const TYPE& element() const {return mElement;}
			Cellule* suivant() const {return mSuivant;}

		private:
			TYPE mElement;
			Cellule* mSuivant;

	};

	Cellule* premier;

};


#endif // !defined(_PILE_)
