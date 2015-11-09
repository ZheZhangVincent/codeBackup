# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014

""" Student Details
    Student Name: Sai Ma
    Student number: u5224340
    Date: 29/03/2014
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
    unassign_list = []
    for var in csp.variables:
        if var not in assignment:
            unassign_list.append(var)
    
    ##if there are no remaining unassigned variables, return none
    if len(unassign_list) == 0:
        return None
    else:
        
        ##build a dict to store the information between var and number of their number of constraints on other unassignment vairalbes
        dict_degree = {}    
        
        ##for each variables in the unassign list
        for unassign_var in unassign_list:
            
            ##choose any consistent value of the unassign_var to get its constraints number
            value = (csp.current_domains[unassign_var])[0]
            
            ##get the number of constraints for each unassign_var based on the choosen value
            num = len(csp.constraint[(unassign_var , value)])
            
            ##store unassigned var and their number of constraints
            dict_degree[unassign_var] = num
        
        ##get the max number of unassigned neighbour
        max_num = max(dict_degree.values())
        
        ##get the var which have the max number of constraints and build a list to store them
        degree_list = []
        for key,value in dict_degree.iteritems():
            if value == max_num:
                degree_list.append(key)
        
        ##return the heuristic value on the first order of list
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
    unassign_list = []
    for var in csp.variables:
        if var not in assignment:
            unassign_list.append(var)
    
    ##if there are no remaining unassigned variables, return none
    if len(unassign_list) == 0:
        return None
    else:
        
        ##build a dict to store the information between var and number of their legal values
        dict_mrv = {}

        ##get the legal values of each unassign_var in list_unassign
        for unassign_var in unassign_list:

            ##get the number of legal values of each unassigned var
            num = len(csp.current_domains[unassign_var])
            
            ##store the information between unassigned var and their number of legal values
            dict_mrv[unassign_var] = num
            
        ##get the minimum number of legal values of unassined var
        min_num = min(dict_mrv.values())
        
        ##get the list of unassigned var which number of legal values is min_mun
        mrv_list = []
        for key, value in dict_mrv.iteritems():
            if value == min_num:
                mrv_list.append(key)
                
        ##return the heuristic values on the first order of the list
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
    list_unassign = []
    for var in csp.variables:
        if var not in assignment:
            list_unassign.append(var)
    
    ##if there are no remaining unassigned variables, return none
    if len(list_unassign) == 0:
        return None
    else:
        for n in range(len(list_unassign)):
            
            ##build a dict to store the information between var and number of their unassign neighbour
            dict_degree = {}
            
            ##get the neighbour var for each var in unassign var string
            temp = csp.neighbours[list_unassign[n]]
            
            ##get the number of unassigned neighbour var
            temp_num = len(set(temp) & set(list_unassign))
            
            ##store unassigned var and their number of unassigned neighbour var
            dict_degree[list_unassign[n]] = temp_num
        
        ##get the max number of unassigned neighbour
        max_num = max(dict_degree.values())
        
        ##get the var which have the max number of unassigned neighbour and build a list to store them
        list_degree = []
        for key,value in dict_degree.iteritems():
            if value == max_num:
                list_degree.append(key)
        
        ##if the var which fulfil the maximum degree have more than one, perform mrv to select one
        if len(list_degree) > 1:
            for n in range(len(list_degree)):
                dict_mrv = {}
                temp_num = len(csp.current_domains[list_degree[n]])
                dict_mrv[list_degree[n]] = temp_num
            min_num = min(dict_mrv.values())
            
            list_mrv = []
            for key, value in dict_mrv.iteritems():
                if value == min_num:
                    list_mrv.append(key)
            return list_mrv[0]
        else:
            return list_degree[0]
            

def mrv_degree_heuristic(assignment, csp):
    """ Select the next variable from the CSP given the current assignment.
        Return None if there are no remaining unassigned variables.
        
        This heuristic implements the MRV heuristic and then the maximum degree
        heuristic for tiebreaking.
        
        ({str : str}, CSP) -> str
    """
    
    """ *** YOUR CODE HERE *** """
    
    ##build a list to get the unassigned var according to variables and assignment
    list_unassign = []
    for var in csp.variables:
        if var not in assignment:
            list_unassign.append(var)
    
    ##if there are no remaining unassigned variables, return none
    if len(list_unassign) == 0:
        return None
    else:
        for n in range(len(list_unassign)):
            
            ##build a dict to store the information between var and number of their legal values
            dict_mrv = {}
            
            ##get the number of legal values of each unassigned var
            temp_num = len(csp.current_domains[list_unassign[n]])
            
            ##store the information between unassigned var and their number of legal values
            dict_mrv[list_unassign[n]] = temp_num
            
        ##get the minimum number of legal values of unassined var
        min_num = min(dict_mrv.values())
        
        ##get the list of unassigned var which number of legal values is min_mun
        list_mrv = []
        for key, value in dict_mrv.iteritems():
            if value == min_num:
                list_mrv.append(key)
                
        ##if the var which fulfil the mrv have more than one, perform degree_heuristic to get one
        if len(list_mrv) > 1:
            for n in range(len(list_mrv)):
                dict_degree = {}
                temp = csp.neighbours[list_mrv[n]]
                temp_num = len(set(temp) & set(list_mrv))
                dict_degree[list_mrv[n]] = temp_num
            max_num = max(dict_degree.values())
            
            list_degree = []
            for key,value in dict_degree.iteritems():
                if value == max_num:
                    list_degree.append(key)
            return list_degree[0]
        
        else:
            return list_mrv[0]
    

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
    
    ##get the unassignment variables
    list_unassign = []
    for variable in csp.variables:
        if variable not in assignment:
            list_unassign.append(variable)
    
    ##get the given var's neighbour variable and store in a list
    list_neighbour = csp.neighbours[var]
    
    ##get the list_variable
    list_variable = []
    for variables in list_unassign:
        if variables in list_neighbour:
            list_variable.append(variables)
    
    ##get the domians of the values of var
    values = csp.current_domains[var]
    
    ##get the number of constraining values based on the neighbour variables and valuses in list_values and store in a dict
    dict_lcv = {}
    
    ##for each values in the values which can be assign to var
    for value in values:
        
        ##get a total_count to save total number of constraints based on this variable and value
        count = 0
        
        ##get the number total number of constraints based on the given var and its values
        temp_constraints = csp.constraints[(var, value)]
        for temp_var, temp_value in temp_constraints.iteritems():
            if temp_var in list_variable:
                count = count + 1
                
        ##save the information on var and number of constraints in dict_lcv
        dict_lcv[value] = count
    
    print dict_lcv
        
    ##sort the order of values in dict_lcv
    sorted(dict_lcv.values())
    
    print dict_lcv
    
    ##return the first value of the list
    return None
            

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
    

