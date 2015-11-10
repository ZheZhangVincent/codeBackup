# minimax_agent.py
# --------------
# COMP3620/6320 Artificial Intelligence
# The Australian National University
# For full attributions, see attributions.txt on Wattle at the end of the course

""" Q5 Minimax (4 marks)
    
    Your code for the MinimaxAgent goes in this class. The MinimaxAgent runs on
    AdversarialSearchProblems (in search_problems.py).
    
    In these problems the red bird and the black bird compete to see who can find
    the most yellow birds. The two agents take turns to move. They cannot move
    into each other. The red bird always goes first. Neither agent can stay still
    unless they have no other valid move.
    
    You have two major tasks:
    
    1. Implement the minimize and maximize methods below (2 marks).
    2. Implement a suitable evaluation function for AdversarialSearchProblems to replace
       the one that is there (2 marks).
    
    Like all other agents, MinimaxAgent has a get_action method. We have implemented
    this for you. It creates an instance of an AdversarialSearchProblem. Feel
    free to look at this class in search_problems.py, but have described all 
    of the information which you need below.
    
    In an AdversarialSearchProblem a state is a tuple:
        (player, red_pos, black_pos, yellow_birds, score) where:
        - player is an int indicating the id of the current player -- the player
            who's turn it is to play in this state (0 for red, 1 for black)
        - red_pos is a tuple (x,y) of integers specifying the red bird's position
        - black_pos is a tuple (x,y) of integers specifying the black bird's position
        - yellow_birds: a tuple of tuples specifying the positions of the remaining yellow birds.
        - score: the current score reported in the UI: the sum of the values of the
                  yellow birds collected by red minus the values of those collected
                  by black  
        - yb_value: the current value of a yellow bird
   
    There are the following methods which you may need to use:
    
    - problem.get_maximizing_player() - Returns the id of the maximizing player.

    - problem.opponent(state) - Returns the id of the opponent of the current player in
         the given state
    
    - problem.utility(state) - Returns the utility of a given state. This is defined as
        the score of the state. This is saved in the state representation and displayed
        on the game board. It is defined as the sum of the values of the yellow birds
        collected by red minus the values of those collected by black.

    - problem.terminal_test(state) - Returns if a state is terminal -- i.e. if there are
         no yellow birds left

    - problem.get_successors(state) - Returns the successor states of the given state.
        These are returned as tuples of (state, action, cost) where
            - state is a successor,
            - action is the action used to reach it, and
            - cost is the cost of that action (always 1 and can be ignored here)
        Agents can not move through each other and can only stay still if they
        have no other option.
    
    - problem.get_width() - Returns the width of the maze.

    - problem.get_height() - Returns the height of the maze.

    - problem.get_walls() - Returns the matrix representing the walls -- [[bool]]

    - problem.maze_distance(pos1, pos2) - Return the distance of the shortest path
        between two locations in the maze. This has been precomputed and stored
        with the layouts.
    
    When implementing the maximize and minimize functions below, remember that
    A MINIMIZING OR MAXIMIZING STEP COUNTS AS 1 DEPTH and the algorithm should
    stop when the current_depth == self.depth.
    
    While all instances of a SearchProblem contain a dictionary heuristic_info
    which is used to store information. In this case, it will not be too helpful
    to use. This is because here a new AdversarialSearchProblem is created in the
    get_action method below every time the game requests a new action from
    MinimaxAgent so this information will be forgotten.
    
    Black bird behaviour:
    ----------------------------------------------------------------------------
    
    The black bird you are competing against really wants to save the yellow
    birds. It will always try to move towards the closest yellow bird via the
    shortest route, ignoring the position of the red bird. If the red bird gets
    in its way  it will try moving in another direction, but will probably end
    up right back where it was. It never re-plans.
"""


from agents import Agent
import util

from search_problems import AdversarialSearchProblem

