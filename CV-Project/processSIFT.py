'''
Created on May 14, 2015

@author: Mark
'''

import cv2
import numpy as np
import readImage

def getSIFT(imageName):
    """
    This method used to get SIFT descriptors based image name.
        str => SIFT descriptors
    """
    
    img = cv2.imread(imageName)
    # img = cv2.resize(img, (0,0), fx=0.8, fy=0.8) # half image
    gray= cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    sift = cv2.SIFT()
    
    keypoints = sift.detect(gray,None)
    keypoints, SIFTdescr = sift.compute(gray, keypoints)
    #print len(SIFTdescr)
    return SIFTdescr

if __name__ == '__main__':
    pass