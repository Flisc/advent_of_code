import pprint

# DEFAULT DATA
PRINT_ENABLED = True
file_name = 'example.txt'
# file_name = 'input.txt'

data = []
moves = []
res = 0
current_point = [0, 0]

with open(file_name) as f:
    for line in f.read().strip().split('\n'):
        item = list(line)
        if(len(item) != 0 and ">" not in item):
            data.append(item)
        else:
            moves.extend(item)


def print_matrix():
    for row in data:
        print(" ".join(map(str, row)))

def go(row, col, direction):
    if direction == "^": #done
        if data[row-1][col] == "#":  # wall
            return

        if data[row-1][col] == "O":
            box_segment_start = row - 1
            box_segment_end = row - 1
            while (box_segment_end - 1 > 0
                   and data[box_segment_end - 1][col] == "O"):
                box_segment_end -= 1

            if data[box_segment_end - 1][col] != "#":
                data[box_segment_end - 1][col] = "O"
                data[box_segment_start][col] = "@"
                data[current_point[0]][current_point[1]] = "."
                current_point[0] -= 1
                return
        elif data[row-1][col] == ".":
            data[current_point[0]][current_point[1]] = "."
            current_point[0] -= 1
            data[current_point[0]][current_point[1]] = "@"

    elif direction == "v":
        if data[row+1][col] == "#":  # wall
            return

        if data[row+1][col] == "O":
            box_segment_start = row + 1
            box_segment_end = row + 1
            while (box_segment_end + 1 < len(data) - 1
                   and data[box_segment_end + 1][col] == "O"):
                box_segment_end += 1

            if data[box_segment_end + 1][col] != "#":
                data[box_segment_end + 1][col] = "O"
                data[box_segment_start][col] = "@"
                data[current_point[0]][current_point[1]] = "."
                current_point[0] += 1
                return
        elif data[row+1][col] == ".":
            data[current_point[0]][current_point[1]] = "."
            current_point[0] += 1
            data[current_point[0]][current_point[1]] = "@"


    elif direction == "<":
        if data[row][col - 1] == "#":  # wall
            return

        if data[row][col - 1] == "O":
            box_segment_start = col - 1
            box_segment_end = col - 1
            while (box_segment_end - 1 > 0
                   and data[row][box_segment_end - 1] == "O"):
                box_segment_end -= 1

            if data[row][box_segment_end - 1] != "#":
                data[row][box_segment_end - 1] = "O"
                data[row][box_segment_start] = "@"
                data[current_point[0]][current_point[1]] = "."
                current_point[1] -= 1
                return
        elif data[row][col - 1] == ".":
            data[current_point[0]][current_point[1]] = "."
            current_point[1] -= 1
            data[current_point[0]][current_point[1]] = "@"

    elif direction == ">": # done
        if data[row][col + 1] == "#": # wall
            return

        if data[row][col+1] == "O":
            box_segment_start = col+1
            box_segment_end = col+1
            while (box_segment_end + 1 < len(data)-1
                   and data[row][box_segment_end + 1] == "O"):
                box_segment_end += 1

            if data[row][box_segment_end + 1] != "#":
                data[row][box_segment_end+1] = "O"
                data[row][box_segment_start] = "@"
                data[current_point[0]][current_point[1]] = "."
                current_point[1] += 1
                return
        elif data[row][col+1] == ".":
             data[current_point[0]][current_point[1]] = "."
             current_point[1] += 1
             data[current_point[0]][current_point[1]] = "@"


for i in range(len(data)):
    for j in range(len(data)):
        if data[i][j] == '@':
            print(f"found at {i}, {j}")
            current_point = [i, j]

for move in moves:
    print(f"GO {move}")
    go(current_point[0], current_point[1], move)
    print_matrix()

if PRINT_ENABLED:
    print("\n\n")
    # pprint.pprint(data)
    print("moves")
    pprint.pprint(len(moves))

