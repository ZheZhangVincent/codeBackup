# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014
# COMP3620 and COMP6320 - Assignment 4

""" Student Details
    Student Name: Sai Ma
    Student number: u5224340
    Date: 25/05/2014
"""

""" In this file you should write code to generate the graphs that
    you include in your report in answers.pdf.
    
    We have provided the skeletons of functions which you can use to
    run Sarsa and Q-learning if you choose.
    
    We suggest using Matplotlib to make plots. Example code to make a
    plot is included below. Matplotlib is installed on the lab computers.
    On Ubuntu and other Debian based distributions of linux, you can install it
    with "sudo apt-get install python-matplotlib".
    
    Please put the code to generate different plotsinto different functions and
    at the bottom of this file put clearly labelled calls to these
    functions, to make it easier to test your generation code.
"""

from environment import WindyGrid, WindyGridWater
from agents import Sarsa, QLearn

def get_windy_grid():
    """ This function simplifies making a WindyGrid environment.
        You should call this function to get the environment with the desired
        wind probability (delta) and water penalty.
        
        For Q3 you should call this function to get the environment for Q2.
        
        (float, float) -> GridWater
    """
    return WindyGrid(10, 7, (0, 3), (7, 3), [0, 0, 0, 1, 1, 1, 2, 2, 1, 0])

def get_windy_grid_water(delta, water_reward):
    """ This function simplifies making a WindyGridWater environment.
        You should call this function to get the environment with the desired
        wind probability (delta) and water penalty.
        
        For Q3 you should call this function with water_reward=-10 and varying delta.
        
        For Q4 and Q5 you should call this function with delta=0 and water_reward=-100.
        
        (float, float) -> WindyGridWater
    """
    return WindyGridWater(10, 7, (0, 1), (7, 1),
        [[0, 3], [0, 4], [2, 3], [2, 4], [3, 3], [3, 4]], delta, water_reward)


###############################################################################
#                         Write your code below                               #
###############################################################################



##############################################
## Q4 Answer: algorithm part: with epsilon  ##
##############################################

'''this part is Q-learn with epsilon to collected data
'''
##get a loop to form epsilon number
epsilon = 0.1

##create lists and int to save result
q_epis_list = []
q_epis_r_list = []
q_epis_l_list = []
total_reward = 0.0

while epsilon <= 0.7:
    env = get_windy_grid_water(0, -100)
    agent = QLearn(0.1, 0.99, epsilon, env.num_states, env.actions)

    ##first of all, when Q-Learning algorithm is not convergence
    while not agent.convergence:
        
        ##create a count number to update episode length in batch
        count = 0
        
        ##used to save episode lengths in 200 times
        patch_lengths = 0.0
        while count < 200:
            count = count + 1
            
            ##create time_step to save episode length
            time_step = 0
            
            ##initialize s
            state = env.pos_to_state(env.init_pos)
            
            ##repeat until achieve the goal pos
            while not env.end_of_episode():
                
                ##choose an action from Q based on state
                action = agent.select_action(state)
                
                ##take action, and get reward, new state, new position, add one time_step
                result = env.generate(action)
                reward = result[-1]
                new_state = result[0]

                ##update Q
                agent.update_Q(state, action, new_state, reward)
                
                ##assign new_stae to state
                state = int(new_state)
                
                ##add the time_step
                time_step = time_step + 1
                
            ##test the algorithm is convergence or not
            patch_lengths = patch_lengths + time_step
        
        ##get the average episode lengths in patch
        patch_lengths = float(patch_lengths / 200)

        ##test the algorithm is convergence or not
        agent.test_convergence(patch_lengths)

        ##if not convergence, make the current patch_length to past_episode
        if not agent.convergence:
            agent.past_episode = float(patch_lengths)
        
    ##when the algorithm convergence collected data
    if agent.convergence:
        
        ##create episode to act as count to 500
        episode = 0
        
        ##create sum_time to save total time size of 500 times episodes
        sum_time = 0.0
        
        while episode < 500:
            episode = episode + 1
            time_step = 0
            state = env.pos_to_state(env.init_pos)
            
            ##there is no need to update Q, because algorithm is convergence
            while not env.end_of_episode():
                action = agent.select_action(state)
                result = env.generate(action)
                new_state = result[0]
                total_reward = total_reward + result[1]
                
                state = int(new_state)
                time_step = time_step + 1
            sum_time = sum_time + time_step
            
        ##get the average of 500 episodes
        episode_length = sum_time / 500
        total_reward = total_reward / 500 
        
    ##add these delta and averge_episode_length information into former list
    q_epis_list.append(epsilon)

    ##save the average reward and episode_length 
    average_reward = total_reward / episode_length
    print average_reward
    q_epis_r_list.append(average_reward)
    q_epis_l_list.append(episode_length)
    print epsilon
    ##when finish collecting data with 500 times episodes, add 0.1 to epsilon
    epsilon = epsilon + 0.1

