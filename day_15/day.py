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
            moves = item


def go(row, col, direction):
    if direction == "^": #done
        if data[row-1][col] == "#":  # wall
            return

        box_segment_start = row
        box_segment_end = row
        if data[row - 1][col] == "O":
            while (box_segment_end - 1 > 0
                   and data[box_segment_end-1][col] == "O"):
                box_segment_end -= 1
            data[box_segment_end - 1][col] = "O"
            data[box_segment_start][col] = "@"
            data[current_point[0]][current_point[1]] = "."
        elif data[row - 1][col] == ".":
             data[row - 1][col] = "@"
             data[row][col] = "."
             current_point[0] -= 1

    elif direction == "V":
        for x in range(row, len(data) - 1):
            if data[x][col] == "O":
                if data[x + 1][col] == "#":
                    return
                else:
                    data[x + 1][col] = "O"
                    data[x][col] = "@"
            elif data[x][col] == ".":
                data[x][col] = "@"
                data[x - 1][col] = "."

    elif direction == "<":
        for y in reversed(range(col)):
            if data[row][y] == "O":
                if data[row][y - 1] == "#":
                    return
                else:
                    data[row][y - 1] = "O"
                    data[row][y] = "@"
            elif data[row][y] == ".":
                data[row][y] = "@"
                data[row][y + 1] = "."

    elif direction == ">": # done
        if data[row][col + 1] == "#": # wall
            return

        if data[row][col+1] == "O":
            box_segment_start = col+1
            box_segment_end = col+1
            while box_segment_end + 1 < len(data)-1 and data[row][box_segment_end + 1] == "O":
                box_segment_end += 1
            # TODO: check for wall
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
    go(current_point[0],current_point[1], move)
    pprint.pprint(data)

if PRINT_ENABLED:
    print("\n\n")
    # pprint.pprint(data)
    print("moves")
    pprint.pprint(moves)

