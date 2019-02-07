# !/bin/env python
# -*- coding: latin-1 -*-
##/**************************************************
##*	Fichier : FastSymmlet8.py
##*	Format : PYTHON
##* 
##* (c) 1998-2002 Daniel Lemire, Ph.D.
##* Python translation 2004 Valentin Gies
##* http://www.vgies.com/p=wavelets
##* http://www.ondelette.com/ - wavelet forum - forum sur les ondelettes
##*
##* Last modified on August 19 2004
##*
##* Free for any use, but this copyright notice must remain.
##* Provide as is. Use at your risks.
##*
##* Gratuit pour toute utilisation, mais préservez cette mention.
##* Sans garantie aucune, utilisez à vos risques.
##*
##* Python implementation of the Symmlet8 wavelet.
##* See end of the file for usage.
##*
##* Cette classe utilise les ondelettes Symmlet8. Son
##* utilisation est très simple (voir la fin du fichier
##* pour un exemple). Elle suppose des conditions périodiques
##* aux bords.
##*
##***************************************************/
import math

scale = [0.0322231006040782, -0.0126039672622638,
         -0.0992195435769564, 0.297857795605605,
         0.803738751805386, 0.497618667632563,
         -0.0296355276459604, -0.0757657147893567]
wavelet = [0.0757657147893567, -0.0296355276459604,
           -0.497618667632563, 0.803738751805386,
           -0.297857795605605, -0.0992195435769564,
           0.0126039672622638, 0.0322231006040782]