'''this part is perform Sarsa, with epsilon to collect data, majority code
is same when compare with Q3, then I decide to delete some comments to save
reading time.
'''
    
epsilon = 0.1
s_epis_list = []
s_epis_r_list = []
s_epis_l_list = []
total_reward = 0.0

while epsilon <= 0.7:
    env = get_windy_grid_water(0, -100)
    agent = Sarsa(0.1, 0.99, epsilon, env.num_states, env.actions)

    while not agent.convergence:

        count = 0
        patch_lengths = 0.0
        while count < 200:
            count = count + 1
            time_step = 0
            state = env.pos_to_state(env.init_pos)
            action = agent.select_action(state)
            while not env.end_of_episode():
                
                result = env.generate(action)
                reward = result[-1]
                new_state = result[0]
                new_action = agent.select_action(new_state)
                agent.update_Q(state, action, new_state, new_action, reward)
                action = str(new_action)
                state = int(new_state)
                time_step = time_step + 1
                
            patch_lengths = patch_lengths + time_step
            
        patch_lengths = float(patch_lengths / 200)
        agent.test_convergence(patch_lengths)
        
        if not agent.convergence:
            agent.past_episode = float(patch_lengths)
        
    ##when the algorithm convergence
    if agent.convergence:
        
        episode = 0
        sum_time = 0.0
        while episode < 500:
            episode = episode + 1
            time_step = 0
            state = env.pos_to_state(env.init_pos)
            
            while not env.end_of_episode():
                action = agent.select_action(state)
                result = env.generate(action)
                new_state = result[0]
                total_reward = total_reward + result[1]
                state = int(new_state)
                time_step = time_step + 1
            sum_time = sum_time + time_step
        episode_length = sum_time / 500
        total_reward = total_reward / 500 
        
    s_epis_list.append(epsilon)
    
    average_reward = total_reward / episode_length

    s_epis_r_list.append(average_reward)
    s_epis_l_list.append(episode_length)
    print epsilon
    epsilon = epsilon + 0.1


###############################################################
## Q4 Answer: graph display part: with epsilon (y is reward) ##
###############################################################

import matplotlib.pyplot as plt4
plt4.title("Q4 Graphic with epsilon")
plt4.ylabel("Average Reward")
plt4.xlabel("Epsilon")

##add the information into x-label and y-label, and show
plt4.plot(q_epis_list,q_epis_r_list, label="Q-Learning: Epsilon' function of Reward")
plt4.plot(s_epis_list,s_epis_r_list, label="Sarsa: Epsilon' function of Reward")

plt4.legend(loc='lower right')
plt4.show()




################################################
## Q4 Answer: algorithm part: without epsilon ##
################################################

'''this part is Q-learn without epsilon to collected data (y is reward)
'''
##get a loop to form epsilon number
epsilon = 0.1

##create lists and int to save result
q_epis_list_without = []
q_epis_r_list_without = []
q_epis_l_list_without = []
total_reward = 0.0

