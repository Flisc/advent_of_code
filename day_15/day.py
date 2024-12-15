import pprint

# DEFAULT DATA
PRINT_ENABLED = True
file_name = 'example.txt'
# file_name = 'input.txt'

data = []
moves = []
res = 0


with open(file_name) as f:
    for line in f.read().strip().split('\n'):
        item = list(line)
        if(len(item) != 0 and ">" not in item):
            data.append(item)
        else:
            moves = item


def go(i, j, direction):
    if(direction == "^"):
        print(f"\n go up from {i}, {j} ")
        if(data[i-1][j] == "#"):
            return
        else:
            #
            print(f"\n shift up col from pos {i}, {j}")
            for x in reversed(range(i)):
                if data[x][j] == "O":
                    if data[x-1][j] == "#":
                        return
                    else:
                        data[x-1][j] = "O"
                        data[x][j] = "@"
                elif data[x][j] == ".":
                    data[x][j] = "@"
                    data[x+1][j] = "."




for i in range(len(data)):
    for j in range(len(data)):
        if data[i][j] == "@":
            # for move in moves:
                go(i, j, "^")



if PRINT_ENABLED:
    print("\n\n")
    pprint.pprint(data)
    print("moves")
    pprint.pprint(moves)

