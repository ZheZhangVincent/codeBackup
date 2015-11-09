'''
Created on May 9, 2015

@author: Mark
'''

import cv2
import numpy as np

#sift = cv2.SIFT()
img = cv2.imread('trainingImage/image001.JPG')

# gray= cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
#     
# sift = cv2.SIFT(img)
# kp = sift.detect(gray,None)
#     
# img=cv2.drawKeypoints(gray,kp)
# cv2.namedWindow("Image")  
# cv2.imshow('image001',img)
# cv2.waitKey (0) 

gray= cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
sift = cv2.SIFT()
kp = sift.detect(gray,None)

for k in kp:
    print type(k)

img=cv2.drawKeypoints(gray,kp)

cv2.imwrite('sift_keypoints.jpg',img)