# COMP3620/6320 Artificial Intelligence
# The Australian National University - 2014

""" Student Details
    Student Name: Sai Ma
    Student number: u5224340
    Date: 19/04/2014
"""

"""  Write your problem generator for Q7 in here. """

import sys, random

def main():
    if len(sys.argv) < 2:
        print "Error: need number of variables."
        return
    try:
        nvars = int(sys.argv[1])
    except:
        print "Error: invald number of variables."
        return
    
    """ *** Write your code here *** """
    ''' this block used to help create random var-val pair to assign '''
    ##get a count to use to make sure fill in enough squares
    count = 0
    
    ##create a list to save all the variables
    variables = []
    for count1 in range(10):
        if count1 != 0:
            for count2 in range(10):
                if count2 != 0:
                    variables.append((str(count1) + str(count2)))
    
    ##create a dict to save the domain of variables
    domain_dict = {}
    for variable in variables:
        domain_dict[variable] = ['1','2','3','4','5','6','7','8','9']
    
    ##save the assignment var-value into assignment dict
    assignment_dict = {}
    
    ##save the constraints into a constraint_list
    constraint_list = []
    
    ''' this block used to write defalut information into file '''
    
    file_name = open('randomtest.csp', 'w')
    
    file_name.write('%Sudoku\n')
    
    file_name.writelines('var 11 12 13 14 15 16 17 18 19 : 1 2 3 4 5 6 7 8 9\n')
    file_name.writelines('var 21 22 23 24 25 26 27 28 29 : 1 2 3 4 5 6 7 8 9\n')
    file_name.writelines('var 31 32 33 34 35 36 37 38 39 : 1 2 3 4 5 6 7 8 9\n')
    file_name.writelines('var 41 42 43 44 45 46 47 48 49 : 1 2 3 4 5 6 7 8 9\n')
    file_name.writelines('var 51 52 53 54 55 56 57 58 59 : 1 2 3 4 5 6 7 8 9\n')
    file_name.writelines('var 61 62 63 64 65 66 67 68 69 : 1 2 3 4 5 6 7 8 9\n')
    file_name.writelines('var 71 72 73 74 75 76 77 78 79 : 1 2 3 4 5 6 7 8 9\n')
    file_name.writelines('var 81 82 83 84 85 86 87 88 89 : 1 2 3 4 5 6 7 8 9\n')
    file_name.writelines('var 91 92 93 94 95 96 97 98 99 : 1 2 3 4 5 6 7 8 9\n')
    file_name.write('\n')
    constraint_list.append(('11', '12', '13', '14', '15', '16', '17', '18', '19'))
    file_name.writelines('alldiff 11 12 13 14 15 16 17 18 19\n')
    constraint_list.append(('21', '22', '23', '24', '25', '26', '27', '28', '29'))
    file_name.writelines('alldiff 21 22 23 24 25 26 27 28 29\n')
    constraint_list.append(('31', '32', '33', '34', '35', '36', '37', '38', '39'))
    file_name.writelines('alldiff 31 32 33 34 35 36 37 38 39\n')
    constraint_list.append(('41', '42', '43', '44', '45', '46', '47', '48', '49'))
    file_name.writelines('alldiff 41 42 43 44 45 46 47 48 49\n')
    constraint_list.append(('51', '52', '53', '54', '55', '56', '57', '58', '59'))
    file_name.writelines('alldiff 51 52 53 54 55 56 57 58 59\n')
    constraint_list.append(('61', '62', '63', '64', '65', '66', '67', '68', '69'))
    file_name.writelines('alldiff 61 62 63 64 65 66 67 68 69\n')
    constraint_list.append(('71', '72', '73', '74', '75', '76', '77', '78', '79'))
    file_name.writelines('alldiff 71 72 73 74 75 76 77 78 79\n')
    constraint_list.append(('81', '82', '83', '84', '85', '86', '87', '88', '89'))
    file_name.writelines('alldiff 81 82 83 84 85 86 87 88 89\n')
    constraint_list.append(('91', '92', '93', '94', '95', '96', '97', '98', '99'))
    file_name.writelines('alldiff 91 92 93 94 95 96 97 98 99\n')
    file_name.write('\n')
    constraint_list.append(('11', '21', '31', '41', '51', '61', '71', '81', '91'))
    file_name.writelines('alldiff 11 21 31 41 51 61 71 81 91\n')
    constraint_list.append(('12', '22', '32', '42', '52', '62', '72', '82', '92'))
    file_name.writelines('alldiff 12 22 32 42 52 62 72 82 92\n')
    constraint_list.append(('13', '23', '33', '43', '53', '63', '73', '83', '93'))
    file_name.writelines('alldiff 13 23 33 43 53 63 73 83 93\n')
    constraint_list.append(('14', '24', '34', '44', '54', '64', '74', '84', '94'))
    file_name.writelines('alldiff 14 24 34 44 54 64 74 84 94\n')
    constraint_list.append(('15', '25', '35', '45', '55', '65', '75', '85', '95'))
    file_name.writelines('alldiff 15 25 35 45 55 65 75 85 95\n')
    constraint_list.append(('16', '26', '36', '46', '56', '66', '76', '86', '96'))
    file_name.writelines('alldiff 16 26 36 46 56 66 76 86 96\n')
    constraint_list.append(('17', '27', '37', '47', '57', '67', '77', '87', '97'))
    file_name.writelines('alldiff 17 27 37 47 57 67 77 87 97\n')
    constraint_list.append(('18', '28', '38', '48', '58', '68', '78', '88', '98'))
    file_name.writelines('alldiff 18 28 38 48 58 68 78 88 98\n')
    constraint_list.append(('19', '29', '39', '49', '59', '69', '79', '89', '99'))
    file_name.writelines('alldiff 19 29 39 49 59 69 79 89 99\n')
    file_name.write('\n')
    constraint_list.append(('11', '12', '13', '21', '22', '23', '31', '32', '33'))
    file_name.writelines('alldiff 11 12 13 21 22 23 31 32 33\n')
    constraint_list.append(('41', '42', '43', '51', '52', '53', '61', '62', '63'))
    file_name.writelines('alldiff 41 42 43 51 52 53 61 62 63\n')
    constraint_list.append(('71', '72', '73', '81', '82', '83', '91', '92', '93'))
    file_name.writelines('alldiff 71 72 73 81 82 83 91 92 93\n')
    constraint_list.append(('14', '15', '16', '24', '25', '26', '34', '35', '36'))
    file_name.writelines('alldiff 14 15 16 24 25 26 34 35 36\n')
    constraint_list.append(('44', '45', '46', '54', '55', '56', '64', '65', '66'))
    file_name.writelines('alldiff 44 45 46 54 55 56 64 65 66\n')
    constraint_list.append(('74', '75', '76', '84', '85', '86', '94', '95', '96'))
    file_name.writelines('alldiff 74 75 76 84 85 86 94 95 96\n')
    constraint_list.append(('17', '18', '19', '27', '28', '29', '37', '38', '39'))
    file_name.writelines('alldiff 17 18 19 27 28 29 37 38 39\n')
    constraint_list.append(('47', '48', '49', '57', '58', '59', '67', '68', '69'))
    file_name.writelines('alldiff 47 48 49 57 58 59 67 68 69\n')
    constraint_list.append(('77', '78', '79', '87', '88', '89', '97', '98', '99'))
    file_name.writelines('alldiff 77 78 79 87 88 89 97 98 99\n')
    file_name.write('\n')
    
    ''' this block used to create random var-val pair to assign '''
    
    ##when less than k squares filled
    while count < nvars:
        
        ##get the var-val pair based on a random way
        result = addconn(count, variables, domain_dict, assignment_dict, constraint_list)
        
        ##if there is one variable which did not assign before, but without value can assign to
        if result[-1]:
            print 'This problem only fill in ',
            print count,
            print ' squares.'
            break
        else:
            var = result[0]
            val = result[1]
            domain_dict = result[2]
            assignment_dict = result[3]
            count = result[4]
            
            ##add these constraints into file
            file_name.writelines('con ' + var + ' : ' + val + '\n')
        
    file_name.close()

