//////////////////////////////////////////////////////////////////
//
// (c) 1999-2003 Daniel Lemire and Kevin Grantham
//  http://www.ondelette.com/ - wavelet forum
//
//////////////////////////////////////////////////////////////////
//
// A Karhunen-Loève C++ class
//
// This class was used in the following paper to process ECG data:
// 
//   Daniel Lemire, Chantal Pharand, Jean-Claude Rajaonah, Bruno Dubé, A.-Robert LeBlanc,
//   Wavelet time entropy, T wave morphology and myocardial ischemia, IEEE Transactions
//   in Biomedical Engineering, vol. 47, no. 7, July 2000.
//
//   http://www.ondelette.com/lemire/abstracts/IEEE2000.html
//
//////////////////////////////////////////////////////////////////
// Usage :
//			if "length" is the length of your signal,
//			and you have 1 pointers using
//			short integers (X,Y,Z,X,Y,Z,...) call
//
//				KLTransform a(length,XYZ);
//				double* XYZKL = a.getTransformedData();
//
//			(XYZ must have 3 * length shorts)
//
//			will the "transformed components"
//			if the energy in the third component is
//			very low, then you have detected a system
//			having only two degrees of freedom.
//
//////////////////////////////////////////////////////////////////
//
//	Copyright : You can freely use and modify this software as long as
//		this copyright notice and my name stay within the code.
//
//////////////////////////////////////////////////////////////////
//
// last revised on September 22nd 2003
//
//	Kevin Grantham submitted inverse function.
//
// 	WARNING : THIS IS STILL A PRE-BETA RELEASE. REPORT
//	ANY BUG.
//
//////////////////////////////////////////////////////////////////
//
// Compiled and tested with GNU C compiler (ANSI compliant).
//
//////////////////////////////////////////////////////////////////

#include <iostream.h>
#include <math.h>



double abs (double v) {
	if (v >= 0 )
		return(v);
	return(v);
}


class KLTransform {
	public :
		KLTransform (int, short *);
		double * getTransformedData();
		void Inverse(int length, double* XYZ);

	private :

		double * firstVector;
		double * secondVector;
		double * thirdVector;

		static double * computeCorrelationMatrix(short *, int);
		static double * add (double *, double *);
		static double scalarMultiply (double * , double * );
		static double scalarMultiply (short * , double * );

		static double * eigenSolveSymmetric(double *,double *);
		static void eigenSolveSymmetricTridiagonalMatrix(double * ,double * , 
double *);
		double * mTransformedData;
		//static const int Size = 3;
#define Size 3
};

// assume m has three element
// length must be the length of the signal
double * KLTransform::computeCorrelationMatrix (short * m, int length) {
	double * ans=new double[Size * Size];
	for (int row = 0; row < Size; row++) {
		for (int col = 0; col < Size; col++) {
			ans[row * Size + col] = m[col] * m [row] / (double) length;
		}
	}
	return(ans);
}

// add two 3 by 3 matrices
double * KLTransform::add (double * m1, double * m2) {
	double * ans = new double[Size * Size];
	for (int row = 0; row < Size; row++) {
		for (int col = 0; col < Size; col++) {
			ans[row * Size + col] += m2[row * Size + col] + m1[row * Size + col];
		}
	}
	return(ans);
}

double KLTransform::scalarMultiply (double * v1, double * v2) {
	double product = 0;
	for (int k = 0 ; k < 3 ; k++ )
		product += v1[k]*v2[k];
	return(product);
}
double KLTransform::scalarMultiply (short * v1, double * v2) {
	double product = 0;
	for (int k = 0 ; k < 3 ; k++ )
		product += v1[k]*v2[k];
	return(product);
}

