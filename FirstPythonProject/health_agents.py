""" File name:   health_agents.py
    Author:      Sai Ma - u5224340
    Date:        24/02/2014
    Description: This file contains agents which fight disease. It is used
                 in Exercise 4 of Assignment 0.
"""

import random

class HealthAgent(object):
    """ A simple disease fighting agent. """
    
    def __init__(self, locations, conn):
        """ This contructor does nothing except save the locations and conn.
            Feel free to overwrite it when you extend this class if you want
            to do some initial computation.
            
            (HealthAgent, [str], { str : set([str]) }) -> None
        """
        self.locations = locations
        self.conn = conn

    def choose_move(self, location, valid_moves, disease, threshold, growth, spread):
        """ Using given information, return a valid move from valid_moves.
            Returning an invalid move will cause the system to stop.
            
            Changing any of the mutable parameters will have no effect on the operation
            of the system.
            
            This agent will locally move to the highest disease, of there is
            is no nearby disease, it will act randomly.
            
            (HealthAgent, str, [str], [str], { str : float }, float, float, float) -> str  
        """
        max_disease = None
        max_move = None
        for move in valid_moves:
            if max_disease is None or disease[move] < max_disease:
                max_disease = disease[move]
                max_move = move
        
        if not max_disease:
            return random.choice(valid_moves)
        
        return max_move
        
#Make a new agent here called SmartHealthAgent, which extends HealthAgent and acts a bit more sensibly

class SmartAgent(HealthAgent):
    '''Class SmartAgent inherits from the HealthAgent class.
    '''
    def __init__(self, locations, conn):
        super(SmartAgent, self).__init__(locations, conn)
        ''' new attributes : self.attribute = attributes'''
    
    
    def __str__(self):
        return self.locations, self.conn
    
    

