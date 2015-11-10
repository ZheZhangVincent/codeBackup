# search_strategies.py
# ----------------
# COMP3620/6320 Artificial Intelligence
# The Australian National University
# For full attributions, see attributions.txt on Wattle at the end of the course

""" This class contains search functions which you are required to implement
    for Q1-Q3 in the assignment.

    Your search algorithms needs to return a list of actions [str] that reaches
    the goal from the start state in the given problem.

    Make sure to implement graph search algorithms. We suggest that you look at
    the lecture notes to find out how to do this.

    In this case, your search algorithms will be passed a SearchProblem as
    defined in search_problems.py. You can take a look at this if you like,
    but all of the methods you will need to use are listed later in this comment.

    In practice, the search algorithms will either be run with a
    PositionSearchProblem or a MultiplePositionSearchProblem.

    In the former case, a state is a tuple (int, int) representing the
    postion of the red bird. In the latter, a state is a tuple
    ((int, int), ((int, int), ...)) containing a tuple representing the
    position of the red bird and the positions of the remaining yellow birds.

    What is important is that in either case state is hashable and can be
    put into a set or used as the key in a dictionary if your algorithms
    require it.

    The methods from problem that you will require are as follows:
        
        - problem.get_initial_state()
            (SearchProblem) -> state

        - problem.goal_test(state)
            (SearchProblem, state) -> bool

        - problem.get_successors(state)
            (SearchProblem, state) -> [(state, str, int)]
        
            Here, the successors are a list of triples (successor, action, cost):
                - successor is a successor to the current state,
                - action is the action required to get there,
                - cost is the cost of the action.

    We suggest that you use the SearchNode class defined in this file to implement
    your searches.
"""

import util, heuristics, frontiers

class SearchNode:
    """ The data structure representing a search node. It has
        state:      A state as defined by the appropriate SearchProblem
        action:     The action that led to this state from its parent
        path_cost:  The cost to get to this state from the start state
        parent:     The parent SearchNode

        This is created for you to use when implementing the search problems
        later in this file. You do not need to modify this.
    """
    
    def __init__(self, state, action, path_cost, parent):
        """ Make a new search node with the given parameters.
            (SearchNode, state, str, int, SearchNode) -> None
        """
        self.state = state
        self.action = action
        self.path_cost = path_cost
        self.parent = parent

    def __str__(self):
        """ Return a string representation of the SearchNode
            (SearchNode) -> str
        """
        return "Node(" + str(self.state) + ", " + str(self.action) + ", " +\
            str(self.path_cost) + ", " + str(self.parent) + ")"
  
#-------------------------------------------------------------------------------
# YOUR CODE STARTS HERE
#-------------------------------------------------------------------------------
 
#You could write some helper methods here. Maybe a generic search function
#to be used by the following searches if you so desire.

def depth_first_search(problem):
    """ Q1: Depth-First-Search (2 marks)
        DFS searches the deepest nodes in the search tree first.
        
        DFS is best implemented with a Stack found in frontiers.py. It can
        be accessed as follows:
            
            stack = frontiers.Stack()
            
        We suggest that you use the SearchNode class which is defined earlier
        in this file.
          
        (SearchProblem) -> [str]
    """
  
    """ *** YOUR CODE HERE *** """
    
    ##prepare the open list(stack) and close list to perform graph search
    stack = frontiers.Stack()
    close = []
    
    ##prepare the path to save node from start to goal
    path = []
    
    ##make start node as a object and assign its values
    start = SearchNode(problem.get_initial_state(), '', 0, '')

    ##at the same time, push the start node into the stack
    stack.push(start)
    
    ##test the stack.peek() is goal node or not
    while not problem.goal_test(stack.peek().state):
        
        ##when the node has never explored before, continue, or go to *1 part
        if stack.peek().state not in close:
            
            ##add the explored node to close list
            close.append(stack.peek().state)

            ##save next nodes information into a list
            nextstate = []
            
            for state in problem.get_successors(stack.peek().state):
                nextstate.append(state)

            ##save the explored node to father node and delete it from stack
            parent = stack.pop()
            
            ##if the next nodes has been explored before
            for item in nextstate:
                if item[0] not in close:
                    next_node = SearchNode(item[0], item[1], parent.path_cost + item[2], parent)

                    ##save the next node into stack
                    stack.push(next_node)
                            
        ##' *1 ': if the node has explored before, delete it
        else:
            stack.pop()

        ##if the stack is empty, return failure to find solution    
        if stack.is_empty():
            return failure
            
    ##get the goal node is stack.peek(), by find its parent node to collect
    ##the actions in the path from goal node to start node
    temp = stack.peek()

    while temp.state is not start.state:
        path.append(temp.action)
        temp = temp.parent
        
    ##change the order of the actions to get a path from start to goal
    path = path[::-1]
       
    return path
    
