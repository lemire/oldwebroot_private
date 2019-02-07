#include <fstream>
using namespace std;

#ifndef _PSD_
#define _PSD_

template <class TYPE>
class PileSurDisque {
	public:
		PileSurDisque() {
			mFichier.open("test",ios::binary | ios::out | ios::in);
			mFichier.seekg (0, ios::end);
			mTaille = mFichier.tellg();
			cout << "taille ... " << mTaille << endl;
		}
		
		TYPE depiler() {
			TYPE e;		
			mTaille -= sizeof(e);
			
			mFichier.seekg (mTaille, ios::beg);
			cout << "Going to ... " << mFichier.tellg() << endl;
			mFichier.read((char*)& e, sizeof(e));
			return e;
		}
		
		void empiler(TYPE e) {
			mTaille += sizeof(e);
			mFichier.seekp(0,ios::end);
			mFichier.write((char *) & e, sizeof(e));		
		}
		
		~PileSurDisque() {
			mFichier.close();
		}
	private:

		fstream mFichier;
		int mTaille;
};

#endif
