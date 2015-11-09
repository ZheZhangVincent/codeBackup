# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014
# COMP3620 and COMP6320 - Assignment 4

"""
   This file defines a Visualisation which can be imported and used
   in experiments.py to help visualise the progress of your RL algorithms.

   It should be used in the following way:
   
   > import visualisation, time
   > vis = visualisation.Visualisation(env, 800, 600, min_reward, max_reward)
   
   Here, sensible values for min_reward and max_reward should be determined 
   by looking at the Q as your algorithm runs
   (-15 and 1 should be about right for Q2)
   
   Next, the current Q values, the current greedy policy and a trace of an episode
   can be shown by calling:
   
   vis.show_Q(agent, show_greedy_policy, trace)
   
   Here, show_greedy_policy is a bool - the greedy policy will be shown in black.
   
   The trace is a list [(int, str)] of state, action pairs which will be shown
   coloured from white to green starting at the beginning of the list.
   
   Pass an empty list if you don't want this to be displayed.
   
   The Q values themselves will be shown as the colour of the triangle with the
   base on the relevant edge of each grid square. The colour blue indicates low
   reward, while red indicates high reward.
   
   Finally, vis.pause() will block until the visualisation is closed.
"""

import Tkinter, sys
from environment import WindyGrid

Q_TRIANGLE_PERCENT = 0.9
Q_TRIANGLE_BORDER = 2
LINE_COLOR = "black"

MIN_Q_COLOR = (255, 0, 0)
MAX_Q_COLOR = (0, 0, 255)

G_INNER_PERCENT = 0.7
G_OUTER_PERCENT = 0.95
G_COLOR = "black"

T_INNER_PERCENT = 0.9
T_OUTER_PERCENT = 0.98
T_TRIANGLE_COLOR = "green"
MIN_T_COLOR = (255, 255, 255)
MAX_T_COLOR = (0, 255, 0)

def lerp_color(min_val, max_val, val, min_col, max_col):
    """ Interpolate between two colours specified as (r, g, b) values.
        
        (float, float, float, (int, int, int), (int, int, int)) -> (int, int, int)
    """
    s1 = float(val-min_val) / (max_val-min_val)
    s2 = 1.0 - s1
    return [int(s1*i+s2*j) for i,j in zip(min_col, max_col)]