def breadth_first_search(problem):
    """ Q2: Breadth-First-Search (2 marks)
        
        BFS search the shallowest nodes in the search tree first.
        
        BFS is best implemented with a Queue found in frontiers.py. It can
        be accessed as follows:
            queue = frontiers.Queue()

        We suggest that you use the SearchNode class which is defined earlier
        in this file.
        
        (SearchProblem) -> [str]
    """

    """ *** YOUR CODE HERE *** """
    
    ##prepare the open list(queue) and close list to perform graph search
    queue = frontiers.Queue()
    close = []
    
    ##prepare the path to save node from start to goal
    path = []
    
    ##make start node as a object and assign its values
    start = SearchNode(problem.get_initial_state(), '', 0, '')

    ##at the same time, push the start node into the queue
    queue.push(start)
    
    ##test the queue.peek() is goal node or not
    while not problem.goal_test(queue.peek().state):
        
        ##when the node has never explored before, continue, or go to *1 part
        if queue.peek().state not in close:
            
            ##add the explored node to close list
            close.append(queue.peek().state)

            ##save next nodes information into a list
            nextstate = []
            
            for state in problem.get_successors(queue.peek().state):
                nextstate.append(state)

            ##save the explored node to father node and delete it from queue
            parent = queue.pop()
            
            ##if the next nodes has been explored before
            for item in nextstate:
                if item[0] not in close:
                    next_node = SearchNode(item[0], item[1], parent.path_cost + item[2], parent)
                    
                    ##save the next node into queue
                    queue.push(next_node)
                            
        ##' *1 ': if the node has explored before, delete it
        else:
            queue.pop()

        ##if the stack is empty, return failure to find solution    
        if queue.is_empty():
            return failure
            
    ##get the goal node is queue.peek(), by find its parent node to collect
    ##the actions in the path from goal node to start node
    temp = queue.peek()

    while temp.state is not start.state:
        path.append(temp.action)
        temp = temp.parent
        
    ##change the order of the actions to get a path from start to goal
    path = path[::-1]
    
    return path

def a_star_search(problem, heuristic=heuristics.null_heuristic):
    """ Q3: A* Search (3 marks)
        
        Search the node that has the lowest combined cost and heuristic first.

        Your A* search will use the heuristic contained in the heuristic argument.
        The heuristics take a state and return an estimate of the cost to reach
        the goal from that state. The heuristics are defined in heuristics.py
        and for this problem include a null_heuristic, a Manhattan heuristic,
        and a Euclidean heuristic.
        
        It can be assumed that the supplied heuristic function will work with
        the state representation of whatever SearchProblem you have been given
        here.
        
        A* is best implemented with a priority queue found in frontiers.py.
        You can use it by having:
        
            queue = frontiers.PriorityQueue()   OR
            queue = frontiers.PriorityQueueWithFunction(evaluation_function)
            
        In the latter case, you will need to define and pass an evaluation function
        which takes a search node and returns an appropriate f value for the node
        using the given heuristic.
        
        You might want to do this to aid in making a generic search function to
        be used for Q1-3. Making such a generic function is not required.
    """

    """ *** YOUR CODE HERE *** """

    ##prepare the open list(queue) and close list to perform graph search
    queue = frontiers.PriorityQueue()
    close = []
    
    ##prepare the path to save node from start to goal
    path = []

    ##make start node as a object and assign its values
    start = SearchNode(problem.get_initial_state(), '', 0, '')
    problem.heuristic_info[problem.get_initial_state()] = start

    ##at the same time, push the start node into the queue
    queue.push(start, heuristic(start.state ,problem) + start.path_cost)
    
    
    ##test the queue.peek() is goal or not

    while not problem.goal_test(queue.peek().state):

        ##pick node with lowest value in queue to test the node has been visit before
        ##or go to the else: part
        if queue.peek().state not in close:
            
            ##add the explored node to close list
            close.append(queue.peek().state)

            ##save the next node based on the current state to nextnode list
            nextnode = []
            
            for node in problem.get_successors(queue.peek().state):    
                nextnode.append(node)

            ##get parent_state and delete explored node
            parent = queue.pop()

            ##item[0] is state, item[1] is action, item[2] is action_cost
            for item in nextnode:
                
                ##if the node has never been explored before
                if item[0] not in close or queue.find(lambda x : x.state == item[0]) is None:
                    next_node = SearchNode(item[0], item[1], parent.path_cost + item[2], parent)
                    
                    ##push the next_node into queue with value
                    queue.push(next_node, heuristic(next_node.state ,problem) + next_node.path_cost)
                    
                    ##save the item and node information to problem.heuristic_info dictionary
                    problem.heuristic_info[item[0]] = next_node
                
                ##when we found a node has already explored with lower cost, explored it again
                elif item[0] in close and parent.path_cost + item[2] < problem.heuristic_info[item[0]].path_cost:
                    next_node = SearchNode(item[0], item[1], parent.path_cost + item[2], parent)
                    
                    ##push the node back to queue list again
                    queue.push(next_node, heuristic(next_node.state ,problem) + next_node.path_cost)
                    
                    ##delete it from close list
                    close.remove(item[0])
                                
        ##if the node has explored before, delete it
        else:
            queue.pop()

        ##stop the search when queue is empty    
        if queue.is_empty():

            return 'Cannot find a path'
            
    ##get the goal node is queue.peek(), by find its parent node to collect
    ##the actions in the path from goal node to start node
    temp = queue.peek()

    while temp.state is not start.state:
        path.append(temp.action)
        temp = temp.parent
        
    ##change the order of the actions to get a path from start to goal
    path = path[::-1]
    
    return path
    

# Abbreviations to be used elsewhere in the program
bfs = breadth_first_search
dfs = depth_first_search
astar = a_star_search

