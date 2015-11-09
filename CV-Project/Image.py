'''
Created on May 17, 2015

@author: Mark
'''

class Image(object):
    '''
    This class is used to save the trainImage class
    '''

    def __init__(self, imageNumber, SIFTdescr, w, h):
        self.imageName = imageNumber
        self.SIFTdescr = SIFTdescr
        self.w = w
        self.h = h
        self.wordList = []
        self.tfList = []
        self.VSM = []
        self.edgeColorHist = {}
        self.classLabel = -1
        
    
    def setClassLabel(self, classLabel):
        self.classLabel = classLabel
    
    def setWordList(self, wordList):
        self.wordList = wordList
    
    def getWordList(self):
        return self.wordList
    
    def settfList(self, tfList):
        self.tfList = tfList
        
    def gettfList(self):
        return self.tfList    
        
    def setVSM(self, vsm):
        self.VSM = vsm
        
    def getVSM(self):
        return self.VSM
    
    def setEdgeColorHist(self, edgeColorHist):
        self.edgeColorHist = edgeColorHist
        
