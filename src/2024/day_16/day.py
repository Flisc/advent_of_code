import heapq


# DEFAULT DATA
PRINT_ENABLED = False
# file_name = 'example.txt'
file_name = 'input.txt'
data = []

def find_start_and_end():
    start, finish = None, None
    for y in range(len(data)):
        for x in range(len(data[y])):
            if data[y][x] == 'S':
                start = (x, y)
            elif data[y][x] == 'E':
                finish = (x, y)
    return start, finish

def a_star_search(matrix, start, finish):
    directions = [(1, 0), (0, 1), (-1, 0), (0, -1)]
    direction_names = ['east', 'south', 'west', 'north']
    direction_cost = 1000

    pq = []
    heapq.heappush(pq, (0, start[0], start[1], 0))

    visited = set()

    while pq:
        cost, x, y, direction_index = heapq.heappop(pq)

        if (x, y) == finish:
            return cost

        if (x, y, direction_index) in visited:
            continue

        visited.add((x, y, direction_index))

        dx, dy = directions[direction_index]
        nx, ny = x + dx, y + dy
        if matrix[ny][nx] != '#':
            heapq.heappush(pq, (cost + 1, nx, ny, direction_index))

        for rotation in [-1, 1]:
            new_direction_index = (direction_index + rotation) % 4
            heapq.heappush(pq, (cost + direction_cost, x, y, new_direction_index))

    return float('inf')

def print_matrix():
    for row in data:
        print(" ".join(map(str, row)))


with open(file_name) as f:
    for line in f.read().strip().split('\n'):
        item = list(line)
        if(len(item) != 0 and ">" not in item):
            data.append(item)

if PRINT_ENABLED:
    print_matrix()


start, end = find_start_and_end()
result = a_star_search(data, start, end)
print("score: ", result)
