/**************************************************
*	Fichier : FastSymmlet8.C
*	Format : ANSI C++
*
* (c) 1998-2002 Daniel Lemire, Ph.D.
* http://www.ondelette.com/ - wavelet forum - forum sur les ondelettes
*
* Last modified on March 6th 1998
* Dernière modification le 6 mars 1998
*
* Free for any use, but this copyright notice must remain.
* Provide as is. Use at your risks.
*
* Gratuit pour toute utilisation, mais préservez cette mention.
* Sans garantie aucune, utilisez à vos risques.
*
* C++ implementation of the Symmlet8 wavelet.
* See end of the file for usage.
*
* Cette classe utilise les ondelettes Symmlet8. Son
* utilisation est très simple (voir la fin du fichier
* pour un exemple). Elle suppose des conditions périodiques
* aux bords.
*
***************************************************/

#include <iostream.h>

	  static const float scale[8] = {0.0322231006040782f,
	                                -0.0126039672622638f,
	                                -0.0992195435769564f,
	                                0.297857795605605f,
	                                0.803738751805386f,
	                                0.497618667632563f,
	                                -0.0296355276459604f,
	                                -0.0757657147893567f};
	  static const float wavelet[8] = {0.0757657147893567f,
	  				-0.0296355276459604f,
	  				-0.497618667632563f,
	  				0.803738751805386f,
	  				-0.297857795605605f,
	  				-0.0992195435769564f,
	  				0.0126039672622638f,
	  				0.0322231006040782f};

/************************************************
* An efficient implementation of
* the Fast Wavelet Transform using
* Symmlet8 wavelets (FBI fingerprints standard)
*************************************************/
class FastSymmlet8 {
	public:
	
		static void transform (int,float *);
		
		static void invTransform (int,float *);	
		static void expand (int,float *);
		
		class NotAPowerOf2Exception{};
		class NotDivisibleBy2Exception{};
		
		static void invTransform(int,float *, int);
		static void transform(int,float *, int);
		
	private:
		
};
void FastSymmlet8::transform(int length,float * v, int last) {
    float * ans=new float[last];
	for(int k=0;k<last;k++) {
		ans[k]=0;
	}
    int half=last/2;
    for(int k=0;k<half-3;k++) {
          ans[k+half]=v[(2*k+0)]*wavelet[0]+v[(2*k+1)]*wavelet[1]+v[(2*k+2)]*wavelet[2]+v[(2*k+3)]*wavelet[3]+v[(2*k+4)]*wavelet[4]+v[(2*k+5)]*wavelet[5]+v[(2*k+6)]*wavelet[6]+v[(2*k+7)]*wavelet[7];
          ans[k]=v[(2*k+0)]*scale[0]+v[(2*k+1)]*scale[1]+v[(2*k+2)]*scale[2]+v[(2*k+3)]*scale[3]+v[(2*k+4)]*scale[4]+v[(2*k+5)]*scale[5]+v[(2*k+6)]*scale[6]+v[(2*k+7)]*scale[7];
	}
    ans[last-3]=v[last-6]*wavelet[0]+v[last-5]*wavelet[1]+v[last-4]*wavelet[2]+v[last-3]*wavelet[3]+v[last-2]*wavelet[4]+v[last-1]*wavelet[5]+v[0]*wavelet[6]+v[1]*wavelet[7];
    ans[half-3]=v[last-6]*scale[0]+v[last-5]*scale[1]+v[last-4]*scale[2]+v[last-3]*scale[3]+v[last-2]*scale[4]+v[last-1]*scale[5]+v[0]*scale[6]+v[1]*scale[7];
    ans[last-2]=v[last-4]*wavelet[0]+v[last-3]*wavelet[1]+v[last-2]*wavelet[2]+v[last-1]*wavelet[3]+v[0]*wavelet[4]+v[1]*wavelet[5]+v[2]*wavelet[6]+v[3]*wavelet[7];
    ans[half-2]=v[last-4]*scale[0]+v[last-3]*scale[1]+v[last-2]*scale[2]+v[last-1]*scale[3]+v[0]*scale[4]+v[1]*scale[5]+v[2]*scale[6]+v[3]*scale[7];
    ans[last-1]=v[last-2]*wavelet[0]+v[last-1]*wavelet[1]+v[0]*wavelet[2]+v[1]*wavelet[3]+v[2]*wavelet[4]+v[3]*wavelet[5]+v[4]*wavelet[6]+v[5]*wavelet[7];
    ans[half-1]=v[last-2]*scale[0]+v[last-1]*scale[1]+v[0]*scale[2]+v[1]*scale[3]+v[2]*scale[4]+v[3]*scale[5]+v[4]*scale[6]+v[5]*scale[7];
	for(int k=0;k<last;k++) {
		v[k]=ans[k];
	}
	delete[] ans;
}

