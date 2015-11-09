# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014
# COMP3620 and COMP6320 - Assignment 4

""" Student Details
    Student Name: Sai Ma
    Student number: u5224340
    Date: 24/05/2014
"""

""" This class contains placeholders classes in which you will implement
    your agents that interact with the various grid worlds.
    
    These agents will extend RLAgent and must implement the select_action and
    update_Q methods.
    
    You should comment your implementations and submit this file.
"""

import random, abc

class RLAgent(object):
    """ Generic class for RLAgent """
    
    def __init__(self, alpha, gamma, epsilon, num_states, actions):
        """ Make a new RLAgent with the specified parameters.
            (RLAgent, float, float, float, int, int) -> None
        """
        self.alpha = alpha
        self.gamma = gamma
        self.epsilon = epsilon    
        self.num_states = num_states
        self.actions = actions
        #Q is a dictionary mapping (state, action) pairs to Q values
        self.Q = dict([((s, a), 0.0) for s in xrange(num_states) for a in actions])
    
    @abc.abstractmethod
    def select_action(self, state):
        """ This returns an action based on the value of the Q-table,
            and the exploration constant (epsilon). Break ties uniformly at random.
            
            (RLAgent, int) -> str
        """
        
    @abc.abstractmethod
    def update_Q(self):
        """  Update the Q-value table. Decide what arguments to pass into it.
            (RLAgent, ??) -> None
        """

###############################################################################
#                         Write your code below                               #
###############################################################################

###############   Implement the following two classes #########################

class Sarsa(RLAgent):
    """ The Sarsa agent implements the Sarsa algorithm.
        You must override select_action and update_Q.
    """
    
    def __init__(self, alpha, gamma, epsilon, num_states, actions):
        RLAgent.__init__(self, alpha, gamma, epsilon, num_states, actions)
        self.Q =  dict([((s, a), 0.0) for s in xrange(num_states) for a in actions])
        
        ##create a convergence attribute to save algorithm convergence situation
        self.convergence = False
        
        ##create a episode_lengths list to save episode_lengths information in the running
        self.past_episode = 0
    
    ##override select_action method
    def select_action(self, state):
        """Perform epsilon-greedy approach
            (Sarsa, int) -> str
        """
        
        ##create lists and string to save relative action information
        actions = []
        action = ''
        all_actions = []
        
        ##get the action with the maximum value
        temp = {}
        for (s, a), value in self.Q.iteritems():
            if s == state:
                temp[(s, a)] = value
                all_actions.append(a)
                
        max_value = max(temp.values())
        for (s, a) , value in temp.iteritems():
            if value == max_value:
                actions.append(a)

        ##if we have more than one action with max_values, random return one
        if len(actions) > 1:
            index = random.randint(0,len(actions) - 1)
            action = str(actions[index])
        else:
            for item in actions:
                action = item
        
        ##when the random number less than epsilon, then return one action randomly 
        if random.random() < self.epsilon:
            index = random.randint(0, len(all_actions) - 1)
            action = str(all_actions[index])
        
        ##if the random number not less than epsilon, then return the action with max value
        return action
        
    ##override update_Q method
    def update_Q(self, state, action, new_state, new_action, reward):
        """According to the Sarsa algorithm, the Q(s,a) could update based on
           next_state, next_action, reward, alpha and gamma
            (Sarsa, int, str, int, str, float) -> None
        """
        
        self.Q[(state, action)] = float(self.Q[(state, action)] + self.alpha*(reward + self.gamma * self.Q[new_state, new_action] - self.Q[(state, action)]))
    
    ##create a method to test the algorithm is convergence or not
    def test_convergence(self, time_step):
        """This method aims to find the situation when the algorithm is
           convergence, and set self.convergence to True
            (Sarsa, int) -> None
        """
        
        ##compare the average episode length between two loop
        if self.past_episode == time_step:
            self.convergence = True
        else:
            self.convergence = False
    
    ##create method to get the average reward from Q
    def get_reward(self):
        """This method aims to get the average reward of Q when algorithm
           is convergence, and return the average reward number
            (QLearn) -> float
        """
        
        ##get total reward value from Q
        total_reward = 0.0
        for value in self.Q.itervalues():
            total_reward = total_reward + value
        
        ##return the average reward
        return total_reward
    
    ##create a method to change the initial Q value to the given number
    def initial_Q(self, negative):
        """This method would be used in Q5 to make the Q value initial to 
            the given number
            (QLearn, int) -> None
        """
        
        ##get each values in the Q, and change their content to given number, plan to use in Q5
        for key in self.Q.iterkeys():
            self.Q[key] = float(negative)
        
        
