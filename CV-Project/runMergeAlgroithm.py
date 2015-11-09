'''
Created on May 20, 2015

@author: Mark
'''

import Image
import readImage
import time
import matlab.engine
import random
import math
import sys

import processSIFT

from edgeColorHistogram import getEdgeColorHistogram
from edgeColorHistogram import sim
from processDistances import getCosineSimiliary
from processImages import getTFlist
from processImages import getVSM
from processDistances import getDistances 
import numpy as np
import cv2 as cv

# from collections import Counter
import matplotlib.pyplot as plt

def main():
    
    start_time = time.time()
    
    print 'Prepare environment setting'
    
    mleng = matlab.engine.start_matlab()
    classSize = 50
    K = 1200 # optimal 1350
    againNum = 1
    c = 150
    
    repeatTime = 1
    
    correctNumList = []
    histNumList = []
    vsmNumList = []
    indexList = []
    
    for index in xrange(repeatTime):
        
        print 'Perform', index + 1, 'Test with', K
        totalBagNum = 0
        for index2 in xrange(againNum):
            print 'Perform', index2 + 1, 'Repeat'
            mergeCorrectNum, histCorrectNum, vsmCorrectNum = runMergeAlgorithm(classSize, K, c, mleng)
            totalBagNum = totalBagNum + vsmCorrectNum
        K = K + 50
        indexList.append(index)
        #correctNumList.append(mergeCorrectNum*2)
        #histNumList.append(histCorrectNum*2)
        vsmNumList.append(totalBagNum*2.0/againNum)
    
    print 'Total USED TIME: ', time.time() - start_time, 's'
    
    #plt.plot(correctNumList, label = 'Merge Algorithm')
    #plt.plot(histNumList, label = 'Histogram Algorithm')
    plt.plot(vsmNumList, label = 'Bag of Words Algorithm')
    
    plt.legend(loc = 'upper right')
    
    plt.show()
    

def runMergeAlgorithm(classSize, K, c, mleng):
    """
    This method aims to run merged algorithm which include bag-of-word
    and edgeHSVHist classification.
        int, int, int, mleng => int
    """
    
    # create all trainDict and testDict
    trainDict, testDict = getTrainAndTestDict(classSize)

    testCountList = sorted(testDict.keys())
    trainCountList = sorted(trainDict.keys())
    
    trainImageList = getImages(trainCountList, mleng)
    testImageList = getImages(testCountList, mleng)
    
    # set class labels to image lists
    trainImageList = setLabeltoImages(trainDict, trainImageList)
    testImageList = setLabeltoImages(testDict, testImageList)
    
    #for image in trainImageList:
        #print 'ImageName', getImageName(image.imageName), 'ImageClass', image.classLabel
        
    #for image in testImageList:
        #print 'ImageName', getImageName(image.imageName), 'ImageClass', image.classLabel
    
    # create idf list, and perform k-means to get clustering result
    centroids, labels = getCentroids(trainImageList, K)
    
    # then, we add word list and tf list to all images
    # trainImageList = setTFList(trainImageList, centroids, K)
    trainImageList = setTFListTrainImageFast(trainImageList, labels, K)
    
    testImageList = setTFList(testImageList, centroids, K)
    
    # then, get idf list from train image list
    idfList = getIDFList(trainImageList, K)
    
    # then, we add vector space model to all images
    trainImageList = setVSMtoImages(trainImageList, idfList)
    testImageList = setVSMtoImages(testImageList, idfList)
    
    ## self test for c
    #repeatC = 15
    #startC = 50
    
    #for cIndex in xrange(repeatC):
    
        ## get distances from test image to train images
        #imageDistances = getImagesDistances(trainImageList, testImageList, startC + cIndex*20)
    
        #correctNum = getCorrectNum(imageDistances, testImageList, trainImageList)
        
        #print 'When c is', startC + cIndex*20, 'correctNum is', correctNum
    
    weightH = 0.4
    histDistances = getHistgromDistances(trainImageList, testImageList, weightH)
    imageDistances = getImagesDistances(trainImageList, testImageList, c)
    vsmDistances = getVSMDistances(trainImageList, testImageList)
    
    print 'Merged Algorithm:'
    mergeCorrectNum = getCorrectNum(imageDistances, testImageList, trainImageList)
    print 'Histogram Algorithm:'
    histCorrectNum = getCorrectNum(histDistances, testImageList, trainImageList)
    print 'Bag of Words Algorithm:'
    vsmCorrectNum = getCorrectNum(vsmDistances, testImageList, trainImageList)
    
    return (mergeCorrectNum, histCorrectNum, vsmCorrectNum)
    
