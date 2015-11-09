""" File name:   math_functions.py
    Author:      Sai Ma - u5224340
    Date:        21/2/2014
    Description: This file defines a set of variables and simple functions.
                 
                 It should be implemented for Exercise 1 of Assignment 0.
                 
                 See the assignment notes for a description of its contents.
"""

import math

pie = math.pi * math.e

twenty_radians = math.radians(20)


def quotient_ceil(numberator, denominator):
    '''(number, number) -> number
    Return the ceiling of the numberator divided by the denominator.

    >>>quotient_ceil(4, 3)
    2
    >>>quotient_ceil(7, 2)
    4

    '''
    
    return math.ceil(numberator / denominator)


def quotient_floor(numberator, denominator):
    '''(number, number) -> number
    Return the floor of the numberator divided by the denominator.

    >>>quotient_floor(4, 3)
    1
    >>>quotient_floor(7, 2)
    3

    '''
    return math.floor(numberator / denominator)


def euclidean(x1, y1, x2, y2):
    '''(number, number, number, number) -> number
    Return the Euclidean distance between the two points specified by these
    arguments

    >>>euclidean(3, 4, 2, 1)
    3.1622776601683795
    >>>euclidean(5, 3, 3, 1)
    2.8284271247461903   

    '''

    distence = math.sqrt((x1-x2)**2+(y1-y2)**2)
    return distence


