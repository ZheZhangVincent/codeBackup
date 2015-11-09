# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014

""" Student Details
    Student Name: Sai Ma
    Student number: u5224340
    Date: 04/04/2014
"""

""" This is where you need to write your heuristics for variable selection and
    value ordering.
    
    Information about these heuristics can be found in the Constraint Satisfaction Problems
    chapter of the text book.
    
    You will need to implement: degree_heuristic, mrv_heuristic,
        degree_mrv_heuristic, mrv_degree_heuristic, and
        least_constrained_value_ordering
        
    To do this you will need to understand the structure of a CSP.
    Look in the file csp.py to understand this.
"""

#-------------------------------------------------------------------------------
# Variable Selection Heuristics
#-------------------------------------------------------------------------------

def next_variable_heuristic(assignment, csp):
    """ Select the next variable from the remaining variables.
        This heuristic just selects the next variable in the CSP's variable list.
        Return None if there are no remaining unassigned variables.
        
        We have implemented this one for you.
        
        ({str : str}, CSP) -> str
    """
    for var in csp.variables:
        if var not in assignment:
            return var
    return None
    

def degree_heuristic(assignment, csp):
    """ Select the next variable from the CSP given the current assignment.
        Return None if there are no remaining unassigned variables.
        
        This heuristic implements the maximum degree heuristic.
        
        It returns the variable that is involved in the largest number of
        constraints on other unassigned variables. That is, it returns the
        variable with the most unassigned neighbours.
        
        It can break ties between variables arbitrarily.
        
        ({str : str}, CSP) -> str
    """
    
    """ *** YOUR CODE HERE *** """

    ##build a list to get the unassigned var according to variables and assignment
    unassign_list = [unassign_var for unassign_var in csp.variables if unassign_var not in assignment]
    
    ##if there are no remaining unassigned variables, return none
    if len(unassign_list) == 0:
        return None
    else:
        
        ##build a dict to store the information between var and number of their number of constraints on other unassignment vairalbes
        dict_degree = {}
        
        ##for each variables in the unassign list
        for unassign_var in unassign_list:
            
            ##get the neighbour of the unassign_var which mean they have the relationship on constraints
            neighbour_list = csp.neighbours[unassign_var]
            num = 0
            ##get the num of the unassignment_var in the neighbour list
            for variables in neighbour_list:

                ##if the variables is not assignment, add 1 to the degree
                if variables in unassign_list:
                    num = num + 1
            
            ##store unassigned var and their number of constraints with neighbour unassigned variables
            dict_degree[unassign_var] = num
        
        ##get the max number of unassigned neighbour
        max_num = max(dict_degree.values())
        
        ##get the var which have the max number of constraints and build a list to store them
        degree_list = []
        for key,value in dict_degree.iteritems():
            if value == max_num:
                degree_list.append(key)

        ##sort the degree_list to return the first order of this list
        degree_list = sorted(degree_list)
        
        return degree_list[0]

def mrv_heuristic(assignment, csp):
    """ Select the next variable from the CSP given the current assignment.
        Return None if there are no remaining unassigned variables.
        
        This heuristic implements the minimum remaining values (MRV) heuristic.
        
        It can break ties between variables arbitrarily.
        
        ({str : str}, CSP) -> str
    """
    
    """ *** YOUR CODE HERE *** """
    
    ##build a list to get the unassigned var according to variables and assignment
    unassign_list = [unassign_var for unassign_var in csp.variables if unassign_var not in assignment]
    
    ##if there are no remaining unassigned variables, return none
    if len(unassign_list) == 0:
        return None
    else:
        
        ##build a dict to store the information between var and number of their legal values
        dict_mrv = {}

        ##get the legal values of each unassign_var in list_unassign
        for unassign_var in unassign_list:

            ##save the domains of unassign_var
            unassign_value_list = list(csp.current_domains[unassign_var])
            
            ##consider of the neighbours which has been assigned
            for assign_var in csp.neighbours[unassign_var]:
                if assign_var in assignment.keys():
                    for value in csp.constraints[(assign_var,assignment[assign_var])][unassign_var]:
                        if value in unassign_value_list:
                            unassign_value_list.remove(assignment[assign_var])
            
            num = len(unassign_value_list)
            
            ##store the information between unassigned var and their number of legal values
            dict_mrv[unassign_var] = num
            
        ##get the minimum number of legal values of unassined var
        min_num = min(dict_mrv.values())
        
        ##get the list of unassigned var which number of legal values is min_mun
        mrv_list = []
        for key, value in dict_mrv.iteritems():
            if value == min_num:
                mrv_list.append(key)
                
        ##sort the mrv_list to return the first order of the list
        mrv_list = sorted(mrv_list)
        
        return mrv_list[0]
    

