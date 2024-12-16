def calculate_area_and_perimeter(garden_map):
    rows, cols = len(garden_map), len(garden_map[0])
    visited = [[False for _ in range(cols)] for _ in range(rows)]

    # Directions for moving up, down, left, right
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    # def is_valid(r, c, plant_type):
    #     return (0 <= r < rows
    #             and 0 <= c < cols
    #             and garden_map[r][c] == plant_type
    #             and not visited[r][c])

    def dfs(r, c):
        stack = [(r, c)]
        visited[r][c] = True
        area, perimeter = 0, 0

        while stack:
            x, y = stack.pop()
            area += 1
            local_perimeter = 4  # Start with 4 sides for each cell

            for dr, dc in directions:
                nr, nc = x + dr, y + dc  # Neighbor's coordinates
                if 0 <= nr < rows and 0 <= nc < cols:
                    if garden_map[nr][nc] == garden_map[x][y]:  # Same region
                        if not visited[nr][nc]:  # If not visited, add it to stack
                            stack.append((nr, nc))
                            visited[nr][nc] = True
                        local_perimeter -= 1  # Reduce the perimeter side count
            perimeter += local_perimeter

        return area, perimeter

    results = []
    for r in range(rows):
        for c in range(cols):
            if not visited[r][c]:  # Found a new region
                area, perimeter = dfs(r, c)
                results.append((garden_map[r][c], area, perimeter))

    return results


# Example Input
garden = [
    ['A', 'A', 'A', 'A'],
    ['B', 'B', 'C', 'D'],
    ['B', 'B', 'C', 'C'],
    ['E', 'E', 'E', 'C']
]

# Calculate
regions = calculate_area_and_perimeter(garden)
res = 0
# Output Results
for plant_type, area, perimeter in regions:
    print(f"Plant: {plant_type}, Area: {area}, Perimeter: {perimeter}")
    res += area*perimeter

print(f"Result: {res}")