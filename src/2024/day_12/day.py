from collections import defaultdict
import pprint

from python.day_12.Models import Direction

PRINT_ENABLED = True
file_name = '../../../python/day_12/example.txt'
# file_name = 'input.txt'

data = []
plants_dict = defaultdict(list)
perimeter_dict = defaultdict(int)

def in_bounds(x, y):
    return (x >= 0 and x < len(data) and
            y >= 0 and y < len(data))

def next_in_plot(plant_val, i, j, direction: Direction):
    if PRINT_ENABLED:
        print(f'current plot: {plant_val} dir: {direction}')
    if not in_bounds(i+1, j+1):
        return




def search_plots():
    # curr_plant = data[0][0]
    for i in range(len(data)):
        for j in range(len(data)):
            plants_dict[data[i][j]].append((i, j, False))

            # if curr_plant == data[i][j]:
            #     plants_dict[curr_plant].append((i, j))
            # else:
            #     curr_plant = data[i][j]
            #     plants_dict[curr_plant].append(list(i, j))

    if PRINT_ENABLED:
        pprint.pprint(plants_dict)


with open(file_name) as f:
    for line in f.read().strip().split('\n'):
        data.append(list(line))

if PRINT_ENABLED:
    pprint.pprint(data)

search_plots()