def degree_mrv_heuristic(assignment, csp):
    """ Select the next variable from the CSP given the current assignment.
        Return None if there are no remaining unassigned variables.
        
        This heuristic implements the maximum degree heuristic.
        It then uses the MRV heuristic for tie breaking.
        
        ({str : str}, CSP) -> str
    """
 
    """ *** YOUR CODE HERE *** """
    
    ##build a list to get the unassigned var according to variables and assignment
    unassign_list = [unassign_var for unassign_var in csp.variables if unassign_var not in assignment]
    
    ##if there are no remaining unassigned variables, return none
    if len(unassign_list) == 0:
        return None
    else:
        
        ##build a dict to store the information between var and number of their number of constraints on other unassignment vairalbes
        dict_degree = {}
        
        ##for each variables in the unassign list
        for unassign_var in unassign_list:
            
            ##get the neighbour of the unassign_var which mean they have the relationship on constraints
            neighbour_list = csp.neighbours[unassign_var]
            num = 0
            ##get the num of the unassignment_var in the neighbour list
            for variables in neighbour_list:

                ##if the variables is not assignment, add 1 to the degree
                if variables in unassign_list:
                    num = num + 1
            
            ##store unassigned var and their number of constraints with neighbour unassigned variables
            dict_degree[unassign_var] = num
        
        ##get the max number of unassigned neighbour
        max_num = max(dict_degree.values())
        
        ##get the var which have the max number of constraints and build a list to store them
        degree_list = []
        for key,value in dict_degree.iteritems():
            if value == max_num:
                degree_list.append(key)

        degree_list = sorted(degree_list)
        
        ##if the var which fulfil the maximum degree have more than one, perform mrv to select one
        if len(degree_list) > 1:
            dict_mrv = {}
            for degree_var in degree_list:
                unassign_value_list = list(csp.current_domains[degree_var])
                for assign_var in csp.neighbours[unassign_var]:
                    if assign_var in assignment.keys():
                        for value in csp.constraints[(assign_var,assignment[assign_var])][degree_var]:
                            if value in unassign_value_list:
                                unassign_value_list.remove(assignment[assign_var])
                num = len(unassign_value_list)
                dict_mrv[degree_var] = num
            min_num = min(dict_mrv.values())
            
            mrv_list = []
            for key, value in dict_mrv.iteritems():
                if value == min_num:
                    mrv_list.append(key)

            mrv_list = sorted(mrv_list)
            return mrv_list[0]

        ##else, return the degree value without tie
        else:
            return degree_list[0]


def mrv_degree_heuristic(assignment, csp):
    """ Select the next variable from the CSP given the current assignment.
        Return None if there are no remaining unassigned variables.
        
        This heuristic implements the MRV heuristic and then the maximum degree
        heuristic for tiebreaking.
        
        ({str : str}, CSP) -> str
    """
    
    """ *** YOUR CODE HERE *** """
    
    ##build a list to get the unassigned var according to variables and assignment
    unassign_list = [unassign_var for unassign_var in csp.variables if unassign_var not in assignment]
    
    ##if there are no remaining unassigned variables, return none
    if len(unassign_list) == 0:
        return None
    else:
        
        ##build a dict to store the information between var and number of their legal values
        dict_mrv = {}

        ##get the legal values of each unassign_var in list_unassign
        for unassign_var in unassign_list:

            ##save the domains of unassign_var
            unassign_value_list = list(csp.current_domains[unassign_var])
            
            ##consider of the neighbours which has been assigned
            for assign_var in csp.neighbours[unassign_var]:
                if assign_var in assignment:
                    for value in csp.constraints[(assign_var,assignment[assign_var])][unassign_var]:
                        if value in unassign_value_list:
                            unassign_value_list.remove(assignment[assign_var])
            
            num = len(unassign_value_list)
            
            ##store the information between unassigned var and their number of legal values
            dict_mrv[unassign_var] = num
            
        ##get the minimum number of legal values of unassined var
        min_num = min(dict_mrv.values())
        
        ##get the list of unassigned var which number of legal values is min_mun
        mrv_list = []
        for key, value in dict_mrv.iteritems():
            if value == min_num:
                mrv_list.append(key)

        mrv_list = sorted(mrv_list)
                
        ##if the var which fulfil the mrv have more than one, perform degree_heuristic to get one
        if len(mrv_list) > 1:
            dict_degree = {}
            for mrv_var in mrv_list:
                num = 0
                for neigh_var in csp.neighbours[mrv_var]:
                    if neigh_var in unassign_list:
                        num = num + 1
            dict_degree[mrv_var] = num
            max_num = max(dict_degree.values())
            
            degree_list = []
            for key,value in dict_degree.iteritems():
                if value == max_num:
                    degree_list.append(key)

            degree_list = sorted(degree_list)
            return degree_list[0]

        ##else, return the mrv value without tie
        else:
            return mrv_list[0]
 

