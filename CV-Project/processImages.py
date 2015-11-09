'''
Created on May 17, 2015

@author: Mark
'''
import Image
import readImage
import processSIFT
import random
import numpy as np
import cv2 as cv
import math
from processDistances import getDistances 
import sys


def processTrainImages(classSize, K):
    """
    This method used to process train images, and return cluster centers, trainImage list and
    idfList.
        int, int => [[int, int,...],..], trainImages, [int, int,...]
    """
    
    totalDsecr =  np.array([])
    imageCountList = [1, 2, 3, 4]
    trainImages = []
    
    testImageNumList = []
    # read train images
    for classIndex in xrange(classSize):
        
        randomNum = random.randint(1, 4)
        # randomNum = 4
        #print 'Find testImage',
        #print classIndex*5 + randomNum
        testImageNumList.append(classIndex*5 + randomNum)
        
        for imageNumber in imageCountList:
            if imageNumber != randomNum:
                imageNumber = classIndex*5 + imageNumber
                #print 'Find trainImage',
                #print imageNumber
                imageName = readImage.getImageName(imageNumber)

                SIFTdescr = processSIFT.getSIFT(imageName)
                
                # new train image object, with image name, classIndex and SIFT
                trainImageObject = Image.Image(imageName, classIndex, SIFTdescr)
                trainImages.append(trainImageObject)
                
                # merge all descriptors together
                totalDsecr = np.append(totalDsecr, SIFTdescr)
    
    totalDsecr = np.reshape(totalDsecr, (len(totalDsecr)/128, 128))
    totalDsecr = np.float32(totalDsecr)
    
    print 'START run Kmeans'
    compactness, labels, centers = cv.kmeans(totalDsecr, K, criteria = (cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER, 100000, 0.000001), attempts = 0, flags = cv.KMEANS_RANDOM_CENTERS)
    print 'Get compactness is', compactness
    print 'FINISH run Kmeans'
    
    start = 0
    # now, we assign labels to train images
    for trainImageIndex in xrange(len(trainImages)):

        descr = trainImages[trainImageIndex].SIFTdescr
        trainImages[trainImageIndex].setWordList(labels[start : start + len(descr)])
        
        wordList = trainImages[trainImageIndex].wordList
        
        tfList = getTFlist(wordList, K)
        trainImages[trainImageIndex].settfList(tfList)

        start = start + len(descr)
        
    # then, we aims to get idf list
    idfList = []
    for element in xrange(K):
        totalValue = 0
        
        for trainImageIndex in xrange(len(trainImages)):
            if trainImages[trainImageIndex].tfList[element] > 0:
                totalValue = totalValue + 1
        
        idfValue = math.log(len(trainImages)/totalValue)
        idfList.append(idfValue)
        
    # then, we build space vector model
    for trainImageIndex in xrange(len(trainImages)):
        tfList = trainImages[trainImageIndex].tfList
        
        svm = getVSM(tfList, idfList)
        trainImages[trainImageIndex].setVSM(svm)
        
    return (centers, trainImages, idfList, testImageNumList)

def getVSM(tfList, idfList):
    """
    This method transform tf list and idf list to vector space model.
        [float,float,...], [float,float,...] => [float,float,...]
    """
    
    svm = []
    
    for index in xrange(len(tfList)):
        tempNumber = tfList[index]*idfList[index]
        svm.append(tempNumber)
    return svm

def processTest(testImageNumList, K, idfList, centers):
    """
    This method used to process test images, and transform images to space vector model, and 
    return testImage list.
    """
    
    testImages = []
    
    processGap = len(testImageNumList)/10.0
    processIndex = 0
    
    for testImageIndex in xrange(len(testImageNumList)):
        
        if testImageIndex == len(testImageNumList) - 1:
            print ''
        
        elif testImageIndex > processIndex*processGap:
            
                sys.stdout.write('#')
                processIndex = processIndex + 1
        
        imageName = readImage.getImageName(testImageNumList[testImageIndex])
        SIFTdescr = processSIFT.getSIFT(imageName)
        
        # new train image object, with image name, testImageIndex and SIFT
        testImageObject = Image.Image(imageName, testImageIndex, SIFTdescr)
        testImages.append(testImageObject)
    
        wordList = []
        SIFTdescr = testImages[testImageIndex].SIFTdescr

        # get distances between SIFT and centers
        distances = getDistances(centers, SIFTdescr)
        
        for siftIndex in xrange(len(testImages[testImageIndex].SIFTdescr)):
            # minIndex, minValue = min(enumerate(distances[siftIndex], key = operator.itemgetter(1)))
            
            minIndex = np.argmin(distances[siftIndex])
            wordList.append(minIndex)
        
        # save word list into test image
        testImages[testImageIndex].setWordList(wordList)
        
        # get tf list
        tfList = getTFlist(wordList, K)
        
        svm = getVSM(tfList, idfList)
        testImages[testImageIndex].setVSM(svm)
        # print svm
    
    return testImages
        
def getTFlist(wordList, K):
    """
    This method use word list to transform word list to tf list.
        [int, int, int,...] => [float, float,...]
    
    """   
         
    # build a dictionary with all value equals to 0
    tempDict = {}
    for key in xrange(K):
        tempDict[key] = 0
        
    for index in xrange(len(wordList)):
        key = int(wordList[index])
        tempDict[key] = tempDict[key] + 1
        
    #print len(wordList)
    #print sum(tempDict.values())
        
    # then, we get tf list
    tfList = []
    for key in xrange(K):
        tfList.append(tempDict[key]/float(len(wordList)))
    
    return tfList

if __name__ == '__main__':
    pass