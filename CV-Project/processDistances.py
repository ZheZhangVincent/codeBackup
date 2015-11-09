'''
Created on May 17, 2015

@author: Mark
'''

import math
import numpy as np

def getDistances(centers, SIFTdescr):
    """
    This method used to get distances between centers and SIFTdescr by Euclidean distance
        [int, int,...], [int, int,...] => [int, int,...]
    """
    
    # centers, rows = K, column = 128
    # SIFTdescr, rows = actualNumber, column = 128
    
    distances = []
    
    for SIFTIndex in xrange(len(SIFTdescr)):
        
        distance = []
        
        for centerIndex in xrange(len(centers)):
            
            # get Euclidean Distances
            value = math.sqrt(sum((SIFTdescr[SIFTIndex] - centers[centerIndex])**2))
            
            distance.append(float(value))
        
        distances.append(distance)
    
    return distances

def getResults(testImages, trainImages):
    """
    This method used to collect results of train images and test images based on cosine similarity.
        [Image, Image,...], [Image, Image,..] = int
    """
    
    cosineDistances = []

    # then, we compute cosine similarity of different images
    for testImageIndex in xrange(len(testImages)):
        testVSM = testImages[testImageIndex].VSM
        cosineDistance = []
        
        for trainImageIndex in xrange(len(trainImages)):
            trainVSM = trainImages[trainImageIndex].VSM
            cosineSimilarity = getCosineSimiliary(testVSM, trainVSM)
            cosineDistance.append(cosineSimilarity)
        
        cosineDistances.append(cosineDistance)
        
    labelList = getLabelResult(cosineDistances, trainImages, testImages)
    correctNumber = getCorrectNumber(labelList)
    
    return correctNumber
    
def getCorrectNumber(labelList):
    """
    This method used to transformed label list result to the correct number.
        [int, int,...] => int
    """
    
    correctNumber = 0
    for lableIndex in xrange(len(labelList)):
        
        if labelList[lableIndex] == lableIndex:
            correctNumber = correctNumber + 1
            
    return correctNumber
    
def getLabelResult(cosineDistances, trainImages, testImages):
    """
    This method aism to transform distances to max labels.
        [[flato,...],[float,...]] => [int, int,...]
    """
    
    labelList = []
    
#     displayResults = []
#     for count1 in xrange(len(cosineDistances)):
#         displayResult = []
#         for count2 in xrange(len(cosineDistances[count1])):
#             displayResult.append(cosineDistances[count1, count2])
#         displayResults.append(displayResult)
#     
#     print 'Final Result'
#     for element in displayResults:
#         print element
    
    # maxApproachPoint = 0
    # KNNApproachPoint = 0
    
    for count in xrange(len(cosineDistances)):
        # print cosineDistances[count]
        
        maxIndex = np.argmax(cosineDistances[count]) # only left biggest index
        
        if trainImages[maxIndex].classLabel != count:
            
            print 'Find the', testImages[count].imageName,
            print 'incorrect match to', trainImages[maxIndex].imageName
        
            
            # maxApproachPoint = maxApproachPoint + 1
        
        # print 'No perform KNN, label is', trainImages[maxIndex].classLabel
        
        # transform type to numpy format
        # cosineDistance = np.array(cosineDistances[count])
        #sortIndexList = np.argsort(cosineDistance)
        
        # get biggest 5 index
        # biggestIndex = sortIndexList[len(sortIndexList) - 3 : len(sortIndexList)]
        
        # labels = []
        # for trainImageIndex in biggestIndex:
            # labels.append(trainImages[trainImageIndex].classLabel)
        
        # data = Counter(labels) # count biggest indexes list
        
        # tempList = data.most_common(1)
        
        # for element in tempList:
            # if element[-1] == 1:
                # print 'Find a single Point'
                # label = labels[-1]
            # else:
                # label = element[0]
        
        # if label != count:
            # KNNApproachPoint = KNNApproachPoint + 1
        
        # print 'After perform KNN, our result is', label
        label = trainImages[maxIndex].classLabel
        
        labelList.append(label)
    
    # print 'KNNApproachPoint is', KNNApproachPoint
    # print 'maxApproachPoint is ', maxApproachPoint
    
    return labelList
        
def getCosineSimiliary(testSVM, trainSVM):
    """
    This method used to compute the cosine similarity of two given space vector models.
        [float, float,...], [float, float,...] => float
    """
    
    # get up first
    up = 0
    for count in xrange(len(testSVM)):
        up = up + testSVM[count]*trainSVM[count]
        
    leftDown = 0
    rightDown = 0
    for count in xrange(len(testSVM)):
        leftDown = leftDown + testSVM[count]**2
        rightDown = rightDown + trainSVM[count]**2
        
    down = math.sqrt(leftDown)*math.sqrt(rightDown)
    
    cosineSimilarty = up/down
    
    return cosineSimilarty

if __name__ == '__main__':
    pass