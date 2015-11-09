'''
Created on May 14, 2015

@author: Mark
'''

from processImages import processTrainImages
from processImages import processTest
from processDistances import getResults
import time


# from collections import Counter
import matplotlib.pyplot as plt

# min_index, min_value = min(enumerate(a), key=operator.itemgetter(1))


def runDAapproach(classSize, K):
    """
    This method used to perform tf-idf approach to construct vector space model of each image,
    then perform classifier to classification.
    """
    start_time = time.time()
    
    print 'START process TrainImages'
    centers, trainImages, idfList, testImageNumList = processTrainImages(classSize, K)
    
    print 'PROCESS TrainImages USED TIME: ',
    test_time = time.time()
    print test_time - start_time
    
    print 'START process TestImages: ',
    testImages = processTest(testImageNumList, K, idfList, centers)
    
    print 'START get Results'
    correctNumber = getResults(testImages, trainImages)
    
    print 'Correct Percentages is',
    print correctNumber*2,
    print '%'
    
    print 'Total USED TIME: ',
    print time.time() - start_time
    
    return correctNumber


def main():
    
        startTime = time.time()
        
        classSize = 50
        startK = 1140 # 1140, current optimal
        correctNumberDict = {}
        gap = 0
        
        repeatTime = 20
        increaseTime = 1
        
        KValues = []
        CorrectList = []
        
        for value in xrange(increaseTime):
            
            totalCorrectNumber = []
            meanCorrectNumber = 0
            
            KValues.append(startK + value*gap)
            
            for repeatIndex in xrange(repeatTime):
                print 'For K value is', startK + value*gap,
                print 'Repeat at time', repeatIndex + 1
                correctNumber = runDAapproach(classSize, startK + value*gap)
                
                totalCorrectNumber.append(correctNumber)
    
                meanCorrectNumber = meanCorrectNumber + correctNumber*2
            
            CorrectList.append(meanCorrectNumber/float(repeatTime))
            
            correctNumberDict[startK + value*gap] = totalCorrectNumber
        
        print 'KValues:', KValues
        print 'CorrectList:', CorrectList
        
        print 'ALL SImuation time is',
        print time.time() - startTime, 's'
    
        
        plt.plot(KValues, CorrectList, 'ro')
        plt.show()
    
    # for key in correctNumberDict.keys():
        # print 'For K value is', key
        # print 'Results are: '
        # for count in correctNumberDict[key]:
            # print count*2,
            # print '%'
        # print
        
    #for count in xrange(len(totalCorrectNumber)):
        #print 'When K is',  K + count*50, 
        #print 'Correct percentage is', totalCorrectNumber[count]*2, 
        #print '%'
    
    
    
if __name__ == '__main__': main()