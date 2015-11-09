# COMP3620/6320 Artificial Intelligence
# The Australian National University
# Author: Nathan Robinson
# Date:   2014

""" Student Details
    Student Name: Sai Ma
    Student number: u5224340
    Date: 1/04/2014
"""

""" This file contains inference functions for you to implement to use with the
    backtracking search.
    
    Information about these inference functions can be found in the Constraint
    Satisfaction Problems chapter of the text book.
    
    You will need to implement the functions forward_checking and arc_consistency.
    
    To do this you will need to understand the structure of a CSP. Look in the file
    csp.py to understand this.
"""

import collections

def forward_checking(var, assignment, csp):
    """ Prune values from the domains of neighbouring variables which are arc
        inconsistent with the assigned value for the given variable.

        - var is the variable which has just been assigned in the problem.
        - assignment is a dictionary of the current assignments
        - csp is the CSP object

        In the case that the algorithm detects a conflict, the assignment and csp
        should remain unchanged and the function should return None.
       
        Otherwise, the algorithm should return a pair of (pruned_list, new_assignments) where:
            pruned_list - is a list of the variable, value pairs that will be pruned
                          out of the domains of variables in the problem.
            new_assignments - is a dictionary of the new assignments made by
                              the inference procedure
        
        You should not change assignment or csp yourself.
        
        To implement this algorithm you will likely have to keep track of the domains
        that are being changed by the inference procedure yourself.
        
        When doing forward checking you will never reduce the domain of a variable
        to empty (otherwise the algorithm would not try the given var, value pair).
        
        You may reduce the domain of a variable to size 1. The last remaining
        element in the domain in this case will be a new assignment. If this new
        assignment would cause a conflict, you should return None. Otherwise
        remember it to return at the end.
        
        (str, { str : str}, CSP) -> None / ([(str, str)], {str : str})
    """
    val = assignment[var]
    
    """ *** YOUR CODE HERE *** """
    
    ##create a queue to store the variables which its domains size equals to 1 (which could be used to remove conflicts return)
    queue = collections.deque()
    queue.append((var,val))
    
    ##create a pruned_list to store the var and value pairs which should be pruned
    pruned_list = []
    
    ##create a dict to save the information about unass_var and their size of domains
    new_assignment = {}

    ##save each variables and their domains into a temp dict
    domain_dict = {}
    for variables in csp.variables:
        domain_dict[variables] = list(csp.current_domains[variables])

    ##when the queue is not empty, test its element's neighbour's domains change to size 0 or not
    while len(queue) > 0:
        var_val = queue.popleft()
        
        ##for each variables in the neighbour of given variables
        for variables in csp.neighbours[var_val[0]]:

            ##if the given variable's value in its neighbour's domains, it should be add to pruned_list
            if var_val[1] in domain_dict[variables]:
                domain_dict[variables].remove(var_val[1])

                ##if the size of domain has been reduce to 1, it should be put into queue to wait to test it would raise conflict or not
                if len(domain_dict[variables]) == 1:
                    queue.append((variables,domain_dict[variables][0]))
                    if var_val[0] != var:
                        new_assignment[var_val[0]] = var_val[1]

                ##if the size of domain has been reduce to 0, it should return None
                if len(domain_dict[variables]) == 0:
                    return None

                ##else, this var-value pair should be added into prunded_list, if its size is 1, it should be the new_assignment
                else:
                    pruned_list.append((variables,var_val[1]))
                        
                        
    ##return the pruned_list and new_assignment(if have)
    return (pruned_list,new_assignment)