def addconn(count, variables, domain_dict, assignment_dict, constraint_list):
    
    '''add the conn to file and test this process of creating file is success or not
    (int, list, {str : str}, {str : str}, list) -> (str, str, int, {str : str}, {str : str}, int, bool)
    '''
    
    ##a boolean value to help us know there is conflict or not
    Boolean_value = False
    
    ##get a random var-value pair to save
    index = random.randint(0, len(variables) - 1)
    var = variables[index]
    index = random.randint(0, len(domain_dict[var]) - 1)
    val = domain_dict[var][index]
       
    ##save them into assignment and update domain_dict and variables
    assignment_dict[var] = val
    domain_dict[var] = val
    variables.remove(var)
        
    ##reduce the domain of variables which neighbour of var
    for neighbour_list in constraint_list:
        if var in neighbour_list:
            for variable in neighbour_list:
                if variable != var:
                    if val in domain_dict[variable]:
                        domain_dict[variable].remove(val)
        
    count = count + 1
    
    ##test each vairables has domains at least one value could be assigned
    for domain in domain_dict.values():
        if len(domain) == 0:
            Boolean_value = True
            break
        else:
            Boolean_value = False
    
    return (var, val, domain_dict, assignment_dict, count, Boolean_value)
    
if __name__ == "__main__":
    main()
    