class QLearn(RLAgent):
    """ The QLEarn agent implements the Q-learning algorithm.
        You must override select_action and update_Q.
    """
    
    def __init__(self, alpha, gamma, epsilon, num_states, actions):
        RLAgent.__init__(self, alpha, gamma, epsilon, num_states, actions)
        self.Q =  dict([((s, a), 0.0) for s in xrange(num_states) for a in actions])
        
        ##create a convergence attribute to save algorithm convergence situation
        self.convergence = False
        ##create a episode_lengths list to save episode_lengths information in the running
        self.past_episode = 0
    
    ##override select_action method
    def select_action(self, state):
        """Perform epsilon-greedy approach
            (QLearn, int) -> str
        """
        
        ##create lists and string to save relative action information
        actions = []
        action = ''
        all_actions = []
        
        ##get the action with the maximum value
        temp = {}
        for (s, a), value in self.Q.iteritems():
            if s == state:
                temp[(s, a)] = value
                all_actions.append(a) 
        max_value = max(temp.values())
        for (s, a) , value in temp.iteritems():
            if value == max_value:
                actions.append(a)

        ##if we have more than one action with max_values, random return one
        if len(actions) > 1:
            index = random.randint(0,len(actions) - 1)
            action = str(actions[index])
        else:
            for item in actions:
                action = item
        
        ##when the random number less than epsilon, then return one action randomly 
        if random.random() < self.epsilon:
            index = random.randint(0, len(all_actions) - 1)
            action = str(all_actions[index])
        
        ##if the random number not less than epsilon, then return the action with max value
        return action
    
    ##override update_Q method
    def update_Q(self, state, action, new_state, reward):
        """According to the Sarsa algorithm, the Q(s,a) could update based on
           next_state, next_action, reward, alpha and gamma
            (QLearn, int, str, int, float) -> None
        """
        
        self.Q[(state, action)] = self.Q[(state, action)] + self.alpha*(reward + self.gamma * self.max_value(new_state) - self.Q[(state, action)])

    ##write addition method to get the max value based on action and new_state
    def max_value (self, new_state):
        """return  the maximum reward that is attainable in the state following the current one. 
           i.e the reward for taking the optimal action thereafter
            (QLearn, (int,int)) -> int
        """
        
        ##create a list to save reward information
        return_list = []
        
        ##get each values from Q based on the new_state and its possible actions
        for s, a in self.Q.keys():
            if s == new_state:
                return_list.append(self.Q[s,a])
        
        ##return the maximum value based on new_state
        return max(return_list)
        
    ##create a method to test the algorithm is convergence or not
    def test_convergence(self, time_step):
        """This method aims to find the situation when the algorithm is
           convergence, and set self.convergence to True
            (QLearn, int) -> None
        """
        
        ##compare the average episode length between two loop
        if self.past_episode == time_step:
            self.convergence = True
        else:
            self.convergence = False
            
    ##create method to get the average reward from Q
    def get_reward(self):
        """This method aims to get the average reward of Q when algorithm
           is convergence, and return the average reward number
            (QLearn) -> float
        """
        
        ##get total reward value from Q
        total_reward = 0.0
        for value in self.Q.itervalues():
            total_reward = total_reward + value
        
        ##return the average reward
        return total_reward
        
        
        
        
        

