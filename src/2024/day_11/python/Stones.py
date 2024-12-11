import time
from collections import defaultdict


def broke_stones(stone_counts):
    newStones = defaultdict(int)
    for stone, count in stone_counts.items():
        apply_rule(count, newStones, stone)
    print('stones:', newStones)

    return newStones


def apply_rule(count, newStones, stone):
    if stone == '0':
        newStones['1'] += count
    elif len(stone) % 2 == 0:
        mid = len(stone) // 2
        left = stone[:mid].lstrip('0') or '0'
        right = stone[mid:].lstrip('0') or '0'
        newStones[left] += count
        newStones[right] += count
        # print('new-stones:', newStones[left], newStones[right])
    else:
        new_stone = str(int(stone) * 2024)
        newStones[new_stone] += count
        # print("new-stone", new_stone)


stone_counts = defaultdict(int)

start = time.time()

with open("input.txt") as f:
    for stone in f.read().strip().split():
        stone_counts[stone] += 1
    print(stone_counts)

#
for _ in range(75):
    stone_counts = broke_stones(stone_counts)

end = time.time()
runtime = (end - start)
print(f"Execution time: {runtime} seconds")

print("stones: ", sum(stone_counts.values()))