def arc_consistency(initial_var, assignment, csp):
    """ Take a CSP and do arc consistency on it.
    
        - initial_var is the variable which has just been assigned in the problem.
        - assignment is a dictionary of the current assignments
        - csp is the CSP object
        
        This function should implement both AC3 and MAC (Maintaining Arc Consistency).
        
        In the case that arc consistency is used for preprocessing, initial_var will be
        None and AC3 should be used.
        
        In the case that initial_var is not None, it will be a variable that has just
        been assigned in the problem. In this case implement MAC starting with
        the arcs including initial_var.

        The definition of AC-3 and MAC can be found in the Constraint Satisfaction
        Problem chapter of the text book.
        
        In the case that the algorithm detects a conflict, the assignment and csp
        should remain unchanged and the function should return None.
       
        Otherwise, the algorithm should return a pair of (pruned_list, new_assignments) where:
            pruned_list - is a list of the variable, value pairs that will be pruned
                          out of the domains of variables in the problem.
            new_assignments - is a dictionary of the new assignments made by
                              the inference procedure
        
        You should not change assignment or csp yourself.

        To implement this algorithm you will likely have to keep track of the domains
        that are being changed by the inference procedure yourself.
        
        You may reduce the domain of a variable to size 1. The last remaining
        element in the domain in this case will be a new assignment. AC3 should
        check for conflicts relating to this assignment by putting all required
        arcs back into the queue, so you don't have to write an explicit check here.
        
        We suggest that you use a collections.deque for your queue as it allows
        for constant time addition and removal of elements from both ends.
        
        (str, { str : str}, CSP) -> None / ([(str, str)], {str : str})
    """
    
    """ *** YOUR CODE HERE *** """

    ##save the information between unassignment_var and their domains to help trace in checking
    domains_dict = {}
    for var in csp.variables:
        domains_dict[var] = list(csp.current_domains[var])
    
    ##create a queue to store arcs
    queue = collections.deque()
    
    ##create a list to save prunded values and new_assignment to save new_assignment information
    prunded_list = []
    new_assignment = {}
    
    ##if have the initial_var, the MAC should be implement, one the other words, the initial_var should be test first
    if initial_var != None:
        for variables in csp.neighbours[initial_var]:
            if variables not in assignment.keys():
                queue.append((variables,initial_var))
    
    else:
        ##if not have initial_var, put all arcs from the csp into queue
        for X in csp.variables:
            for Y in csp.neighbours[X]:
                    
                ##for these X and Y, they are one arc, save arc into quueu
                queue.append((X,Y))

    ##while the queue is not empty
    while len(queue) > 0:
            
        ##pop off an arbitrary arc from the queue and make arc-consistent
        var_pairs = queue.popleft()
        Xi = var_pairs[0]
        Xj = var_pairs[1]
        
        ##for each values in domains of Xi
        for x in domains_dict[Xi]:
            result = list(revise(csp, Xi, x, Xj,domains_dict))
            
            if result[2]:
                domains_dict = result[0]
        
                ##if the domain of Xi to size 0
                if len(domains_dict[Xi]) == 0:
                    return None

                ##if the domains of Xi to size 1
                if len(domains_dict[Xi]) == 1:
                    new_assignment[Xi] = domains_dict[Xi][0]

                ##add the x into prunded_list
                prunded_list.append((Xi, result[1]))
        
                ##once delete Xi, x, it should check the neighbours variables Xk of Xi to Xi
                for Xk in csp.neighbours[Xi]:
                    if Xk != Xj:
                        queue.append((Xi,Xk))

                        '''as the textbook describe this line should not be exist, however, because of the situation of this
                        csp in this assignment, this could increase the productiveness because these var-val pairs in assignment
                        not perform to prund their neighbour's domain, because these pairs were put into sudoku directly, not by
                        backtracking search, this mean they have no opportunity to create prunded list. However, these assignment
                        var-val could reduce their neighbours' domain effectively, that is why I decide to add this line here.
                        '''
                        queue.append((Xk,Xi))
    
    return (prunded_list,new_assignment)


def revise(csp, Xi, x, Xj, domains_dict):
    '''this function used to test the 'if no value y in Dj allows (x,y) to satisfy the constraint between Xi and Xj'
        (csp, str, str, str, str, {str : set(str)}) -> ({str : set(str)}, str, bool)
    '''
    
    revised = False
    
    ##for each values in domain of Xi
    for y in domains_dict[Xj]:
                
        ##if the Xj-y raise a conflict between Xi-x, the revised would be assign to True
        if y not in csp.constraints[(Xi,x)][Xj]:
            revised = False
            break

        ##if there is no y in Xj can satisful the consistence with Xi-x pair    
        else:
            revised = True
    
    ##if there is a conflict, delete x
    if revised:
        domains_dict[Xi].remove(x)
        
    return (domains_dict, x, revised)

#-------------------------------------------------------------------------------
# A function use to get the correct inference method for the search
# You do not need to touch this.
#-------------------------------------------------------------------------------

def get_inference_function(inference_type):
    """ Return a function which does the specified inference on a given
        CSP problem.
        (str) -> (str, {str : str}, CSP) -> bool
    """
    
    if inference_type == "forward":
        return forward_checking
    if inference_type == "arc":
        return arc_consistency

    def inf(var, assignment, csp):
        return [], {}
    return inf
    

