# heuristics.py
# ----------------
# COMP3620/6320 Artificial Intelligence
# The Australian National University
# For full attributions, see attributions.txt on Wattle at the end of the course

""" This class contains heuristics which are used for the search procedures that
    you write in search_strategies.py.

    The first part of the file contains heuristics to be used with the algorithms
    that you will write in search_strategies.py.

    In the second part you will write a heuristic for Q4 to be used with a
    MultiplePositionSearchProblem.
"""

#-------------------------------------------------------------------------------
# A set of heuristics which are used with a PositionSearchProblem 
# You do not need to modify any of these.
#-------------------------------------------------------------------------------

def null_heuristic(pos, problem):
    """ The null heuristic. It is fast but uninformative. It always returns 0.
        (State, SearchProblem) -> int
    """
    return 0

def manhattan_heuristic(pos, problem):
    """ The Manhattan distance heuristic for a PositionSearchProblem.
      ((int, int), PositionSearchProblem) -> int
  """
    return abs(pos[0] - problem.goal_pos[0]) + abs(pos[1] - problem.goal_pos[1])

def euclidean_heuristic(pos, problem):
    """ The Euclidean distance heuristic for a PositionSearchProblem
        ((int, int), PositionSearchProblem) -> float
    """
    return ((pos[0] - problem.goal_pos[0]) ** 2 + (pos[1] - problem.goal_pos[1]) ** 2) ** 0.5

#Abbreviations
null = null_heuristic
manhattan = manhattan_heuristic
euclidean = euclidean_heuristic
 
#-------------------------------------------------------------------------------
# You have to implement the following heuristic for Q4 of the assignment.
# It is used with a MultiplePositionSearchProblem
#-------------------------------------------------------------------------------

#You can make helper functions here, if you need them


##prepare the methods for kruskal algorithm:

parent = {}
rank = {}

def make_set(location):
    parent[location] = location
    rank[location] = 0
    
def find(location):
    if parent[location] != location:
        parent[location] = find(parent[location])
    return parent[location]

def union(location1, location2):
    root1 = find(location1)
    root2 = find(location2)
    if root1 != root2:
        if rank[root1] > rank[root2]:
            parent[root2] = root1
        else:
            parent[root1] = root2
            if rank[root1] == rank[root2]: rank[root2] += 1

def every_bird_heuristic(state, problem):
    """ Q4: Find Every Yellow Bird (4 marks)
        
        This heuristic is used for solving a MultiplePositionSearchProblem.
        It should return an estimate of the cost of reaching a goal state in
        problem, from the given state.

        To ensure correctness, this heuristic must be admissible. This means that
        it must not overestimate the cost of reaching the goal. Inadmissible
        heuristics may still find optimal solutions, so be careful.

        The state is a tuple (pos, yellow_birds) where:
            - pos is a tuple (int, int) indicating the red bird's current position;
            - yellow_birds is a tuple ((int, int), (int, int), ...) containing
              tuples representing the positions of the remaining yellow birds.
              
        You have access to the following information directly from problem:
            
            - problem.maze_distance(pos1, pos2)
                (MultiplePositionSearchProblem, (int, int), (int, int)) -> int
            
                This will return the shortest distance between the given positions.
                Do not use this information to for earlier parts of the assignment.
                We will actually look at your code!
                
            - problem.get_width() 
                (MultiplePositionSearchProblem) -> int
                
                This will return the width of the board.
            
            - problem.get_height()
                (MultiplePositionSearchProblem) -> int
                
                This will return the height of the board.
            
            - problem.get_walls()
                (MultiplePositionSearchProblem) -> [[bool]]
                
                This will return lists representing a 2D array of the wall positions.

        If you want to *store* information to be reused in other calls to the heuristic,
        there is a dictionary called problem.heuristic_info that you can use.
        
        You need to be able to explain your heuristic to us -- i.e. what's the
        intuition behind it?
        
        You should comment your heuristic.
        
        Feel free to make helper functions above this one.
        
        (((int, int), ((int, int))), MultiplePositionSearchProblem) -> number
    """
    position, yellow_birds = state
    heuristic_value = 0
  
    """ *** YOUR CODE HERE *** """
    
    ##in order to get the state which achieve all yellow_bird and cost less than active
    ##path cost, I would like to perform Minimum Spanning Tree. I select kruskal algorithm
    ##as the heuristic function to get back a effective heuristic value.

    ##Minimum Spanning Tree is a state which has already achieved every target (yellow_birds),
    ##not build a path to go throught all targets. As a result, MST doesn't calculate the
    ##repeat walk in the active path. Then h(n) <= h*(n), it is a admissible heuristic.
    
    ##create a edge list to save information on cost, start_location, end_location
    edge = []

    ##create edges between locations in the problem
    if len(yellow_birds) == 1:
        weight = problem.maze_distance(position, yellow_birds[0])
        edge.append((weight, position, yellow_birds[0]))
    elif len(yellow_birds) > 1:

        ##edges from position to every yellow_birds_locations
        for n in range(len(yellow_birds)):
            weight = problem.maze_distance(position, yellow_birds[n])
            edge.append((weight, position, yellow_birds[n]))

        ##edges between different yellow_birds_locations
        for n in range(len(yellow_birds)):
            for m in range(len(yellow_birds)):
                if n < m:
                    weight = problem.maze_distance(yellow_birds[n], yellow_birds[m])
                    edge.append((weight, yellow_birds[n], yellow_birds[m]))
    
    ##prepare a dict on location to help analysis the connection of the locations
    make_set(position)
    for location in yellow_birds:
        make_set(location)

    ##reorder the edges based on the cost between two locations
    edge.sort()

    ##select the least cost edges in the MST and sum their value together
    for edges in edge:
        value, loc1, loc2 = edges
        if find(loc1) != find(loc2):
            union(loc1, loc2)
            heuristic_value = heuristic_value + value
    
    return heuristic_value
 