double * KLTransform::getTransformedData() {
	return(mTransformedData);
}
KLTransform::KLTransform(int length, short * XYZ) {
	double * CorrelationMatrix = new double[ Size * Size ];
	for (int row = 0; row < Size; row++)
		for (int col = 0; col < Size; col++)
			CorrelationMatrix[row * Size + col] = 0;
	for(int sample = 0 ; sample < length ; sample++ ) {
		double * tempCorr = computeCorrelationMatrix(XYZ + 3 * sample, 3);
		CorrelationMatrix = add (CorrelationMatrix , tempCorr);
	}
	double * eigenvectors = new double [Size * Size];
	double * eigenvalues = eigenSolveSymmetric(CorrelationMatrix ,
eigenvectors);
	int first = 0;
	for (int k = 1 ;  k < Size ; k++)  {
		if(eigenvalues[k] > eigenvalues[first])
			first = k;
	}
	int second = (first + 1) % 3;
	int third = (first + 2) % 3;
	if (eigenvalues[third] > second){
		second = (first + 2) % 3;
		third = (first + 1) % 3;
	}

	firstVector = eigenvectors + first * 3;
	secondVector = eigenvectors + second * 3;
	thirdVector = eigenvectors + third * 3;
	mTransformedData = new double[ 3 * length ];
	for (k = 0 ; k < length ; k++ ) {
		mTransformedData[ 3 * k + 0 ] = scalarMultiply (XYZ + 3 * k, firstVector);
		mTransformedData[ 3 * k + 1 ] = scalarMultiply (XYZ + 3 * k, 
secondVector);
		mTransformedData[ 3 * k + 2 ] = scalarMultiply (XYZ + 3 * k, thirdVector);
	}
}


void KLTransform::Inverse(int length, double* XYZ)
{
	double *Matx = new double [Size * Size];
	double *IMatx = new double [Size * Size];
	for (int i=0; i<3; i++)
	{
		Matx[i] = firstVector[i];
		Matx[i+3] = secondVector[i];
		Matx[i+6] = thirdVector[i];
	}

	// Invert Matrix

	#define KLx Matx[0]
	#define KLy Matx[3]
	#define KLz Matx[6]
	#define KLp Matx[1]
	#define KLq Matx[4]
	#define KLr Matx[7]
	#define KLa Matx[2]
	#define KLb Matx[5]
	#define KLc Matx[8]

    double  
det=KLx*((KLq*KLc)-(KLb*KLr))-KLy*((KLp*KLc)-(KLa*KLr))+KLz*((KLp*KLb)-(KLq*KLa));
	if(det==0)		// error - cannot invert matrix
		return;

	// Calculate cofactors

	IMatx[0]=((KLq*KLc)-(KLb*KLr));
	IMatx[1]=-((KLp*KLc)-(KLa*KLr));
	IMatx[2]=((KLp*KLb)-(KLq*KLa));
	IMatx[3]=-((KLy*KLc)-(KLb*KLz));
	IMatx[4]=((KLx*KLc)-(KLa*KLz));
	IMatx[5]=-((KLx*KLb)-(KLa*KLy));
	IMatx[6]=((KLy*KLr)-(KLq*KLz));
	IMatx[7]=-((KLx*KLr)-(KLp*KLz));
	IMatx[8]=((KLx*KLq)-(KLp*KLy));

//	for (i=0; i<8; i++)
	//	IMatx[i] *= 1.0/det;

	for (int k = 0 ; k < length ; k++ ) {
		XYZ[(3 * k) + 0 ] = scalarMultiply (mTransformedData + (3 * k), 
&IMatx[0]);
		XYZ[(3 * k) + 1 ] = scalarMultiply (mTransformedData + (3 * k), 
&IMatx[3]);
		XYZ[(3 * k) + 2 ] = scalarMultiply (mTransformedData + (3 * k), 
&IMatx[6]);
	}

	delete Matx;
	delete IMatx;
}