void FastSymmlet8::transform (int length,float * v) {
	int last;
    for (last=length;last>8;last/=2) {
      transform(length,v,last);
    }
    if(last!=8) {
      cout<< "Attention : n'est pas une puissance de 2 : " << length << endl;
      cout<< "Careful : not a power of 2 : " << length << endl;
	  throw NotAPowerOf2Exception();
	}
}

void FastSymmlet8::invTransform(int length, float * v, int last) {
    int ResultingLength=2*last;
    float * ans=new float[ResultingLength];
	for(int k=0;k<ResultingLength;k++) {
		ans[k]=0;
	}
    for(int k=0;2*k+7<ResultingLength;k++) {
          ans[(2*k+7)]+=scale[7]*v[k]+wavelet[7]*v[k+last] ;
          ans[(2*k+6)]+=scale[6]*v[k]+wavelet[6]*v[k+last] ;
          ans[(2*k+5)]+=scale[5]*v[k]+wavelet[5]*v[k+last] ;
          ans[(2*k+4)]+=scale[4]*v[k]+wavelet[4]*v[k+last] ;
          ans[(2*k+3)]+=scale[3]*v[k]+wavelet[3]*v[k+last] ;
          ans[(2*k+2)]+=scale[2]*v[k]+wavelet[2]*v[k+last] ;
          ans[(2*k+1)]+=scale[1]*v[k]+wavelet[1]*v[k+last] ;
          ans[(2*k+0)]+=scale[0]*v[k]+wavelet[0]*v[k+last] ;
    }
    ans[ResultingLength-6]+=scale[0]*v[last-3]+wavelet[0]*v[ResultingLength-3] ;
    ans[ResultingLength-5]+=scale[1]*v[last-3]+wavelet[1]*v[ResultingLength-3] ;
    ans[ResultingLength-4]+=scale[2]*v[last-3]+wavelet[2]*v[ResultingLength-3] ;
    ans[ResultingLength-3]+=scale[3]*v[last-3]+wavelet[3]*v[ResultingLength-3] ;
    ans[ResultingLength-2]+=scale[4]*v[last-3]+wavelet[4]*v[ResultingLength-3] ;
    ans[ResultingLength-1]+=scale[5]*v[last-3]+wavelet[5]*v[ResultingLength-3] ;
    ans[0]+=scale[6]*v[last-3]+wavelet[6]*v[ResultingLength-3] ;
    ans[1]+=scale[7]*v[last-3]+wavelet[7]*v[ResultingLength-3] ;
    ans[ResultingLength-4]+=scale[0]*v[last-2]+wavelet[0]*v[ResultingLength-2] ;
    ans[ResultingLength-3]+=scale[1]*v[last-2]+wavelet[1]*v[ResultingLength-2] ;
    ans[ResultingLength-2]+=scale[2]*v[last-2]+wavelet[2]*v[ResultingLength-2] ;
    ans[ResultingLength-1]+=scale[3]*v[last-2]+wavelet[3]*v[ResultingLength-2] ;
    ans[0]+=scale[4]*v[last-2]+wavelet[4]*v[ResultingLength-2] ;
    ans[1]+=scale[5]*v[last-2]+wavelet[5]*v[ResultingLength-2] ;
    ans[2]+=scale[6]*v[last-2]+wavelet[6]*v[ResultingLength-2] ;
    ans[3]+=scale[7]*v[last-2]+wavelet[7]*v[ResultingLength-2] ;
    ans[ResultingLength-2]+=scale[0]*v[last-1]+wavelet[0]*v[ResultingLength-1] ;
    ans[ResultingLength-1]+=scale[1]*v[last-1]+wavelet[1]*v[ResultingLength-1] ;
    ans[0]+=scale[2]*v[last-1]+wavelet[2]*v[ResultingLength-1] ;
    ans[1]+=scale[3]*v[last-1]+wavelet[3]*v[ResultingLength-1] ;
    ans[2]+=scale[4]*v[last-1]+wavelet[4]*v[ResultingLength-1] ;
    ans[3]+=scale[5]*v[last-1]+wavelet[5]*v[ResultingLength-1] ;
    ans[4]+=scale[6]*v[last-1]+wavelet[6]*v[ResultingLength-1] ;
    ans[5]+=scale[7]*v[last-1]+wavelet[7]*v[ResultingLength-1] ;
	for(int k=0;k<ResultingLength;k++) {
		v[k]=ans[k];
	}
	delete[] ans;
}

