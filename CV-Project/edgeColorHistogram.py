from os import walk
from random import randint
import numpy as np
import cv2
import matlab.engine
import time

def getImage(mypath):
	fileList = ['HEAD']
	imgNum = 0
	for (dirpath, dirnames, filenames) in walk(mypath):
		break
	for filename in filenames:
		if filename.lower().endswith('.jpg'):
			imgNum += 1
			fileList.append(dirpath + '/' + filename)
	return fileList

def setRandomTestImage(fileList, clusterNum):
	randFileList = ['HEAD']
	for clusterCount in range(0, clusterNum):
		randIdx = randint(1, 4)
		for imgCount in range(1, (len(fileList) - 1) / clusterNum + 1):
			if imgCount != randIdx:
				randFileList.append(fileList[clusterCount * 4 + imgCount])
		randFileList.append(fileList[clusterCount * 4 + randIdx])
	return randFileList	

def getEdgeColorHistogram(imgPath, mleng):

	hist = {}
	
	histFromMatlab = mleng.edge_color_histogram(imgPath)
	hist[1] = np.asarray(histFromMatlab[0]) # h
	hist[2] = np.asarray(histFromMatlab[1]) # s
	hist[3] = np.asarray(histFromMatlab[2]) # v
	hist[4] = np.asarray(histFromMatlab[3]) # g
	
	return hist

def sim(weightH, testHist, trainingHist):
	
	weightV = 1 - weightH
	SD = np.zeros((2, 1))
	s = np.zeros((2, 1))

	for i in range(1, 5):
		SD = SD + testHist[i].sum(axis=1)
		s = s + (testHist[i] - trainingHist[i]).min(1)
	sim = 1 - s / SD
	sim = (weightH * sim[0, 1] + weightV * sim[1, 1]) / (weightH + weightV)
	
	return sim