while epsilon <= 0.7:
    env = get_windy_grid_water(0, -100)
    agent = QLearn(0.1, 0.99, epsilon, env.num_states, env.actions)

    while not agent.convergence:
        count = 0
        patch_lengths = 0.0
        while count < 200:
            count = count + 1
            time_step = 0
            state = env.pos_to_state(env.init_pos)
            while not env.end_of_episode():
                
                action = agent.select_action(state)
                result = env.generate(action)
                reward = result[-1]
                new_state = result[0]
                agent.update_Q(state, action, new_state, reward)
                state = int(new_state)
                
                time_step = time_step + 1
            patch_lengths = patch_lengths + time_step
        patch_lengths = float(patch_lengths / 200)

        agent.test_convergence(patch_lengths)
        
        if not agent.convergence:
            agent.past_episode = float(patch_lengths)
            
    if agent.convergence:
        agent.epsilon = 0
        episode = 0
        sum_time = 0.0
        
        while episode < 500:
            episode = episode + 1
            time_step = 0
            state = env.pos_to_state(env.init_pos)
            while not env.end_of_episode():
                action = agent.select_action(state)
                result = env.generate(action)
                new_state = result[0]
                total_reward = total_reward + result[1]
                state = int(new_state)
                time_step = time_step + 1
            sum_time = sum_time + time_step
        episode_length = sum_time / 500
        total_reward = total_reward / 500 
        
    q_epis_list_without.append(epsilon)
    
    average_reward = total_reward / episode_length
    q_epis_r_list_without.append(average_reward)
    q_epis_l_list_without.append(episode_length)
    
    epsilon = epsilon + 0.1
    
'''this part is perform Sarsa, without epsilon to collect data
'''
    
epsilon = 0.1
s_epis_list_without = []
s_epis_r_list_without = []
s_epis_l_list_without = []
total_reward = 0.0

while epsilon <= 0.7:
    env = get_windy_grid_water(0, -100)
    agent = Sarsa(0.1, 0.99, epsilon, env.num_states, env.actions)

    while not agent.convergence:

        count = 0
        patch_lengths = 0.0
        while count < 200:
            count = count + 1
            time_step = 0
            state = env.pos_to_state(env.init_pos)
            action = agent.select_action(state)
            while not env.end_of_episode():
                
                result = env.generate(action)
                reward = result[-1]
                new_state = result[0]
                new_action = agent.select_action(new_state)
                agent.update_Q(state, action, new_state, new_action, reward)
                action = str(new_action)
                state = int(new_state)
                time_step = time_step + 1
                
            patch_lengths = patch_lengths + time_step
            
        patch_lengths = float(patch_lengths / 200)
        agent.test_convergence(patch_lengths)
        
        if not agent.convergence:
            agent.past_episode = float(patch_lengths)
        
    ##when the algorithm convergence
    if agent.convergence:
        agent.epsilon = 0
        episode = 0
        sum_time = 0.0
        while episode < 500:
            episode = episode + 1
            time_step = 0
            state = env.pos_to_state(env.init_pos)
            
            while not env.end_of_episode():
                action = agent.select_action(state)
                result = env.generate(action)
                new_state = result[0]
                total_reward = total_reward + result[1]
                state = int(new_state)
                time_step = time_step + 1
            sum_time = sum_time + time_step
        episode_length = sum_time / 500
        total_reward = total_reward / 500 
        
    s_epis_list_without.append(epsilon)
    average_reward = total_reward / episode_length
    
    s_epis_r_list_without.append(average_reward)
    s_epis_l_list_without.append(episode_length)
    
    epsilon = epsilon + 0.1

##################################################################
## Q4 Answer: graph display part: without epsilon (y is reward) ##
##################################################################

import matplotlib.pyplot as plt5
plt5.title("Q4 Graphic without epsilon")
plt5.ylabel("Average Reward")
plt5.xlabel("Epsilon")

##add the information into x-label and y-label, and show
plt5.plot(q_epis_list_without,q_epis_r_list_without, label="Q-Learning: Epsilon' function of Reward")
plt5.plot(s_epis_list_without,s_epis_r_list_without, label="Sarsa: Epsilon' function of Reward")

plt5.legend(loc='lower right')
plt5.show()



#######################################################################
## Q4 Answer: graph display part: with epsilon (y is Episode Length) ##
#######################################################################

