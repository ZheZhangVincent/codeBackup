""" File name:   truth_tables.py
    Author:      <Sai Ma - u5224340>
    Date:        <21/2/2014>
    Description: This file defines a number of functions which implement Boolean
                 expressions.
                 
                 It also defines a function to generate and print truth tables
                 using these functions.
                 
                 It should be implemented for Exercise 2 of Assignment 0.
                 
                 See the assignment notes for a description of its contents.
"""

def boolean_fn1(a, b, c):
    '''(bool, bool, bool) -> bool

    Return the bool value based on the given valuses of a, b and c, the decision
    function is (a and b) union (-b and c) union (-c and -a).

    >>>boolean_fn1(False, False, False)
    True
    >>>boolean_fn1(True, False, False)
    False

    '''
    
    res = (a and b) or (not b and c) or (not c and not a)
    return res

def boolean_fn2(a, b, c):
    '''(bool, bool, bool) -> bool

    Return the bool value based on the given valuses of a, b and c, the decision
    function is -(a imply b) imply -(-b imply -a).

    >>>boolean_fn2(False, False, False)
    True
    >>>boolean_fn2(True, False, False)
    True

    '''
    
    res = (not a or b) or not (b or not a)
    return res


def boolean_fn3(a, b, c):
    '''(bool, bool, bool) -> bool

    Return the bool value based on the given valuses of a, b and c, the decision
    function is ((a imply b) and -(b imply c)) imply (a imply c).

    >>>boolean_fn3(False, False, False)
    True
    >>>boolean_fn3(True, False, False)
    True
    >>>boolean_fn3(True, True, True)    
    True
    
    '''
    
    res = not ((not a or b) and (not b or c)) or (not a or c)
    return res


def draw_truth_table(boolean_fn):
    """ This function prints a truth table for the given boolean function.
        It is assumed that the supplied function has three arguments.
    
        ((bool, bool, bool) -> bool) -> None


    >>>draw_truth_table(boolean_fn1)
    a     b     c     res
    -----------------------
    False False False True
    False False True  True
    False True  False True
    False True  True  False
    True  False False False
    True  False True  True
    True  True  False True
    True  True  True  True
    
    """
    
    print 'a     b     c     res'
    print '-----------------------'
    print 'False False False',
    print boolean_fn(False, False, False)
    print 'False False True ',
    print boolean_fn(False, False, True)
    print 'False True  False',
    print boolean_fn(False, True, False)
    print 'False True  True ',
    print boolean_fn(False, True, True)
    print 'True  False False',
    print boolean_fn(True, False, False)
    print 'True  False True ',
    print boolean_fn(True, False, True)
    print 'True  True  False',
    print boolean_fn(True, True, False)
    print 'True  True  True ',
    print boolean_fn(True, True, True)
 
    
