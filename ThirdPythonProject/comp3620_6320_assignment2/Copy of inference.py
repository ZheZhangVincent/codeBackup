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
    
    ##build a unassign_list to get the unassigned var according to variables and assignment
    unassign_list = [unassign_var for unassign_var in csp.variables if unassign_var not in assignment if unassign_var in csp.neighbours[var]]
    
    ##create a pruned_list to store the values which should be pruned
    pruned_list = []
    
    ##reduce the unassigned var domians based on given var and value
    for unass_var in unassign_list:
        
        ##get the inconsistent value with unassign var and given var
        temp_value_list = (csp.constraints[var, val])[unass_var]
        
        ##for the value is in the domains of unass_var
        for temp_value in temp_value_list:

            if temp_value in csp.current_domains[unass_var]:
                pruned_list.append(temp_value)
                num = len(csp.current_domains[unass_var]) - 1

            ##while the num is greate than 1, means we should perform forward continue
            while num > 1:
                forward_checking(unass_var, assignment, csp)
                
            ##if reduce the domain of variable empty
            if num == 0:
                return None
        
            ##if reduce the domain of a variable to size 1, the last remianing element in the domain
            ##would be a new assignment
            if num == 1:
            
                ##if the new assignment would cause a conflict, return None
                if csp.conflicts(unass_var, csp.current_domains[unass_var]) > 0:
                    return None
            
                ##otherwise remember it to return at the end
                else:
                    return (pruned_list , {unass_var : csp.current_domains[unass_var]})

            
                

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
    
    ##create a list to store arc information
    arc_dict = {}
    
    ##create a list to save prunded values
    prunded_values = []
    
    ##build the arc list based on csp
    for Xi in csp.variables:
            
        ##get the constraints between Xi and Xj
        for Xj in csp.neighbour[Xi]:
            
            ##get value of domains of temp_var
            for Xi_value in csp.current_domains[Xi]:
                
                ##get value of domains of neigh_var
                for Xj_value in csp.current_domains[Xj]:
                    
                    ##add this arc constraint in arc_list
                    arc_dict[[Xi, Xj]] = (Xi_value, Xj_value)
        
    ##create a queue to store arc_dict
    queue = collections.deque(arc_dict)
    
    ##when the initial_var is none, the AC3 should be implement
    if initial_var is None:
        
        ##while the queue is not empty
        while len(queue) > 0:
            
            ##pop off an arbitrary arc from the queue and make arc-consistent
            var_pairs = queue.pop()
            Xi = (var_pairs.getkeys())[0]
            Xj = (var_pairs.getkeys())[1]
                
            ##test each value in domains of temp_var could satisfy the constraint between temp_var and neigh_var
            for x in csp.current_domains[Xi]:
                for y in csp.current_domains[Xj]:
                    
                    ##if not value satisfy the constraint between x and y
                    if (x,y) not in var_pairs.getvalues():
                        prunded_values.add(Xi,x)
                        (csp.current_domains[Xi]).remove(x)
                
            ##if the domain of a variable to size 0 or 1
            if len(csp.current_domains[Xi]) == 0:
                return False
            
            ##add (Xk, Xi) to queue
            for Xk in csp.neighbour[Xi].remove(Xj):
                for Xk_value in csp.current_domains[Xk]:
                    for Xi_value in csp.current_domains[Xi]:
                        arc_dict[[Xk, Xi]] = (Xk_value, Xi_value)
            queue.append(arc_dict)
                
            if len(csp.current_domains[Xi]) == 1:
                if csp.conflicts(Xi, csp.current_domains[Xi]) > 0:
                    return None
                else:
                    return (prunded_values, {Xi : csp.current_domains[Xi]})
    
    ##if the initial_var is not none, the MAC should be implement                     
    else:
        
        ##test constraint propagation, get neighbour_var of initial_var
        neighbour_list = csp.neighbour(initial_var)
        
        ##begin to test the arc consistent for each neighbour_var in neighbour_list
        for neighbour_var in neighbour_list:
            
            ##get the arc from queue
            for key, arc in queue:
                if key == (initial_var, neighbour_var):
                    arc_list = arc
            
            for x in csp.current_domains(initial_var):
                for y in csp.current_domains(initial_var):
                    if (x,y) not in arc_list:
                        prunded_values.add(initial_var,x)
                        (csp.current_domains[initial_var]).remove(x)
            
            ##if the domain of a variable to size 0 or 1
            if len(csp.current_domains[initial_var]) == 0:
                return False
            
            ##add (Xk, Xi) to queue
            for Xk in csp.neighbour[initial_var].remove(Xj):
                for Xk_value in csp.current_domains[neighbour_var]:
                    for Xi_value in csp.current_domains[initial_var]:
                        arc_dict[[initial_var, neighbour_var]] = (x, y)
            queue.append(arc_dict)
                
            if len(csp.current_domains[initial_var]) == 1:
                if csp.conflicts(initial_var, csp.current_domains[initial_var]) > 0:
                    return None
                else:
                    return (prunded_values, {initial_var : csp.current_domains[initial_var]})

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
    

