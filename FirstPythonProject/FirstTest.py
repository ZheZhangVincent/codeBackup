'''
Created on 26/02/2014

@author: masai
'''


conn = {'City B': ('City G', 'City E'), 'City A': ('City E', 'City F')}
disease = {'City C': 0, 'City B': 2, 'City A': 1}

give_location = []
receive_location = []
temp = []
print conn

def test():
    for key in disease.keys():
        if disease[key] >= 1:
            give_location.append(key)
       
    print key

    for i in range(len(give_location)):
            receive_location.append(conn[give_location[i]])

    print give_location
    print receive_location

    for i in range(len(receive_location)):
        temp.append(receive_location[i])

    print temp