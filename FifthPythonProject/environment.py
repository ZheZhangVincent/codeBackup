# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014
# COMP3620 and COMP6320 - Assignment 4

""" This file implements the following environments for use in Assignment 4.

    You should familarise yourself with the environments contained within,
    but you do not need to change anything in this file.

    WindyGrid - This is a grid world with deterministic upward-blowing
        wind with varying strength in different columns of squares.
    
    WindyGridWater - This is a grid world with wind that randomly blows
        in a random direction each move. A set of squares are specified 
        as water and there is a specified penalty for an agent falling
        in the water.
"""

import random, abc

class Environment(object):
    """ The class Environment will be inherited by all of the types of
        envroment in which we will run experiments.
    """
    
    @abc.abstractmethod
    def __init__(self, width, height, init_pos, goal_pos):
        """ All Environments share these common features.
            
            (Environment, int, int, (int, int), (int, int)) -> None
        """
        self.width = width
        self.height = height
        self.init_pos = init_pos
        self.goal_pos = goal_pos
        
        self.num_states = width * height
        self.init_state = self.pos_to_state(init_pos)
        self.current_pos = init_pos
        
        self.num_actions = 4
        self.actions = ["up", "down", "left", "right"]
        self.action_dirs = {"up"    : (0, -1),
                            "down"  : (0, 1),
                            "left"  : (-1, 0),
                            "right" : (1, 0) }

    @abc.abstractmethod
    def generate(self, action):    
        """ Apply the given action to the current state and return
            an (observation, reward) pair.
            (Environment, str) -> ((int, int), float)
        """

    def end_of_episode(self):
        """ Return iff it is the end of the episode. If so, reset the
            environment to the initial state.
            (Environment) -> bool
        """
        if self.current_pos == self.goal_pos:   
            self.current_pos = self.init_pos
            self.init_state = self.pos_to_state(self.init_pos)  
            return True
        return False

    def pos_to_state(self, pos):
        """ Return the index (representing the state) of the current position.
            (Environment, (int, int)) -> int
        """
        return pos[1]*self.width + pos[0]


class WindyGrid(Environment):
    """ WindyGrid has deterministic upward-blowing wind with the given
        strength in the specified columns.
    """

    def __init__(self, width, height, init_pos, goal_pos, wind):
        """ Make a new WindyGrid environment.
            
            The wind should be a list with width elements indicating
            the upwards wind in each column.
            
            (WindyGrid, int, int, (int, int), (int, int), [int, ...]) -> int
        """
        super(WindyGrid, self).__init__(width, height, init_pos, goal_pos)
        self.wind = wind

    def generate(self, action):
        """ Apply the given action to the current state and return
            an (observation, reward) pair.
            (WindyGrid, str) -> ((int, int), float)
        """
        #Clever min-max bounds checking
        a_dir = self.action_dirs[action]
        pos = self.current_pos
        pos = (min(max(pos[0] + a_dir[0], 0), self.width-1),
               min(max(pos[1] + a_dir[1] - self.wind[pos[0]], 0), self.height-1))
        self.current_pos = pos
        if pos == self.goal_pos:
            r = 1.0
        else:
            r = -1.0
        return (self.pos_to_state(pos), r)

    def print_map(self):
        """ Print an ASCII map of the simulation.
            (WindyGrid) -> None
        """
        for y in xrange(self.height):
            for x in xrange(self.width):
                pos = (x, y)
                if pos == self.current_pos:
                    print "A",
                elif pos == self.init_pos:
                    print "S",
                elif pos == self.goal_pos:
                    print "G",
                else:
                    print "*",
            print    

class WindyGridWater(Environment):
    """ An environment where every time the agent moves, with probability
        delta, they get blown in a random direction. There is water and
        a specified penalty for falling into it.
    """

    def __init__(self, width, height, init_pos, goal_pos, water, delta, water_reward):
        """ Make a new WindyGridWater environment.
            
           Water is a list of positions that are filled with water.
           Delta is the probability of the wind blowing the agent
           in a random direction each move. 
           
           The agent gets water_reward reward when it falls into the water.
            
           (WindyGrid, int, int, (int, int), (int, int), [(int, int), ...],
                float, float) -> int
        """
        super(WindyGridWater, self).__init__(width, height, init_pos, goal_pos)
        self.water = water
        self.delta = delta
        self.water_reward = water_reward

    def generate(self, action):
        """ Apply the given action to the current state and return
            an (observation, reward) pair.
            (WindyGridWater, str) -> ((int, int), float)
        """
        if random.random() < self.delta:
            wind_dir = self.action_dirs[random.choice(self.actions)]
        else:
            wind_dir = (0, 0)
        a_dir = self.action_dirs[action]
        pos = self.current_pos
        pos = (min(max(pos[0] + a_dir[0] +  wind_dir[0], 0), self.width-1),
               min(max(pos[1] + a_dir[1] + wind_dir[1], 0), self.height-1))
        self.current_pos = pos

        if self.current_pos in self.water:
            r = self.water_reward
        elif self.current_pos == self.goal_pos:
            r = 1
        else:
            r = -1

        return (self.pos_to_state(self.current_pos), r)

    def print_map(self):
        """ Print an ASCII map of the simulation.
            (WindyGrid) -> None
        """
        for y in xrange(self.height):
            for x in xrange(self.width):
                pos = (x, y)
                if pos == self.current_pos:
                    print "A",
                elif pos == self.init_pos:
                    print "S",
                elif pos == self.goal_pos:
                    print "G",
                elif pos in self.water:
                    print "W",
                else:
                    print "*",
            print    

