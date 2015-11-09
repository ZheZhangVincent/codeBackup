# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014

""" This is the base file of the CSP solver.
    
    It can solve CSPs with binary and unary constraints as well as
    alldiff and allsame constraints. These latter constraints are decomposed
    into binary constraints by the system.
    
    It has a special output mode to nicely format the solutions to Sudoku
    problems with a given format.
    
    See problem_syntax.txt for a description of the file format.
    
    Run the solver with the -h flag for usage information.
    
    It should not be necessary to look at the code in this file.
    
    ********** Do not modify any code in this file **********
"""

import sys
import argparse
from csp import CSP
from heuristics import get_variable_selection_function, get_value_ordering_function
from inference import get_inference_function
from backtracking_search import search

def parse_cmd_line_args():
    """ Parse the command line arguments and return an object with attributes
        containing the parsed arguments or their default values.
        () -> argparse.Namespace
    """
    parser = argparse.ArgumentParser()
    parser.add_argument("input_file_name", metavar="INPUT",
        help="The CSP file to read.")
    parser.add_argument("-o", "--output", dest="output_file_name", metavar="OUTPUT",
        help="write the grounded CSP to a file (don't solve it)") 
    parser.add_argument("-s", "--solution", dest="solution_file_name", metavar="SOLUTION",
        help="write the satisfying assignment to this file.")
    parser.add_argument("-v", "--var_heuristic", dest="variable_heuristic", 
        choices=["next", "degree", "mrv", "degree-mrv", "mrv-degree"], default="next",
            metavar="VAR", help="Select-Unassigned-Variable heuristic "+\
            "[%(choices)s] (default: %(default)s)")
    parser.add_argument("-l", "--val_heuristic", dest="value_heuristic", 
        choices=["default", "lcv"], default="default",  metavar="VAL",
        help="Order-Domain-Values heuristic " +\
             "[%(choices)s] (default: %(default)s)")
    parser.add_argument("-p", "--preprocessing", dest="preprocessing", 
        choices=["arc"], default=None,  metavar="PRE",
        help="Preprocessing inference function " +\
             "[%(choices)s] (default: %(default)s)")
    parser.add_argument("-i", "--inference", dest="search_inference", 
        choices=["forward", "arc"], default=None,  metavar="INF",
        help="Preprocessing inference function " +\
             "[%(choices)s] (default: %(default)s)")   
    parser.add_argument("-k", "--sudoku", dest="sudoku_output", 
        action="store_true", default=False,
        help="interpret the solution as Sudoku output and display.")
    
    args = parser.parse_args()
    
    """
    print "Command line options:"
    print "    Input file:         ", args.input_file_name
    print "    Output file:        ", args.output_file_name
    print "    Solution file:      ", args.solution_file_name
    print "    Variable heuristic: ", args.variable_heuristic
    print "    Value heuristic:    ", args.value_heuristic
    print "    Preprocessing:      ", args.preprocessing
    print "    Search Inference:   ", args.search_inference 
    print "    Sudoku output:      ", args.sudoku_output
    """
    
    return args


def main():
    """ Parse the command line arguments. Make the CSP object. Do preprocessing
        if requested. Then call the search object.
        () -> None
    """
    args = parse_cmd_line_args()
    
    #Get the appropriate functions to be used by the search
    variable_selection_function = get_variable_selection_function(args.variable_heuristic)
    value_ordering_function = get_value_ordering_function(args.value_heuristic)
    inference_pre_function = get_inference_function(args.preprocessing)
    inference_search_function = get_inference_function(args.search_inference)

    print "Parsing CSP file:", args.input_file_name
    csp = CSP()
    if not csp.parse_csp_file(args.input_file_name):
        return 
    print "Success."
    initial_assignment = dict([ (var, csp.domains[var][0])\
        for var in csp.variables if len(csp.domains[var]) == 1 ])
    #Apply preprocessing if requested
    if inference_pre_function is not None:
        print "Preprocessing..."
        result = inference_pre_function(None, initial_assignment, csp)
        
        if result is None:
            print "Error: inconsistency detected in preprocessing."
            return
        csp.notify_of_inference(None, initial_assignment, result[0], result[1])
        print "Preprocessing made", len(result[1]), "assignments."

    if args.output_file_name is not None:
        print "Writing grounded CSP to:", args.output_file_name
        try:
            with file(args.output_file_name, "w") as output_file:
                csp.write(output_file)      
        except IOError as e:
            print "Error: cannot open output file:", args.output_file_name
        return
    
    print "Performing backtracking search..."
    assignment, explored, search_time = search(csp, initial_assignment,
        variable_selection_function, value_ordering_function, inference_search_function)
    
    if args.solution_file_name is None:
        solution_file = sys.stdout
    else:
        try:
            solution_file = file(args.solution_file_name, "w")
        except IOError as e:
            print "Error: could not open output file:", args.solution_file_name
            return
    
    #Display the result
    if assignment is None:
        print "There is no solution!"
        solution_file.write("UNSAT\n")
        solution_file.write("Explored: " + str(explored) + "\n")
        solution_file.write("Time: " + str(search_time) + "\n")
    else:
        print "Solution found."
        if args.sudoku_output:
            for y in xrange(1, 10):
                for x in xrange(1, 10):
                    solution_file.write(assignment[str(x)+str(y)] + " ")
                    if x % 3 == 0 and x < 9:
                        solution_file.write("| ")
                solution_file.write("\n")
                if y%3 == 0 and y < 9:
                    solution_file.write("---------------------\n")
        else:
            solution_file.write("SAT\n")
            solution_file.write("Explored: " + str(explored) + "\n")
            solution_file.write("Time: " + str(search_time) + "\n")
            solution_file.write("Solution: " + " ".join([var+"="+val\
                for var,val in assignment.iteritems() ]) + "\n")


if __name__ == "__main__":
    main()