class MinimaxAgent(Agent):
    """ The agent you will implement to compete with the black bird to try and
        save as many yellow birds as possible. """

    def __init__(self, max_player, depth="2"):
        """ Make a new Adversarial agent with the optional depth argument.
            (MinimaxAgent, str) -> None
        """
        self.max_player = max_player
        self.depth = int(depth)

    def evaluation(self, problem, state):
        """ Q5 - 2 Marks. You need to implement a good evaluation function in
            here. This is called when the search cannot reach a terminal state
            within the given depth limit.
            
            The value that this function returns should reflect an estimate of
            the expected utility from the given state. However, you might find
            this exercise difficult if you take that too literally.
            
            See the comments at the top of this file to see what methods you can
            use from the problem object.
            
            By default this just returns the utility of the given state. This is
            probably a poor measure of the expected utility in a terminal state
            that is likely to be reached from this state. So, implement your own,
            better method.
            
            (MinimaxAgent, AdversarialSearchProblem,
                (int, (int, int), (int, int), ((int, int)), number, number))
                    -> number
        """
        player, red_pos, black_pos, yellow_birds, score, yb_score = state

        ##in order to make a return result which can help red-bird make a decision,
        ##we should try our best to calculate the finial score in this trend, not just
        ##return the current_socre. then we calculate score will get based on the current
        ##location to the rest yellow_birds in the map.

        ##firstly, calculate the idealization red bird score based on current location       
        red_score = 0

        ##calcuate score to every rest yellow_bird
        for n in range(len(yellow_birds)):
            distence = problem.maze_distance(red_pos, yellow_birds[n])

            ##if when red achieve yellow, yb_score still greater than 1, add the yb_score
            if yb_score - distence > 1:
                red_score = red_score + yb_score - distence

            ##else (ye_score less than 1), red score add 1
            else:
                red_score = red_score + 1
   
        
        ##secondly, calcuate the idealization black bird score based on current location
        black_score = 0
            
        ##calcuate score to every rest yellow_bird
        for n in range(len(yellow_birds)):
            distence = problem.maze_distance(black_pos, yellow_birds[n])

            ##if when black achieve yellow, yb_score still greater than 1, add the yb_score
            if yb_score - distence > 1:
                black_score = black_score + yb_score - distence

            ##else (ye_score less than 1), black score add 1
            else:
                black_score = black_score + 1

        ##calcuate the returen value result based on current_score, black_score(minus) and red_score(add)
        result = score - black_score + red_score

        return result
                

    def maximize(self, problem, state, current_depth):
        """ Q5 - Your method for maximize (1 mark)
            It will call minimize unless current_depth == self.depth or
            state is a terminal state.
     
            This method should return a pair (max_utility, max_action).
     
             (MinimaxAgent, AdversarialSearchProblem,
                 (int, (int, int), (int, int), ((int, int)), number, number)
                     -> (number, str)
        """
        
        "*** YOUR CODE GOES HERE ***"

        ##make sure the current player is a maximize player
        if problem.get_maximizing_player() == 0:
                    
            ##if search to terminal node or to the end of search depth, stop search        
            if problem.terminal_test(state) or current_depth == self.depth:
            
                ##make the value and action to be pair and return
                return (self.evaluation(problem, state),'no action')

            else:
                ##if the current state is not the terminal state, make value is -infinity
                max_utility = - float("inf")
                    
                ##evaluate the next depth based on current state
                for nextmove in problem.get_successors(state):

                    ##get the value in the following search result from minimize method based on 'move' state
                    value = self.minimize(problem, nextmove[0], (current_depth + 1))
                    
                    ##compare with value and max_utility, then choose the big one
                    if value > max_utility:
                        max_utility = value
                        max_action = nextmove[-2]
                
                ##make max_action and max_utility to become a pair and return
                
                return (max_utility, max_action)
 
    def minimize(self, problem, state, current_depth):
        """ Q5 - Your function for minimize (1 mark)
            It will call maximize unless current_depth == self.depth or
            state is a terminal state.
            
            This function should just return the minimum utility.
            
            (MinimaxAgent, AdversarialSearchProblem,
                 (int, (int, int), (int, int), ((int, int)), number, number)
                     -> number
        """
        
        ##if search to terminal node or to the end of search depth, stop search
        if problem.terminal_test(state) or current_depth == self.depth:
                   
            ##make the value and action to be pair and return
            return self.evaluation(problem, state)

        else:
        
            ##if the current state is not the terminal state, make value is infinity
            min_utility = float("inf")
                
            ##evaluate the next depth based on current state
            for nextmove in problem.get_successors(state):

                ##select the max value in the following search result
                value,_ = self.maximize(problem, nextmove[0], (current_depth + 1))
                ##if the value is less than min_utility, select value
                if value < min_utility:
                    min_utility = value
                    
            ##return the min_utility
            
            return min_utility
                

    def get_action(self, game_state):
        """ This method is called by the system to solicit an action from
            MinimaxAgent. It is passed in a State object.
            
            Like with all of the other search problems, we have abstracted
            away the details of the game state by producing a SearchProblem.
            You will use the states of this AdversarialSearchProblem to
            implement your minimax procedure. The details you need to know
            are explained at the top of this file.
        """
        #We tell the search problem what the current state is and which player
        #is the maximizing player (i.e. who's turn it is now).
        problem = AdversarialSearchProblem(game_state, self.max_player)
        state = problem.get_initial_state()
        utility, max_action = self.maximize(problem, state, 0)
        return max_action