void FastSymmlet8::invTransform (int length,float * v) {
    int last;
    for (last=8;2*last<=length;last*=2) {
      invTransform(length, v,last);
    }
    if(last!=length) {
      cout << "Careful: not a power of 2 : " << length << endl;
      cout << "Attention : n'est pas une puissance de 2 : " << length << endl;
	  throw NotAPowerOf2Exception();
	}

}

void FastSymmlet8::expand(int length, float * v) {
    
	int last=length/2;
	int ResultingLength=2*last;
    float * ans=new float[ResultingLength];
	for(int k=0;k<ResultingLength;k++) {
		ans[k]=0;
	}
    for(int k=0;2*k+7<ResultingLength;k++) {
          ans[(2*k+7)]+=scale[7]*v[k];
          ans[(2*k+6)]+=scale[6]*v[k];
          ans[(2*k+5)]+=scale[5]*v[k];
          ans[(2*k+4)]+=scale[4]*v[k];
          ans[(2*k+3)]+=scale[3]*v[k];
          ans[(2*k+2)]+=scale[2]*v[k];
          ans[(2*k+1)]+=scale[1]*v[k];
          ans[(2*k+0)]+=scale[0]*v[k];
    }
    ans[ResultingLength-6]+=scale[0]*v[last-3];
    ans[ResultingLength-5]+=scale[1]*v[last-3];
    ans[ResultingLength-4]+=scale[2]*v[last-3];
    ans[ResultingLength-3]+=scale[3]*v[last-3];
    ans[ResultingLength-2]+=scale[4]*v[last-3];
    ans[ResultingLength-1]+=scale[5]*v[last-3];
    ans[0]+=scale[6]*v[last-3];
    ans[1]+=scale[7]*v[last-3];
    ans[ResultingLength-4]+=scale[0]*v[last-2];
    ans[ResultingLength-3]+=scale[1]*v[last-2];
    ans[ResultingLength-2]+=scale[2]*v[last-2];
    ans[ResultingLength-1]+=scale[3]*v[last-2];
    ans[0]+=scale[4]*v[last-2];
    ans[1]+=scale[5]*v[last-2];
    ans[2]+=scale[6]*v[last-2];
    ans[3]+=scale[7]*v[last-2];
    ans[ResultingLength-2]+=scale[0]*v[last-1];
    ans[ResultingLength-1]+=scale[1]*v[last-1];
    ans[0]+=scale[2]*v[last-1];
    ans[1]+=scale[3]*v[last-1];
    ans[2]+=scale[4]*v[last-1];
    ans[3]+=scale[5]*v[last-1];
    ans[4]+=scale[6]*v[last-1];
    ans[5]+=scale[7]*v[last-1];
	for(int k=0;k<length;k++) {
		v[k]=ans[k];
	}
	delete[] ans;
}

/***************
* Thanks to Peter Meerwald for pointing out a bug on the web release
* of this code.
***************/
int main () {
	int length = 32;
	float t[32]={1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f};
	FastSymmlet8::transform(length,t);
	FastSymmlet8::invTransform (length,t);
	for(int k=0;k<length;k++) {
		cout << t[k] << endl;
	}

}