#-------------------------------------------------------------------------------
# Value Ordering Heuristics
#-------------------------------------------------------------------------------

def default_value_ordering(var, assignment, csp):
    """ Select an ordering of the values of the given variable given the current
        assignment and the CSP.
        
        This heuristic just returns the default ordering.
        
        We have implemented this heuristic for you.
        
        (str, {str : str}, CSP) -> [str]
    """
    return list(csp.current_domains[var])


def least_constrained_value_ordering(var, assignment, csp):
    """ Select an ordering of the values of the given variable given the current
        assignment and the CSP.
        
        This heuristic returns values in order of how constraining they are.
        It prefers the value that rules out the fewest choices for the
        neighbouring variables in the constraint graph.
        
        That is, it prefers values which remove the fewest elements from the current
        domains of their neighbouring variables.
        
        (str, {str : str}, CSP) -> [str]
    """
    
    """ *** YOUR CODE HERE *** """
    
    ##build a list to get the unassigned var according to variables and assignment
    unassign_neigh = [unassign_var for unassign_var in csp.variables if unassign_var not in assignment if unassign_var in csp.neighbours[var]]
    
    ##get the current domains of the given var
    var_domains = list(csp.current_domains[var])
    for assigned_var in csp.neighbours[var]:
        if assigned_var in assignment.keys() and assignment[assigned_var] in var_domains:
            var_domains.remove(assignment[assigned_var])
    
    ##create a list to save the relationship between value and its constraints which can be used to sort
    lcv = []
    
    ##get the number of constraints for each of value in var_domains
    for value in var_domains:
        num = 0
        constraint_list = csp.constraints[(var,value)]
        for neighbour_var in constraint_list.keys():

            ##if this neighbour_var is not assign
            if neighbour_var in unassign_neigh:

                ##get the current domains based on the assignment for neighbour_var
                temp_domains = list(csp.current_domains[neighbour_var])
                for item in csp.neighbours[neighbour_var]:
                    if item in assignment.keys() and assignment[item] in temp_domains:
                        temp_domains.remove(assignment[item])
                        
                ##if the constraints could reduce its domains, reduce it
                for item in constraint_list[neighbour_var]:
                    if item in temp_domains:
                        num = num + 1
        
        ##save the relationship between num and value
        lcv.append((num , value))

    ##sorted the number of constaints 
    lcv = sorted(lcv)
    
    ##create a list to save the order of values
    order_values = []
    
    ##add the values into list by their orders
    for index in range(len(lcv)):
        order_values.append(lcv[index][1])
    
    ##return the ordered list
    return order_values

#-------------------------------------------------------------------------------
# Functions used by the system to select from the above heuristics for the search
# You do not need to look any further.
#-------------------------------------------------------------------------------

def get_variable_selection_function(variable_heuristic):
    """ Return the appropriate variable selection function
    
        (str) -> ({str : str}, CSP) -> str
    """
    if variable_heuristic == "next":
        return next_variable_heuristic
    
    if variable_heuristic == "degree":
        return degree_heuristic
    
    if variable_heuristic == "mrv":
        return mrv_heuristic
    
    if variable_heuristic == "degree-mrv":
        return degree_mrv_heuristic
    
    if variable_heuristic == "mrv-degree":
        return mrv_degree_heuristic
    
    assert False, "Error: unknown variable selection heuristic: " +\
        str(variable_heuristic)
    
  
def get_value_ordering_function(value_heuristic):
    """ Return the appropriate value ordering function.
        (str) -> (str, {str : str}, CSP) -> [str]
    """
    if value_heuristic == "default":
        return default_value_ordering
    
    if value_heuristic == "lcv":
        return least_constrained_value_ordering
    
    assert False, "Error: unknown value ordering heuristic: " +\
        str(value_heuristic)
    