def getCorrectNum(imageDistances, testImageList, trainImageList):
    """
    This method used to get correctness percentage in based on given 
    distances information.
        [[float,..],..] => int
    """
    
    correctNum = 0
    for testImageIndex in xrange(len(testImageList)):
        correctAnswer = testImageList[testImageIndex].classLabel

        ourIndex = np.argmin(imageDistances[testImageIndex])
        ourAnswer = trainImageList[ourIndex].classLabel

        if correctAnswer == ourAnswer:
            correctNum = correctNum + 1
        else:
            pass
            #print 'Mismatch', getImageName(testImageIndex), '&', getImageName(ourIndex)
    print 'correct percentage is:', correctNum*2, '%'
    
    return correctNum
    
def setLabeltoImages(imageDict, imageList):
    """
    This method used to set class label to images.
        {int:int,...}, [image, image,..] => [image, image,..],
    """    
    
    for image in imageList:
        
        imageName = image.imageName
        
        classLabel = imageDict[imageName]

        image.setClassLabel(classLabel)
    
    return imageList
    
def getImagesDistances(trainImageList, testImageList, c):
    """
    This method used to get distances between trainImages and testImages.
        [image, image,...], [image, image,...], int => [[float, float,...]]
    """
    weightH = 0.4
    histDistances = getHistgromDistances(trainImageList, testImageList, weightH)
    histDistances = np.array(histDistances)
    vsmDistances = getVSMDistances(trainImageList, testImageList)
    vsmDistances = np.array(vsmDistances)
    
    imageDistances = getTotalSimilarity(vsmDistances, histDistances, c, testImageList)
    
    return imageDistances

def getTotalSimilarity(distVSM, distECH, c, testImageList):
    
    for testImage in testImageList:
        
        n = len(testImage.SIFTdescr)
        w = testImage.w
        h = testImage.h
    
        alpha = (float(c) * float(n)) / (float(w) * float(h) + float(c) * float(n))
        beta =  (float(w) * float(h)) / (float(w) * float(h) + float(c) * float(n))
        
    sim = ((alpha * distVSM) + (beta * distECH)) / (alpha + beta)
    np.savetxt("distances.txt", sim)
    # return np.abs(sim - np.ones(sim.shape) - np.ones(sim.shape))
    return sim

def getVSMDistances(trainImageList, testImageList):
    """
    This method used to get vector space model cosine similiarity distances
    between each test image and each train images.
        [image, image,...], [image, image,...] => [[float, float,...]]
    """
    
    cosineDistances = []

    # then, we compute cosine similarity of different images
    for testImage in testImageList:
        testVSM = testImage.VSM
        cosineDistance = []
        
        for trainImage in trainImageList:
            trainVSM = trainImage.VSM
            cosineSimilarity = getCosineSimiliary(testVSM, trainVSM)
            cosineDistance.append(1 - cosineSimilarity)
        
        cosineDistances.append(cosineDistance)
    
    return cosineDistances

def getHistgromDistances(trainImageList, testImageList, weightH):
    """
    This method used to get histgram distances between train images and
    test images.
        [image, image,...], [image, image,...] => [[float, float,...]]
    """
    
    histDistances = []
    testHists = []
    trainHists = []
    
    for testImage in testImageList:
        testHist = testImage.edgeColorHist
        testHists.append(testHist)
    
    for trainImage in trainImageList:
        trainHist = trainImage.edgeColorHist
        trainHists.append(trainHist)

    for testHist in testHists:
        
        histDistance = []
        
        for trainHist in trainHists:
            histDistance.append(sim(weightH, testHist, trainHist))
            
        histDistances.append(histDistance)
            
    return histDistances

def setVSMtoImages(imageList, idfList):
    """
    This method used to add vector space model to image based on idf list and
    tf list in image.
        [image, image,...], [float, float,..] => [image, image,...]
    """

    for imageIndex in xrange(len(imageList)):

        tfList = imageList[imageIndex].tfList

        vsm = getVSM(tfList, idfList)
        
        imageList[imageIndex].setVSM(vsm)
        
    return imageList
    
def getIDFList(trainImageList, K):
    """
    This method used to get inverse document frequency from train image list.
        [image, image,...], int => [image, image,...]
    """

    # then, we aims to get idf list
    idfList = []
    for element in xrange(K):
        totalValue = 0
        
        for trainImageIndex in xrange(len(trainImageList)):
            if trainImageList[trainImageIndex].tfList[element] > 0:
                totalValue = totalValue + 1
        
        idfValue = math.log(len(trainImageList)/totalValue)
        idfList.append(idfValue)
        
    return idfList
    