// eigenvectors is a pointer to a Size by Size double array
        double * KLTransform::eigenSolveSymmetric(double *  matrix,double * 
eigenvectors){
				//	eigenvectors = new double[n * n];
				int n = Size;
                int nm1=n-1;
                double * eigenvalue = new double[n];
                double * offdiag = new double[n];
                double * id = new double[n * n];


				for (int k = 0 ; k < n*n ; k ++)
					id[k] = 0;

                int i,j;
                for(i=0;i<nm1;i++) {
                        id[i*Size + i]=1.0;
                        eigenvalue[i] = matrix[i * Size + i];
                        offdiag[i] = matrix[i * Size + i + 1];
                }
                id[nm1 * n + nm1]=1.0;
                eigenvalue[nm1]=matrix[nm1 *Size + nm1];
                offdiag[nm1]=0.0;

                eigenSolveSymmetricTridiagonalMatrix(eigenvalue,offdiag,id);
                for(i=0;i<n;i++) {
                        for(j=0;j<n;j++)
                                eigenvectors[i *n + j]=id[j*Size + i];
                }
                return eigenvalue;
        }


        void KLTransform::eigenSolveSymmetricTridiagonalMatrix(double * 
diag,double * offdiag, double * transf){
                int n=Size;
                int nm1=n-1;
                int m,l,iteration,i,k;
                double s,r,p,g,f,dd,c,b;
                for(l=0;l<n;l++) {
                        iteration=0;
                        do {
                                for(m=l;m<nm1;m++) {
                                        dd=abs(diag[m])+abs(diag[m+1]);
                                        if(abs(offdiag[m])+dd==dd)
                                                break;
                                }
                                if(m!=l) {
                                        if(iteration++==50) {
                                                cout << ("No convergence 
after 50 iterations.");
												return;
										}
                                        
g=(diag[l+1]-diag[l])/(2.0*offdiag[l]);
                                        r=sqrt(g*g+1.0);
                                        
g=diag[m]-diag[l]+offdiag[l]/(g+(g<0.0?-abs(r):abs(r)));
                                        s=c=1.0;
                                        p=0.0;
                                        for(i=m-1;i>=l;i--) {
                                                f=s*offdiag[i];
                                                b=c*offdiag[i];
                                                if(abs(f)>=abs(g)) {
                                                        c=g/f;
                                                        r=sqrt(c*c+1.0);
                                                        offdiag[i+1]=f*r;
                                                        s=1/r;
                                                        c*=s;
                                                } else {
                                                        s=f/g;
                                                        r=sqrt(s*s+1.0);
                                                        offdiag[i+1]=g*r;
                                                        c=1/r;
                                                        s*=c;
                                                }
                                                g=diag[i+1]-p;
                                                r=(diag[i]-g)*s+2.0*c*b;
                                                p=s*r;
                                                diag[i+1]=g+p;
                                                g=c*r-b;
                                                for(k=0;k<n;k++) {
                                                        f=transf[k*Size + 
i+1];
                                                        transf[k*Size + 
i+1]=s*transf[k*Size + i]+c*f;
                                                        transf[k*Size + 
i]=c*transf[k*Size + i]-s*f;
                                                }
                                        }
                                        diag[l]=diag[l]-p;
                                        offdiag[l]=g;
                                        offdiag[m]=0.0;
                                }
                        } while(m!=l);
                }
        }


int main () {
	cout << "(c) 1999-2003 Daniel Lemire and Kevin Grantham" << endl;

	short XYZ[6*3] = {10,11,0,
						22,22,0,
						36,12,-1,
						30,13,0,
						45,34,0,
						36,14,1};
	cout << "Original Data :" << endl;
	for (int k =0 ; k<6 ; k++) {
		cout << " X = " << XYZ[3*k] << " ";
		cout << " Y = " << XYZ[3*k + 1] << " ";
		cout << " Z = " << XYZ[3*k + 2] << endl;
	}
	cout << "Computing Karhunen-Loeve..." ;
	KLTransform a(6,XYZ);
	double* XYZKL = a.getTransformedData();
	cout << "ok." <<endl;
	cout << "Transformed data :" << endl;

	for (k =0 ; k<6 ; k++){
		cout << " X1 = " << XYZKL[3*k] << " ";
		cout << " X2 = " << XYZKL[3*k + 1] << " ";
		cout << " X3 = " << XYZKL[3*k + 2] << endl;
	}

	cout << "As you can see, most of the energy is on the first eigenvector." 
<< endl;

	cout << "Computing Inverse Karhunen-Loeve...";
	double OUT[6*3];
	a.Inverse(6,OUT);
	cout << "ok." <<endl;

	cout << "Inverse Transformed data :" << endl;

	for (k =0 ; k<6 ; k++){
		cout << " X1 = " << OUT[3*k] << " ";
		cout << " X2 = " << OUT[3*k + 1] << " ";
		cout << " X3 = " << OUT[3*k + 2] << endl;
	}
}



