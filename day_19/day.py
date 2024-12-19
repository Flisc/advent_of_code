import pprint

# DEFAULT DATA
PRINT_ENABLED = True
file_name = 'example.txt'
# file_name = 'input.txt'
data = []
res = 0
towels = []


def find_start_and_end():
    start, finish = None, None
    for y in range(len(data)):
        for x in range(len(data[y])):
            if data[y][x] == 'S':
                start = (x, y)
            elif data[y][x] == 'E':
                finish = (x, y)
    return start, finish


def print_matrix():
    for row in data:
        print(" ".join(map(str, row)))


with open(file_name) as f:
    for line in f.read().strip().split('\n'):
        if "," in line:
            towels = line.split(", ")
            pprint.pprint(towels)
        elif len(line) > 0:
            data.append(line.strip())

    # pprint.pprint(data)



print("res: ", res)