class Visualisation(object):
  
    def __init__(self, env, width, height, min_reward, max_reward):
        """ Initialise the visualisation with the given parameters.
        
            env is an Environment.
            width and height are the dimensions of the visualisation in pixels
            min_reward is the minimum reward for coloring (this will be blue)
            max_reward is the maximum reward for coloring (this will be red)
            
            (Environment, int, int, float, float) -> None
        """    
        self.env = env
        self.width  = width
        self.height = height
        self.min_reward = min_reward
        self.max_reward = max_reward
        
        self.root_window = Tkinter.Tk()
        self.root_window.title("Gridworld Visualisation")
        self.root_window.geometry(str(width)+"x"+str(height)+"+200+200")
        self.root_window.resizable(0, 0)
    
        self.canvas = Tkinter.Canvas(self.root_window, width=width, height=height)
        self.canvas.pack(fill=Tkinter.BOTH, expand=1)
        
        self.grid_x_inc = float(self.width) / self.env.width
        self.half_grid_x_inc = self.grid_x_inc / 2.0
        self.grid_y_inc = float(self.height) / self.env.height
        self.half_grid_y_inc = self.grid_y_inc / 2.0
        
        self.h_lines = []
        for l in xrange(self.env.height):
            pos = l * self.grid_y_inc
            self.h_lines.append(self.canvas.create_line(0, pos, self.width, pos,
                fill=LINE_COLOR))
        
        self.v_lines = []
        for l in xrange(self.env.width):
            pos = l * self.grid_x_inc
            self.v_lines.append(self.canvas.create_line(pos, 0, pos, self.height,
                fill=LINE_COLOR))
        
        self.Q_values = {}
        for state in xrange(self.env.num_states):
            x, y = self.state_to_pos(state)
            x_pos = x * self.grid_x_inc
            y_pos = y * self.grid_y_inc
            #up
            self.Q_values[(state, "up")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER,
                y_pos+Q_TRIANGLE_BORDER, 
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1,
                y_pos+Q_TRIANGLE_BORDER,
                x_pos+self.half_grid_x_inc,
                y_pos+Q_TRIANGLE_PERCENT*self.half_grid_y_inc,
                fill="blue")
            #down
            self.Q_values[(state, "down")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1, 
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1,
                x_pos+self.half_grid_x_inc,
                y_pos+self.grid_y_inc-Q_TRIANGLE_PERCENT*self.half_grid_y_inc,
                fill="blue")
            #left
            self.Q_values[(state, "left")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER,
                y_pos+Q_TRIANGLE_BORDER,
                x_pos+Q_TRIANGLE_BORDER,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+self.half_grid_y_inc,
                fill="blue")
            #right
            self.Q_values[(state, "right")] = self.canvas.create_polygon(\
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1,
                y_pos+Q_TRIANGLE_BORDER,
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1,
                x_pos+self.grid_x_inc-Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+self.half_grid_y_inc,
                fill="blue")
           
        self.greedy_policy = {}
        for state in xrange(self.env.num_states):
            x, y = self.state_to_pos(state)
            x_pos = x * self.grid_x_inc
            y_pos = y * self.grid_y_inc
            #up
            self.greedy_policy[(state, "up")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER+self.half_grid_x_inc*G_OUTER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER, 
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1-self.half_grid_x_inc*G_OUTER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER,
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1-self.half_grid_x_inc*G_INNER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc,
                x_pos+Q_TRIANGLE_BORDER+self.half_grid_x_inc*G_INNER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc,
                fill=G_COLOR, state=Tkinter.HIDDEN)
            #down
            self.greedy_policy[(state, "down")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER+self.half_grid_x_inc*G_OUTER_PERCENT,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1, 
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1-self.half_grid_x_inc*G_OUTER_PERCENT,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1,
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1-self.half_grid_x_inc*G_INNER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc,
                x_pos+Q_TRIANGLE_BORDER+self.half_grid_x_inc*G_INNER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc,
                fill=G_COLOR, state=Tkinter.HIDDEN)
            #left
            self.greedy_policy[(state, "left")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc*G_OUTER_PERCENT,
                x_pos+Q_TRIANGLE_BORDER,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1-self.half_grid_y_inc*G_OUTER_PERCENT,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1-self.half_grid_y_inc*G_INNER_PERCENT,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc*G_INNER_PERCENT,
                fill=G_COLOR, state=Tkinter.HIDDEN)
            #right
            self.greedy_policy[(state, "right")] = self.canvas.create_polygon(\
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc*G_OUTER_PERCENT,
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1-self.half_grid_y_inc*G_OUTER_PERCENT,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1-self.half_grid_y_inc*G_INNER_PERCENT,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc*G_INNER_PERCENT,
                fill=G_COLOR, state=Tkinter.HIDDEN)
             
        self.actions_taken = {}
        for state in xrange(self.env.num_states):
            x, y = self.state_to_pos(state)
            x_pos = x * self.grid_x_inc
            y_pos = y * self.grid_y_inc
            #up
            self.actions_taken[(state, "up")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER+self.half_grid_x_inc*T_OUTER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER, 
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1-self.half_grid_x_inc*T_OUTER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER,
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1-self.half_grid_x_inc*T_INNER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc,
                x_pos+Q_TRIANGLE_BORDER+self.half_grid_x_inc*T_INNER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc,
                fill=T_TRIANGLE_COLOR, state=Tkinter.HIDDEN)
            #down
            self.actions_taken[(state, "down")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER+self.half_grid_x_inc*T_OUTER_PERCENT,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1, 
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1-self.half_grid_x_inc*T_OUTER_PERCENT,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1,
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1-self.half_grid_x_inc*T_INNER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc,
                x_pos+Q_TRIANGLE_BORDER+self.half_grid_x_inc*T_INNER_PERCENT,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc,
                fill=T_TRIANGLE_COLOR, state=Tkinter.HIDDEN)
            #left
            self.actions_taken[(state, "left")] = self.canvas.create_polygon(\
                x_pos+Q_TRIANGLE_BORDER,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc*T_OUTER_PERCENT,
                x_pos+Q_TRIANGLE_BORDER,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1-self.half_grid_y_inc*T_OUTER_PERCENT,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1-self.half_grid_y_inc*T_INNER_PERCENT,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc*T_INNER_PERCENT,
                fill=T_TRIANGLE_COLOR, state=Tkinter.HIDDEN)
            #right
            self.actions_taken[(state, "right")] = self.canvas.create_polygon(\
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc*T_OUTER_PERCENT,
                x_pos+self.grid_x_inc-Q_TRIANGLE_BORDER+1,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1-self.half_grid_y_inc*T_OUTER_PERCENT,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+self.grid_y_inc-Q_TRIANGLE_BORDER+1-self.half_grid_y_inc*T_INNER_PERCENT,
                x_pos+Q_TRIANGLE_PERCENT*self.half_grid_x_inc,
                y_pos+Q_TRIANGLE_BORDER+self.half_grid_y_inc*T_INNER_PERCENT,
                fill=T_TRIANGLE_COLOR, state=Tkinter.HIDDEN)
            

    def state_to_pos(self, state):
        """ Convert a state number to a grid position.
            (Visualisation, int) -> (int, int)
        """
        return (state % self.env.width, state / self.env.width)
    

    def show_Q(self, agent, show_greedy_policy, trace):
        """ Update the display to show the Q values of the given agent.
            
            This will also show the current greedy policy computed from these
            Q values.
            
            trace is a list of state action pairs (int, str) which will also
            be displayed. This can be built from each episode
            
            (RLAgent, bool, [(state, action)]) -> None
        """
        try:
            state_max = {}
            state_max_action = {}
            for sa, q in agent.Q.iteritems():
                color = lerp_color(self.min_reward, self.max_reward,
                    max(self.min_reward, min(self.max_reward, q)),
                    MIN_Q_COLOR, MAX_Q_COLOR)
                color ="#%02x%02x%02x"%tuple(color)
                self.canvas.itemconfigure(self.Q_values[sa], fill=color)
                state, action = sa
                if state not in state_max or state_max[state] < q:
                    state_max[state] = q
                    state_max_action[state] = action
                
                self.canvas.itemconfigure(self.actions_taken[sa], state=Tkinter.HIDDEN)
                self.canvas.itemconfigure(self.greedy_policy[sa], state=Tkinter.HIDDEN)
            
            if show_greedy_policy:
                for state, max_action in state_max_action.iteritems():
                    self.canvas.itemconfigure(self.greedy_policy[(state, max_action)],
                        state=Tkinter.NORMAL)

            for tid, (state, action) in enumerate(trace):
                if action is None: continue
                lerp_pos = float(tid) / len(trace)
                color = lerp_color(0.0, 1.0, lerp_pos, MIN_T_COLOR, MAX_T_COLOR)
                color ="#%02x%02x%02x"%tuple(color)
                self.canvas.itemconfigure(self.actions_taken[(state, action)],
                    state=Tkinter.NORMAL, fill=color)
            
            self.root_window.update()
    
        except Tkinter.TclError:
            pass
        
    def pause(self):
        """ Wait here for the window to close!
            (Visualisation) -> None
        """
        self.root_window.mainloop()
 
        
 