##/************************************************
##* An efficient implementation of
##* the Fast Wavelet Transform using
##* Symmlet8 wavelets (FBI fingerprints standard)
##*************************************************/
class FastSymmlet8:
    def transform2(self, length, v, last):
        ans=[0.0 for i in range(last)]
        half = last/2
        
        for k in range(half-3):
            ans[k+half]=v[(2*k+0)]*wavelet[0]+v[(2*k+1)]*wavelet[1]+v[(2*k+2)]*wavelet[2]+v[(2*k+3)]*wavelet[3]+v[(2*k+4)]*wavelet[4]+v[(2*k+5)]*wavelet[5]+v[(2*k+6)]*wavelet[6]+v[(2*k+7)]*wavelet[7]
            ans[k]=v[(2*k+0)]*scale[0]+v[(2*k+1)]*scale[1]+v[(2*k+2)]*scale[2]+v[(2*k+3)]*scale[3]+v[(2*k+4)]*scale[4]+v[(2*k+5)]*scale[5]+v[(2*k+6)]*scale[6]+v[(2*k+7)]*scale[7]

        ans[last-3]=v[last-6]*wavelet[0]+v[last-5]*wavelet[1]+v[last-4]*wavelet[2]+v[last-3]*wavelet[3]+v[last-2]*wavelet[4]+v[last-1]*wavelet[5]+v[0]*wavelet[6]+v[1]*wavelet[7]
        ans[half-3]=v[last-6]*scale[0]+v[last-5]*scale[1]+v[last-4]*scale[2]+v[last-3]*scale[3]+v[last-2]*scale[4]+v[last-1]*scale[5]+v[0]*scale[6]+v[1]*scale[7]
        ans[last-2]=v[last-4]*wavelet[0]+v[last-3]*wavelet[1]+v[last-2]*wavelet[2]+v[last-1]*wavelet[3]+v[0]*wavelet[4]+v[1]*wavelet[5]+v[2]*wavelet[6]+v[3]*wavelet[7]
        ans[half-2]=v[last-4]*scale[0]+v[last-3]*scale[1]+v[last-2]*scale[2]+v[last-1]*scale[3]+v[0]*scale[4]+v[1]*scale[5]+v[2]*scale[6]+v[3]*scale[7]
        ans[last-1]=v[last-2]*wavelet[0]+v[last-1]*wavelet[1]+v[0]*wavelet[2]+v[1]*wavelet[3]+v[2]*wavelet[4]+v[3]*wavelet[5]+v[4]*wavelet[6]+v[5]*wavelet[7]
        ans[half-1]=v[last-2]*scale[0]+v[last-1]*scale[1]+v[0]*scale[2]+v[1]*scale[3]+v[2]*scale[4]+v[3]*scale[5]+v[4]*scale[6]+v[5]*scale[7]

        for k in range(last):
            v[k]=ans[k]
            
    def transform (self, length, v):
        last = length
        while (last>8):
            self.transform2(length,v,last)
            last /=2

        if(last!=8):
            print "Attention : n'est pas une puissance de 2 : "+str(length)
            print "Careful : not a power of 2 : "+str(length)

    def invTransform2(self, length, v, last):
        ResultingLength=2*last
        ans=[0.0 for i in range(ResultingLength)]
        for k in range(int(math.ceil((ResultingLength-7)/2.0))):
            ans[(2*k+7)]+=scale[7]*v[k]+wavelet[7]*v[k+last]
            ans[(2*k+6)]+=scale[6]*v[k]+wavelet[6]*v[k+last]
            ans[(2*k+5)]+=scale[5]*v[k]+wavelet[5]*v[k+last]
            ans[(2*k+4)]+=scale[4]*v[k]+wavelet[4]*v[k+last]
            ans[(2*k+3)]+=scale[3]*v[k]+wavelet[3]*v[k+last]
            ans[(2*k+2)]+=scale[2]*v[k]+wavelet[2]*v[k+last]
            ans[(2*k+1)]+=scale[1]*v[k]+wavelet[1]*v[k+last]
            ans[(2*k+0)]+=scale[0]*v[k]+wavelet[0]*v[k+last]

        ans[ResultingLength-6]+=scale[0]*v[last-3]+wavelet[0]*v[ResultingLength-3]
        ans[ResultingLength-5]+=scale[1]*v[last-3]+wavelet[1]*v[ResultingLength-3]
        ans[ResultingLength-4]+=scale[2]*v[last-3]+wavelet[2]*v[ResultingLength-3]
        ans[ResultingLength-3]+=scale[3]*v[last-3]+wavelet[3]*v[ResultingLength-3]
        ans[ResultingLength-2]+=scale[4]*v[last-3]+wavelet[4]*v[ResultingLength-3]
        ans[ResultingLength-1]+=scale[5]*v[last-3]+wavelet[5]*v[ResultingLength-3]
        ans[0]+=scale[6]*v[last-3]+wavelet[6]*v[ResultingLength-3]
        ans[1]+=scale[7]*v[last-3]+wavelet[7]*v[ResultingLength-3]
        ans[ResultingLength-4]+=scale[0]*v[last-2]+wavelet[0]*v[ResultingLength-2]
        ans[ResultingLength-3]+=scale[1]*v[last-2]+wavelet[1]*v[ResultingLength-2]
        ans[ResultingLength-2]+=scale[2]*v[last-2]+wavelet[2]*v[ResultingLength-2]
        ans[ResultingLength-1]+=scale[3]*v[last-2]+wavelet[3]*v[ResultingLength-2]
        ans[0]+=scale[4]*v[last-2]+wavelet[4]*v[ResultingLength-2]
        ans[1]+=scale[5]*v[last-2]+wavelet[5]*v[ResultingLength-2]
        ans[2]+=scale[6]*v[last-2]+wavelet[6]*v[ResultingLength-2]
        ans[3]+=scale[7]*v[last-2]+wavelet[7]*v[ResultingLength-2]
        ans[ResultingLength-2]+=scale[0]*v[last-1]+wavelet[0]*v[ResultingLength-1]
        ans[ResultingLength-1]+=scale[1]*v[last-1]+wavelet[1]*v[ResultingLength-1]
        ans[0]+=scale[2]*v[last-1]+wavelet[2]*v[ResultingLength-1]
        ans[1]+=scale[3]*v[last-1]+wavelet[3]*v[ResultingLength-1]
        ans[2]+=scale[4]*v[last-1]+wavelet[4]*v[ResultingLength-1]
        ans[3]+=scale[5]*v[last-1]+wavelet[5]*v[ResultingLength-1]
        ans[4]+=scale[6]*v[last-1]+wavelet[6]*v[ResultingLength-1]
        ans[5]+=scale[7]*v[last-1]+wavelet[7]*v[ResultingLength-1]

        for k in range(ResultingLength):
            v[k]=ans[k]
            
    def invTransform (self, length, v):
        last = 8
        while (2*last <= length):
            self.invTransform2(length, v, last)
            last*=2
        if(last!=length):
            print "Attention : n'est pas une puissance de 2 : "+str(length)
            print "Careful : not a power of 2 : "+str(length)

    def expand(length, v):
        last=length/2
        ResultingLength=2*last
        ans=[0.0 for i in range(ResultingLength)]
        for k in range((ResultingLength-7)/2):
            ans[ResultingLength-6]+=scale[0]*v[last-3]
            ans[ResultingLength-5]+=scale[1]*v[last-3]
            ans[ResultingLength-4]+=scale[2]*v[last-3]
            ans[ResultingLength-3]+=scale[3]*v[last-3]
            ans[ResultingLength-2]+=scale[4]*v[last-3]
            ans[ResultingLength-1]+=scale[5]*v[last-3]
            ans[0]+=scale[6]*v[last-3]
            ans[1]+=scale[7]*v[last-3]
            ans[ResultingLength-4]+=scale[0]*v[last-2]
            ans[ResultingLength-3]+=scale[1]*v[last-2]
            ans[ResultingLength-2]+=scale[2]*v[last-2]
            ans[ResultingLength-1]+=scale[3]*v[last-2]
            ans[0]+=scale[4]*v[last-2]
            ans[1]+=scale[5]*v[last-2]
            ans[2]+=scale[6]*v[last-2]
            ans[3]+=scale[7]*v[last-2]
            ans[ResultingLength-2]+=scale[0]*v[last-1]
            ans[ResultingLength-1]+=scale[1]*v[last-1]
            ans[0]+=scale[2]*v[last-1]
            ans[1]+=scale[3]*v[last-1]
            ans[2]+=scale[4]*v[last-1]
            ans[3]+=scale[5]*v[last-1]
            ans[4]+=scale[6]*v[last-1]
            ans[5]+=scale[7]*v[last-1]

            for k in range(length):
                v[k]=ans[k]
                
#Test du programme : t est l'echantillon a analyser

t=[1.0,1.0,1.0,1.0,
   1.0,1.0,1.0,1.0,
   0.0,0.0,0.0,0.0,
   0.0,0.0,0.0,0.0,
   1.0,1.0,1.0,1.0,
   1.0,1.0,1.0,1.0,
   0.0,0.0,0.0,0.0,
   0.0,0.0,0.0,0.0]
length = len(t)
Ondelette = FastSymmlet8()
Ondelette.transform(length,t)
Ondelette.invTransform(length,t)
print t
