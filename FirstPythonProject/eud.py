'''
Created on 25/03/2014

@author: masai
'''
import math


def Euclidean(list1, list2):
    '''(list1, list2) => number
    '''
    dist = 0
    
    for n in range(len(list1)):
        ##the two list have same length
        temp = (list1[n] - list2[n])**2
        dist = dist + temp

    dist = math.sqrt(dist)

    print dist
    return dist