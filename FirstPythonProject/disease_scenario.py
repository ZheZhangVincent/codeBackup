""" File name:   disease_scenario.py
    Author:      Sai Ma - u5224340
    Date:        24/02/2014
    Description: This file represents a scenario simulating the spread of an
                 infectious disease around Australia. It should be 
                 implemented for Part 1 of Exercise 4 of Assignment 0.
                 
                 See the lab notes for a description of its contents.
"""



class DiseaseScenario(object):
    '''(DiseaseScenario, str) -> bool
    Class DiseaseScenario describes the attributes read from scenario_file_name
    and defines and store relative attributes

    '''
    def __init__(self, threshold, growth, spread, location, locations=[], disease={},conn = {}):
        '''(DiseaseScenario, int, int, int, str, [str], {str:int}, {str:set()})'''
        self.threshold = threshold
        self.growth = growth
        self.spread =  spread
        self.location = location
        self.locations = list(locations)
        self.disease = dict(disease)
        self.conn = dict(conn)
        
    def read_scenario_file(self, scenario_file_name):
        '''aim to get the variables from data_file and store in the class.

        >>>DiseaseScenario.read_scenario_file('scenario1.scn')
        '''
        conn_element = []
        disease_location = []
        disease_degree = []


        data_file = open(scenario_file_name)

        ##by useing attributes 'startswith' to get the line which startswith
        ##given str.

        for line in data_file:

            if line.startswith('location'):
                new_line = line.strip()
                self.locations.append(new_line[9:])

            if line.startswith('threshold'):
                new_line = line.strip()
                self.threshold = new_line[10:]
                self.threshold = float(self.threshold)

            if line.startswith('growth'):
                new_line = line.strip()
                self.growth = new_line[7:]
                self.growth = float(self.growth)

            if line.startswith('spread'):
                new_line = line.strip()
                self.spread = new_line[7:]
                self.spread = float(self.spread)

            if line.startswith('start'):
                self.location = line[6:]

            if line.startswith('disease'):
                new_line = line.strip()
                disease_location.append(new_line[8:-3])
                disease_degree.appen(new_line[-2:])

            if line.startswith('conn'):
                new_line = line.strip('\n')
                conn_element.append(new_line[5:].split(' '))

        data_file.close()

        ##by using attribute 'setdefault' to give one key in dict get more
        ##than one values.

        m = 0

        while m < len(conn_element):
            self.conn.setdefault(conn_element[m][0], []).append(conn_element[m][1])
            m = m + 1
            
        ##this section is used to make the conn{} has the symmetrical content.

        m = 0

        while m < len(conn_element):
            self.conn.setdefault(conn_element[m][1], []).append(conn_element[m][0])
            m = m + 1

        ##set all locations has the default disease_degree = 0

        locations_tuple = tuple(self.locations)
        disease_empty = self.disease.fromkeys(locations_tuple, 0)
        disease = disease_empty

        ##update the disease_degree for the disease_location

        i = 0
        while i < len(disease_location):
            disease.update({disease_location[i]:int(disease_degree[i])})
            i = i + 1

        print self.threshold
        print self.locations
        print self.disease
        print self.conn

    def valid_move(self):
        '''(DiseaseScenario) -> [str]
        valid the move of health agent and return a actionable move location,
        and return a list of valid move. the valid move is either neighbouring
        location or the current location of agent.

        '''

        valid_moves = []

        ##get the list of valid_move result.

        valid_moves.append(self.conn[self.location])
        valid_moves.append(self.location)

        return valid_moves
    
    def  move(self, loc):
        '''(DiseaseScenario, str) -> bool
        set the disease_degree to 0 once the health-agent move to a location.

        '''
        ##select a move which can make agent to move.

        if HealthAgent.move in valid_moves:
            self.disease[move] = 0
        else:
            print 'Cannot move to this location'
        
    def spread_disease(self):
        '''(DiseaseScenario -> None
        if the disease >= s_threshold, spread the disease to the adjacent locations
        in the rate based on spread-rate and growth-rate. PS. the spread will stop
        by health-agent.
    
        '''

        ##the spreading scenario will iterate 50 times.
        ##calcuate the disease_growth and disease_spread respectively, then sum
        ##them to disease{}.

        disease_g = {}
        disease_s = {}
                ##temp = {}

        for step in range(50):

        ##every step reset the give_location and receive_location.
            
            give_location = []
            receive_location = []

            for key in self.disease.keys():
                if self.disease[key] >= self.threshold:
                    give_location.append(key)

            for i in range(len(give_location)):
                receive_location.append(self.conn[give_location[i]])

        ##calcuate the disease_growth based on the rate of growth.

            for i in range(len(self.locations)):
                disease_g = self.disease
                disease_g[self.locations_tuple[i]] =  self.disease[self.location[i]] * (1 + self.growth)

        ##calcuate the disease_spread based on the rate of spread.

            for i in range(len(give_location)):
                rate = 0
                rate = self.disease[give_location[i]] * self.spread
                for m in range(len(receive_location)):
                    disease_s = self.disease
                    for l in range(self.disease):
                        disease_s[l] = 0
                    disease_s[receive_location[i][m]] = disease_s[receive_location[i][m]] + rate

        ##sum the disease_growth and disease_spread together.

            for i in range(len(self.locations)):
                self.disease[self.locations[i]] = disease_s[self.locations[i]] + disease_g[self.locations[i]]
                

        

        
            
    

            
            


        
        
        
    
    

