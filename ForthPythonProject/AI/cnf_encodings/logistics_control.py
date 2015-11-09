# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014
# Nathan Robinson (nathan.m.robinson@gmail.com)

""" Student Details
    Student Name: 
    Student number:
    Date:
"""

""" Part 2 Q10 - 20 Marks

    In this file you will make some constraints which represent domain-specific
    control knowledge for the Logistics domain (benchmarks/logistics).
    
    These constraints will be used in addition to a standard flat encoding of
    the Logistics problem instances.
    
    They should make solving the problem easier. This may be at the cost of
    optimality. That is, your additional constraints may rule out some solutions
    to make planning easier -- for example, by restricting the way elevators
    and passengers can move -- but they should preserve SOME solution (the
    problems might be very easy to solve if you added a contradiction, but
    wholly uninteresting!).
    
    Often control knowledge for planning problems is based on LTL
    (Linear Temporal Logic - https://en.wikipedia.org/wiki/Linear_temporal_logic)
    and you might get inspired by studying this. 
    
    We do not expect you to implement an automatic complication of
    arbitrary LTL into SAT, just some control knowledge rules for problems
    from the Logistics domain.
    
    As an example rule to get you started, you could assert that if a passenger
    was at their destination, then they could not leave.
    
    That is you could iterate over the goal of the problem to find the 
    propositions which talk about where the packages should end up and make
    some constraints asserting that if one of the corresponding fluents
    is true at step $t$ then it must still be true at step t + 1
    
    You will be marked based on the correctness, inventiveness, and
    effectiveness of the control knowledge you devise.
    
    You should aim to make at least three different control rules.
    Feel free to leave (but comment out) rules which you abandon
    if you think they are interesting and want us to look at them.
    
    Use the flag "-e logistics" to select this encoding. The execution semantics
    and plangraph options should work as normal.
"""

from strips_problem import Action, Proposition
from basic import BasicEncoding
encoding_class = 'LogisticsEncoding'

class LogisticsEncoding(BasicEncoding):
    """ An encoding which extends BasicEncoding but adds control knowledge
        specific for the Logistics domain.
    """

################################################################################
#                You need to implement the following methods                   #
################################################################################

    def make_control_knowledge_variables(self, horizon):
        """ Make the variable for the problem.

            Use the function self.new_cnf_code(step, name, object) to make
            whatever codes for CNF variables you need to make your control
            knowledge for the Logistics problem.
            
            You can make variables which mean anything if you can think of 
            constraints to make that enforce that meaning. As an example, you
            might make variables which keep track if each passenger has ever
            been in an elevator and is now not.
            
            For a passenger p, and t > 0:
                was_boarded(p)@t <->
                    (-boarded(p)@t ^ (boarded(p)@t-1 v was_boarded(p)@t-1))
            
            You can then use these variables, along with the fluent and action
            variables to make your control knowledge.
            
            (LogisticsEncoding, int) -> None
        """
        #You might want to save your variables here, or feel free to make as many data
        #structures as you need to keep track of them.
        self.control_fluent_codes = {}

        """ *** YOUR CODE HERE *** """
        

    def make_control_knowledge(self, horizon):
        """ This is where you should make your control knowledge clauses.
            
            These clauses should have the type "control".
            
            (LogisticsEncoding, int) -> None
        """
        
        """ *** YOUR CODE HERE *** """


################################################################################
#                    Do not change the following method                        #
################################################################################

    def encode(self, horizon, exec_semantics, plangraph_constraints):
        """ Make an encoding of self.problem for the given horizon.
        
            For this encoding, we have broken this method up into a number
            of sub-methods that you need to implement.

           (LogisticsEncoding, int, str, str) -> None
        """
        super(LogisticsEncoding, self).encode(horizon, exec_semantics, plangraph_constraints)
        self.make_control_knowledge_variables(horizon)
        self.make_control_knowledge(horizon)