def setTFListTrainImageFast(trainImageList, labels, K):
    """
    This method used to use K-means labels result to set tf list to train images.
        [image, image,...], [[int, int,...]] => [image, image,..] 
    """
    start = 0
    
    for trainImage in trainImageList:

        descr = trainImage.SIFTdescr
        trainImage.setWordList(labels[start : start + len(descr)])
        
        wordList = trainImage.wordList
        
        tfList = getTFlist(wordList, K)
        trainImage.settfList(tfList)

        start = start + len(descr)
        
    return trainImageList
    
def setTFList(imageList, centroids, K):
    """
    This method used to set tf list to each image. We use centroids and compare
    distance between sift descriptors in image, and assign these features to 
    visual words.
        [image, image,..], [[int, int,...],...] => [image, image,..]
    """
    
    processGap = len(imageList)/10.0
    processIndex = 0
    imageCount = 0
    
    print 'PROCESS Images ',
    for image in imageList:
        
        if imageCount == len(imageList) - 1:
            print '#'
        
        elif imageCount > processIndex*processGap:
            
            sys.stdout.write('#')
            processIndex = processIndex + 1
        
        imageCount = imageCount + 1
        
        siftDescriptor = image.SIFTdescr

        wordList = []
        
        # get distances between SIFT and centers
        distances = getDistances(centroids, siftDescriptor)
       
        for siftIndex in xrange(len(siftDescriptor)):

            minIndex = np.argmin(distances[siftIndex])
            wordList.append(minIndex)
        
        # save word list into test image
        image.setWordList(wordList)
        
        # get tf list
        tfList = getTFlist(wordList, K)
        
        image.settfList(tfList)

    return imageList
    
def getCentroids(trainImageList, K):
    """
    This method used to get the centroids from k-means result and idf list.
        [int, int,...], K => [[int, int,...],...], [int, int,...]
    """
    
    totalSIFT = []
    
    for image in trainImageList:
        SIFTdescr = image.SIFTdescr
        totalSIFT = np.append(totalSIFT, SIFTdescr)
    
    totalSIFT = np.reshape(totalSIFT, (len(totalSIFT)/128, 128))
    totalSIFT = np.float32(totalSIFT)
    
    print 'START run Kmeans'
    compactness, labels, centers = cv.kmeans(totalSIFT, K, criteria = (cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER, 100000, sys.float_info.epsilon), attempts = 0, flags = cv.KMEANS_RANDOM_CENTERS)
    print 'Get compactness is', compactness
    print 'FINISH run Kmeans'
    
    return (centers, labels)
    
def getImages(imageCountList, mleng):
    """
    This method used to get the image list which contains basic information, 
    which contain sift descriptors, image name and edge colorhistogram
        [int, int,...], mleng => [image, image,...]
    """
    
    imageList = []
    
    for imageNumber in imageCountList:
        imageName = readImage.getImageName(imageNumber)
        image = getImageObject(imageName, mleng, imageNumber)
        imageList.append(image)
        
    return imageList


def getImageObject(imageName, mleng, imageNumber):
    """
    This method used to transform a image to a vector space model and edgeColorHistogram.
        str, mleng => image object
    """

    SIFTdescr = processSIFT.getSIFT(imageName)
    
    img = cv.imread(imageName)
    imageShape = img.shape
    
    # new train image object, with image name, classIndex and SIFT
    trainImageObject = Image.Image(imageNumber, SIFTdescr, imageShape[0], imageShape[1])
    trainImageObject.edgeColorHist = getEdgeColorHistogram(imageName, mleng)
    
    return trainImageObject

def getTrainAndTestDict(classSize):
    """
    This method aims to return two list, which include train list, contain all image count 
    used for train, and test list contain all image count used for test.
        int => [int, int,...], [int, int,...]
    """
    
    imageCountList = [1, 2, 3, 4]
    
    trainDict = {}
    testDict = {}
    
    for classIndex in xrange(classSize):
        testNum = random.randint(1, 4)
        testDict[classIndex*5 + testNum] = classIndex
        
        for imageNumber in imageCountList:
            if imageNumber != testNum:
                imageNumber = classIndex*5 + imageNumber
                trainDict[imageNumber] = classIndex
    
    return (trainDict, testDict)

if __name__ == '__main__':
    main()