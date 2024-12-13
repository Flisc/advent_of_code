import math
from collections import defaultdict
import pprint

from day_13.Models import Machine

PRINT_ENABLED = True
file_name = 'example.txt'
# file_name = 'input.txt'

data = []

def solve_ecuation(xt, yt, ax, ay, bx, by):
    """
    Button A: X+94, Y+34
    Button B: X+22, Y+67
    Prize: X=8400, Y=5400
        V
    xt (8400) = a * (X + 94) + b * (X + 22)
    xt (5400) = a * (Y + 34) + b * (Y + 67)
        V
    a = (xt - bx * b) / ax
        V
    ay * (xt - bx * b) / ax + ay * b = yt
        V
    ((ay * xt) / ax)*b - ((ay * bx) / ax)*b  + ay = yt

    """
    var_b = (ay * xt) / ax
    var2_b = (ay * bx) / ax
    combined = by - var2_b

    b = (yt - var_b) / combined
    a = (xt - bx * b) / ax

    # if PRINT_ENABLED:
    #     print("a=", int(a), "b=", int(b))

    return (a, b)

def create_machine(data):
    button_a_line = data[0]
    button_b_line = data[1]
    prize_line = data[2]

    ax = int(button_a_line.split(': ')[1].split(', ')[0][2:])
    ay = int(button_a_line.split(', ')[1][2:])

    bx = int(button_b_line.split(': ')[1].split(', ')[0][2:])
    by = int(button_b_line.split(', ')[1][2:])

    xt =  int('10000000000000'+prize_line.split(': ')[1].split(', ')[0][2:])
    yt = int('10000000000000'+prize_line.split(', ')[1][2:])

    return Machine(ax, ay, bx, by, xt, yt)

def can_be_int(number):
    if isinstance(number, int):
        return True
    elif isinstance(number, float):
        if number.is_integer():
            return True
        else:
            return False
    else:
        return False

with open(file_name) as f:
    current_data = []
    for line in f.read().strip().split('\n'):
        line = line.strip()
        if not line:
            if current_data:
                data.append(create_machine(current_data))
                current_data = []
        else:
            current_data.append(line)

    if current_data:
        data.append(create_machine(current_data))


if PRINT_ENABLED:
    pprint.pprint(data)

for m in data:
    (a, b) = solve_ecuation(m.xt, m.yt, m.buttons[0].dx, m.buttons[0].dy,
                            m.buttons[1].dx, m.buttons[1].dy)
    new_a = math.floor(a)
    new_b = math.floor(b)
    print(a, b, new_a, new_b)
    m.tokens = int(3 * new_a + new_b)
    # if can_be_int(a) and can_be_int(b):
    #     m.tokens = int(3 * a + b)
    #     print(data.index(m), a,"type: ", type(a),  b, m.tokens)


res = sum([ m.tokens for m in data if m.tokens is not ( None or 0 ) ])
print(res)
