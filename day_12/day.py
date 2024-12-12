from collections import defaultdict
import pprint

PRINT_ENABLED = True
file_name = 'example.txt'
# file_name = 'input.txt'

data = []
plants_dict = defaultdict(list)


def search_plots():
    # curr_plant = data[0][0]
    for i in range(len(data)):
        for j in range(len(data)):
            plants_dict[data[i][j]].append((i, j))
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

