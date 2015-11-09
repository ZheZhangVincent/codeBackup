# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014
# Nathan Robinson (nathan.m.robinson@gmail.com)

""" This file is responsible for calling the PrecoSAT binary and extracting the
    satisfying valuation which it returns (or detecting unsat, time out etc.)
"""

import sys, subprocess, os

from solver_base import SolvingException, Solver, solver_sat_code, solver_unsat_code,\
    solver_time_out_code, solver_error_code

solver_class = 'PrecoSAT'

class PrecoSAT(Solver):

    solver_sat_code = 10
    solver_unsat_code = 20
    solver_unknown_code = 0
    
    solver_path = os.path.join(os.path.dirname(__file__), "precosat")

    def __init__(self, cnf_file_name, tmp_path, exp_name, time_out):
        self.cnf_file_name = cnf_file_name
        self.tmp_path = tmp_path
        self.exp_name = exp_name
        self.time_out = time_out
        
        self.sln_file_name = os.path.join(tmp_path, exp_name + '.sln')
        self.run_str = self.solver_path
        if time_out is not None:
            self.run_str += " -t " + str(time_out)
        self.run_str += " " + cnf_file_name + " > " + self.sln_file_name
        
    def solve(self):
        try:
            with file(self.sln_file_name, 'w') as sln_file:
                solver_res = subprocess.call(self.run_str, stdout=sln_file, stderr=sln_file, shell=True)
            if solver_res not in [self.solver_sat_code, self.solver_unsat_code,
                self.solver_unknown_code]:
                raise SolvingException("There was a problem running precosat. Return code: " +\
                    str(solver_res), solver_error_code)
        except IOError as e:
            raise SolvingException("Error: could not open solution file: " + self.sln_file_name,
                solver_error_code)
        except OSError as e:
            sys.stdout.flush()
            raise SolvingException("Error: There was a problem running the solver: " + self.run_str,
                solver_error_code)
        try:
            with file(self.sln_file_name, 'r') as sln_file:
                sln_lines = sln_file.readlines()
                linepos = 0
                for linepos, line in enumerate(sln_lines):
                    if line[0] == 's': break
                if linepos < len(sln_lines):
                    result = sln_lines[linepos].rstrip().split(' ')[1]
                    if result == 'SATISFIABLE':
                        true_vars = filter(lambda x: x>0, \
                            map(int, sln_lines[linepos+1][:-1].split(' ')[1:]))
                        cpu_time = float(sln_lines[linepos+2].split(' ')[1])
                        return (True, cpu_time, true_vars)
                    elif result == 'UNSATISFIABLE':
                        cpu_time = float(sln_lines[linepos+1].split(' ')[1])
                        return (False, cpu_time, [])
                    elif sln_lines[linepos].rstrip() == 's UNKNOWN':
                        raise SolvingException("Solving timed out.", solver_time_out_code)
                    raise SolvingException("Solving CNF instance " +\
                        self.cnf_file_name + " resulted in an error:\n" + "\n".join(sln_lines),
                        solver_error_code)
                
                raise SolvingException("There was a problem in the solution file: " +\
                    self.sln_file_name, solver_error_code)
        except IOError:
            raise SolvingException("could not open solution file: " +\
                self.sln_file_name, solver_error_code)

