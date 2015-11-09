""" File name:   dfa.py
    Author:      Sai Ma - u5224340
    Date:        21/2/2014
    Description: This file defines a function which reads in a description of
                 a DFA from a file and builds an appropriate datastructure.
                 
                 It define another function which takes this DFA and a word
                 and returns iff the word is accepted by the DFA.
                 
                 It should be  implemented for Exercise 4 of Assignment 0.
                 
                 See the assignment notes for a description of its contents.
"""
start_s = ''
start_s = ''
accept_s = ''
a = []
edge = {}

def load_dfa(dfa_file_name):
    """ This function reads the DFA in the specified file and returns a
        data structure representing it. It is up to you to choose an appropriate
        data structure. The returned DFA will be used by your accepts_word
        function. Consider using a tuple to hold the parts of your DFA, one of which
        might be a dictionary containing the edges.
        
        We suggest that you return a tuple containing the names of the start
        and accepting states, and a dictionary which represents the edges in
        the DFA.
        
        (str) -> Object

        >>>load_dfa("test1.dfa")
        
    """
    
    ##used for the loop to assign edge{}
    i = 0
    ##used for the middle_value to assign edge{}
    temp = []
        
    dfa_file = open(dfa_file_name)
    line = dfa_file.readline()
    start_s = line[6:]
    line = dfa_file.readline()
    accept_s = line[10:]
    line = dfa_file.readline()
    while line:
        new_line = line.strip('\n')
        a.append(new_line[5:].split(' '))

    ##make these lines become individual elements in one tuple.
        
        line = dfa_file.readline()
    dfa_file.close()
    
    ##return start_s
    ##return accept_s
    ##because there are only three elements in a subtuple, the a[i][0],
    ##a[i][1] and a[i][-1]/a[i][2] can represent them respectively.

    while i < len(a):
        temp = a[i][0] + a[i][-1]
        edge[temp] = a[i][1]
        i = i + 1
    print "<---Import",
    print dfa_file_name,
    print "successful--->"

    print start_s
    print accept_s

def accepts_word(dfa, word):
    """ This function takes in a DFA (that is produced by your load_dfa function)
        and then returns True if the DFA accepts the given word, and False
        otherwise.
        
        (Object, str) -> bool

        >>>accepts_word('test1.dfa','planning')
        
    """
    load_dfa(dfa)
    print start_s
    word_list = []
    location = start_s


    temp = ' '.join(word)
    word_list.append(temp.split(' '))

    
    
    ##there is a assumption on this loop, the location + word_list[i] is
    ##unique, because if it is not, the DFA can move to two different
    ##state at same move
    
 
    for n in range(len(word)):
        if location + str(word_list[n]) in edge.keys():
            location = edge[location + str(word_list[n])]
        else:
            location = location


    while location in accept_s:
        print 'Accept word'
    else:
        print 'Refuse word'