import matplotlib.pyplot as plt6
plt6.title("Q4 Graphic with epsilon")
plt6.ylabel("Average Episode Length")
plt6.xlabel("Epsilon")

##add the information into x-label and y-label, and show
plt6.plot(q_epis_list,q_epis_l_list, label="Q-Learning: Epsilon' function of Episode Length")
plt6.plot(s_epis_list,s_epis_l_list, label="Sarsa: Epsilon' function of Episode Length")

plt6.legend(loc='lower right')
plt6.show()



##########################################################################
## Q4 Answer: graph display part: without epsilon (y is episode length) ##
##########################################################################

import matplotlib.pyplot as plt7
plt7.title("Q4 Graphic without epsilon")
plt7.ylabel("Average Episode Length")
plt7.xlabel("Epsilon")

##add the information into x-label and y-label, and show
plt7.plot(q_epis_list_without,q_epis_l_list_without, label="Q-Learning: Epsilon' function of Episode Length")
plt7.plot(s_epis_list_without,s_epis_l_list_without, label="Sarsa: Epsilon' function of Episode Length")

plt7.legend(loc='lower right')
plt7.show()





#####################
## Q5 Testing Code ##
#####################

'''this part is perform Sarsa, to get data for initi_Q value from -100 to 0.0
'''

##create lists to save information
finish_lengths = []
give_nums = []

env = get_windy_grid_water(0, -100)
agent = Sarsa(0.1, 0.99, 0.0, env.num_states, env.actions)
give_num = 0.0

while give_num >= -100:
    
    ##initial the Q values to the give_num (from -100 to 0)
    agent.initial_Q(give_num)
    
    ##collected 500 times to reduce the fluctuate of result line
    count = 0
    patch_lengths = 0.0
    while count < 500:
        count = count + 1
        time_step = 0
        state = env.pos_to_state(env.init_pos)
        action = agent.select_action(state)
        while not env.end_of_episode():             
            result = env.generate(action)
            reward = result[-1]
            new_state = result[0]
            new_action = agent.select_action(new_state)
            agent.update_Q(state, action, new_state, new_action, reward)
            action = str(new_action)
            state = int(new_state)
            time_step = time_step + 1
            
        patch_lengths = patch_lengths + time_step
        
    ##save the collected data
    finish_lengths.append(patch_lengths / 500.0)
    give_nums.append(give_num)
    give_num = give_num - 0.1


import matplotlib.pyplot as plt8

##set graphic name and x, y label's name
plt8.title("Q5 Graphic (Test Lowest Initi_Num)")
plt8.ylabel("Finish_Length")
plt8.xlabel("Give_Nums")

plt8.plot(give_nums, finish_lengths, label = 'Relation between Finish_Length and Init_Nums' )

plt8.legend(loc='lower right')
plt8.show()

















#Here is how to make the environment and agent for Q2 and Q4
#env = get_windy_grid()
#agent = Sarsa(alpha, gamma, epsilon, env.num_states, env.actions)


#Here is how to make the environment and agent for Q3, Q4, Q5
#env = get_windy_grid_water(delta, water_reward)
#agent = qlearn(alpha, gamma, epsilon, env.num_states, env.actions)  




#Here is how to plot with matplotlib
#import matplotlib.pyplot as plt
#plt.title("A test plot")
#plt.xlabel("x label")
#plt.ylabel("y label")
#plt.plot([1,2, 3, 4, 5], [1, 2, 3, 4, 5], label="line1")
#plt.plot([1, 2, 3, 4, 5], [5, 4, 3, 2, 1], label="line2")
#plt.yscale('linear')
#plt.legend(loc='upper left')
#plt.show()

#To use the graphical visualisation system, read the comments in visualisation.py
#This is totally optional and only exists to help you understand what your
#algorithms are doing

#Above your main loop
#import visualisation, time
#vis = visualisation.Visualisation(env, 800, 600, min_reward, max_reward)

#During an episode build up trace [(state, action)]
#At the ends of an episode do
#vis.show_Q(agent, show_greedy_policy, trace)
#time.sleep(0.1)

#At the end block until the visualisation is closed.
#vis.pause()


